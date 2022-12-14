package mk.ukim.finki.wp.lab.web.controller.rest;

import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.service.GradeService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/grades")
public class RestGradeController {
    private final GradeService gradeService;

    public RestGradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }
    @GetMapping
    public List<Grade> getAll(){
        return gradeService.findAll();
    }
    @GetMapping(path = "/range", produces = "application/json")
    public List<Grade> getBook(@RequestParam String from,
                               @RequestParam String to) {
        return gradeService.findBetweenDates(from, to);
    }
}
