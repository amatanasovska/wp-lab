package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.exceptions.EmptyFieldsException;
import mk.ukim.finki.wp.lab.model.exceptions.UsernameAlreadyTakenException;
import mk.ukim.finki.wp.lab.repository.jpa.StudentRepository;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> listAll() {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> searchByNameOrSurname(String text) {
        return studentRepository.findAllByNameOrSurname(text, text);
    }

    @Override
    public Student save(String username, String password, String name, String surname) {
        if(studentRepository.findByUsername(username)!=null)
            throw new UsernameAlreadyTakenException();
        else if (username.equals("") || password.equals("") || name.equals("") || surname.equals(""))
            throw new EmptyFieldsException();

        return studentRepository.save(new Student(username, password, name, surname));
    }

    @Override
    public Student searchByUsername(String username) {
        return studentRepository.findByUsername(username);
    }


}
