package ma.example.mobile.models;

public class PositionModel {
    int positin_id ;
    int phone_id;
    String latitude ;
    String longitude;

    public int getPositin_id() {
        return positin_id;
    }

    public void setPositin_id(int positin_id) {
        this.positin_id = positin_id;
    }

    public int getPhone_id() {
        return phone_id;
    }

    public void setPhone_id(int phone_id) {
        this.phone_id = phone_id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
