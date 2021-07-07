package nl.templify.fuel2endure.repository;

import nl.templify.fuel2endure.domain.RacePlanForm;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RacePlanForm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RacePlanFormRepository extends JpaRepository<RacePlanForm, Long>, JpaSpecificationExecutor<RacePlanForm> {}
