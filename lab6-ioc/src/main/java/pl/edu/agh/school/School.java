package pl.edu.agh.school;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import pl.edu.agh.school.guice.SchoolModule;

import java.util.ArrayList;
import java.util.Collection;

public class School {
    private SchoolDAO schoolDAO;
    private Injector injector = Guice.createInjector(new SchoolModule());
    @Inject
    public School() {
        this.schoolDAO = injector.getInstance(SchoolDAO.class);
    }
    public void addTeacher(Teacher teacher) {
        schoolDAO.addTeacher(teacher);
    }

    public Collection<Person> findPerson(String name, String surname) {
        Collection<Person> foundPersons = findPerson(name, surname,
                PersonType.Student);
        foundPersons.addAll(findPerson(name, surname, PersonType.Teacher));
        return foundPersons;
    }

    public Collection<Person> findPerson(String name, String surname,
                                         PersonType personType) {
        ArrayList<Person> foundTeachers = new ArrayList<>();
        if (personType == PersonType.Teacher) {
            for (Teacher teacher : schoolDAO.getTeachers()) {
                if (teacher.meetsSearchCriteria(name, surname)) {
                    foundTeachers.add(teacher);
                }
            }
        } else if (personType == PersonType.Student) {
            for (SchoolClass schoolClass : schoolDAO.getClasses()) {
                for (Student student : schoolClass.getStudents()) {
                    if (student.meetsSearchCriteria(name, surname)) {
                        foundTeachers.add(student);
                    }
                }
            }
        }
        return foundTeachers;
    }

    public void addClass(SchoolClass newClass) {
       schoolDAO.addClass(newClass);
    }

    public Collection<SchoolClass> findClass(String name, String profile) {
        ArrayList<SchoolClass> foundClasses = new ArrayList<>();
        for (SchoolClass schoolClass : schoolDAO.getClasses()) {
            if (schoolClass.meetSearchCriteria(name, profile)) {
                foundClasses.add(schoolClass);
            }
        }
        return foundClasses;
    }

    public int getTermCount(Person person) {
        return person.getSchedule().size();
    }
}
