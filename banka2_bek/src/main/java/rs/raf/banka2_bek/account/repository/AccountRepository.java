package rs.raf.banka2_bek.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.raf.banka2_bek.account.model.Account;
import rs.raf.banka2_bek.auth.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUserIdAndRole(Long userId, String role);

    Optional<Account> findByUserAndRole(User user, String role);

    List<Account> findAllByUser(User user);

    List<Account> findAllByUserId(Long userId);

    Optional<Account> findByAccountNumber(String accountNumber);

    boolean existsByUserIdAndRole(Long userId, String role);
}
