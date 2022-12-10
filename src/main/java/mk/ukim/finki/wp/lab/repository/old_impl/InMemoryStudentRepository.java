package mk.ukim.finki.wp.lab.repository.old_impl;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class InMemoryStudentRepository {
//    private final List<Student> studentList;
//
//    public StudentRepository() {
//        studentList = new ArrayList<>();
//        for (int i = 1; i <=5 ; i++) {
//            studentList.add(new Student("student"+i,"student",
//                    "Student name " + i,"Surname"+i));
//        }
//    }

    public List<Student> findAllStudents()
    {
        return DataHolder.studentList;
    }

    public List<Student> findAllByNameOrSurname(String text)
    {
        return DataHolder.studentList.stream().filter(x->x.getName().contains(text) || x.getSurname().contains(text))
                .collect(Collectors.toList());
    }
//    go nema vo tekst
    public Student findByUsername(String username)
    {
        return DataHolder.studentList.stream().filter(x -> x.getUsername().equals(username)).findFirst().orElse(null);
    }
//    go nema vo tekst
    public Student addNewStudent(Student student)
    {
        DataHolder.studentList.add(student);
        return student;
    }



}
