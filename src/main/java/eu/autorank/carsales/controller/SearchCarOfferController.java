package eu.autorank.carsales.controller;

import eu.autorank.carsales.model.CarOffer;
import eu.autorank.carsales.repository.CarOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchCarOfferController {
    private final CarOfferRepository carOfferRepository;

    @Autowired
    public SearchCarOfferController(CarOfferRepository carOfferRepository) {
        this.carOfferRepository = carOfferRepository;
    }

    @GetMapping
    public String search(@RequestParam("make") String make, @RequestParam("model") String carModel, Model model){
        List<CarOffer> carOfferList = carOfferRepository.searchOffers(make, carModel);

        model.addAttribute("offers", carOfferList);

        return "search-result";
    }
}
