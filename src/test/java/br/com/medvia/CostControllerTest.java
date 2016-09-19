package br.com.medvia;

import br.com.medvia.util.TestUtil;
import static org.hamcrest.Matchers.containsString;
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
@WebMvcTest(CostController.class)
@WebAppConfiguration
public class CostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void listByTicket() throws Exception {

        // inserção de Users é randomica!
        String contentPart1 = "[{\"date\":\"18/09/2016 17:51\",\"description\":\"2 horas do técnico para instalar o equipamento\",\"Id\":1,\"userId\":";
        String contentPart2 = "},{\"date\":\"18/09/2016 17:51\",\"description\":\"Necessitou chamar um técnico na madrugada do final de semana, por isso custou mais caro\",\"Id\":2,\"userId\":";
        String contentPart3 = "},{\"date\":\"18/09/2016 17:51\",\"description\":\"50 pila de cabos blindados\",\"Id\":3,\"userId\":";

        this.mockMvc.perform(get("/api/tickets/{id}/costs", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(content().string(containsString(contentPart1)))
                .andExpect(content().string(containsString(contentPart2)))
                .andExpect(content().string(containsString(contentPart3)));
    }
    
    @Test
    public void getById() throws Exception {

        // inserção de Users é randomica!
        String contentPart1 = "{\"cost\":240.0,\"description\":\"Necessitou chamar um técnico na madrugada do final de semana, por isso custou mais caro\",\"userId\":";
        String contentPart2 = ",\"tickteId\":1,\"date\":\"18/09/2016 17:51\",\"Id\":2}";

        this.mockMvc.perform(get("/api/costs/{id}", 2))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(content().string(containsString(contentPart1)))
                .andExpect(content().string(containsString(contentPart2)));
    }    

}
