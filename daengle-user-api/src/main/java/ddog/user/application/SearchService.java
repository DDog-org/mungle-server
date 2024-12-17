package ddog.user.application;

import ddog.domain.groomer.Groomer;
import ddog.domain.groomer.enums.GroomingKeyword;
import ddog.domain.groomer.port.GroomerPersist;
import ddog.domain.vet.Vet;
import ddog.domain.vet.enums.CareKeyword;
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

    public SearchGroomingResultByKeyword getGroomerResultBySearch(int page, int size, String address, String keyword, GroomingKeyword tag) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Groomer> groomerPage = groomerPersist.findGroomerByKeyword(address, keyword, tag, pageable);

        List<SearchGroomingResultByKeyword.ResultList> resultList = groomerPage.stream()
                .map(groomer -> SearchGroomingResultByKeyword.ResultList.builder()
                        .partnerId(groomer.getGroomerId())
                        .partnerName(groomer.getName())
                        .partnerImage(groomer.getImageUrl())
                        .groomingKeywords(groomer.getKeywords())
                        .build())
                .toList();

        return SearchGroomingResultByKeyword.builder()
                .result(resultList)
                .page(groomerPage.getNumber())
                .size(groomerPage.getSize())
                .totalElements(groomerPage.getTotalElements())
                .build();

    }
    public SearchVetResultByKeyword getVetResultBySearch(int page, int size, String address, String keyword, CareKeyword tag) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Vet> vetPage = vetPersist.findVetByKeyword(address, keyword, tag, pageable);

        List<SearchVetResultByKeyword.ResultList> resultList = vetPage.stream()
                .map(vet -> SearchVetResultByKeyword.ResultList.builder()
                        .partnerId(vet.getVetId())
                        .partnerName(vet.getName())
                        .partnerImage(vet.getImageUrl())
                        .careKeywords(vet.getKeywords())
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
