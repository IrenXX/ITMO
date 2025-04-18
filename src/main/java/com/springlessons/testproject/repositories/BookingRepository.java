package com.springlessons.testproject.repositories;

import com.springlessons.testproject.model.Booking;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByBookingTimeBetween(@NotNull LocalDateTime createdDateAfter, @NotNull LocalDateTime createdDateBefore);

    List<Booking> getByBookingTime(LocalDateTime time);

    // Получение выручки за период
//    @Query("SELECT CAST(create_date AS date) AS date, SUM(b.price) as revenue " +
//            "FROM orders b " +
//            "WHERE b.create_date BETWEEN :start AND :end " +
//            "GROUP BY CAST(create_date AS date) " +
//            "ORDER BY date")
//    List<DailyRevenueDto> getDailyRevenue(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Booking b SET b.discount = :discount WHERE b.user.id = :userId AND b.bookingTime > :currentTime")
    @Transactional
    void updateDiscountForUserFutureBookings(
            @Param("userId") Long userId,
            @Param("discount") double discount,
            @Param("currentTime") LocalDateTime currentTime);
}
