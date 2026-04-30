package rs.raf.banka2_bek.tax.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.raf.banka2_bek.auth.model.User;
import rs.raf.banka2_bek.order.repository.OrderRepository;
import rs.raf.banka2_bek.order.model.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaxService {

    private final OrderRepository orderRepository;
    private static final BigDecimal TAX_RATE = new BigDecimal("0.15");  // 15% porez

    @Transactional(readOnly = true)
    public BigDecimal calculateCapitalGainsTax(User supervisor, LocalDateTime fromDate, LocalDateTime toDate) {
        log.info("Obračunavam kapitalnu dobit za supervizora {} od {} do {}",
                supervisor.getId(), fromDate, toDate);

        // 🔴 KLJUČNO: Preskoči FUND ordere - samo SUPERVISOR orderi se obračunavaju
        List<Order> taxableOrders = orderRepository.findAllByUserIdAndUserRoleAndOrderTypeAndCreatedAtBetween(
                supervisor.getId(),
                "SUPERVISOR",  // ← SAMO SUPERVISOR, ne FUND
                "SELL",
                fromDate,
                toDate
        );

        BigDecimal totalCapitalGains = BigDecimal.ZERO;

        for (Order order : taxableOrders) {
            BigDecimal gain = calculateGain(supervisor, order);
            totalCapitalGains = totalCapitalGains.add(gain);
        }

        BigDecimal tax = totalCapitalGains.multiply(TAX_RATE);
        log.info("Ukupna kapitalna dobit: {}, porez (15%): {}", totalCapitalGains, tax);

        return tax;
    }

    private BigDecimal calculateGain(User supervisor, Order sellOrder) {
        // Pronađi odgovarajući BUY order za istu hartiju
        List<Order> buyOrders = orderRepository.findAllByUserIdAndUserRoleAndOrderTypeAndCreatedAtBetween(
                supervisor.getId(),
                "SUPERVISOR",
                "BUY",
                sellOrder.getCreatedAt().minusYears(10),  // Traži unazad do 10 godina
                sellOrder.getCreatedAt()
        );

        // Pronađi BUY order za istu hartiju
        for (Order buyOrder : buyOrders) {
            if (buyOrder.getSecurityId().equals(sellOrder.getSecurityId())) {
                BigDecimal selling = sellOrder.getTotalAmount();
                BigDecimal buying = buyOrder.getTotalAmount();
                return selling.subtract(buying);
            }
        }

        // Ako nema BUY, cela prodajna cena je dobit
        return sellOrder.getTotalAmount();
    }

    @Transactional(readOnly = true)
    public BigDecimal getTotalTaxForPeriod(User supervisor, LocalDateTime fromDate, LocalDateTime toDate) {
        return calculateCapitalGainsTax(supervisor, fromDate, toDate);
    }
}
