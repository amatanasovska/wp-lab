package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.exceptions.InvalidCourseIdException;
import mk.ukim.finki.wp.lab.model.exceptions.InvalidStudentUsernameException;
import mk.ukim.finki.wp.lab.model.exceptions.StudentAlreadyInCourseException;
import mk.ukim.finki.wp.lab.repository.CourseRepository;
import mk.ukim.finki.wp.lab.repository.StudentRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final StudentService studentService;
    public CourseServiceImpl(CourseRepository courseRepository, StudentService studentService) {
        this.courseRepository = courseRepository;
        this.studentService = studentService;
    }

    @Override
    public List<Student> listStudentsByCourse(Long courseId) {
        return courseRepository.findAllStudentsByCourse(courseId);
    }

    @Override
    public Course addStudentInCourse(String username, Long courseId) {
        Course course =courseRepository.findById(courseId);
        Student student = studentService.searchByUsername(username);
        if(username.equals("") || student==null)
            throw new InvalidStudentUsernameException();
        else if(course==null)
            throw new InvalidCourseIdException();
        else if(course.getStudents().stream()
                .filter(x->x.getUsername().equals(username)).findFirst()
                .orElse(null)==null)
            return courseRepository.addStudentToCourse(student,course);

        return course;
    }

    @Override
    public List<Course> listAll() {
        return courseRepository.findAllCourses();
    }
}
