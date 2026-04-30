package rs.raf.banka2_bek.order.model;

import jakarta.persistence.*;
import lombok.*;
import rs.raf.banka2_bek.auth.model.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 50)
    private String userRole;  // "FUND" ili "SUPERVISOR"

    @Column(nullable = false, length = 10)
    private String orderType;  // "BUY" ili "SELL"

    @Column(nullable = false)
    private Long securityId;  // ID hartije od vrednosti

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private BigDecimal totalAmount;  // quantity * price

    @Column(nullable = false, length = 20)
    private String status;  // "PENDING", "FILLED", "CANCELLED"

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime filledAt;
}
