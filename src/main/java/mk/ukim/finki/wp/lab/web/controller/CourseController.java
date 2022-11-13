package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.exceptions.CourseAlreadyExistsException;
import mk.ukim.finki.wp.lab.model.exceptions.InvalidCourseIdException;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;
    private final TeacherService teacherService;
    public CourseController(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }

    @GetMapping
    public String getCoursesPage(@RequestParam(required = false) String error, Model model)
    {
        model.addAttribute("courses",courseService.listAllSorted());
        if(error!=null)
        {
            model.addAttribute("hasError",true);
            model.addAttribute("error",error);
        }
        else
        {            model.addAttribute("hasError",false);


        }
        return "listCourses";
    }
    @PostMapping("/add")
    public String saveCourse(@RequestParam(required = false) Long courseId,
                             @RequestParam String name,
                             @RequestParam String description,
                             @RequestParam Long id)
    {
        try{
            courseService.saveCourse(courseId,name,description,id);

            return "redirect:/courses";
        }
        catch (CourseAlreadyExistsException exception)
        {
            return "redirect:/edit-form/"+id+"?error="+exception.getMessage();
        }

    }
    @DeleteMapping("/delete/{id}")
    public Object deleteCourse(@PathVariable Long id)
    {
        courseService.removeCourse(id);
        RedirectView rv = new RedirectView("/courses");
        rv.setStatusCode(HttpStatus.SEE_OTHER);
//        httpServletRequest.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, 303);
        return rv;
//        return "redirect:/courses";
    }

    @GetMapping("/edit-form/{id}")
    public String getEditCoursePage(@PathVariable Long id, Model model)
    {
        try{
                Course course = courseService.findById(id);
                model.addAttribute("course", course);
            List<Teacher> teacherList = teacherService.findAll();

            model.addAttribute("teachers", teacherList);
            return "add-course.html";
        }
        catch (InvalidCourseIdException exception)
        {
            return "redirect:/courses?error=" + exception.getMessage();
        }

    }
    @GetMapping("/newCourse")
    public String addNewCourse(Model model)
    {
        model.addAttribute("teachers", teacherService.findAll());

        return "add-course.html";
    }
}
