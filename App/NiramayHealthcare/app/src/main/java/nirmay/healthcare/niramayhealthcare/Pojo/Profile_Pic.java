package nirmay.healthcare.niramayhealthcare.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile_Pic {
    @SerializedName("user_username")
    @Expose
    private String user_username;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("updated_profile")
    @Expose
    private String updated_profile;
    @SerializedName("status")
    @Expose
    private String status;

    public String getUser_username() {
        return user_username;
    }

    public void setUser_username(String user_username) {
        this.user_username = user_username;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUpdated_profile() {
        return updated_profile;
    }

    public void setUpdated_profile(String updated_profile) {
        this.updated_profile = updated_profile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Profile_Pic{" +
                "user_username='" + user_username + '\'' +
                ", photo='" + photo + '\'' +
                ", updated_profile='" + updated_profile + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
