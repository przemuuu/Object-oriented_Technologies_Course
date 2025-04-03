package pl.edu.agh.school.guice;

import com.google.inject.*;
import com.google.inject.name.Named;
import pl.edu.agh.logger.FileMessageSerializer;
import pl.edu.agh.logger.Logger;
import pl.edu.agh.school.persistence.IPersistenceManager;
import pl.edu.agh.school.persistence.SerializablePersistenceManager;

public class SchoolModule extends AbstractModule {
//    @Provides
//    public IPersistenceManager providePersistenceManager(SerializablePersistenceManager persistenceManager) {
//        return persistenceManager;
//    }
    @Provides
    IPersistenceManager providePersistenceManager() {
        Injector injector = Guice.createInjector(new SchoolModule());
        return injector.getInstance(SerializablePersistenceManager.class);
    }
    @Provides
    @Named("teachersStorage")
    String provideTeachersStorageFileName() {
        return "guice-teachers.dat";
    }
    @Provides
    @Named("classesStorage")
    String provideClassesStorageFileName() {
        return "guice-classes.dat";
    }

    @Provides
    @Singleton
    Logger provideLogger(){
        FileMessageSerializer fileMessageSerializer = new FileMessageSerializer("persistence.log");
        Logger logger = new Logger();
        logger.registerSerializer(fileMessageSerializer);
        return logger;
    }
}
