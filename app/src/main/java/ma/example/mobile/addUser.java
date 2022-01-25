package ma.example.mobile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import ma.example.mobile.database.dbhelper;
import ma.example.mobile.utils.SharedPref;

public class addUser extends AppCompatActivity {

    dbhelper dbhelper;

    TextInputEditText user_nameedt , adresseedt, emailedt;
    MaterialButton btnadduser ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        dbhelper = new dbhelper(getApplicationContext());
        btnadduser= (MaterialButton) findViewById(R.id.adduserbtn);
        user_nameedt = (TextInputEditText)  findViewById(R.id.user_name_edit_text);
        adresseedt = (TextInputEditText)  findViewById(R.id.adresse_edit_text);
        emailedt = (TextInputEditText)  findViewById(R.id.email_edit_text);


        btnadduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean noErrors = true;
                String user_nametst = user_nameedt.getText().toString();
                String adressetst = adresseedt.getText().toString() ;
                String emailtst = emailedt.getText().toString() ;

                if (user_nametst.isEmpty()) {
                    user_nameedt.setError("Please fill out all fields completely. ");
                    noErrors = false;
                } else {
                    user_nameedt.setError(null);
                }
                if (adressetst.isEmpty()) {
                    adresseedt.setError("Please fill out all fields completely. ");
                    noErrors = false;
                } else {
                    adresseedt.setError(null);
                }
                if (emailtst.isEmpty()) {
                    emailedt.setError("Please fill out all fields completely. ");
                    noErrors = false;
                } else {
                    emailedt.setError(null);
                }

                if (noErrors) {

                    adduser us=    new adduser(getApplicationContext(), user_nametst,adressetst,emailtst,dbhelper);
                        us.execute();



                }
            }
        });
            }


    private class adduser extends AsyncTask<Void,Void,String> {
        String user_name, adresse , emailtst ;
        byte[] bytesimage;
        Context ct;
        dbhelper dbh;

        public adduser(Context applicationContext,  String user_nametst, String adressetst, String emailtst, dbhelper dbhelper) {
            this.user_name =user_nametst;

            this.adresse=adressetst;
            this.emailtst=emailtst;

            this.ct = applicationContext;

            this.dbh=dbhelper;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Toast.makeText(ct, "wait !!! ", Toast.LENGTH_LONG).show();
        }



        @Override
        protected String doInBackground(Void... voids) {
           // int retailer_id = SharedPref.readSharedSettingint(addUser.this,"user_id",0);
            ContentValues values = new ContentValues();

            values.put("user_name", user_name);
            values.put("user_adresse", adresse);
            values.put("user_email", emailtst);

            dbh.Insert(values,"user");
            Intent user = new Intent(addUser.this,UserActivity.class);
            addUser.this.startActivity(user);
            return "user added";


        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(),"response : "+s,Toast.LENGTH_LONG);

        }




    }




}