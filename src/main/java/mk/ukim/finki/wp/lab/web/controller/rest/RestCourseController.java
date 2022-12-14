package mk.ukim.finki.wp.lab.web.controller.rest;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Data;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.service.CourseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/courses-rest")
public class RestCourseController {
    private final CourseService courseService;

    public RestCourseController(CourseService courseService) {
        this.courseService = courseService;
    }
    @Data
    class BestTeacher
    {
        final Teacher teacher;
        final Integer count;

        public BestTeacher() {
            Map.Entry<Teacher,Integer> bestTeacher = courseService.getBestTeacher();

            teacher = bestTeacher!=null?bestTeacher.getKey():new Teacher();
            count = bestTeacher!=null?bestTeacher.getValue():-1;
        }
    }
    @GetMapping("/best-teacher")
    public BestTeacher getBestTeacher()
    {
        return new BestTeacher();
    }



}
