package pl.edu.agh.iisg.to.repository;

import pl.edu.agh.iisg.to.dao.CourseDao;
import pl.edu.agh.iisg.to.model.Course;
import pl.edu.agh.iisg.to.model.Student;
import pl.edu.agh.iisg.to.dao.StudentDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StudentRepository implements Repository<Student>{
    private final StudentDao studentDao;
    private final CourseDao courseDao;
    public StudentRepository(StudentDao studentDao, CourseDao courseDao) {
        this.studentDao = studentDao;
        this.courseDao = courseDao;
    }
    @Override
    public Optional<Student> add(Student student) {
        return studentDao.save(student);
    }
    @Override
    public Optional<Student> getById(int id) {
        return studentDao.findByIndexNumber(id);
    }

    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    public void remove(Student student) {
        student.courseSet().forEach(course -> course.studentSet().remove(student));
        studentDao.remove(student);
    }
    public List<Student> findAllByCourseName(String courseName) {
        Optional<Course> courseOpt = courseDao.findByName(courseName);
        return courseOpt.map(course -> new ArrayList<>(course.studentSet()))
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
    }

    public void removeByIndexNumber(int indexNumber) {
        studentDao.findByIndexNumber(indexNumber).ifPresent(student -> {
            student.courseSet().forEach(course -> course.studentSet().remove(student));
            studentDao.remove(student);
        });
    }
}
