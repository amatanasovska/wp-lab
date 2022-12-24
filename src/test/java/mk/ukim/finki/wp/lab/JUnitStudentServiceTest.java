package mk.ukim.finki.wp.lab;

import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.exceptions.EmptyFieldsException;
import mk.ukim.finki.wp.lab.model.exceptions.UsernameAlreadyTakenException;
import mk.ukim.finki.wp.lab.repository.jpa.StudentRepository;
import mk.ukim.finki.wp.lab.service.StudentService;
import mk.ukim.finki.wp.lab.service.impl.StudentServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class JUnitStudentServiceTest {
    @Mock
    private StudentRepository studentRepository;

    private StudentService studentService;
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        Student user = new Student("username", "password", "name", "surname");
        Mockito.when(this.studentRepository.save(Mockito.any(Student.class))).thenReturn(user);


        this.studentService = Mockito.spy(new StudentServiceImpl(this.studentRepository));
    }
    @Test
    public void testAlreadyTakenUsername()
    {
        Student student = new Student("username", "password", "name", "surname");
        Mockito.when(this.studentRepository.findByUsername(Mockito.anyString())).thenReturn(student);

        Assert.assertThrows("Username is not taken.",
                UsernameAlreadyTakenException.class,
                () -> this.studentService.save(student.getUsername(),student.getPassword(),student.getName(),student.getSurname()));
        Mockito.verify(this.studentService, Mockito.times(1)).save(student.getUsername(),student.getPassword(),student.getName(),student.getSurname());

    }
    @Test
    public void testEmptyFields()
    {
        Assert.assertThrows("All the fields are mandatory.",
                EmptyFieldsException.class,
                () -> this.studentService.save("", "password", "name", "surname"));
        Mockito.verify(this.studentService).save("", "password", "name", "surname");
        Assert.assertThrows("All the fields are mandatory.",
                EmptyFieldsException.class,
                () -> this.studentService.save("username", "", "name", "surname"));
        Mockito.verify(this.studentService).save("username", "", "name", "surname");
        Assert.assertThrows("All the fields are mandatory.",
                EmptyFieldsException.class,
                () -> this.studentService.save("username", "password", "", "surname"));
        Mockito.verify(this.studentService).save("username", "password", "", "surname");
        Assert.assertThrows("All the fields are mandatory.",
                EmptyFieldsException.class,
                () -> this.studentService.save("username", "password", "name", ""));
        Mockito.verify(this.studentService).save("username", "password", "name", "");


    }
    @Test
    public void testSuccessfulSave()
    {

        Student student = this.studentService.save("username", "password", "name", "surname");
        Mockito.verify(this.studentService).save("username", "password", "name", "surname");

        Assert.assertNotNull("User is null", student);
        Assert.assertEquals("Names do not match", "name", student.getName());
        Assert.assertEquals("Surnames do not match", "surname", student.getSurname());
        Assert.assertEquals("Password do not match", "password", student.getPassword());
        Assert.assertEquals("Usernames do not match", "username", student.getUsername());


    }
}
