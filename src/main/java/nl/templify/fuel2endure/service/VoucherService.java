package nl.templify.fuel2endure.service;

import java.util.List;
import java.util.Optional;
import nl.templify.fuel2endure.domain.Voucher;

/**
 * Service Interface for managing {@link Voucher}.
 */
public interface VoucherService {
    /**
     * Save a voucher.
     *
     * @param voucher the entity to save.
     * @return the persisted entity.
     */
    Voucher save(Voucher voucher);

    /**
     * Partially updates a voucher.
     *
     * @param voucher the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Voucher> partialUpdate(Voucher voucher);

    /**
     * Get all the vouchers.
     *
     * @return the list of entities.
     */
    List<Voucher> findAll();

    /**
     * Get the "id" voucher.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Voucher> findOne(Long id);

    /**
     * Delete the "id" voucher.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
