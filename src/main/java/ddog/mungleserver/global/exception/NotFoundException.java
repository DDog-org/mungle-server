package ddog.mungleserver.global.exception;

public class NotFoundException extends CustomRuntimeException {
    public NotFoundException(String message, Object... args) {
        super(message, args);
    }
}
