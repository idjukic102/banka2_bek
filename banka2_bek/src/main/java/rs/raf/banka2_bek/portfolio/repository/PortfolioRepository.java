package rs.raf.banka2_bek.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.raf.banka2_bek.auth.model.User;
import rs.raf.banka2_bek.portfolio.model.Portfolio;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    Optional<Portfolio> findByUserIdAndRole(Long userId, String role);

    Optional<Portfolio> findByUserAndRole(User user, String role);

    List<Portfolio> findAllByUser(User user);

    List<Portfolio> findAllByUserId(Long userId);

    boolean existsByUserIdAndRole(Long userId, String role);
}
