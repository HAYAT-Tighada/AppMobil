package ma.example.mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import ma.example.mobile.database.dbhelper;

public class addPosition extends AppCompatActivity implements LocationListener {
    TextView textlocation,textlocation2;
    dbhelper dbhelper;
    GoogleMap googleMap;
    int CODE_APPEL=103;
    LocationManager lm;
    LatLng pointt;
    ArrayList<LatLng> points ;
    MaterialButton btnadd ;
    SupportMapFragment mapFragment;
    int phone_id;
    int user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_position);
        dbhelper = new dbhelper(getApplicationContext());
        textlocation = (TextView) findViewById(R.id.textlocationaddposition);
        textlocation2 = (TextView) findViewById(R.id.textlocationaddposition2);
        points= new ArrayList<LatLng>();
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
btnadd = (MaterialButton) findViewById(R.id.addpositionbtn);
        subProviders();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            phone_id = extras.getInt("phone_id");
            user_id = extras.getInt("user_id");

        }
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean noErrors = true;
                if (pointt==null) {
                    noErrors = false;
                    Toast.makeText(getApplicationContext(),"s'il vous plait ajouter une position!!!",Toast.LENGTH_LONG).show();
                }

                if (noErrors) {

                    addposition us=    new addposition(getApplicationContext(), pointt.longitude,pointt.latitude,dbhelper);
                        us.execute();



                }
            }
        });

    }

    private class addposition extends AsyncTask<Void,Void,String> {
        String  longitude, latitude;
        Context ct;
        dbhelper dbh;

        public addposition(Context applicationContext,double longitude, double latitude, dbhelper dbhelper) {
            this.longitude= String.valueOf(longitude);
            this.latitude=String.valueOf(latitude);
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

            values.put("latitude", latitude);
            values.put("longitude", longitude);
            values.put("phoneId", phone_id);

            dbh.Insert(values,"position");
            Intent i = new Intent(addPosition.this,PositionActivity.class);
            i.putExtra("user_id",user_id);
            i.putExtra("phone_id",phone_id);
            addPosition.this.startActivity(i);
            return "position added";


        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(),"response : "+s,Toast.LENGTH_LONG);

        }




    }



    protected void subProviders() {
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, CODE_APPEL);
            return;
        }
        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
        if(lm.isProviderEnabled(LocationManager.PASSIVE_PROVIDER))
            lm.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 1000, 0, this);
        if(lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);

        map();
    }

    @SuppressWarnings("MissingPermission")
    protected void map(){
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                addPosition.this.googleMap=googleMap;
                googleMap.moveCamera(CameraUpdateFactory.zoomBy(20));
                googleMap.setMyLocationEnabled(true);
                //   googleMap.addMarker(new MarkerOptions().position(new LatLng(10, 10)).title("Ma position"));
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng point) {
                        pointt=point;
                        points.add(point);
                        googleMap.clear();
                        googleMap.addMarker(new MarkerOptions().position(point));
                        textlocation.setText(point.latitude + " " + point.longitude);
                        textlocation2.setText(point.latitude + " " + point.longitude);
                    }
                });

            }     });





    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        LatLng myposition = new LatLng(latitude,longitude);
        googleMap.moveCamera(CameraUpdateFactory.zoomBy(20));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(myposition));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(addPosition.this, PositionActivity.class);

        i.putExtra("user_id",user_id);
        i.putExtra("phone_id",phone_id);
        addPosition.this.startActivity(i);

    }

}