package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CourseService{
    List<Student> listStudentsByCourse(Long courseId);
    Course addStudentInCourse(String username, Long courseId);
    List<Course> listAll(); //go nema vo tekstot, go ima no vo baranje 8
    List<Student> studentsNotInCourse(Long courseId); // go nema vo tekstot, dopolnitelen feature
    Course findById(Long courseId);

    Optional<Course> saveCourse(Long courseId, String name, String description, Long id);

    void removeCourse(Long id);

    List<Course> listAllSorted();
    Map.Entry<Teacher,Integer> getBestTeacher();
    List<Character> getStudentsGrades(Long courseId);
}
