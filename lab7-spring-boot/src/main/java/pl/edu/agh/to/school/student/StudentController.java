package pl.edu.agh.to.school.student;

import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAllStudents(@RequestParam(value = "indexNumber", required = false) String indexNumber) {
//        if (indexNumber != null) {
//            return List.of(studentService.getStudentByIndexNumber(indexNumber));
//        }
//        return studentService.getStudents();
        return studentService.getStudents();
    }

    @PostMapping("/{indexNumber}/courses/{courseId}/grade")
    @Transactional
    public ResponseEntity<Void> assignFinalGrade(@PathVariable String indexNumber,
                                                 @PathVariable int courseId,
                                                 @RequestBody float grade) {
        studentService.assignFinalGrade(indexNumber, courseId, grade);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{indexNumber}/averageGrade")
    public float getStudentAverageGrade(@RequestParam String indexNumber) {
        if(indexNumber != null) {
            return studentService.calculateStudentAverage(indexNumber);
        }
        return 0;
    }

}
