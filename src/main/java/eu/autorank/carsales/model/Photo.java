package eu.autorank.carsales.model;

import javax.persistence.*;

@Embeddable
public class Photo {
    @Column(name = "PHOTO_NAME")
    private String photoName;

    public Photo() {
    }

    public String getPhoto() {
        return photoName;
    }

    public void setPhoto(String photo) {
        this.photoName = photo;
    }
}
