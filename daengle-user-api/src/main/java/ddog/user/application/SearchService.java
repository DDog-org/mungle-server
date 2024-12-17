package ddog.user.application;

import ddog.domain.groomer.port.GroomerPersist;
import ddog.domain.vet.port.VetPersist;
import ddog.user.presentation.search.dto.SearchResultByKeyword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final GroomerPersist groomerPersist;
    private final VetPersist vetPersist;

    public SearchResultByKeyword getResultBySearch(String keyword) {
        return null;
    }

}
