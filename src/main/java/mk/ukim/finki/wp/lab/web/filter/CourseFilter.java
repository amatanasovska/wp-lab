package mk.ukim.finki.wp.lab.web.filter;

import org.springframework.context.annotation.Profile;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

//@Profile("servlet")
@WebFilter(filterName = "CourseFilter")
public class CourseFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
        Filter.super.init(config);
    }

    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String path = httpServletRequest.getServletPath();
        String courseId = (String) httpServletRequest.getSession().getAttribute("selectedCourse");

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        if((Objects.equals(httpServletRequest.getMethod(), "POST") && "/addStudent".equals(path)) ||
            "/listCourses".equals(path) || courseId!=null || path.contains("/courses") || path.contains("/grades") ||
                path.contains("/login"))
        {
            chain.doFilter(request, response);
        }
        else
        {
            httpServletResponse.sendRedirect("/courses");
        }

    }
}
