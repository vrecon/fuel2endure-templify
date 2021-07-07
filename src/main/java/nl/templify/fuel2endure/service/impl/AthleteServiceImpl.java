package nl.templify.fuel2endure.service.impl;

import java.util.List;
import java.util.Optional;
import nl.templify.fuel2endure.domain.Athlete;
import nl.templify.fuel2endure.repository.AthleteRepository;
import nl.templify.fuel2endure.service.AthleteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Athlete}.
 */
@Service
@Transactional
public class AthleteServiceImpl implements AthleteService {

    private final Logger log = LoggerFactory.getLogger(AthleteServiceImpl.class);

    private final AthleteRepository athleteRepository;

    public AthleteServiceImpl(AthleteRepository athleteRepository) {
        this.athleteRepository = athleteRepository;
    }

    @Override
    public Athlete save(Athlete athlete) {
        log.debug("Request to save Athlete : {}", athlete);
        return athleteRepository.save(athlete);
    }

    @Override
    public Optional<Athlete> partialUpdate(Athlete athlete) {
        log.debug("Request to partially update Athlete : {}", athlete);

        return athleteRepository
            .findById(athlete.getId())
            .map(
                existingAthlete -> {
                    if (athlete.getMiddleName() != null) {
                        existingAthlete.setMiddleName(athlete.getMiddleName());
                    }
                    if (athlete.getLength() != null) {
                        existingAthlete.setLength(athlete.getLength());
                    }
                    if (athlete.getWeight() != null) {
                        existingAthlete.setWeight(athlete.getWeight());
                    }
                    if (athlete.getStatus() != null) {
                        existingAthlete.setStatus(athlete.getStatus());
                    }

                    return existingAthlete;
                }
            )
            .map(athleteRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Athlete> findAll() {
        log.debug("Request to get all Athletes");
        return athleteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Athlete> findOne(Long id) {
        log.debug("Request to get Athlete : {}", id);
        return athleteRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Athlete : {}", id);
        athleteRepository.deleteById(id);
    }
}
