package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.enumerations.Type;
import mk.ukim.finki.wp.lab.model.exceptions.CourseTypeNotFound;
import mk.ukim.finki.wp.lab.model.exceptions.EmptyFieldsException;
import mk.ukim.finki.wp.lab.model.exceptions.InvalidCourseIdException;
import mk.ukim.finki.wp.lab.model.exceptions.InvalidStudentUsernameException;
import mk.ukim.finki.wp.lab.repository.jpa.CourseRepository;
import mk.ukim.finki.wp.lab.repository.jpa.TeacherRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.GradeService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final GradeService gradeService;
    private final StudentService studentService;
    public CourseServiceImpl(CourseRepository courseRepository, TeacherRepository teacherRepository, GradeService gradeService, StudentService studentService) {
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.gradeService = gradeService;
        this.studentService = studentService;
    }

    @Override
    public List<Student> listStudentsByCourse(Long courseId) {
        return courseRepository.findById(courseId).get().getStudents();
    }

    @Override
    public Course addStudentInCourse(String username, Long courseId) {
        Course course =courseRepository.findById(courseId).orElse(null);
        Student student = studentService.searchByUsername(username);
        if(username == null)
            throw new EmptyFieldsException();
        else if(username.equals("") || student==null)
            throw new InvalidStudentUsernameException();
        else if(course==null)
            throw new InvalidCourseIdException();
        else if(course.getStudents().stream()
                .filter(x->x.getUsername().equals(username)).findFirst()
                .orElse(null)==null) {
            List<Student> students = course.getStudents();
            students.add(student);
            course.setStudents(students);
            return courseRepository.save(course);
        }
        return course;
    }

    @Override
    public List<Course> listAll() {
        return courseRepository.findAll();
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
    public Optional<Course> saveCourse(Long courseId, String name, String description, Long id, String courseType) {

        Course course = courseRepository.findById(courseId).orElse(null);
        Teacher teacher = teacherRepository.findById(id).orElse(null);

        Type course_type = null;

        try
        {
            course_type = Type.valueOf(courseType);
        }
        catch (IllegalArgumentException exception)
        {
            throw new CourseTypeNotFound(courseType);
        }

        if(course!=null)
        {
            course.setName(name);
            course.setDescription(description);
            course.setTeacher(teacher);
            course.setType(course_type);
        }
        else
        {
            course = new Course(name,description,new ArrayList<>(),teacher,course_type);
        }
        return Optional.of(courseRepository.save(course));

//        if(courseRepository.findById(courseId).isPresent())
//            return courseRepository.save(courseId,name,description,id);
//        else if(courseId==-1)
//            return courseRepository.save((long) -1,name,description,id);
//        throw new InvalidCourseIdException();
    }

    @Transactional
    @Override
    public void removeCourse(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(InvalidCourseIdException::new);
        List<Grade> grades = gradeService.findAll();
        gradeService.deleteAll(grades.stream().filter(x->x.getCourse().equals(course)).collect(Collectors.toList()));

//        course.getStudents().removeAll(course.getStudents());
//        courseRepository.save(course);
        courseRepository.delete(course);
    }

    @Transactional
    @Override
    public List<Course> listAllSorted() {
        return listAll().stream().sorted().collect(Collectors.toList());
    }



    @Override
    public Map.Entry<Teacher,Integer> getBestTeacher() {
        Map<Teacher,Integer> teacherIntegerMap = new HashMap<>();

        for(Course course:courseRepository.findAll())
        {
            if(course.getTeacher()!=null)
            {
                Integer t = teacherIntegerMap.getOrDefault(course.getTeacher(),0);
                t+=1;
                teacherIntegerMap.put(course.getTeacher(),t);
            }

        }
        List<Map.Entry<Teacher,Integer>> sorted = teacherIntegerMap.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).collect(Collectors.toList());
        if(sorted.size()>0)
            return sorted.get(0);
        return null;
    }

    @Override
    public List<Grade> getStudentsGrades(Long courseId) {
        Course course = findById(courseId);
        List<Student> students = course.getStudents();
        return students.stream().map(x->gradeService.findStudentGradeInCourse(x,course)).collect(Collectors.toList());
    }
}
