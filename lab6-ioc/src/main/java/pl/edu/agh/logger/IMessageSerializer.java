package pl.edu.agh.logger;

import java.io.Serializable;

public interface IMessageSerializer extends Serializable {

    void serializeMessage(String message);
}
