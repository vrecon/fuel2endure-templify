package nl.templify.fuel2endure.domain;

import static org.assertj.core.api.Assertions.assertThat;

import nl.templify.fuel2endure.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RacePlanFormTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RacePlanForm.class);
        RacePlanForm racePlanForm1 = new RacePlanForm();
        racePlanForm1.setId(1L);
        RacePlanForm racePlanForm2 = new RacePlanForm();
        racePlanForm2.setId(racePlanForm1.getId());
        assertThat(racePlanForm1).isEqualTo(racePlanForm2);
        racePlanForm2.setId(2L);
        assertThat(racePlanForm1).isNotEqualTo(racePlanForm2);
        racePlanForm1.setId(null);
        assertThat(racePlanForm1).isNotEqualTo(racePlanForm2);
    }
}
