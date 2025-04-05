package com.springlessons.testproject.repositories;

import com.springlessons.testproject.dto.DailyRevenueDto;
import com.springlessons.testproject.model.Order;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Formula;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findById(Long id);

    List<Order> getByCreatedDate(@NotNull LocalDateTime createdDate);

    List<Order> findAllByCreatedDateBetween(@NotNull LocalDateTime createdDateAfter, @NotNull LocalDateTime createdDateBefore);

    // Получение выручки за период
//    @Query("SELECT CAST(create_date AS date) AS date, SUM(b.price) as revenue " +
//            "FROM orders b " +
//            "WHERE b.create_date BETWEEN :start AND :end " +
//            "GROUP BY CAST(create_date AS date) " +
//            "ORDER BY date")
//    List<DailyRevenueDto> getDailyRevenue(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);


}
