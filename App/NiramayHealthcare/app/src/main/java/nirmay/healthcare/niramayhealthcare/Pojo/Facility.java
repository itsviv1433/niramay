package nirmay.healthcare.niramayhealthcare.Pojo;


import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Facility {

    @SerializedName("fac_id")
    @Expose
    private String fac_id;
    @SerializedName("fac_name")
    @Expose
    private String fac_name;
    @SerializedName("fac_city")
    @Expose
    private String fac_city;
    @SerializedName("fac_address")
    @Expose
    private String fac_address;
    @SerializedName("fac_time")
    @Expose
    private String fac_time;
    @SerializedName("fac_contact")
    @Expose
    private String fac_contact;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("beds_occupacy")
    @Expose
    private String beds_occupacy;
    @SerializedName("data_last_updated")
    @Expose
    private String data_last_updated;
    @SerializedName("cities")
    @Expose
    private String cities;

    public String getFac_id() {
        return fac_id;
    }

    public void setFac_id(String fac_id) {
        this.fac_id = fac_id;
    }

    public String getFac_name() {
        return fac_name;
    }

    public void setFac_name(String fac_name) {
        this.fac_name = fac_name;
    }

    public String getFac_city() {
        return fac_city;
    }

    public void setFac_city(String fac_city) {
        this.fac_city = fac_city;
    }

    public String getFac_address() {
        return fac_address;
    }

    public void setFac_address(String fac_address) {
        this.fac_address = fac_address;
    }

    public String getFac_time() {
        return fac_time;
    }

    public void setFac_time(String fac_time) {
        this.fac_time = fac_time;
    }

    public String getFac_contact() {
        return fac_contact;
    }

    public void setFac_contact(String fac_contact) {
        this.fac_contact = fac_contact;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBeds_occupacy() {
        return beds_occupacy;
    }

    public void setBeds_occupacy(String beds_occupacy) {
        this.beds_occupacy = beds_occupacy;
    }

    public String getData_last_updated() {
        return data_last_updated;
    }

    public void setData_last_updated(String data_last_updated) {
        this.data_last_updated = data_last_updated;
    }

    public String getCities() {
        return cities;
    }

    public void setCities(String cities) {
        this.cities = cities;
    }

    @Override
    public String toString() {
        return "Facility{" +
                "fac_id='" + fac_id + '\'' +
                ", fac_name='" + fac_name + '\'' +
                ", fac_city='" + fac_city + '\'' +
                ", fac_address='" + fac_address + '\'' +
                ", fac_time='" + fac_time + '\'' +
                ", fac_contact='" + fac_contact + '\'' +
                ", status='" + status + '\'' +
                ", beds_occupacy='" + beds_occupacy + '\'' +
                ", data_last_updated='" + data_last_updated + '\'' +
                ", cities='" + cities + '\'' +
                '}';
    }
}