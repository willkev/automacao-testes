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
 * @author Willian
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(InstitutionController.class)
@WebAppConfiguration
public class InstitutionControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getInstitutions() throws Exception {
        this.mockMvc.perform(get("/api/institutions"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(content().string("[{\"description\":\"Menino Deus\",\"id\":1},{\"description\":\"Hospital de Clínicas (POA)\",\"id\":2},{\"description\":\"Moinhos de Vento\",\"id\":3}]"));
    }

    @Test
    public void listEquipmentsByInstitution() throws Exception {
        this.mockMvc.perform(get("/api/institutions/{id}/equipments", 3))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(content().string("[{\"description\":\"Arco de luz LED\",\"institutionId\":3,\"brand\":\"Samsung\",\"manufacturer\":\"Samsung\",\"serieNumber\":\"45590-AJQOA-39404\",\"typeEquipmentId\":0,\"workedHoursPerDay\":0,\"active\":true,\"id\":4},{\"description\":\"Furador de crânio\",\"institutionId\":3,\"brand\":\"LG\",\"manufacturer\":\"LG\",\"serieNumber\":\"906627119909\",\"typeEquipmentId\":0,\"workedHoursPerDay\":0,\"active\":true,\"id\":5},{\"description\":\"Nobreak 900mA\",\"institutionId\":3,\"brand\":\"Brastemp\",\"manufacturer\":\"Brastemp\",\"serieNumber\":\"0918204-49\",\"typeEquipmentId\":0,\"workedHoursPerDay\":0,\"active\":true,\"id\":6}]"));
    }

    @Test
    public void listEquipmentsByInstitutionWithFields() throws Exception {
        this.mockMvc.perform(get("/api/institutions/{id}/equipments", 1).param("fields", "description,institutionId,typeEquipmentId,id"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(content().string("[{\"typeEquipmentId\":0,\"description\":\"Tomografia 24 Horas\",\"id\":1,\"institutionId\":1},{\"typeEquipmentId\":0,\"description\":\"Ressonancia 20 Tesla\",\"id\":2,\"institutionId\":1}]"));
    }
}
