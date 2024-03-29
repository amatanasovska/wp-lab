package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.repository.jpa.GradeRepository;
import mk.ukim.finki.wp.lab.service.GradeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;

    public GradeServiceImpl(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    @Override
    public void save(Character grade, Student student, Course course, LocalDateTime timestamp) {
        gradeRepository.save(new Grade(grade,student,course,timestamp));
    }

    @Override
    public Grade findStudentGradeInCourse(Student student, Course course) {
        return gradeRepository.findGradeByStudentAndCourse(student,course);
    }

    @Override
    public List<Grade> findAll() {
        return gradeRepository.findAll();
    }

    @Override
    public void deleteAll(List<Grade> grades) {
        gradeRepository.deleteAll(grades);
    }

    @Override
    public List<Grade> findBetweenDates(String from, String to) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime from_date = null;
        LocalDateTime to_date = null;
        if(!from.equals("all"))
        {
            from = from.replace('T',' ');
            from= from.trim();
            from_date = LocalDateTime.parse(from,formatter);

        }
        else
        {
            from_date = LocalDateTime.of(1990,12,12,20,12);
        }
        if(!to.equals("all"))
        {
        to = to.replace('T',' ');

        to = to.trim();
        to_date = LocalDateTime.parse(to,formatter);
        }
        else
        {
            to_date = LocalDateTime.now();
        }
        return gradeRepository.findByTimestampGreaterThanEqualAndTimestampLessThanEqual(from_date, to_date);
    }

    @Override
    public List<Grade> findBetweenGrades(Character from, Character to) {
        return gradeRepository.findByGradeGreaterThanEqualAndGradeLessThanEqual(from,to);
    }
}
