package bg.softuni.exercisemore.exception;

public class EntityNotFoundForIdException extends RuntimeException {

    public EntityNotFoundForIdException(String message) {
        super(message);
    }
}
