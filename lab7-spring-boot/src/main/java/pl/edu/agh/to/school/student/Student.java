package pl.edu.agh.to.school.student;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import pl.edu.agh.to.school.course.Course;
import pl.edu.agh.to.school.grade.Grade;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student {
    @Id
    @GeneratedValue
    private int id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String indexNumber;

    @OneToMany
    private List<Grade> grades = new ArrayList<>();

    public Student() {
    }

    public Student(String firstName, String lastName, LocalDate birthDate, String indexNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.indexNumber = indexNumber;
    }

    public int getId() {
        return this.id;
    }
    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    public String getIndexNumber() {
        return this.indexNumber;
    }

    public void giveGrade(Grade grade) {
        grades.add(grade);
    }

    public List<Grade> getGrades() {
        return this.grades;
    }
}
