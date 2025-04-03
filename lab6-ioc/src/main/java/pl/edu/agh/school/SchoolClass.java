package pl.edu.agh.school;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.inject.Inject;
import pl.edu.agh.logger.Logger;

public class SchoolClass implements Serializable {

	private static final long serialVersionUID = -1458264557391305041L;

	private String name;
	private String profile;

	private final List<Student> students = new ArrayList<>();
	private final List<Subject> subjects = new ArrayList<>();

	private Logger log;

	@Inject
	public SchoolClass(String name, String profile, Logger log) {
		this.name = name;
		this.profile = profile;
		this.log = log;
	}

	public String getName() {
		return name;
	}

	public String getProfile() {
		return profile;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	@Override
	public String toString() {
		return "class " + name + ", profile " + profile;
	}

	public void addSubject(Subject subject) {
		if (!subjects.contains(subject)) {
			subjects.add(subject);
			log.log(
					"Added " + subject.toString() + " to " + this.toString());
		}
	}

	public Collection<Subject> getSubjects() {
		return subjects;
	}

	public void addStudent(Student student) {
		if (!students.contains(student)) {
			students.add(student);
			student.setSchoolClass(this);
			log.log(
					"Added " + student.toString() + " to class "
							+ this.toString());
		}
	}

	public Collection<Student> getStudents() {
		return students;
	}

	public Collection<Term> getSchedule() {
		Collection<Term> terms = new ArrayList<Term>();
		for (Subject subject : subjects) {
			terms.addAll(subject.getSchedule());
		}
		return terms;
	}

	public boolean meetSearchCriteria(String name, String profile) {
		return this.name.equals(name) && this.profile.equals(profile);
	}
}
