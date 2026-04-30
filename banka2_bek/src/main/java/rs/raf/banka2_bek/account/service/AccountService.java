package rs.raf.banka2_bek.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.raf.banka2_bek.account.model.Account;
import rs.raf.banka2_bek.account.repository.AccountRepository;
import rs.raf.banka2_bek.auth.model.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public void withdrawFromAccount(User user, String role, BigDecimal amount) {
        Account account = accountRepository.findByUserAndRole(user, role)
                .orElseThrow(() -> new RuntimeException(
                        "Račun sa ulogom " + role + " nije pronađen za korisnika: " + user.getId()
                ));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Nedovoljno sredstava na računu. Stanje: " + account.getBalance() + ", traženo: " + amount);
        }

        account.setBalance(account.getBalance().subtract(amount));
        account.setUpdatedAt(LocalDateTime.now());
        accountRepository.save(account);

        log.info("Isplaćeno {} sa računa {} (uređaja: {})", amount, account.getAccountNumber(), role);
    }

    @Transactional
    public void depositToAccount(User user, String role, BigDecimal amount) {
        Account account = accountRepository.findByUserAndRole(user, role)
                .orElseThrow(() -> new RuntimeException(
                        "Račun sa ulogom " + role + " nije pronađen za korisnika: " + user.getId()
                ));

        account.setBalance(account.getBalance().add(amount));
        account.setUpdatedAt(LocalDateTime.now());
        accountRepository.save(account);

        log.info("Uplaćeno {} na račun {} (uređaja: {})", amount, account.getAccountNumber(), role);
    }

    @Transactional
    public BigDecimal getBalance(User user, String role) {
        return accountRepository.findByUserAndRole(user, role)
                .map(Account::getBalance)
                .orElse(BigDecimal.ZERO);
    }

    @Transactional
    public Account createAccount(User user, String role, String accountNumber, BigDecimal initialBalance) {
        if (accountRepository.existsByUserIdAndRole(user.getId(), role)) {
            throw new RuntimeException("Račun sa ovom ulogom već postoji");
        }

        Account account = Account.builder()
                .user(user)
                .role(role)
                .accountNumber(accountNumber)
                .balance(initialBalance != null ? initialBalance : BigDecimal.ZERO)
                .createdAt(LocalDateTime.now())
                .build();

        return accountRepository.save(account);
    }
}
