package br.com.medvia;

import br.com.medvia.util.TestUtil;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isOneOf;
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
@WebMvcTest(CostController.class)
@WebAppConfiguration
public class CostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void listByTicket() throws Exception {

        this.mockMvc.perform(get("/api/tickets/{id}/costs", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[0].userId", isOneOf(1, 2, 3, 4, 5, 6)))
                .andExpect(jsonPath("$[0].value", equalTo(120.77)))
                .andExpect(jsonPath("$[0].description", containsString("2 horas do técnico para instalar o equipamento")))
                .andExpect(jsonPath("$[0].date", anything()))
                .andExpect(jsonPath("$[2].id", equalTo(3)))
                .andExpect(jsonPath("$[2].userId", isOneOf(1, 2, 3, 4, 5, 6)))
                .andExpect(jsonPath("$[2].value", equalTo(50.00)))
                .andExpect(jsonPath("$[2].description", containsString("50 pila de cabos blindados")))
                .andExpect(jsonPath("$[2].date", anything()))
                .andExpect(jsonPath("$[0].tickteId").doesNotExist())
                .andExpect(jsonPath("$[1].tickteId").doesNotExist())
                .andExpect(jsonPath("$[2].tickteId").doesNotExist());
    }

    @Test
    public void getById() throws Exception {

        this.mockMvc.perform(get("/api/costs/{id}", 2))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", equalTo(2)))
                .andExpect(jsonPath("$.tickteId", equalTo(1)))
                .andExpect(jsonPath("$.userId", isOneOf(1, 2, 3, 4, 5, 6)))
                .andExpect(jsonPath("$.value", equalTo(240.00)))
                .andExpect(jsonPath("$.description", containsString("Necessitou chamar um técnico na madrugada do final de semana, por isso custou mais caro")))
                .andExpect(jsonPath("$.date", anything()));
    }

}
