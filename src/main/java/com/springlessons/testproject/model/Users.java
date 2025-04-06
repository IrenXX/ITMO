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

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "user_rule")
    private UserRule userRule;

    @NotNull
    @Column(name = "user_name", unique = true)
    private String username;

    @Email
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "discount")
    private Double discount;

    @NotNull
    @Column(name = "user_pass")
    private String password;

    @Column(name = "work_schedule")
    private String workSchedule;
}
