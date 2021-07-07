package nl.templify.fuel2endure.service.impl;

import java.util.List;
import java.util.Optional;
import nl.templify.fuel2endure.domain.RacePlanForm;
import nl.templify.fuel2endure.repository.RacePlanFormRepository;
import nl.templify.fuel2endure.service.RacePlanFormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RacePlanForm}.
 */
@Service
@Transactional
public class RacePlanFormServiceImpl implements RacePlanFormService {

    private final Logger log = LoggerFactory.getLogger(RacePlanFormServiceImpl.class);

    private final RacePlanFormRepository racePlanFormRepository;

    public RacePlanFormServiceImpl(RacePlanFormRepository racePlanFormRepository) {
        this.racePlanFormRepository = racePlanFormRepository;
    }

    @Override
    public RacePlanForm save(RacePlanForm racePlanForm) {
        log.debug("Request to save RacePlanForm : {}", racePlanForm);
        return racePlanFormRepository.save(racePlanForm);
    }

    @Override
    public Optional<RacePlanForm> partialUpdate(RacePlanForm racePlanForm) {
        log.debug("Request to partially update RacePlanForm : {}", racePlanForm);

        return racePlanFormRepository
            .findById(racePlanForm.getId())
            .map(
                existingRacePlanForm -> {
                    if (racePlanForm.getComp() != null) {
                        existingRacePlanForm.setComp(racePlanForm.getComp());
                    }
                    if (racePlanForm.getName() != null) {
                        existingRacePlanForm.setName(racePlanForm.getName());
                    }
                    if (racePlanForm.getSelectedOrgGelQuery() != null) {
                        existingRacePlanForm.setSelectedOrgGelQuery(racePlanForm.getSelectedOrgGelQuery());
                    }
                    if (racePlanForm.getSelectedOrgDrankQuery() != null) {
                        existingRacePlanForm.setSelectedOrgDrankQuery(racePlanForm.getSelectedOrgDrankQuery());
                    }
                    if (racePlanForm.getOrsBeforeStart() != null) {
                        existingRacePlanForm.setOrsBeforeStart(racePlanForm.getOrsBeforeStart());
                    }
                    if (racePlanForm.getDrinkCarbo() != null) {
                        existingRacePlanForm.setDrinkCarbo(racePlanForm.getDrinkCarbo());
                    }
                    if (racePlanForm.getGelCarbo() != null) {
                        existingRacePlanForm.setGelCarbo(racePlanForm.getGelCarbo());
                    }
                    if (racePlanForm.getDrinkOrgCarbo() != null) {
                        existingRacePlanForm.setDrinkOrgCarbo(racePlanForm.getDrinkOrgCarbo());
                    }
                    if (racePlanForm.getGelOrgCarbo() != null) {
                        existingRacePlanForm.setGelOrgCarbo(racePlanForm.getGelOrgCarbo());
                    }
                    if (racePlanForm.getSportDrinkOrgBike() != null) {
                        existingRacePlanForm.setSportDrinkOrgBike(racePlanForm.getSportDrinkOrgBike());
                    }
                    if (racePlanForm.getWaterOrgBike() != null) {
                        existingRacePlanForm.setWaterOrgBike(racePlanForm.getWaterOrgBike());
                    }
                    if (racePlanForm.getGelsOrgBike() != null) {
                        existingRacePlanForm.setGelsOrgBike(racePlanForm.getGelsOrgBike());
                    }
                    if (racePlanForm.getSportDrinkOrgRun() != null) {
                        existingRacePlanForm.setSportDrinkOrgRun(racePlanForm.getSportDrinkOrgRun());
                    }
                    if (racePlanForm.getWaterOrgRun() != null) {
                        existingRacePlanForm.setWaterOrgRun(racePlanForm.getWaterOrgRun());
                    }
                    if (racePlanForm.getGelsOrgRun() != null) {
                        existingRacePlanForm.setGelsOrgRun(racePlanForm.getGelsOrgRun());
                    }
                    if (racePlanForm.getSportDrinkToTakeBike() != null) {
                        existingRacePlanForm.setSportDrinkToTakeBike(racePlanForm.getSportDrinkToTakeBike());
                    }
                    if (racePlanForm.getWaterToTakeBike() != null) {
                        existingRacePlanForm.setWaterToTakeBike(racePlanForm.getWaterToTakeBike());
                    }
                    if (racePlanForm.getExtraWaterToTakeBike() != null) {
                        existingRacePlanForm.setExtraWaterToTakeBike(racePlanForm.getExtraWaterToTakeBike());
                    }
                    if (racePlanForm.getOrsToTakeBike() != null) {
                        existingRacePlanForm.setOrsToTakeBike(racePlanForm.getOrsToTakeBike());
                    }
                    if (racePlanForm.getGelsToTakeBike() != null) {
                        existingRacePlanForm.setGelsToTakeBike(racePlanForm.getGelsToTakeBike());
                    }
                    if (racePlanForm.getSportDrinkToTakeRun() != null) {
                        existingRacePlanForm.setSportDrinkToTakeRun(racePlanForm.getSportDrinkToTakeRun());
                    }
                    if (racePlanForm.getWaterToTakeRun() != null) {
                        existingRacePlanForm.setWaterToTakeRun(racePlanForm.getWaterToTakeRun());
                    }
                    if (racePlanForm.getExtraWaterToTakeRun() != null) {
                        existingRacePlanForm.setExtraWaterToTakeRun(racePlanForm.getExtraWaterToTakeRun());
                    }
                    if (racePlanForm.getOrsToTakeRun() != null) {
                        existingRacePlanForm.setOrsToTakeRun(racePlanForm.getOrsToTakeRun());
                    }
                    if (racePlanForm.getGelsToTakeRun() != null) {
                        existingRacePlanForm.setGelsToTakeRun(racePlanForm.getGelsToTakeRun());
                    }
                    if (racePlanForm.getWeightLossDuringBike() != null) {
                        existingRacePlanForm.setWeightLossDuringBike(racePlanForm.getWeightLossDuringBike());
                    }
                    if (racePlanForm.getCarboNeedDuringRun() != null) {
                        existingRacePlanForm.setCarboNeedDuringRun(racePlanForm.getCarboNeedDuringRun());
                    }
                    if (racePlanForm.getFluidNeedPerHourBike() != null) {
                        existingRacePlanForm.setFluidNeedPerHourBike(racePlanForm.getFluidNeedPerHourBike());
                    }
                    if (racePlanForm.getFluidNeedPerHourSwim() != null) {
                        existingRacePlanForm.setFluidNeedPerHourSwim(racePlanForm.getFluidNeedPerHourSwim());
                    }
                    if (racePlanForm.getCarboNeedDuringBike() != null) {
                        existingRacePlanForm.setCarboNeedDuringBike(racePlanForm.getCarboNeedDuringBike());
                    }
                    if (racePlanForm.getFluidNeedDuringBike() != null) {
                        existingRacePlanForm.setFluidNeedDuringBike(racePlanForm.getFluidNeedDuringBike());
                    }
                    if (racePlanForm.getFluidNeedPerHourRun() != null) {
                        existingRacePlanForm.setFluidNeedPerHourRun(racePlanForm.getFluidNeedPerHourRun());
                    }
                    if (racePlanForm.getFluidNeedDuringRun() != null) {
                        existingRacePlanForm.setFluidNeedDuringRun(racePlanForm.getFluidNeedDuringRun());
                    }
                    if (racePlanForm.getWeightLossDuringRun() != null) {
                        existingRacePlanForm.setWeightLossDuringRun(racePlanForm.getWeightLossDuringRun());
                    }
                    if (racePlanForm.getDiffCarboRun() != null) {
                        existingRacePlanForm.setDiffCarboRun(racePlanForm.getDiffCarboRun());
                    }
                    if (racePlanForm.getDiffMoisterRun() != null) {
                        existingRacePlanForm.setDiffMoisterRun(racePlanForm.getDiffMoisterRun());
                    }
                    if (racePlanForm.getDifCarbo() != null) {
                        existingRacePlanForm.setDifCarbo(racePlanForm.getDifCarbo());
                    }
                    if (racePlanForm.getDifMoister() != null) {
                        existingRacePlanForm.setDifMoister(racePlanForm.getDifMoister());
                    }
                    if (racePlanForm.getActFluidNeedBike() != null) {
                        existingRacePlanForm.setActFluidNeedBike(racePlanForm.getActFluidNeedBike());
                    }
                    if (racePlanForm.getActFluidNeedRun() != null) {
                        existingRacePlanForm.setActFluidNeedRun(racePlanForm.getActFluidNeedRun());
                    }
                    if (racePlanForm.getCarboNeedDuringBikeMin() != null) {
                        existingRacePlanForm.setCarboNeedDuringBikeMin(racePlanForm.getCarboNeedDuringBikeMin());
                    }
                    if (racePlanForm.getCarboNeedDuringBikeMax() != null) {
                        existingRacePlanForm.setCarboNeedDuringBikeMax(racePlanForm.getCarboNeedDuringBikeMax());
                    }
                    if (racePlanForm.getCarboNeedDuringRunMin() != null) {
                        existingRacePlanForm.setCarboNeedDuringRunMin(racePlanForm.getCarboNeedDuringRunMin());
                    }
                    if (racePlanForm.getCarboNeedDuringRunMax() != null) {
                        existingRacePlanForm.setCarboNeedDuringRunMax(racePlanForm.getCarboNeedDuringRunMax());
                    }
                    if (racePlanForm.getStartGel() != null) {
                        existingRacePlanForm.setStartGel(racePlanForm.getStartGel());
                    }
                    if (racePlanForm.getStartDrank() != null) {
                        existingRacePlanForm.setStartDrank(racePlanForm.getStartDrank());
                    }

                    return existingRacePlanForm;
                }
            )
            .map(racePlanFormRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RacePlanForm> findAll() {
        log.debug("Request to get all RacePlanForms");
        return racePlanFormRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RacePlanForm> findOne(Long id) {
        log.debug("Request to get RacePlanForm : {}", id);
        return racePlanFormRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RacePlanForm : {}", id);
        racePlanFormRepository.deleteById(id);
    }
}
