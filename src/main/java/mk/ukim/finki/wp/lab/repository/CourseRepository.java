package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class CourseRepository {
//    podobra praktika e da e vo DataHolder
//    private final List<Course> courses;
//
//    public CourseRepository() {
//        courses = new ArrayList<>();
//        for (long i = 1L; i <=5 ; i++) {
//            courses.add(new Course(i,"Course"+i,"course description "+ i, new ArrayList<>()));
//        }
//    }

    public List<Course> findAllCourses()
    {
        return DataHolder.courses;
    }

    public Course findById(Long courseId){
        return DataHolder.courses.stream().filter(x-> Objects.equals(x.getCourseId(), courseId)).findFirst().orElse(null);
    }

    public List<Student> findAllStudentsByCourse(Long courseId) {
        return DataHolder.courses.stream().filter(x-> Objects.equals(x.getCourseId(), courseId)).findFirst().orElse(null).getStudents();
    }

    public Course addStudentToCourse(Student student, Course course) {
        Course found = DataHolder.courses.stream().filter(x-> Objects.equals(x.getCourseId(), course.getCourseId())).findFirst().orElse(null);
//        if(found!= null && found.getStudents().stream().filter(x -> Objects.equals(x.getUsername(), student.getUsername())).findFirst().orElse(null) == null)
        found.getStudents().add(student);
        return found;
    }

}
