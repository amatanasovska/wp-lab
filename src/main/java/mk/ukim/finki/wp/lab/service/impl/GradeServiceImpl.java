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
        from = from.replace('T',' ');
        to = to.replace('T',' ');
        from= from.trim();
        to = to.trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime from_date = LocalDateTime.parse(from,formatter);

        LocalDateTime to_date = LocalDateTime.parse(to,formatter);
        return gradeRepository.findByTimestampGreaterThanEqualAndTimestampLessThanEqual(from_date, to_date);
    }
}
