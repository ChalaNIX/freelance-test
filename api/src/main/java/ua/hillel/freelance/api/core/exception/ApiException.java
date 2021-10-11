package ua.hillel.freelance.api.core.exception;

public class ApiException extends RuntimeException {
    public ApiException(Integer code, String message) {

        super("Status: " + code + "; " + message);
    }


}
