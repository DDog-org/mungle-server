package ddog.user.application.mapper;

import ddog.domain.estimate.CareEstimate;
import ddog.domain.estimate.GroomingEstimate;
import ddog.domain.groomer.Groomer;
import ddog.domain.payment.Reservation;
import ddog.domain.pet.Pet;
import ddog.domain.vet.Vet;
import ddog.user.presentation.reservation.dto.EstimateDetail;
import ddog.user.presentation.reservation.dto.ReservationInfo;

public class ReservationMapper {

    public static ReservationInfo.Grooming.Content mapToGroomingContent(Reservation estimate, Pet pet) {
        return ReservationInfo.Grooming.Content.builder()
                .reservationId(estimate.getReservationId())
                .estimateId(estimate.getEstimateId())
                .groomerName(estimate.getRecipientName())
                .petName(pet.getName())
                .petImageUrl(pet.getImageUrl())
                .shopName(estimate.getShopName())
                .reservedDate(estimate.getSchedule())
                .build();
    }

    public static ReservationInfo.Care.Content mapToCareContent(Reservation estimate, Pet pet) {
        return ReservationInfo.Care.Content.builder()
                .reservationId(estimate.getReservationId())
                .estimateId(estimate.getEstimateId())
                .vetName(estimate.getRecipientName())
                .petName(pet.getName())
                .petImageUrl(pet.getImageUrl())
                .reservedDate(estimate.getSchedule())
                .build();
    }

    public static EstimateDetail.Grooming mapToGroomingEstimateDetail(Long reservationId, Long estimateId, Groomer groomer, GroomingEstimate estimate, Pet pet) {
        return EstimateDetail.Grooming.builder()
                .reservationId(reservationId)
                .groomingEstimateId(estimateId)
                .groomerId(groomer.getAccountId())
                .imageUrl(groomer.getImageUrl())
                .name(groomer.getName())
                .shopId(groomer.getShopId())
                .shopName(groomer.getShopName())
                .daengleMeter(groomer.getDaengleMeter())
                .keywords(groomer.getKeywords())
                .introduction(groomer.getIntroduction())
                .address(groomer.getAddress())
                .reservedDate(estimate.getReservedDate())
                .weight(pet.getWeight())
                .desiredStyle(estimate.getDesiredStyle())
                .overallOpinion(estimate.getOverallOpinion())
                .build();
    }

    public static EstimateDetail.Care mapToCareEstimateDetail(Long reservationId, Long estimateId, Vet vet, CareEstimate estimate) {
        return EstimateDetail.Care.builder()
                .reservationId(reservationId)
                .careEstimateId(estimateId)
                .vetId(vet.getAccountId())
                .imageUrl(vet.getImageUrl())
                .name(vet.getName())
                .daengleMeter(vet.getDaengleMeter())
                .keywords(vet.getKeywords())
                .introduction(vet.getIntroduction())
                .address(vet.getAddress())
                .reservedDate(estimate.getReservedDate())
                .diagnosis(estimate.getDiagnosis())
                .cause(estimate.getCause())
                .treatment(estimate.getTreatment())
                .build();
    }
}
