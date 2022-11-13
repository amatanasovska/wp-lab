package mk.ukim.finki.wp.lab.web.servlet;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.exceptions.EmptyFieldsException;
import mk.ukim.finki.wp.lab.model.exceptions.InvalidCourseIdException;
import mk.ukim.finki.wp.lab.model.exceptions.InvalidStudentUsernameException;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(name = "StudentEnrollmentSummary", value = "/StudentEnrollmentSummary")
public class StudentEnrollmentSummary extends HttpServlet {
    private final StudentService studentService;
    private final CourseService courseService;
    private final SpringTemplateEngine springTemplateEngine;
    public StudentEnrollmentSummary(StudentService studentService, CourseService courseService, SpringTemplateEngine springTemplateEngine) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.sendRedirect("/addStudent");
        Long courseId = null;
        if(request.getParameter("dropDownCourse")!=null)
        {
            courseId = Long.valueOf(request.getParameter("dropDownCourse"));
        }
        else{
            courseId = Long.parseLong((String) request.getSession().getAttribute("selectedCourse"));
        }

        WebContext context = new WebContext(request, response, request.getServletContext());
        try {
            Course course = courseService.findById(courseId);
            context.setVariable("course", course);
            context.setVariable("courses", courseService.listAll());
            springTemplateEngine.process("studentsInCourse", context, response.getWriter());
        }
        catch(InvalidCourseIdException exception)
        {
            context.setVariable("course", courseService.listAll().stream().findFirst().get());
            context.setVariable("courses", courseService.listAll());
            context.setVariable("error",exception.getMessage());
            springTemplateEngine.process("studentsInCourse", context, response.getWriter());
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long courseId = Long.parseLong((String) request.getSession().getAttribute("selectedCourse"));
        String studentUsername = request.getParameter("size");
        try {
            Course course = null;
            WebContext context = new WebContext(request, response, request.getServletContext());
            context.setVariable("courses",courseService.listAll());

            course = courseService.addStudentInCourse(studentUsername, courseId);
            context.setVariable("course", course);

//            request.getSession().removeAttribute("selectedCourse");
            springTemplateEngine.process("studentsInCourse", context, response.getWriter());
        }
        catch (InvalidStudentUsernameException | InvalidCourseIdException | EmptyFieldsException exception)
        {
            response.sendRedirect("/addStudent?error=" + exception.getMessage());
        }


    }
}
