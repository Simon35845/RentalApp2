package itacademy.rentalapp2.exceptions;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final String errorCode;

    public CustomException(String errorCode) {
        super();
        this.errorCode = errorCode;
    }
}
