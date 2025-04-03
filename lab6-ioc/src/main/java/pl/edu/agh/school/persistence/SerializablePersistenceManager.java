package pl.edu.agh.school.persistence;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import pl.edu.agh.logger.Logger;
import pl.edu.agh.school.SchoolClass;
import pl.edu.agh.school.Teacher;

public final class SerializablePersistenceManager implements IPersistenceManager {

    private Logger log;

    private String teachersStorageFileName;

    private String classStorageFileName;

    @Inject
    public SerializablePersistenceManager(Logger log) {
        teachersStorageFileName = "teachers.dat";
        classStorageFileName = "classes.dat";
        this.log = log;
    }
    @Inject
    public void setTeachersStorageFileName(@Named("teachersStorage") String teachersStorageFileName) {
        this.teachersStorageFileName = teachersStorageFileName;
    }
    @Inject
    public void setClassStorageFileName(@Named("classesStorage") String classStorageFileName) {
        this.classStorageFileName = classStorageFileName;
    }
    @Override
    public void saveTeachers(List<Teacher> teachers) {
        if (teachers == null) {
            log.log("Teachers list is null");
            throw new IllegalArgumentException();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(teachersStorageFileName))) {
            oos.writeObject(teachers);
            log.log("Saved teachers data to " + teachersStorageFileName);
        } catch (FileNotFoundException e) {
            log.log("File named " + teachersStorageFileName + " not found", e);
            throw new IllegalArgumentException(e);
        } catch (IOException e) {
            log.log("There was an error while saving the teachers data", e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Teacher> loadTeachers() {
        ArrayList<Teacher> res = null;
        log.log("Loading teachers data from " + teachersStorageFileName);
        try (ObjectInputStream ios = new ObjectInputStream(new FileInputStream(teachersStorageFileName))) {
            res = (ArrayList<Teacher>) ios.readObject();
            log.log("Loaded " + res.size() + " teachers from " + teachersStorageFileName);
        } catch (FileNotFoundException e) {
            log.log("File named " + teachersStorageFileName + " not found. Returning empty list", e);
            res = new ArrayList<>();
        } catch (IOException e) {
            log.log("There was an error while loading the teachers data", e);
        } catch (ClassNotFoundException e) {
            log.log("There was an error while loading the teachers data", e);
            throw new IllegalArgumentException(e);
        }
        return res;
    }

    @Override
    public void saveClasses(List<SchoolClass> classes) {
        if (classes == null) {
            log.log("Classes list is null");
            throw new IllegalArgumentException();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(classStorageFileName))) {
            oos.writeObject(classes);
            log.log("Saved classes data to " + classStorageFileName);
        } catch (FileNotFoundException e) {
            log.log("File named " + classStorageFileName + " not found", e);
            throw new IllegalArgumentException(e);
        } catch (IOException e) {
            log.log("There was an error while saving the classes data", e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SchoolClass> loadClasses() {
        ArrayList<SchoolClass> res = null;
        log.log("Loading classes data from " + classStorageFileName);
        try (ObjectInputStream ios = new ObjectInputStream(new FileInputStream(classStorageFileName))) {
            res = (ArrayList<SchoolClass>) ios.readObject();
            log.log("Loaded " + res.size() + " classes from " + classStorageFileName);
        } catch (FileNotFoundException e) {
            log.log("File named " + classStorageFileName + " not found. Returning empty list", e);
            res = new ArrayList<>();
        } catch (IOException e) {
            log.log("There was an error while loading the classes data", e);
        } catch (ClassNotFoundException e) {
            log.log("There was an error while loading the classes data", e);
            throw new IllegalArgumentException(e);
        }
        return res;
    }
}
