package br.com.medvia;

import br.com.medvia.mail.EmailSender;
import br.com.medvia.util.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@WebMvcTest(UserController.class)
@WebAppConfiguration
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        EmailSender.TEST_RUNNING = true;
        ServerController sc = new ServerController();
        sc.dbCreateFakes();
    }

    @Test
    public void getByID001() throws Exception {
        this.mockMvc.perform(get("/api/users/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));
    }

    @Test
    public void getByID004() throws Exception {
        this.mockMvc.perform(get("/api/users/{id}", 4))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.email", equalTo("user4@email.com")))
                .andExpect(jsonPath("$.institutions", hasSize(4)))
                .andExpect(jsonPath("$.institutions[0]", equalTo(1)))
                .andExpect(jsonPath("$.institutions[1]", equalTo(2)))
                .andExpect(jsonPath("$.institutions[2]", equalTo(3)))
                .andExpect(jsonPath("$.institutions[3]", equalTo(4)));
    }
}
