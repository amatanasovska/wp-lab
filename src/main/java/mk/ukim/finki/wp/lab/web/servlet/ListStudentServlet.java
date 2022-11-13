package mk.ukim.finki.wp.lab.web.servlet;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.exceptions.InvalidCourseIdException;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ListStudentServlet", value = "/addStudent")
public class ListStudentServlet extends HttpServlet {
    private final StudentService studentService;
    private final CourseService courseService;
    private final SpringTemplateEngine springTemplateEngine;
    public ListStudentServlet(StudentService studentService, CourseService courseService, SpringTemplateEngine springTemplateEngine) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Long courseId = Long.parseLong((String) request.getSession().getAttribute("selectedCourse"));
        try
        {
            Course course = courseService.findById(courseId);
            WebContext context = new WebContext(request,response, request.getServletContext());
            context.setVariable("students", courseService.studentsNotInCourse(courseId));
            context.setVariable("selectedCourse", courseId);
            String req_error = request.getParameter("error");
            if(req_error!=null)
            {
                context.setVariable("hasError", true);
                context.setVariable("error",req_error);
            }
            else {
                context.setVariable("hasError", false);
            }
            springTemplateEngine.process("listStudents.html",context,response.getWriter());
        }
        catch(InvalidCourseIdException exception)
        {
            response.sendRedirect("/listCourses?error=" + exception.getMessage());
        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("selectedCourse",request.getParameter("courseId"));

        response.sendRedirect("/addStudent");
    }
}
