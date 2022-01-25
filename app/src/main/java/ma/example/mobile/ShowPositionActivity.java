package ma.example.mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class ShowPositionActivity extends AppCompatActivity  implements LocationListener {
    int phone_id;
    int user_id;
    int CODE_APPEL=103;
    LocationManager lm;
    LatLng pointt;
    ArrayList<LatLng> points ;
    SupportMapFragment mapFragment;
    GoogleMap googleMap;
    String longitude, latitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_position);
        Bundle extras = getIntent().getExtras();
        points= new ArrayList<LatLng>();
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map2);
        subProviders();
        if (extras != null) {
            phone_id = extras.getInt("phone_id");
            user_id = extras.getInt("user_id");
            longitude = extras.getString("longitude");
            latitude = extras.getString("latitude");

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
                ShowPositionActivity.this.googleMap=googleMap;
             //   googleMap.moveCamera(CameraUpdateFactory.zoomBy(20));
                //   googleMap.setMyLocationEnabled(true);
                Double lo = Double.valueOf(longitude);
                Double la = Double.valueOf(latitude);
                points.add(new LatLng(la, lo));
            //    googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(new LatLng(la, lo)));
                 // googleMap.addMarker(new MarkerOptions().position().title("Ma position"));


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

        Intent i = new Intent(ShowPositionActivity.this, PositionActivity.class);

        i.putExtra("user_id",user_id);
        i.putExtra("phone_id",phone_id);
        ShowPositionActivity.this.startActivity(i);

    }

}