package ddog.user.application;

import ddog.domain.groomer.Groomer;
import ddog.domain.groomer.enums.GroomingBadge;
import ddog.domain.groomer.port.GroomerPersist;
import ddog.domain.vet.Vet;
import ddog.domain.vet.enums.CareBadge;
import ddog.domain.vet.port.VetPersist;
import ddog.user.presentation.search.dto.SearchGroomingResultByKeyword;
import ddog.user.presentation.search.dto.SearchVetResultByKeyword;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final GroomerPersist groomerPersist;
    private final VetPersist vetPersist;

    public SearchGroomingResultByKeyword getGroomerResultBySearch(int page, int size, String address, String keyword, GroomingBadge badge) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Groomer> groomerPage = groomerPersist.findGroomerByKeyword(address, keyword, badge, pageable);

        List<SearchGroomingResultByKeyword.ResultList> resultList = groomerPage.stream()
                .map(groomer -> SearchGroomingResultByKeyword.ResultList.builder()
                        .partnerId(groomer.getAccountId())
                        .partnerName(groomer.getName())
                        .partnerImage(groomer.getImageUrl())
                        .groomingBadges(groomer.getBadges())
                        .build())
                .toList();

        return SearchGroomingResultByKeyword.builder()
                .result(resultList)
                .page(groomerPage.getNumber())
                .size(groomerPage.getSize())
                .totalElements(groomerPage.getTotalElements())
                .build();

    }
    public SearchVetResultByKeyword getVetResultBySearch(int page, int size, String address, String keyword, CareBadge badge) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Vet> vetPage = vetPersist.findVetByKeyword(address, keyword, badge, pageable);

        List<SearchVetResultByKeyword.ResultList> resultList = vetPage.stream()
                .map(vet -> SearchVetResultByKeyword.ResultList.builder()
                        .partnerId(vet.getAccountId())
                        .partnerName(vet.getName())
                        .partnerImage(vet.getImageUrl())
                        .careBadges(vet.getBadges())
                        .build())
                .toList();

        return SearchVetResultByKeyword.builder()
                .result(resultList)
                .page(vetPage.getNumber())
                .size(vetPage.getSize())
                .totalElements(vetPage.getTotalElements())
                .build();
    }
}
