package mk.ukim.finki.wp.lab.bootstrap;

import lombok.Getter;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class DataHolder {
    public static List<Course> courses = new ArrayList<>();
    public static List<Student> studentList = new ArrayList<>();
    public static List<Teacher> teachers = new ArrayList<>();
//    @PostConstruct
//    public void init()
//    {
//
//        for (int i = 1; i <=5 ; i++) {
//            studentList.add(new Student("student"+i,"student",
//                    "Student name " + i,"Surname"+i));
//        }
//        for (long i = 1L; i <=5 ; i++) {
//            teachers.add(new Teacher("Teacher"+i,"Teacher Surname "+ i));
//        }
//        for (long i = 1L; i <=5 ; i++) {
//            courses.add(new Course("Course"+i,"course description "+ i, new ArrayList<>(),null));
//        }
//    }

}
