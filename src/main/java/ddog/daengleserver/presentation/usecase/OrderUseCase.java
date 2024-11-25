package ddog.daengleserver.presentation.usecase;

import ddog.daengleserver.presentation.dto.request.PostOrderReq;
import ddog.daengleserver.presentation.dto.response.PostOrderResp;

public interface OrderUseCase {
    PostOrderResp processOrder(Long accountId, PostOrderReq postOrderReq);
}
