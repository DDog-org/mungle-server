package ddog.daengleserver.presentation.dto.request;

import lombok.Data;

@Data
public class MessageDto {
    private String objType;
    private String text;
    private String webUrl;
    private String mobileUrl;
    private String btnTitle;

}
