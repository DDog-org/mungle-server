package ddog.mungleserver.infrastructure.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto<T> {
    private ResponseStatus status;
    private String message;
    private T data;
}
