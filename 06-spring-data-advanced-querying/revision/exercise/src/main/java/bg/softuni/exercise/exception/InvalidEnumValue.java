package bg.softuni.exercise.exception;

public class InvalidEnumValue extends RuntimeException {
    public InvalidEnumValue(String message) {
        super(message);
    }
}
