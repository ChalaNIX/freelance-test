package ua.hillel.freelance.api.core.exception;

public class ApiException extends RuntimeException {
    public ApiException(Integer code, String message, String reason) {

        super("Status: " + code + "; " + message + "\ncaused by: " + reason);
    }


}
