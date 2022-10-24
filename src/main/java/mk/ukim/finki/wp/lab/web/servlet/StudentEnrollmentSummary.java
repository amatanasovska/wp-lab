package mk.ukim.finki.wp.lab.web.servlet;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

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

     }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long courseId = Long.parseLong((String) request.getSession().getAttribute("selectedCourse"));
        String studentUsername = request.getParameter("size");
        Course course = null;
        WebContext context = new WebContext(request,response,request.getServletContext());

        course = courseService.addStudentInCourse(studentUsername,courseId);
        context.setVariable("course",course);

        request.getSession().invalidate();
        springTemplateEngine.process("studentsInCourse",context,response.getWriter());


    }
}
