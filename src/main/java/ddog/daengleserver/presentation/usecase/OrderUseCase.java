package ddog.daengleserver.presentation.usecase;

import ddog.daengleserver.presentation.dto.request.PostOrderReq;

public interface OrderUseCase {
    void processOrder(PostOrderReq postOrderReq);
}
