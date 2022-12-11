package mk.ukim.finki.wp.lab.model;

import lombok.Data;
import mk.ukim.finki.wp.lab.model.attributeclasses.TeacherFullName;
import mk.ukim.finki.wp.lab.model.converters.TeacherFullNameConverter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Convert(converter = TeacherFullNameConverter.class)
    private TeacherFullName fullName;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfEmployment;

    public Teacher(String name, String surname,LocalDate dateOfEmployment) {
        this.fullName=new TeacherFullName(name, surname);

        this.dateOfEmployment = dateOfEmployment;
    }

    public Teacher() {

    }
}
