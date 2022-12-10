package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.repository.jpa.GradeRepository;
import mk.ukim.finki.wp.lab.service.GradeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;

    public GradeServiceImpl(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    @Override
    public void save(Character grade, Student student, Course course) {
        gradeRepository.save(new Grade(grade,student,course));
    }

    @Override
    public Character findStudentGradeInCourse(Student student, Course course) {
        return gradeRepository.findGradeByStudentAndCourse(student,course).getGrade();
    }

    @Override
    public List<Grade> findAll() {
        return gradeRepository.findAll();
    }

    @Override
    public void deleteAll(List<Grade> grades) {
        gradeRepository.deleteAll(grades);
    }
}
