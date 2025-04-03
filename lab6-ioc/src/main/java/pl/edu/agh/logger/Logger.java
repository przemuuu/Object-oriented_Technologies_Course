package pl.edu.agh.logger;

import com.google.inject.Singleton;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Singleton
public class Logger implements Serializable {
    protected DateFormat dateFormat;

    protected Set<IMessageSerializer> registeredSerializers;

    public Logger() {
        init();
        this.registeredSerializers = new HashSet<IMessageSerializer>();
    }

    public Logger(Set<IMessageSerializer> registeredSerializers) {
        init();
        if (registeredSerializers == null) {
            throw new IllegalArgumentException("null argument");
        }
        this.registeredSerializers = registeredSerializers;
    }

    public void registerSerializer(IMessageSerializer messageSerializer) {
        registeredSerializers.add(messageSerializer);
    }

    public void log(String message) {
        log(message, null);
    }

    public void log(String message, Throwable error) {
        for (IMessageSerializer messageSerializer : registeredSerializers) {
            String formattedMessage = dateFormat.format(new Date())
                    + ": " + message + (error != null ? error.toString() : "");
            messageSerializer.serializeMessage(formattedMessage);
        }
    }

    private void init() {
        dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    }

}
