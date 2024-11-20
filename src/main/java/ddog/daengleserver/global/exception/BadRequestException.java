package ddog.daengleserver.global.exception;

public class BadRequestException extends CustomRuntimeException {
    public BadRequestException(String message, Object... args) {
        super(message, args);
    }
}
