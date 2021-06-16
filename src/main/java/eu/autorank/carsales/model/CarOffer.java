package eu.autorank.carsales.model;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import eu.autorank.carsales.dto.CarDTO;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "car_offers")
public class CarOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String make;
    private String model;
    private BigDecimal price;
    private BodyType body;
    private FuelType fuel;
    private GearboxType gearbox;
    private EcoCategoryType ecoCategory;
    private Date productionYear;
    private boolean registered;
    private Integer power;
    private Date publishedAt;
    @ElementCollection
    @CollectionTable(
            name="photos",
            joinColumns = @JoinColumn(name="PHOTO_ID")
    )
    private List<Photo> photos;
    @ManyToOne
    private User user;
    private Integer mileage;

    public CarOffer(){
    }

    public CarOffer(String title, String make, String model, BigDecimal price, BodyType body, FuelType fuel, GearboxType gearbox, EcoCategoryType ecoCategory, Date productionYear, boolean registered, Integer power, Integer mileage) {
        this.title = title;
        this.make = make;
        this.model = model;
        this.price = price;
        this.body = body;
        this.fuel = fuel;
        this.gearbox = gearbox;
        this.ecoCategory = ecoCategory;
        this.productionYear = productionYear;
        this.registered = registered;
        this.power = power;
        this.mileage = mileage;
    }

    public CarDTO toCarDTO(){
        return new CarDTO(title, make, model, price, body, fuel, gearbox, ecoCategory, productionYear, registered, power, mileage);
    }

    @PrePersist
    void publishedAt(){
        this.publishedAt = new Date();
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BodyType getBody() {
        return body;
    }

    public void setBody(BodyType body) {
        this.body = body;
    }

    public FuelType getFuel() {
        return fuel;
    }

    public void setFuel(FuelType fuel) {
        this.fuel = fuel;
    }

    public GearboxType getGearbox() {
        return gearbox;
    }

    public void setGearbox(GearboxType gearbox) {
        this.gearbox = gearbox;
    }

    public EcoCategoryType getEcoCategory() {
        return ecoCategory;
    }

    public void setEcoCategory(EcoCategoryType ecoCategory) {
        this.ecoCategory = ecoCategory;
    }

    public Date getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(Date productionYear) {
        this.productionYear = productionYear;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public BodyType[] getBodyTypes(){
        BodyType[] values = BodyType.values();
        return values;
    }

    public FuelType[] getFuelTypes(){
        FuelType[] values = FuelType.values();
        return values;
    }

    public GearboxType[] getGearboxTypes(){
        GearboxType[] values = GearboxType.values();
        return values;
    }

    public EcoCategoryType[] getEcoCategories(){
        EcoCategoryType[] values = EcoCategoryType.values();
        return values;
    }

    @Override
    public String toString() {
        return "CarOffer{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", body=" + body +
                ", fuel=" + fuel +
                ", gearbox=" + gearbox +
                ", ecoCategory=" + ecoCategory +
                ", productionYear=" + productionYear +
                ", isRegistered=" + registered +
                ", power=" + power +
                '}';
    }

    public static enum BodyType {
        COMPACT("Compact"), COUPE("Coupe"), CONVERTIBLE("Convertible"), SEDAN("Sedan"), SUV("Suv"), WAGON("Wagon"), VAN("Van"), TRANSPORTER("Transporter"), OTHER("Other");

        private final String text;

        BodyType(String text){
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public static enum FuelType{
        GASOLINE("Gasoline"), DIESEL("Diesel"), ELECTRIC("Electric"), HYBRID("Hybrid"), OTHER("Other");

        private final String text;

        FuelType(String text){
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public static enum GearboxType{
        MANUAL("Manual"), AUTOMATIC("Automatic"), SEMI_AUTOMATIC("Semi Automatic");

        private final String text;

        GearboxType(String text){
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public static enum EcoCategoryType{
        EURO_1("Euro 1"), EURO_2("Euro 2"), EURO_3("Euro 3"), EURO_4("Euro 4"), EURO_5("Euro 5"), EURO_6("Euo 6");

        private final String text;

        EcoCategoryType(String text){
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }
}
