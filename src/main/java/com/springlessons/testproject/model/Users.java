package com.springlessons.testproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Enumerated(EnumType.STRING)
    private UserRule userRule;

    @Column(name = "user_name", unique = true)
    @NotNull
    private String username;

    @Column(name = "email", unique = true)
    @Email
    private String email;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "user_pass")
    @NotNull
    private String password;
//    private String email;

    @Column(name = "work_schedule")
    private String workSchedule;
}
