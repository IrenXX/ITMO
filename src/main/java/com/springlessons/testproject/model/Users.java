package com.springlessons.testproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_rule")
    @NotNull
    private String userRule;

    @Column(name = "user_name")
    private String username;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "user_pass")
    private String password;
//    private String email;

    @Column(name = "work_schedule")
    private String workSchedule;
}
