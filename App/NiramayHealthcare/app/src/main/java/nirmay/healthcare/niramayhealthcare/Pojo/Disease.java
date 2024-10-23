package nirmay.healthcare.niramayhealthcare.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Disease {

    @SerializedName("disease_name")
    @Expose
    private String disease_name;
    @SerializedName("disease_symptoms")
    @Expose
    private String disease_symptoms;
    @SerializedName("modes_of_transmission")
    @Expose
    private String modes_of_transmission;
    @SerializedName("disease_detail")
    @Expose
    private String disease_detail;
    @SerializedName("disease_precaution")
    @Expose
    private String disease_precaution;
    @SerializedName("test_name")
    @Expose
    private String test_name;
    @SerializedName("test_price")
    @Expose
    private String test_price;
    @SerializedName("test_details")
    @Expose
    private String test_details;
    @SerializedName("vaccine_name")
    @Expose
    private String vaccine_name;
    @SerializedName("vaccine_price")
    @Expose
    private String vaccine_price;

    public String getDisease_name() {
        return disease_name;
    }

    public void setDisease_name(String disease_name) {
        this.disease_name = disease_name;
    }

    public String getDisease_symptoms() {
        return disease_symptoms;
    }

    public void setDisease_symptoms(String disease_symptoms) {
        this.disease_symptoms = disease_symptoms;
    }

    public String getModes_of_transmission() {
        return modes_of_transmission;
    }

    public void setModes_of_transmission(String modes_of_transmission) {
        this.modes_of_transmission = modes_of_transmission;
    }

    public String getDisease_detail() {
        return disease_detail;
    }

    public void setDisease_detail(String disease_detail) {
        this.disease_detail = disease_detail;
    }

    public String getDisease_precaution() {
        return disease_precaution;
    }

    public void setDisease_precaution(String disease_precaution) {
        this.disease_precaution = disease_precaution;
    }

    public String getTest_name() {
        return test_name;
    }

    public void setTest_name(String test_name) {
        this.test_name = test_name;
    }

    public String getTest_price() {
        return test_price;
    }

    public void setTest_price(String test_price) {
        this.test_price = test_price;
    }

    public String getTest_details() {
        return test_details;
    }

    public void setTest_details(String test_details) {
        this.test_details = test_details;
    }

    public String getVaccine_name() {
        return vaccine_name;
    }

    public void setVaccine_name(String vaccine_name) {
        this.vaccine_name = vaccine_name;
    }

    public String getVaccine_price() {
        return vaccine_price;
    }

    public void setVaccine_price(String vaccine_price) {
        this.vaccine_price = vaccine_price;
    }

    @Override
    public String toString() {
        return "Disease{" +
                "disease_name='" + disease_name + '\'' +
                ", disease_symptoms='" + disease_symptoms + '\'' +
                ", modes_of_transmission='" + modes_of_transmission + '\'' +
                ", disease_detail='" + disease_detail + '\'' +
                ", disease_precaution='" + disease_precaution + '\'' +
                ", test_name='" + test_name + '\'' +
                ", test_price='" + test_price + '\'' +
                ", test_details='" + test_details + '\'' +
                ", vaccine_name='" + vaccine_name + '\'' +
                ", vaccine_price='" + vaccine_price + '\'' +
                '}';
    }
}
