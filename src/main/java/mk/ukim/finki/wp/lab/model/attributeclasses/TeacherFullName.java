package mk.ukim.finki.wp.lab.model.attributeclasses;


import lombok.Data;

import java.io.Serializable;

@Data
public class TeacherFullName implements Serializable {
    private String name;
    private  String surname;

    public TeacherFullName() {
    }

    public TeacherFullName(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
}
