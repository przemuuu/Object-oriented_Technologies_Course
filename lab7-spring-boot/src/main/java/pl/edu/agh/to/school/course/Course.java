package pl.edu.agh.to.school.course;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import pl.edu.agh.to.school.student.Student;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    @ManyToMany
    private List<Student> students;

    public Course() {
    }

    public Course(String name) {
        this.name = name;
        this.students = new ArrayList<>();
    }

    public Course(String name, List<Student> students) {
        this.name = name;
        this.students = students;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public List<Student> getStudents() {
        return this.students;
    }

    public void assignStudent(Student student) {
        this.students.add(student);
    }

    public void removeStudent(Student student) {
        this.students.remove(student);
    }
}
