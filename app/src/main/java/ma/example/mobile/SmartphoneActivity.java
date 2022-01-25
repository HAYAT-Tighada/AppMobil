package ma.example.mobile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import ma.example.mobile.adapter.SmartphoneAdapter;
import ma.example.mobile.adapter.UserAdapter;
import ma.example.mobile.database.dbhelper;
import ma.example.mobile.models.SmartphoneModel;
import ma.example.mobile.models.UserModel;

public class SmartphoneActivity extends AppCompatActivity {
    FloatingActionButton fab;
    RecyclerView recyclerView;
    SwipeRefreshLayout refresh ;
    SmartphoneAdapter myAdapter;
   dbhelper dbhelper;
   int user_id;
    public ArrayList<SmartphoneModel> mDataList= new ArrayList<SmartphoneModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smartphone);
        dbhelper = new dbhelper(getApplicationContext());
        recyclerView=(RecyclerView) findViewById(R.id.recyclerViewsmartphone);

        fab  = findViewById(R.id.fabsmartphone);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             user_id = extras.getInt("user_id");

        }
        mDataList = dbhelper.getAllSmartphoneByUserId(user_id);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Snackbar.make(v, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();

                Intent i = new Intent(SmartphoneActivity.this, addSmartphone.class);
                i.putExtra("user_id",user_id);
                startActivity(i);
            }
        });
        refresh = (SwipeRefreshLayout) findViewById(R.id.swiperefreshsmartphone);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDataList = dbhelper.getAllSmartphoneByUserId(user_id);
                myAdapter = new SmartphoneAdapter(SmartphoneActivity.this, mDataList,dbhelper);
                recyclerView.setAdapter(myAdapter);
                LinearLayoutManager mLinearLayoutManagert = new LinearLayoutManager(getApplicationContext());
                mLinearLayoutManagert.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(mLinearLayoutManagert);
                refresh.setRefreshing(false);
            }
        });
        myAdapter = new SmartphoneAdapter(SmartphoneActivity.this, mDataList,dbhelper);
        recyclerView.setAdapter(myAdapter);
        LinearLayoutManager mLinearLayoutManagert = new LinearLayoutManager(getApplicationContext());
        mLinearLayoutManagert.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagert);
        TextInputEditText searchphone = (TextInputEditText) findViewById(R.id.searchsmartphone);
        searchphone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (SmartphoneActivity.this).myAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onBackPressed() {


    startActivity(new Intent(SmartphoneActivity.this,UserActivity.class));
    }


}