package com.bancosist.account_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String number;

    @Column(nullable = false)
    private String agency;

    @Column(nullable = false)
    private String customerCpf;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private Boolean blocked;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false)
    private BigDecimal dailyWithdrawLimit;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();

        if (this.balance == null) {
            this.balance = BigDecimal.ZERO;
        }

        if (this.blocked == null) {
            this.blocked = false;
        }

        if (this.active == null) {
            this.active = true;
        }

        if (this.dailyWithdrawLimit == null) {
            this.dailyWithdrawLimit = BigDecimal.valueOf(2000);
        }
    }
}