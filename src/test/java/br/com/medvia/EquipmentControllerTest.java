package br.com.medvia;

import br.com.medvia.util.TestUtil;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import org.junit.Before;
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
 * @author Willian Kirschner willkev@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(EquipmentController.class)
@WebAppConfiguration
public class EquipmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        ServerController sc = new ServerController();
        sc.dbCreateFakes();
    }

    @Test
    public void list_with_fields() throws Exception {
        this.mockMvc.perform(get("/api/equipments?fields=id,name,institutionId,serieNumber,active"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[0].name", equalTo("Tomografia 24 Horas")))
                .andExpect(jsonPath("$[0].institutionId", equalTo(1)))
                .andExpect(jsonPath("$[0].serieNumber", equalTo("EGH-90876-UKMG")))
                .andExpect(jsonPath("$[0].active", equalTo(1)))
                .andExpect(jsonPath("$[1].id", equalTo(2)))
                .andExpect(jsonPath("$[1].name", equalTo("Ressonancia 20 Tesla")))
                .andExpect(jsonPath("$[2].id", equalTo(3)))
                .andExpect(jsonPath("$[2].name", equalTo("Cama levantadora")))
                .andExpect(jsonPath("$[2].active", equalTo(1)))
                .andExpect(jsonPath("$[5].id", equalTo(6)))
                .andExpect(jsonPath("$[5].institutionId", equalTo(3)))
                .andExpect(jsonPath("$[5].serieNumber", equalTo("0918204-49")))
                .andExpect(jsonPath("$[5].name", equalTo("Nobreak 900mA")));
    }

    @Test
    public void list_query() throws Exception {
        this.mockMvc.perform(get("/api/equipments"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[0].equipment", equalTo("Tomografia 24 Horas")))
                //                .andExpect(jsonPath("$[0].institutionId", equalTo(1)))
                .andExpect(jsonPath("$[0].typeEquipment", equalTo("ACESSÓRIOS")))
                .andExpect(jsonPath("$[0].status", equalTo("a")))
                .andExpect(jsonPath("$[1].id", equalTo(2)))
                .andExpect(jsonPath("$[1].equipment", equalTo("Ressonancia 20 Tesla")))
                .andExpect(jsonPath("$[2].id", equalTo(3)))
                .andExpect(jsonPath("$[2].equipment", equalTo("Cama levantadora")))
                .andExpect(jsonPath("$[2].status", equalTo("a")))
                .andExpect(jsonPath("$[5].id", equalTo(6)))
                //                .andExpect(jsonPath("$[5].institutionId", equalTo(3)))
                .andExpect(jsonPath("$[5].typeEquipment", equalTo("RESSONÂNCIA MAGNÉTICA")))
                .andExpect(jsonPath("$[5].equipment", equalTo("Nobreak 900mA")));

    }

}
