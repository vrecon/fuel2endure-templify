package nl.templify.fuel2endure.service.impl;

import java.util.List;
import java.util.Optional;
import nl.templify.fuel2endure.domain.Payment;
import nl.templify.fuel2endure.repository.PaymentRepository;
import nl.templify.fuel2endure.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Payment}.
 */
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment save(Payment payment) {
        log.debug("Request to save Payment : {}", payment);
        return paymentRepository.save(payment);
    }

    @Override
    public Optional<Payment> partialUpdate(Payment payment) {
        log.debug("Request to partially update Payment : {}", payment);

        return paymentRepository
            .findById(payment.getId())
            .map(
                existingPayment -> {
                    if (payment.getPaymentDate() != null) {
                        existingPayment.setPaymentDate(payment.getPaymentDate());
                    }
                    if (payment.getPaymentStatus() != null) {
                        existingPayment.setPaymentStatus(payment.getPaymentStatus());
                    }
                    if (payment.getMollieKey() != null) {
                        existingPayment.setMollieKey(payment.getMollieKey());
                    }

                    return existingPayment;
                }
            )
            .map(paymentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Payment> findAll() {
        log.debug("Request to get all Payments");
        return paymentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Payment> findOne(Long id) {
        log.debug("Request to get Payment : {}", id);
        return paymentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Payment : {}", id);
        paymentRepository.deleteById(id);
    }
}
