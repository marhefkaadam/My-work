package cz.cvut.fit.tjv.arails.web.controller;

import cz.cvut.fit.tjv.arails.web.client.ManufacturerClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class ManufacturerWebController {
    private final ManufacturerClient manufacturerClient;

    public ManufacturerWebController(ManufacturerClient manufacturerClient) {
        this.manufacturerClient = manufacturerClient;
    }

    @GetMapping("/manufacturers/")
    String getManufacturersByProductionYear(Model model, @RequestParam Integer productionYear) {
        model.addAttribute( "manufacturers", manufacturerClient.readManufacturersByYear(productionYear));

        return "manufacturers";
    }
}

