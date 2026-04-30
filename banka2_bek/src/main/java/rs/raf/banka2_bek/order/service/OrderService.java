package rs.raf.banka2_bek.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.raf.banka2_bek.account.service.AccountService;
import rs.raf.banka2_bek.auth.model.User;
import rs.raf.banka2_bek.order.model.Order;
import rs.raf.banka2_bek.order.repository.OrderRepository;
import rs.raf.banka2_bek.portfolio.model.Portfolio;
import rs.raf.banka2_bek.portfolio.repository.PortfolioRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final PortfolioRepository portfolioRepository;
    private final AccountService accountService;

    @Transactional
    public void executeBuyOrder(Order order) {
        log.info("Izvršavam BUY order: {} za korisnika {} sa ulogom {}",
                order.getId(), order.getUser().getId(), order.getUserRole());

        //  Razlikuj FUND od SUPERVISOR
        if ("FUND".equals(order.getUserRole())) {
            // 1. FUND: Hartije idu u FUND portfolio
            Portfolio fundPortfolio = portfolioRepository
                    .findByUserIdAndRole(order.getUser().getId(), "FUND")
                    .orElseGet(() -> createNewPortfolio(order.getUser(), "FUND"));

            fundPortfolio.addSecurity(order.getSecurityId(), order.getQuantity());
            portfolioRepository.save(fundPortfolio);

            // 2. FUND: Novac se skida sa FUND racuna
            accountService.withdrawFromAccount(order.getUser(), "FUND", order.getTotalAmount());

            log.info("BUY order izvršen za FUND: {} hartija dodano u FUND portfolio", order.getQuantity());

        } else {
            // SUPERVISOR: Postojeća logika
            Portfolio supervisorPortfolio = portfolioRepository
                    .findByUserIdAndRole(order.getUser().getId(), "SUPERVISOR")
                    .orElseGet(() -> createNewPortfolio(order.getUser(), "SUPERVISOR"));

            supervisorPortfolio.addSecurity(order.getSecurityId(), order.getQuantity());
            portfolioRepository.save(supervisorPortfolio);

            accountService.withdrawFromAccount(order.getUser(), "SUPERVISOR", order.getTotalAmount());

            log.info("BUY order izvršen za SUPERVISOR: {} hartija dodano u SUPERVISOR portfolio", order.getQuantity());
        }

        order.setStatus("FILLED");
        order.setFilledAt(LocalDateTime.now());
        orderRepository.save(order);
    }

    @Transactional
    public void executeSellOrder(Order order) {
        log.info("Izvršavam SELL order: {} za korisnika {} sa ulogom {}",
                order.getId(), order.getUser().getId(), order.getUserRole());

        // 1. Pronađi portfolio (FUND ili SUPERVISOR)
        Portfolio portfolio = portfolioRepository
                .findByUserIdAndRole(order.getUser().getId(), order.getUserRole())
                .orElseThrow(() -> new RuntimeException(
                        "Portfolio sa ulogom " + order.getUserRole() + " nije pronađen"
                ));

        // 2. Ukloni hartije iz portfolija
        portfolio.removeSecurity(order.getSecurityId(), order.getQuantity());
        portfolioRepository.save(portfolio);

        // 3. Dodaj novac na račun
        accountService.depositToAccount(order.getUser(), order.getUserRole(), order.getTotalAmount());

        // 4. TODO: VAŽNO: Pozovi hook ka T9 (onFillCompleted) - integracija sa drugim servisom
        notifyT9OnFillCompleted(order);

        order.setStatus("FILLED");
        order.setFilledAt(LocalDateTime.now());
        orderRepository.save(order);

        log.info("SELL order izvršen: {} hartija uklonjena iz portfolija, {} dodano na račun",
                order.getQuantity(), order.getTotalAmount());
    }

    private Portfolio createNewPortfolio(User user, String role) {
        Portfolio portfolio = Portfolio.builder()
                .user(user)
                .role(role)
                .createdAt(LocalDateTime.now())
                .build();
        return portfolioRepository.save(portfolio);
    }

    private void notifyT9OnFillCompleted(Order order) {
        // TODO: Implemetrati komunikaciju sa T9 servisom
        // Primer:
        // T9OrderFillClient.onFillCompleted(new OrderFillEvent(
        //     order.getId(),
        //     order.getUser().getId(),
        //     order.getTotalAmount(),
        //     order.getSecurityId(),
        //     order.getQuantity()
        // ));

        log.warn("STUB: T9 notifikacija nije implementirana - poziv ka T9 servisu");
    }

    public Order createOrder(User user, String userRole, String orderType,
                             Long securityId, Integer quantity, BigDecimal price) {
        BigDecimal totalAmount = price.multiply(new BigDecimal(quantity));

        Order order = Order.builder()
                .user(user)
                .userRole(userRole)
                .orderType(orderType)
                .securityId(securityId)
                .quantity(quantity)
                .price(price)
                .totalAmount(totalAmount)
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();

        return orderRepository.save(order);
    }
}
