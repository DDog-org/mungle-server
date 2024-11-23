package ddog.daengleserver.presentation.dto.request;

import lombok.Getter;

@Getter
public class PostOrderReq {
    Long userId;
    Long price;
    Long itemId;
}
