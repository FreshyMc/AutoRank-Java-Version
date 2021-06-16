package eu.autorank.carsales.controller;

import eu.autorank.carsales.dto.CarDTO;
import eu.autorank.carsales.model.CarOffer;
import eu.autorank.carsales.model.Photo;
import eu.autorank.carsales.model.User;
import eu.autorank.carsales.repository.CarOfferRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/car")
public class CarOfferController {
    public static final Resource PICTURES_DIR = new FileSystemResource("./pictures");

    private final CarOfferRepository carOfferRepository;

    @Autowired
    public CarOfferController(CarOfferRepository carOfferRepository) {
        this.carOfferRepository = carOfferRepository;
    }

    /* COMPACT("Compact"),
    COUPE("Coupe"),
    CONVERTIBLE("Convertible"),
    SEDAN("Sedan"), SUV("Suv"),
    WAGON("Wagon"), VAN("Van"),
    TRANSPORTER("Transporter"),
    OTHER("Other") */

    @GetMapping("/category/{type}")
    public String showCarOffersByBodyType(@PathVariable CarOffer.BodyType type, Model model){
        List<CarOffer> offers = carOfferRepository.searchOffersByBodyType(type);

        model.addAttribute("offers", offers);

        model.addAttribute("categoryType", type.toString());

        return "body-category";
    }

    @GetMapping("/user-all")
    public String showUserCarOffers(@AuthenticationPrincipal User user, Model model){
        if(user != null){
            List<CarOffer> offers = carOfferRepository.findAllByUserIsOrderByPublishedAtDesc(user);

            model.addAttribute("offers", offers);

            return "my-offers";
        }

        return "redirect:/";
    }

    @GetMapping("/{carId}")
    public String showCarOffer(@PathVariable Long carId, Model model, @AuthenticationPrincipal User user){
        CarOffer carOffer = carOfferRepository.findById(carId).get();

        if(carOffer.getUser().equals(user)){
            model.addAttribute("isOwner", true);
        }

        model.addAttribute("offer", carOffer);

        return "car";
    }

    @GetMapping("/edit/{carId}")
    public String editCarOffer(@PathVariable Long carId, Model model, @AuthenticationPrincipal User user){
        CarOffer carOffer = carOfferRepository.findById(carId).get();

        if(carOffer.getUser().equals(user)){
            model.addAttribute("id", carId);
            model.addAttribute("offer", carOffer.toCarDTO());
            return "edit-car";
        }

        return "redirect:/";
    }

    @PostMapping("/edit/{carId}")
    public String processEditCarOffer(@PathVariable Long carId, @ModelAttribute("offer") @Validated CarDTO offer, BindingResult bindingResult, @AuthenticationPrincipal User user){
        if(bindingResult.hasErrors()){
            return "edit-car";
        }

        CarOffer carOffer = carOfferRepository.findById(carId).get();

        if(!carOffer.getUser().equals(user)){
            return "redirect:/";
        }

        carOffer.setMake(offer.getMake());
        carOffer.setModel(offer.getModel());
        carOffer.setTitle(offer.getTitle());
        carOffer.setPower(offer.getPower());
        carOffer.setRegistered(offer.isRegistered());
        carOffer.setPrice(offer.getPrice());
        carOffer.setProductionYear(offer.getProductionYear());
        carOffer.setEcoCategory(offer.getEcoCategory());
        carOffer.setFuel(offer.getFuel());
        carOffer.setGearbox(offer.getGearbox());
        carOffer.setBody(offer.getBody());
        carOffer.setMileage(offer.getMileage());

        carOfferRepository.save(carOffer);

        return "redirect:/";
    }

    @PostMapping("/delete/{carId}")
    public String deleteCarOffer(@PathVariable Long carId, @AuthenticationPrincipal User user){
        CarOffer carOffer = carOfferRepository.findById(carId).get();

        if(!carOffer.getUser().equals(user)){
            return "redirect:/";
        }

        for(Photo carPhoto : carOffer.getPhotos()){
            new File( "./pictures/" + carPhoto.getPhoto()).delete();
        }

        carOfferRepository.delete(carOffer);

        return "redirect:/";
    }

    @GetMapping("/register")
    public String showCarOfferForm(@ModelAttribute("offer") CarDTO offer){
        return "register-car";
    }

    @PostMapping("/register")
    public String registerCarOffer(@RequestParam("photos") MultipartFile[] photos, @ModelAttribute("offer") @Validated CarDTO offer, BindingResult bindingResult, Model model, RedirectAttributes redAttrs, @AuthenticationPrincipal User user){
        if(bindingResult.hasErrors()){
            return "register-car";
        }

        CarOffer carOffer = offer.toCarOffer();

        if(photos[0].getSize() != 0){
            if(photos.length > 5){
                redAttrs.addFlashAttribute("offer", offer);
                redAttrs.addFlashAttribute("photosErr", "Exceeded photos limit. You can upload only 5 images!");

                return "redirect:/car/register";
            }else if(!areSupported(photos)) {
                redAttrs.addFlashAttribute("offer", offer);
                redAttrs.addFlashAttribute("photosErr", "Uploaded file is with non supported extension!");

                return "redirect:/car/register";
            }

            List<Photo> photoNames = saveFiles(photos).stream().map(photoName -> {
                Photo photo = new Photo();

                photo.setPhoto(photoName);

                return photo;
            }).collect(Collectors.toList());

            carOffer.setPhotos(photoNames);
        }

        carOffer.setUser(user);

        this.carOfferRepository.save(carOffer);

        return "redirect:/";
    }

    private List<String> saveFiles(MultipartFile[] photos) {
        List<String> fileNames = new ArrayList<>();

        for(MultipartFile photo : photos){

            String filename = photo.getOriginalFilename();

            try {
                File temp = File.createTempFile("pic", getFileExtension(filename), PICTURES_DIR.getFile());

                fileNames.add(temp.getName());

                try(InputStream in = photo.getInputStream();
                    OutputStream out = new FileOutputStream(temp)){
                    IOUtils.copy(in, out);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return fileNames;
    }

    private boolean areSupported(MultipartFile[] photos) {
        boolean areSupported = true;

        for(MultipartFile photo : photos){
            if(!isImage(photo)){
                areSupported = false;
                break;
            }
        }

        return areSupported;
    }

    private boolean isImage(MultipartFile file){
        return file.getContentType().startsWith("image");
    }

    private static String getFileExtension(String name){
        return name.substring(name.lastIndexOf("."));
    }
}
