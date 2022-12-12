package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.enumerations.Type;
import mk.ukim.finki.wp.lab.model.exceptions.*;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.apache.xpath.operations.Mod;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumSet;
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
        if(courseService.getBestTeacher()!=null) {
            model.addAttribute("bestTeacher", courseService.getBestTeacher().getKey().getFullName().getName());
            model.addAttribute("bestTeacherClasses", courseService.getBestTeacher().getValue());
        }
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
                             @RequestParam Long id,@RequestParam(required = false) String error, @RequestParam String course_type, Model model)
    {

        try{
            courseService.saveCourse(courseId,name,description,id,course_type);
            System.out.println("Saved");
            return "redirect:/courses";
        }
        catch (CourseAlreadyExistsException | EmptyFieldsOnExistingCourseException exception)
        {
            return "redirect:/courses/edit-form/"+courseId+"?error="+exception.getMessage();
        }
        catch (CourseAddingErrorNameExists | EmptyFieldsException | CourseTypeNotFound exception)
        {
            return "redirect:/courses/add-form?error=" + exception.getMessage();
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
    public String getEditCoursePage(@PathVariable Long id,@RequestParam(required = false) String error, Model model)
    {
        if(error!=null)
        {
            model.addAttribute("hasError",true);
            model.addAttribute("error",error);
        }
        else
        {
            model.addAttribute("hasError",false);
        }
        try{
                Course course = courseService.findById(id);
                model.addAttribute("course", course);
            List<Teacher> teacherList = teacherService.findAll();

            model.addAttribute("teachers", teacherList);
            model.addAttribute("types",Type.class.getEnumConstants());
            return "add-course.html";
        }
        catch (InvalidCourseIdException exception)
        {
            return "redirect:/courses?error=" + exception.getMessage();
        }

    }
    @GetMapping("/add-form")
    public String addNewCourse(Model model,@RequestParam(required = false) String error)
    {
        if(error!=null)
        {
            model.addAttribute("hasError",true);
            model.addAttribute("error",error);
        }
        else
        {
            model.addAttribute("hasError",false);
        }
        model.addAttribute("teachers", teacherService.findAll());
        model.addAttribute("types", Type.class.getEnumConstants());

        return "add-course.html";
    }
    @GetMapping("/add-teacher-form")
    public String addNewTeacher(){
        return "add-teacher.html";
    }

    @PostMapping("/add-teacher")
    public String saveAddedTeacher(@RequestParam String name,@RequestParam String surname,@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate employment_date){

        Teacher teacher = new Teacher(name,surname,employment_date);
        teacherService.saveTeacher(teacher);
        return "redirect:/courses";
    }


}
