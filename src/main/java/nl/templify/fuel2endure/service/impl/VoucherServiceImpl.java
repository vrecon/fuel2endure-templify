package nl.templify.fuel2endure.service.impl;

import java.util.List;
import java.util.Optional;
import nl.templify.fuel2endure.domain.Voucher;
import nl.templify.fuel2endure.repository.VoucherRepository;
import nl.templify.fuel2endure.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Voucher}.
 */
@Service
@Transactional
public class VoucherServiceImpl implements VoucherService {

    private final Logger log = LoggerFactory.getLogger(VoucherServiceImpl.class);

    private final VoucherRepository voucherRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public Voucher save(Voucher voucher) {
        log.debug("Request to save Voucher : {}", voucher);
        return voucherRepository.save(voucher);
    }

    @Override
    public Optional<Voucher> partialUpdate(Voucher voucher) {
        log.debug("Request to partially update Voucher : {}", voucher);

        return voucherRepository
            .findById(voucher.getId())
            .map(
                existingVoucher -> {
                    if (voucher.getCode() != null) {
                        existingVoucher.setCode(voucher.getCode());
                    }
                    if (voucher.getVoucherType() != null) {
                        existingVoucher.setVoucherType(voucher.getVoucherType());
                    }
                    if (voucher.getRedeemed() != null) {
                        existingVoucher.setRedeemed(voucher.getRedeemed());
                    }
                    if (voucher.getMaxDate() != null) {
                        existingVoucher.setMaxDate(voucher.getMaxDate());
                    }
                    if (voucher.getAmount() != null) {
                        existingVoucher.setAmount(voucher.getAmount());
                    }
                    if (voucher.getMaxRedeemed() != null) {
                        existingVoucher.setMaxRedeemed(voucher.getMaxRedeemed());
                    }
                    if (voucher.getCategory() != null) {
                        existingVoucher.setCategory(voucher.getCategory());
                    }

                    return existingVoucher;
                }
            )
            .map(voucherRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Voucher> findAll() {
        log.debug("Request to get all Vouchers");
        return voucherRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Voucher> findOne(Long id) {
        log.debug("Request to get Voucher : {}", id);
        return voucherRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Voucher : {}", id);
        voucherRepository.deleteById(id);
    }
}
