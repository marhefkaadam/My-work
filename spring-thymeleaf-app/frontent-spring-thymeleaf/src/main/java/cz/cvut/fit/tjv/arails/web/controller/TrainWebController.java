package cz.cvut.fit.tjv.arails.web.controller;

import cz.cvut.fit.tjv.arails.web.client.ManufacturerClient;
import cz.cvut.fit.tjv.arails.web.client.TrainClient;
import cz.cvut.fit.tjv.arails.web.model.TrainModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Controller
public class TrainWebController {
    private final TrainClient trainClient;
    private final ManufacturerClient manufacturerClient;

    public TrainWebController(TrainClient trainClient, ManufacturerClient manufacturerClient) {
        this.trainClient = trainClient;
        this.manufacturerClient = manufacturerClient;
    }

    @GetMapping("/")
    public String getAllTrains(Model model) {
        model.addAttribute("trains", trainClient.readAll().sort((obj1, obj2) -> obj1.getProductionYear().compareTo(obj2.getProductionYear())) );
        model.addAttribute("manufacturers", manufacturerClient.readAll());
        Integer productionYear = -1;
        model.addAttribute(productionYear);

        return "trains";
    }

    @GetMapping("/add")
    public String addTrainGet(Model model) {
        model.addAttribute("trainModel", new TrainModel());
        model.addAttribute("manufacturers", manufacturerClient.readAll());

        return "addTrain";
    }

    @PostMapping("/add")
    public String addTrainSubmit(Model model, @ModelAttribute TrainModel trainModel) {
        model.addAttribute("trainModel", trainClient.create(trainModel));

        return "redirect:/";
    }

    @GetMapping(value = "/edit/{id}")
    public String editTrainGet(Model model, @PathVariable("id") Integer id) {
        model.addAttribute( "trainModel", trainClient.readById(id));
        model.addAttribute("manufacturers", manufacturerClient.readAll());

        return "editTrain";
    }

    @PostMapping(value = "/edit")
    public String editTrainSubmit(Model model, @ModelAttribute TrainModel trainModel, BindingResult bindingResult) {
        model.addAttribute("manufacturers", manufacturerClient.readAll());
        model.addAttribute( "trainModel", trainClient.update(trainModel).onErrorReturn(new TrainModel(trainModel, true)));

        return "editTrain";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteTrain(Model model, @PathVariable("id") Integer id) {
        model.addAttribute( "train", trainClient.delete(id));

        return "redirect:/";
    }

}
