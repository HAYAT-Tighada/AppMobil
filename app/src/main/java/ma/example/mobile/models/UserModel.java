package ma.example.mobile.models;

public class UserModel {
    int user_id ;
    String user_name;
    String user_email;
    String user_adresse;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_adresse() {
        return user_adresse;
    }

    public void setUser_adresse(String user_adresse) {
        this.user_adresse = user_adresse;
    }
}
