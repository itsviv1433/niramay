package nirmay.healthcare.niramayhealthcare.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Medicine {
    @SerializedName("disease")
    @Expose
    private String disease;
    @SerializedName("med_name")
    @Expose
    private String med_name;
    @SerializedName("med_price")
    @Expose
    private String med_price;
    @SerializedName("status")
    @Expose
    private String status;

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getMed_name() {
        return med_name;
    }

    public void setMed_name(String med_name) {
        this.med_name = med_name;
    }

    public String getMed_price() {
        return med_price;
    }

    public void setMed_price(String med_price) {
        this.med_price = med_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "disease='" + disease + '\'' +
                ", med_name='" + med_name + '\'' +
                ", med_price='" + med_price + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
