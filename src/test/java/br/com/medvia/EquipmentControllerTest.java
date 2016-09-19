package br.com.medvia;

import br.com.medvia.util.TestUtil;
import static org.hamcrest.Matchers.hasSize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author Willian
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(EquipmentController.class)
@WebAppConfiguration
public class EquipmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void list() throws Exception {
        String contentExpected = "[{\"description\":\"Tomografia 24 Horas\",\"institutionId\":1,\"brand\":\"Siemens\",\"manufacturer\":\"Siemens\",\"serieNumber\":\"EGH-90876-UKMG\",\"typeEquipmentId\":0,\"workedHoursPerDay\":0,\"active\":true,\"id\":1},{\"description\":\"Ressonancia 20 Tesla\",\"institutionId\":1,\"brand\":\"Philips\",\"manufacturer\":\"Philips\",\"serieNumber\":\"823928276-UKMG\",\"typeEquipmentId\":0,\"workedHoursPerDay\":0,\"active\":true,\"id\":2},{\"description\":\"Cama levantadora\",\"institutionId\":2,\"brand\":\"Siemens\",\"manufacturer\":\"Siemens\",\"serieNumber\":\"OLPK-490196-UKMG\",\"typeEquipmentId\":0,\"workedHoursPerDay\":0,\"active\":true,\"id\":3},{\"description\":\"Arco de luz LED\",\"institutionId\":3,\"brand\":\"Samsung\",\"manufacturer\":\"Samsung\",\"serieNumber\":\"45590-AJQOA-39404\",\"typeEquipmentId\":0,\"workedHoursPerDay\":0,\"active\":true,\"id\":4},{\"description\":\"Furador de crânio\",\"institutionId\":3,\"brand\":\"LG\",\"manufacturer\":\"LG\",\"serieNumber\":\"906627119909\",\"typeEquipmentId\":0,\"workedHoursPerDay\":0,\"active\":true,\"id\":5},{\"description\":\"Nobreak 900mA\",\"institutionId\":3,\"brand\":\"Brastemp\",\"manufacturer\":\"Brastemp\",\"serieNumber\":\"0918204-49\",\"typeEquipmentId\":0,\"workedHoursPerDay\":0,\"active\":true,\"id\":6}]";
        
        this.mockMvc.perform(get("/api/equipments"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(content().string(contentExpected));
    }

}
