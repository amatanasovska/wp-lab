package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


public interface GradeService {
    void save(Character grade, Student student, Course course, LocalDateTime timestamp);
    Grade findStudentGradeInCourse(Student student,Course course);
    List<Grade> findAll();
    void deleteAll(List<Grade> grades);
}
