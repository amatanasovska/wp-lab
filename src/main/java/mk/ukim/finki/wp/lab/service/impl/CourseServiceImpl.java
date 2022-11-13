package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.exceptions.CourseAlreadyExistsException;
import mk.ukim.finki.wp.lab.model.exceptions.EmptyFieldsException;
import mk.ukim.finki.wp.lab.model.exceptions.InvalidCourseIdException;
import mk.ukim.finki.wp.lab.model.exceptions.InvalidStudentUsernameException;
import mk.ukim.finki.wp.lab.repository.CourseRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        if(username==null || courseId==null)
            throw new EmptyFieldsException();
        else if(username.equals("") || student==null)
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
    @Override
    public List<Student> studentsNotInCourse(Long courseId)
    {
        List<Student> studentsInCourse = listStudentsByCourse(courseId);
        List<Student> allStudents = studentService.listAll();

        return allStudents.stream().filter(x-> !studentsInCourse.contains(x)).collect(Collectors.toList());
    }

    @Override
    public Course findById(Long courseId) {
        return listAll().stream().filter(x-> x.getCourseId().equals(courseId)).findFirst().orElseThrow(InvalidCourseIdException::new);
    }

    @Override
    public Optional<Course> saveCourse(Long courseId, String name, String description, Long id) {

        if(courseRepository.findById(courseId)!=null)
            return courseRepository.save(courseId,name,description,id);
        else if(courseId==-1)
            return courseRepository.save(null,name,description,id);
        throw new CourseAlreadyExistsException(name);
    }

    @Override
    public boolean removeCourse(Long id) {
        return courseRepository.deleteCourse(id);
    }

    @Override
    public List<Course> listAllSorted() {
        return listAll().stream().sorted().collect(Collectors.toList());
    }
}
