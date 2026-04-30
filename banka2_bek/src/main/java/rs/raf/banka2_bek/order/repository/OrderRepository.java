package rs.raf.banka2_bek.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.raf.banka2_bek.auth.model.User;
import rs.raf.banka2_bek.order.model.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUser(User user);

    List<Order> findAllByUserRoleAndStatus(String userRole, String status);

    List<Order> findAllByUserIdAndUserRole(Long userId, String userRole);

    List<Order> findAllByUserIdAndOrderType(Long userId, String orderType);

    List<Order> findAllByUserIdAndUserRoleAndOrderTypeAndCreatedAtBetween(
            Long userId,
            String userRole,
            String orderType,
            LocalDateTime from,
            LocalDateTime to
    );

    Optional<Order> findByIdAndUser(Long orderId, User user);
}
