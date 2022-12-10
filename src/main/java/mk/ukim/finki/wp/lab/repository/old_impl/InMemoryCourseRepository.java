package mk.ukim.finki.wp.lab.repository.old_impl;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.exceptions.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class InMemoryCourseRepository {
//    podobra praktika e da e vo DataHolder
//    private final List<Course> courses;
//
//    public CourseRepository() {
//        courses = new ArrayList<>();
//        for (long i = 1L; i <=5 ; i++) {
//            courses.add(new Course(i,"Course"+i,"course description "+ i, new ArrayList<>()));
//        }
//    }
    private final InMemoryTeacherRepository teacherRepository;

    public InMemoryCourseRepository(InMemoryTeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Course> findAllCourses()
    {
        return DataHolder.courses;
    }

    public Course findById(Long courseId){
        return DataHolder.courses.stream().filter(x-> Objects.equals(x.getCourseId(), courseId)).findFirst().orElse(null);
    }

    public List<Student> findAllStudentsByCourse(Long courseId) {
        return findById(courseId).getStudents();
    }

    public Course addStudentToCourse(Student student, Course course) {
        Course found = findById(course.getCourseId());
        found.getStudents().add(student);
        return found;
    }
    public Optional<Course> findByName(String name)
    {
        return DataHolder.courses.stream().filter(x->x.getName().equals(name)).findFirst();
    }
    public boolean deleteCourse(Long id)
    {
        return DataHolder.courses.removeIf(x-> x.getCourseId().equals(id));

    }
    public Optional<Course> save(Long courseId, String name, String description, Long id)
    {

        if((name.equals("") || description.equals(""))) {
            if (courseId == -1)
                throw new EmptyFieldsException();
            throw new EmptyFieldsOnExistingCourseException();
        }

        Course course=null;
        if(courseId!=null)
        {

            course = findById(courseId);
            DataHolder.courses.removeIf(x->x.getCourseId().equals(courseId));

        }
        if(findAllCourses().stream().anyMatch(x -> x.getName().equalsIgnoreCase(name)))
        {
            if(course!=null)
            {
                DataHolder.courses.add(course);
                throw new CourseAlreadyExistsException(name);

            }
            else
                throw new CourseAddingErrorNameExists(name);
        }
//        else
//        {
//            if(findAllCourses().stream().anyMatch(x -> x.getName().equalsIgnoreCase(name)))
//                throw new CourseAddingErrorNameExists(name);
//        }


        List<Student> studentsInCourse;
        if(course!=null)
            studentsInCourse = course.getStudents();
        else
        {

            studentsInCourse = new ArrayList<>();
        }

        Teacher teacher = null;
        if(id!=-1)
            teacher = teacherRepository.findById(id);

        Course added = new Course(name,description,studentsInCourse,teacher);
        DataHolder.courses.add(added);

        return Optional.of(added);

    }

}
