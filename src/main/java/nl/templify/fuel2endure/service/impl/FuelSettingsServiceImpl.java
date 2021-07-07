package nl.templify.fuel2endure.service.impl;

import java.util.List;
import java.util.Optional;
import nl.templify.fuel2endure.domain.FuelSettings;
import nl.templify.fuel2endure.repository.FuelSettingsRepository;
import nl.templify.fuel2endure.service.FuelSettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FuelSettings}.
 */
@Service
@Transactional
public class FuelSettingsServiceImpl implements FuelSettingsService {

    private final Logger log = LoggerFactory.getLogger(FuelSettingsServiceImpl.class);

    private final FuelSettingsRepository fuelSettingsRepository;

    public FuelSettingsServiceImpl(FuelSettingsRepository fuelSettingsRepository) {
        this.fuelSettingsRepository = fuelSettingsRepository;
    }

    @Override
    public FuelSettings save(FuelSettings fuelSettings) {
        log.debug("Request to save FuelSettings : {}", fuelSettings);
        return fuelSettingsRepository.save(fuelSettings);
    }

    @Override
    public Optional<FuelSettings> partialUpdate(FuelSettings fuelSettings) {
        log.debug("Request to partially update FuelSettings : {}", fuelSettings);

        return fuelSettingsRepository
            .findById(fuelSettings.getId())
            .map(
                existingFuelSettings -> {
                    if (fuelSettings.getCarbohydratesGel() != null) {
                        existingFuelSettings.setCarbohydratesGel(fuelSettings.getCarbohydratesGel());
                    }
                    if (fuelSettings.getCarbohydratesSportDrank() != null) {
                        existingFuelSettings.setCarbohydratesSportDrank(fuelSettings.getCarbohydratesSportDrank());
                    }
                    if (fuelSettings.getSelectedOwnGelItem() != null) {
                        existingFuelSettings.setSelectedOwnGelItem(fuelSettings.getSelectedOwnGelItem());
                    }
                    if (fuelSettings.getSelectedOwnDrinkItem() != null) {
                        existingFuelSettings.setSelectedOwnDrinkItem(fuelSettings.getSelectedOwnDrinkItem());
                    }

                    return existingFuelSettings;
                }
            )
            .map(fuelSettingsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FuelSettings> findAll() {
        log.debug("Request to get all FuelSettings");
        return fuelSettingsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FuelSettings> findOne(Long id) {
        log.debug("Request to get FuelSettings : {}", id);
        return fuelSettingsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FuelSettings : {}", id);
        fuelSettingsRepository.deleteById(id);
    }
}
