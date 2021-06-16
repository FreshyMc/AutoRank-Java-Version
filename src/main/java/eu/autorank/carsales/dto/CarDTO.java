package eu.autorank.carsales.dto;

import com.sun.istack.NotNull;
import eu.autorank.carsales.model.CarOffer;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

public class CarDTO {
    @NotBlank(message = "Car title cannot be empty")
    private String title;
    @NotBlank(message = "Car make cannot be empty")
    private String make;
    @NotBlank(message = "Car model cannot be empty")
    private String model;
    @javax.validation.constraints.NotNull(message = "Car price cannot be empty")
    @Min(value = 0, message = "Car price cannot be a negative number")
    private BigDecimal price;
    @NotNull
    private CarOffer.BodyType body;
    @NotNull
    private CarOffer.FuelType fuel;
    @NotNull
    private CarOffer.GearboxType gearbox;
    @NotNull
    private CarOffer.EcoCategoryType ecoCategory;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date productionYear;
    @NotNull
    private boolean registered;
    @javax.validation.constraints.NotNull(message = "Car horse power cannot be empty")
    @Min(value = 0, message = "Car horse power cannot be a negative number")
    private Integer power;
    @javax.validation.constraints.NotNull(message = "Car mileage cannot be empty")
    @Min(value = 0, message = "Car mileage cannot be a negative number")
    private Integer mileage;

    public CarDTO(){
    }

    public CarDTO(String title, String make, String model, BigDecimal price, CarOffer.BodyType body, CarOffer.FuelType fuel, CarOffer.GearboxType gearbox, CarOffer.EcoCategoryType ecoCategory, Date productionYear, boolean registered, Integer power, Integer mileage) {
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

    public CarOffer toCarOffer(){
        return new CarOffer(title, make, model, price, body, fuel, gearbox, ecoCategory, productionYear, registered, power, mileage);
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
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

    public CarOffer.BodyType getBody() {
        return body;
    }

    public void setBody(CarOffer.BodyType body) {
        this.body = body;
    }

    public CarOffer.FuelType getFuel() {
        return fuel;
    }

    public void setFuel(CarOffer.FuelType fuel) {
        this.fuel = fuel;
    }

    public CarOffer.GearboxType getGearbox() {
        return gearbox;
    }

    public void setGearbox(CarOffer.GearboxType gearbox) {
        this.gearbox = gearbox;
    }

    public CarOffer.EcoCategoryType getEcoCategory() {
        return ecoCategory;
    }

    public void setEcoCategory(CarOffer.EcoCategoryType ecoCategory) {
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

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public CarOffer.BodyType[] getBodyTypes(){
        CarOffer.BodyType[] values = CarOffer.BodyType.values();
        return values;
    }

    public CarOffer.FuelType[] getFuelTypes(){
        CarOffer.FuelType[] values = CarOffer.FuelType.values();
        return values;
    }

    public CarOffer.GearboxType[] getGearboxTypes(){
        CarOffer.GearboxType[] values = CarOffer.GearboxType.values();
        return values;
    }

    public CarOffer.EcoCategoryType[] getEcoCategories(){
        CarOffer.EcoCategoryType[] values = CarOffer.EcoCategoryType.values();
        return values;
    }

    @Override
    public String toString() {
        return "CarDTO{" +
                "title='" + title + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", body=" + body +
                ", fuel=" + fuel +
                ", gearbox=" + gearbox +
                ", ecoCategory=" + ecoCategory +
                ", productionYear=" + productionYear +
                ", registered=" + registered +
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
