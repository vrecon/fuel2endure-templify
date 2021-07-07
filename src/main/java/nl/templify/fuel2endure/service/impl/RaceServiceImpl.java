package nl.templify.fuel2endure.service.impl;

import java.util.List;
import java.util.Optional;
import nl.templify.fuel2endure.domain.Race;
import nl.templify.fuel2endure.repository.RaceRepository;
import nl.templify.fuel2endure.service.RaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Race}.
 */
@Service
@Transactional
public class RaceServiceImpl implements RaceService {

    private final Logger log = LoggerFactory.getLogger(RaceServiceImpl.class);

    private final RaceRepository raceRepository;

    public RaceServiceImpl(RaceRepository raceRepository) {
        this.raceRepository = raceRepository;
    }

    @Override
    public Race save(Race race) {
        log.debug("Request to save Race : {}", race);
        return raceRepository.save(race);
    }

    @Override
    public Optional<Race> partialUpdate(Race race) {
        log.debug("Request to partially update Race : {}", race);

        return raceRepository
            .findById(race.getId())
            .map(
                existingRace -> {
                    if (race.getDate() != null) {
                        existingRace.setDate(race.getDate());
                    }
                    if (race.getName() != null) {
                        existingRace.setName(race.getName());
                    }
                    if (race.getLogging() != null) {
                        existingRace.setLogging(race.getLogging());
                    }
                    if (race.getWeight() != null) {
                        existingRace.setWeight(race.getWeight());
                    }
                    if (race.getLength() != null) {
                        existingRace.setLength(race.getLength());
                    }
                    if (race.getTemperature() != null) {
                        existingRace.setTemperature(race.getTemperature());
                    }
                    if (race.getComp() != null) {
                        existingRace.setComp(race.getComp());
                    }
                    if (race.getSwimDuration() != null) {
                        existingRace.setSwimDuration(race.getSwimDuration());
                    }
                    if (race.getBikeDuration() != null) {
                        existingRace.setBikeDuration(race.getBikeDuration());
                    }
                    if (race.getRunDuration() != null) {
                        existingRace.setRunDuration(race.getRunDuration());
                    }
                    if (race.getGelCarbo() != null) {
                        existingRace.setGelCarbo(race.getGelCarbo());
                    }
                    if (race.getDrinkCarbo() != null) {
                        existingRace.setDrinkCarbo(race.getDrinkCarbo());
                    }
                    if (race.getDrinkOrgCarbo() != null) {
                        existingRace.setDrinkOrgCarbo(race.getDrinkOrgCarbo());
                    }
                    if (race.getGelOrgCarbo() != null) {
                        existingRace.setGelOrgCarbo(race.getGelOrgCarbo());
                    }
                    if (race.getGelsbike() != null) {
                        existingRace.setGelsbike(race.getGelsbike());
                    }
                    if (race.getGelsrun() != null) {
                        existingRace.setGelsrun(race.getGelsrun());
                    }
                    if (race.getSelectedOrgGelQuery() != null) {
                        existingRace.setSelectedOrgGelQuery(race.getSelectedOrgGelQuery());
                    }
                    if (race.getSelectedOrgDrankQuery() != null) {
                        existingRace.setSelectedOrgDrankQuery(race.getSelectedOrgDrankQuery());
                    }
                    if (race.getSportDrinkOrgBike() != null) {
                        existingRace.setSportDrinkOrgBike(race.getSportDrinkOrgBike());
                    }
                    if (race.getWaterOrgBike() != null) {
                        existingRace.setWaterOrgBike(race.getWaterOrgBike());
                    }
                    if (race.getGelsOrgBike() != null) {
                        existingRace.setGelsOrgBike(race.getGelsOrgBike());
                    }
                    if (race.getSportDrinkOrgRun() != null) {
                        existingRace.setSportDrinkOrgRun(race.getSportDrinkOrgRun());
                    }
                    if (race.getWaterOrgRun() != null) {
                        existingRace.setWaterOrgRun(race.getWaterOrgRun());
                    }
                    if (race.getGelsOrgRun() != null) {
                        existingRace.setGelsOrgRun(race.getGelsOrgRun());
                    }
                    if (race.getOrsBeforeStart() != null) {
                        existingRace.setOrsBeforeStart(race.getOrsBeforeStart());
                    }
                    if (race.getSportDrinkToTakeBike() != null) {
                        existingRace.setSportDrinkToTakeBike(race.getSportDrinkToTakeBike());
                    }
                    if (race.getWaterToTakeBike() != null) {
                        existingRace.setWaterToTakeBike(race.getWaterToTakeBike());
                    }
                    if (race.getExtraWaterToTakeBike() != null) {
                        existingRace.setExtraWaterToTakeBike(race.getExtraWaterToTakeBike());
                    }
                    if (race.getOrsToTakeBike() != null) {
                        existingRace.setOrsToTakeBike(race.getOrsToTakeBike());
                    }
                    if (race.getGelsToTakeBike() != null) {
                        existingRace.setGelsToTakeBike(race.getGelsToTakeBike());
                    }
                    if (race.getSportDrinkToTakeRun() != null) {
                        existingRace.setSportDrinkToTakeRun(race.getSportDrinkToTakeRun());
                    }
                    if (race.getWaterToTakeRun() != null) {
                        existingRace.setWaterToTakeRun(race.getWaterToTakeRun());
                    }
                    if (race.getExtraWaterToTakeRun() != null) {
                        existingRace.setExtraWaterToTakeRun(race.getExtraWaterToTakeRun());
                    }
                    if (race.getOrsToTakeRun() != null) {
                        existingRace.setOrsToTakeRun(race.getOrsToTakeRun());
                    }
                    if (race.getCarboNeedDuringBikeMin() != null) {
                        existingRace.setCarboNeedDuringBikeMin(race.getCarboNeedDuringBikeMin());
                    }
                    if (race.getCarboNeedDuringBikeMax() != null) {
                        existingRace.setCarboNeedDuringBikeMax(race.getCarboNeedDuringBikeMax());
                    }
                    if (race.getCarboNeedDuringRunMin() != null) {
                        existingRace.setCarboNeedDuringRunMin(race.getCarboNeedDuringRunMin());
                    }
                    if (race.getCarboNeedDuringRunMax() != null) {
                        existingRace.setCarboNeedDuringRunMax(race.getCarboNeedDuringRunMax());
                    }
                    if (race.getDiffCarboRun() != null) {
                        existingRace.setDiffCarboRun(race.getDiffCarboRun());
                    }
                    if (race.getDiffMoisterRun() != null) {
                        existingRace.setDiffMoisterRun(race.getDiffMoisterRun());
                    }
                    if (race.getDifCarbo() != null) {
                        existingRace.setDifCarbo(race.getDifCarbo());
                    }
                    if (race.getDifMoister() != null) {
                        existingRace.setDifMoister(race.getDifMoister());
                    }
                    if (race.getGelsToTakeRun() != null) {
                        existingRace.setGelsToTakeRun(race.getGelsToTakeRun());
                    }
                    if (race.getWeightLossDuringBike() != null) {
                        existingRace.setWeightLossDuringBike(race.getWeightLossDuringBike());
                    }
                    if (race.getCarboNeedDuringRun() != null) {
                        existingRace.setCarboNeedDuringRun(race.getCarboNeedDuringRun());
                    }
                    if (race.getFluidNeedPerHourBike() != null) {
                        existingRace.setFluidNeedPerHourBike(race.getFluidNeedPerHourBike());
                    }
                    if (race.getFluidNeedPerHourSwim() != null) {
                        existingRace.setFluidNeedPerHourSwim(race.getFluidNeedPerHourSwim());
                    }
                    if (race.getCarboNeedDuringBike() != null) {
                        existingRace.setCarboNeedDuringBike(race.getCarboNeedDuringBike());
                    }
                    if (race.getFluidNeedDuringBike() != null) {
                        existingRace.setFluidNeedDuringBike(race.getFluidNeedDuringBike());
                    }
                    if (race.getFluidNeedPerHourRun() != null) {
                        existingRace.setFluidNeedPerHourRun(race.getFluidNeedPerHourRun());
                    }
                    if (race.getFluidNeedDuringRun() != null) {
                        existingRace.setFluidNeedDuringRun(race.getFluidNeedDuringRun());
                    }
                    if (race.getWeightLossDuringRun() != null) {
                        existingRace.setWeightLossDuringRun(race.getWeightLossDuringRun());
                    }
                    if (race.getActFluidNeedBike() != null) {
                        existingRace.setActFluidNeedBike(race.getActFluidNeedBike());
                    }
                    if (race.getActFluidNeedRun() != null) {
                        existingRace.setActFluidNeedRun(race.getActFluidNeedRun());
                    }

                    return existingRace;
                }
            )
            .map(raceRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Race> findAll() {
        log.debug("Request to get all Races");
        return raceRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Race> findOne(Long id) {
        log.debug("Request to get Race : {}", id);
        return raceRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Race : {}", id);
        raceRepository.deleteById(id);
    }
}
