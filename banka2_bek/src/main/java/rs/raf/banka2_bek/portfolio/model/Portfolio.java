package rs.raf.banka2_bek.portfolio.model;

import jakarta.persistence.*;
import lombok.*;
import rs.raf.banka2_bek.auth.model.User;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "portfolios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 50)
    private String role;  // "FUND", "SUPERVISOR", itd.

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "portfolio_securities", joinColumns = @JoinColumn(name = "portfolio_id"))
    @MapKeyColumn(name = "security_id")
    @Column(name = "quantity")
    private Map<Long, Integer> securities = new HashMap<>();  // securityId -> quantity

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public void addSecurity(Long securityId, Integer quantity) {
        securities.merge(securityId, quantity, Integer::sum);
        updatedAt = LocalDateTime.now();
    }

    public void removeSecurity(Long securityId, Integer quantity) {
        if (securities.containsKey(securityId)) {
            int current = securities.get(securityId);
            if (current <= quantity) {
                securities.remove(securityId);
            } else {
                securities.put(securityId, current - quantity);
            }
            updatedAt = LocalDateTime.now();
        }
    }
}
