package pl.edu.agh.to.school.student;

import org.springframework.stereotype.Service;
import pl.edu.agh.to.school.course.Course;
import pl.edu.agh.to.school.course.CourseRepository;
import pl.edu.agh.to.school.grade.Grade;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    private final CourseRepository courseRepository;

    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentByIndexNumber(String indexNumber) {
        return studentRepository.findByIndexNumber(indexNumber);
    }

    public void assignFinalGrade(String indexNumber, int courseId, float gradeValue) {
        Student student = getStudentByIndexNumber(indexNumber);
        Course course = courseRepository.findById(courseId);
        Grade grade = new Grade(gradeValue, course);
        student.giveGrade(grade);
    }

    public float calculateStudentAverage(String indexNumber) {
        return studentRepository.calculateAverageGrade(indexNumber);
    }
}
