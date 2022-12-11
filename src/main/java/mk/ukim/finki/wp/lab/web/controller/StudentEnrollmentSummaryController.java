package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.exceptions.EmptyFieldsException;
import mk.ukim.finki.wp.lab.model.exceptions.InvalidCourseIdException;
import mk.ukim.finki.wp.lab.model.exceptions.InvalidStudentUsernameException;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.GradeService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.WebContext;


import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/StudentEnrollmentSummary")
public class StudentEnrollmentSummaryController {
    private final StudentService studentService;
    private final CourseService courseService;
    private final GradeService gradeService;
    public StudentEnrollmentSummaryController(StudentService studentService, CourseService courseService, GradeService gradeService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.gradeService = gradeService;
    }


    @GetMapping
    public String getEnrollmentPage(HttpSession session, @RequestParam(required = false) String dropDownCourse, Model model)
    {
        Long courseId = null;
        if(dropDownCourse!=null)
        {
            courseId = Long.valueOf(dropDownCourse);
        }
        else{
            courseId = Long.parseLong((String) session.getAttribute("selectedCourse"));
        }

//        WebContext context = new WebContext(request, response, request.getServletContext());
        try {
            Course course = courseService.findById(courseId);
            model.addAttribute("course", course);
            model.addAttribute("courses", courseService.listAll());
            model.addAttribute("grades", courseService.getStudentsGrades(courseId));
            return "studentsInCourse";
        }
        catch(InvalidCourseIdException exception)
        {
            model.addAttribute("course", courseService.listAll().stream().findFirst().get());
            model.addAttribute("courses", courseService.listAll());
            model.addAttribute("grades", courseService.getStudentsGrades(courseId));
            model.addAttribute("error",exception.getMessage());
           return "studentsInCourse";
        }

    }

    @PostMapping
    public String enrollStudent(HttpSession session, @RequestParam(required = false) String size, @RequestParam(required = false) String grade, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime grading_date , Model model)
    {
        Long courseId = Long.parseLong((String) session.getAttribute("selectedCourse"));
        String studentUsername = size;

        try {
            if(grade==null || grade.isEmpty() || grading_date==null)
                throw new EmptyFieldsException();

            Character characterGrade = grade.charAt(0);
            Course course = null;
            model.addAttribute("courses",courseService.listAll());

            course = courseService.addStudentInCourse(studentUsername, courseId);
            model.addAttribute("course", course);
            gradeService.save(characterGrade,studentService.searchByUsername(studentUsername),course,grading_date);
            model.addAttribute("grades", courseService.getStudentsGrades(courseId));
//            request.getSession().removeAttribute("selectedCourse");
            return "studentsInCourse";
        }
        catch (InvalidStudentUsernameException | InvalidCourseIdException | EmptyFieldsException exception)
        {
            return "redirect:/addStudent?error=" + exception.getMessage();
        }
    }
}
