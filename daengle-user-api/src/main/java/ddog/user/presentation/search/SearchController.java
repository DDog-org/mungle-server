package ddog.user.presentation.search;

import ddog.domain.groomer.enums.GroomingKeyword;
import ddog.domain.vet.enums.CareKeyword;
import ddog.user.application.SearchService;
import ddog.user.presentation.search.dto.SearchGroomingResultByKeyword;
import ddog.user.presentation.search.dto.SearchVetResultByKeyword;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;
    @GetMapping("/groomer")
    public SearchGroomingResultByKeyword findGroomerSearchList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) GroomingKeyword tag
    ) {
        return searchService.getGroomerResultBySearch(page, size, address, keyword, tag);
    }

    @GetMapping("/vet")
    public SearchVetResultByKeyword findVetSearchList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) CareKeyword tag
    ) {
        return searchService.getVetResultBySearch(page, size, address, keyword, tag);
    }
}
