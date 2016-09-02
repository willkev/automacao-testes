package br.com.medvia.resources;

import br.com.medvia.EquipmentController;
import br.com.medvia.TicketController;
import br.com.medvia.UserController;
import br.com.medvia.db.DBManager;
import com.google.gson.Gson;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author willian.kirschner
 */
public class TicketTest {

    public TicketTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        DBManager.getInstance().getDbUser().dropAndCreateTable();
        DBManager.getInstance().getDbEquipment().dropAndCreateTable();
        DBManager.getInstance().getDbTicket().dropAndCreateTable();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test_simple() {
        UserController uc = new UserController();
        uc.createfakes();

        EquipmentController ec = new EquipmentController();
        ec.createfakes();

        TicketController tc = new TicketController();
        tc.createFakes();

        List<Map<String, Object>> executeQuery = DBManager.getInstance().getDbTicket().executeQuery(TicketController.QUERY_LIST);
        System.out.println("JSON:\n" + new Gson().toJson(executeQuery));
    }

}
