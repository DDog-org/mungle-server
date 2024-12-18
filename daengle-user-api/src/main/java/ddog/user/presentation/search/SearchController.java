package ddog.user.presentation.search;

import ddog.auth.exception.common.CommonResponseEntity;
import ddog.domain.groomer.enums.GroomingBadge;
import ddog.domain.groomer.enums.GroomingKeyword;
import ddog.domain.vet.enums.CareBadge;
import ddog.domain.vet.enums.CareKeyword;
import ddog.user.application.SearchService;
import ddog.user.presentation.search.dto.SearchGroomingResultByKeyword;
import ddog.user.presentation.search.dto.SearchVetResultByKeyword;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static ddog.auth.exception.common.CommonResponseEntity.success;

@RestController
@RequestMapping("/api/user/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;
    @GetMapping("/groomer")
    public CommonResponseEntity<SearchGroomingResultByKeyword> findGroomerSearchList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) GroomingBadge tag
    ) {
        return success(searchService.getGroomerResultBySearch(page, size, address, keyword, tag));
    }

    @GetMapping("/vet")
    public CommonResponseEntity<SearchVetResultByKeyword> findVetSearchList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) CareBadge tag
    ) {
        return success(searchService.getVetResultBySearch(page, size, address, keyword, tag));
    }
}
