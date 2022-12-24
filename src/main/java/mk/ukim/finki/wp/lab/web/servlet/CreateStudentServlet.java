package mk.ukim.finki.wp.lab.web.servlet;

import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CreateStudentServlet", value = "/createStudent")
public class CreateStudentServlet extends HttpServlet {
    private final StudentService studentService;
    private final SpringTemplateEngine springTemplateEngine;
    public CreateStudentServlet(StudentService studentService, SpringTemplateEngine springTemplateEngine) {
        this.studentService = studentService;
        this.springTemplateEngine = springTemplateEngine;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebContext context = new WebContext(request, response,request.getServletContext());
//        context.setVariable("error",false);
        response.setContentType("application/xhtml+xml");

        springTemplateEngine.process("newStudent",context,response.getWriter());
    }
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        WebContext context = new WebContext(request, response,request.getServletContext());
        response.setContentType("application/xhtml+xml");

        try{
            studentService.save(username, password, name, surname);
            response.sendRedirect("/addStudent");
        }
        catch( Exception ex)
        {
            context.setVariable("error",true);
            context.setVariable("error_message", ex.getMessage());
            springTemplateEngine.process("newStudent", context, response.getWriter());

        }

    }

}
