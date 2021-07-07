package nl.templify.fuel2endure.repository;

import nl.templify.fuel2endure.domain.Race;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Race entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RaceRepository extends JpaRepository<Race, Long>, JpaSpecificationExecutor<Race> {}
