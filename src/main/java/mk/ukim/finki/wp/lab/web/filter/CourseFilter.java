package mk.ukim.finki.wp.lab.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

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
        if((Objects.equals(httpServletRequest.getMethod(), "POST") && "/addStudent".equals(path)) ||
            "/listCourses".equals(path) || courseId!=null)
        {
            chain.doFilter(request, response);
        }
        else
        {
            httpServletResponse.sendRedirect("/listCourses");
        }

    }
}
