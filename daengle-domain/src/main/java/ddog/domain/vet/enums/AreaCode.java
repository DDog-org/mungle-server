package ddog.domain.vet.enums;

public enum AreaCode {

    SEOUL("02"),
    GYEONGGI("031"),
    INCHEON("032"),
    GANGWON("033"),
    CHUNGNAM("041"),
    DAEJEON("042"),
    CHUNGBUK("043"),
    SEJONG("044"),
    BUSAN("051"),
    ULSAN("052"),
    DAEGU("053"),
    GYEONGBUK("054"),
    GYEONGNAM("055"),
    JEONNAM("061"),
    GWANGJU("062"),
    JEONBUK("063"),
    JEJU("064"),

    MOBILE("010"), // 휴대전화 번호
    SAFETY("050"), // 안심번호
    INTERNET("070"); // 인터넷전화

    private final String code;

    AreaCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
