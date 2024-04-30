package it.petrillo.bonagatv.exception;

public class AlreadyLoggedException extends IllegalArgumentException {
    public AlreadyLoggedException() {
        super();
    }

    public AlreadyLoggedException(String message) {
        super(message);
    }

    public AlreadyLoggedException(String message, Throwable cause) {
        super(message,cause);
    }
}
