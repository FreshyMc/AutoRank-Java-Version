package eu.autorank.carsales.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Controller
public class ImageController {
    @RequestMapping(value = "car_image/{imageName}")
    @ResponseBody
    public byte[] getImage(@PathVariable(value = "imageName") String image) {
        File f = new File("./pictures/" + image);

        byte[] file = null;

        try {
            file = Files.readAllBytes(f.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }
}
