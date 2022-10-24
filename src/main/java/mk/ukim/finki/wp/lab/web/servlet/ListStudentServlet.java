package mk.ukim.finki.wp.lab.web.servlet;

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
    private final SpringTemplateEngine springTemplateEngine;
    public ListStudentServlet(StudentService studentService, SpringTemplateEngine springTemplateEngine) {
        this.studentService = studentService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        WebContext context = new WebContext(request,response, request.getServletContext());
        context.setVariable("students", studentService.listAll());
        context.setVariable("selectedCourse", request.getSession().getAttribute("selectedCourse"));
        springTemplateEngine.process("listStudents.html",context,response.getWriter());

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("selectedCourse",request.getParameter("courseId"));

//        WebContext context = new WebContext(request,response, request.getServletContext());
//        context.setVariable("students", studentService.listAll());
//        context.setVariable("selectedCourse", request.getSession().getAttribute("selectedCourse"));
//        springTemplateEngine.process("listStudents.html",context,response.getWriter());

        response.sendRedirect("/addStudent");
    }
}
