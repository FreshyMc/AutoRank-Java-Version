package eu.autorank.carsales.controller;

import eu.autorank.carsales.dto.CarDTO;
import eu.autorank.carsales.model.CarOffer;
import eu.autorank.carsales.repository.CarOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
public class HomeController {
    private final CarOfferRepository carOfferRepository;

    @Autowired
    public HomeController(CarOfferRepository carOfferRepository) {
        this.carOfferRepository = carOfferRepository;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("welcome", "Welcome to Autorank");

        List<CarOffer> topOffers = carOfferRepository.findWithPageable(PageRequest.of(0, 4, Sort.by(Sort.Direction.DESC, "id")));

        model.addAttribute("topOffers", topOffers);

        model.addAttribute("bodyTypes", CarOffer.BodyType.values());

        return "index";
    }

    @GetMapping("/cars")
    public String showCars(Model model){
        List<CarOffer> offers = carOfferRepository.findAll(Sort.by("publishedAt").descending());

        model.addAttribute("carOffers", offers);

        return "cars";
    }
}
