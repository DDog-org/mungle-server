package ddog.domain.chat.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChatType {
    TEXT_MESSAGE("기본 메시지"),
    PICTURE_MESSAGE("사진 첨부");

    private String message;
}
