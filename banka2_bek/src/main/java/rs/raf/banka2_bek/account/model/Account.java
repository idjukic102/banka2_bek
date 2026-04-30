package rs.raf.banka2_bek.account.model;

import jakarta.persistence.*;
import lombok.*;
import rs.raf.banka2_bek.auth.model.User;

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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 50)
    private String role;  // "FUND", "SUPERVISOR", "CLIENT", itd.

    @Column(nullable = false)
    private BigDecimal balance;  // Stanje na racunu

    @Column(nullable = false, length = 30)
    private String accountNumber;  // Broj racuna

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
