package me.jaeuk.hr_module.common.exception;

public class OverTimeValidateException extends RuntimeException{
    public OverTimeValidateException() {
        super();
    }

    public OverTimeValidateException(String message) {
        super(message);
    }

    public OverTimeValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public OverTimeValidateException(Throwable cause) {
        super(cause);
    }
}
