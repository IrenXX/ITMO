package com.springlessons.testproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@Table(name = "booking")
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "service_type")
    private String serviceType;

    @NotNull
    @Column(name = "booking_time")
    @CreationTimestamp
    private LocalDateTime bookingTime;

    @NotNull
    @Column(name = "discount")
    private double discount;

    @Column(name = "price")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "operator_id")
    private Users operator;
}
