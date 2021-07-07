package nl.templify.fuel2endure.domain;

import static org.assertj.core.api.Assertions.assertThat;

import nl.templify.fuel2endure.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FuelSettingsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FuelSettings.class);
        FuelSettings fuelSettings1 = new FuelSettings();
        fuelSettings1.setId(1L);
        FuelSettings fuelSettings2 = new FuelSettings();
        fuelSettings2.setId(fuelSettings1.getId());
        assertThat(fuelSettings1).isEqualTo(fuelSettings2);
        fuelSettings2.setId(2L);
        assertThat(fuelSettings1).isNotEqualTo(fuelSettings2);
        fuelSettings1.setId(null);
        assertThat(fuelSettings1).isNotEqualTo(fuelSettings2);
    }
}
