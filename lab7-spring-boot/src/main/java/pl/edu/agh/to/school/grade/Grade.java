package pl.edu.agh.to.school.grade;

import jakarta.persistence.*;
import pl.edu.agh.to.school.course.Course;

@Entity
public class Grade {
    @Id
    @GeneratedValue
    private int id;
    private float gradeValue;
    @ManyToOne
    private Course course;

    public Grade() {
    }

    public Grade(float gradeValue, Course course) {
        this.gradeValue = gradeValue;
        this.course = course;
    }

    public int getId() {
        return this.id;
    }

    public float getGradeValue() {
        return this.gradeValue;
    }

    public Course getCourse() {
        return this.course;
    }
}
