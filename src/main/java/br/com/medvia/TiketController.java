package br.com.medvia;

import br.com.medvia.util.FakesTickets;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Willian
 */
@RestController
@CrossOrigin
public class TiketController {

    @RequestMapping("/api/tickets/list")
    public String getAllTickets(@RequestParam(value = "repeat", defaultValue = "1") int repeat) {
        System.out.println("repeat=" + repeat);
        FakesTickets fakesTickets = new FakesTickets();
        return new Gson().toJson(fakesTickets.generate(repeat));
    }

}
