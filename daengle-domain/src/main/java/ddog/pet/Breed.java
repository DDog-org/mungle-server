package ddog.pet;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Breed {
    GOLDEN_RETRIEVER("골든 리트리버"),
    GREAT_DANE("그레이트 데인"),
    DACHSHUND("닥스훈트"),
    DALMATIAN("달마시안"),
    DOBERMAN("도베르만"),
    TOSA("도사견"),
    LABRADOR_RETRIEVER("래브라도 리트리버"),
    ROTTWEILER("로트와일러"),
    MALTESE("말티즈"),
    MINIATURE_SCHNAUZER("미니어처 슈나우저"),
    MUTT("믹스견"),
    BASENJI("바센지"),
    BASSET_HOUND("바셋 하운드"),
    BORDER_COLLIE("보더 콜리"),
    BULL_TERRIER("불 테리어"),
    BULLDOG("불독"),
    BEAGLE("비글"),
    PAPILLON("빠삐용"),
    SAMOYED("사모예드"),
    SHAR_PEI("샤페이"),
    ST_BERNARD("세인트 버나드"),
    SHIBA_INU("시바 이누"),
    SIBERIAN_HUSKY("시베리안 허스키"),
    SHIH_TZU("시츄"),
    IRISH_WOLFHOUND("아이리시 울프하운드"),
    AKITA("아키타"),
    AFGHAN_HOUND("아프간 하운드"),
    ALASKAN_MALAMUTE("알래스칸 말라뮤트"),
    YORKSHIRE_TERRIER("요크셔 테리어"),
    CORGI("웰시 코기"),
    ITALIAN_GREYHOUND("이탈리안 그레이하운드"),
    GERMAN_SHEPHERD("저먼 셰퍼드"),
    JINDO("진돗개"),
    CHOW_CHOW("차우차우"),
    CHIHUAHUA("치와와"),
    CANE_CORSO("카네 코르소"),
    COCKER_SPANIEL("코커 스패니얼"),
    POMERANIAN("포메라니안"),
    POODLE("푸들"),
    KOREAN_MASTIFF("한국 토종 마스티프"),
    WHIPPET("휘핏");

    private String name;
}
