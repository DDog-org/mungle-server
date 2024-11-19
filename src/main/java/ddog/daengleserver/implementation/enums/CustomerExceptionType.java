package ddog.daengleserver.implementation.enums;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum CustomerExceptionType {
    CUSTOMER_NOT_EXIST(HttpStatus.NOT_FOUND, "404"),;

    private HttpStatus status;
    private String code;

}
