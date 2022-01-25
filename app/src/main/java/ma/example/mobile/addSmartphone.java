package ma.example.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import ma.example.mobile.database.dbhelper;

public class addSmartphone extends AppCompatActivity {
    ma.example.mobile.database.dbhelper dbhelper;

    TextInputEditText phone_nameedt ;
    MaterialButton btnaddphone ;
    int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_smartphone);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user_id = extras.getInt("user_id");

        }
        dbhelper = new dbhelper(getApplicationContext());
        btnaddphone= (MaterialButton) findViewById(R.id.addphonebtn);
        phone_nameedt = (TextInputEditText)  findViewById(R.id.phone_name_edit_text);

        btnaddphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean noErrors = true;
                String phone_nametst = phone_nameedt.getText().toString();


                if (phone_nametst.isEmpty()) {
                    phone_nameedt.setError("Please fill out all fields completely. ");
                    noErrors = false;
                } else {
                    phone_nameedt.setError(null);
                }


                if (noErrors) {

                    addphone us=    new addphone(getApplicationContext(), phone_nametst,dbhelper);
                    us.execute();



                }
            }
        });
    }


    private class addphone extends AsyncTask<Void,Void,String> {
        String phone_name ;

        Context ct;
        dbhelper dbh;

        public addphone(Context applicationContext,  String phone_nametst, dbhelper dbhelper) {
            this.phone_name =phone_nametst;

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
            ContentValues values = new ContentValues();

            values.put("phone_name", phone_name);
            values.put("userId", user_id);

            dbh.Insert(values,"smartphone");

            Intent i = new Intent(addSmartphone.this, SmartphoneActivity.class);
            i.putExtra("user_id",user_id);
            addSmartphone.this.startActivity(i);
            return "smartphone added";


        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(),"response : "+s,Toast.LENGTH_LONG);

        }




    }




}