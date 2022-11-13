package mk.ukim.finki.wp.lab.model;

import lombok.Data;

import java.util.List;

@Data
public class Course implements Comparable<Course> {

    private Long courseId;
    private String name;
    private String description;
    private List<Student> students;
    private Teacher teacher;

//    *
    public Course(String name, String description, List<Student> students, Teacher teacher) {
        this.courseId =(long)(Math.random()*1000);
        this.name = name;
        this.description = description;
        this.students = students;
        this.teacher = teacher;
    }

    @Override
    public int compareTo(Course o) {
        return getName().toLowerCase().compareTo(o.getName().toLowerCase());
    }
}
