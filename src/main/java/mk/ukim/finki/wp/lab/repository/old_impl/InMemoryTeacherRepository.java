package mk.ukim.finki.wp.lab.repository.old_impl;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.exceptions.InvalidTeacherIdException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InMemoryTeacherRepository {
    public List<Teacher> findAll(){
        return DataHolder.teachers;
    }
    public Teacher findById(Long id) {
        return DataHolder.teachers.stream().filter(x->x.getId().equals(id)).findFirst()
                .orElseThrow(InvalidTeacherIdException::new);
    }
}
