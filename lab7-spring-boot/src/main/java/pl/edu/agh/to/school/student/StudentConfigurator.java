package pl.edu.agh.to.school.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class StudentConfigurator {
//    @Bean
//    CommandLineRunner commandLineRunnerStudent(StudentRepository studentRepository) {
//        return args -> {
//            if (studentRepository.count() == 0) {
//                Student kowalski = new Student("Jan", "Kowalski", LocalDate.now(), "111111");
//                Student mieszko = new Student("Szymon", "Mieszko", LocalDate.now(), "222222");
//                Student krawczyk = new Student("Marek", "Krawczyk", LocalDate.now(), "333333");
//                Student szwagier = new Student("Damian", "Somsiad", LocalDate.now(), "444444");
//                Student lysy = new Student("Jakub", "Frankowski", LocalDate.now(), "555555");
//                studentRepository.save(kowalski);
//                studentRepository.save(mieszko);
//                studentRepository.save(krawczyk);
//                studentRepository.save(szwagier);
//                studentRepository.save(lysy);
//            }
//        };
//    }
}
