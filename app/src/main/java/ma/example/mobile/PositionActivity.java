package ma.example.mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import ma.example.mobile.adapter.PositionAdapter;

import ma.example.mobile.database.dbhelper;
import ma.example.mobile.models.PositionModel;


public class PositionActivity extends AppCompatActivity {
    FloatingActionButton fab;
    RecyclerView recyclerView;
    SwipeRefreshLayout refresh ;
    PositionAdapter myAdapter;
    ma.example.mobile.database.dbhelper dbhelper;
    int phone_id;
    int user_id;
    public ArrayList<PositionModel> mDataList= new ArrayList<PositionModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);
        dbhelper = new dbhelper(getApplicationContext());
        recyclerView=(RecyclerView) findViewById(R.id.recyclerViewposition);

        fab  = findViewById(R.id.fabposition);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            phone_id = extras.getInt("phone_id");
            user_id = extras.getInt("user_id");

        }
        mDataList = dbhelper.getAllPositionsBySmartphone(phone_id);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Snackbar.make(v, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();

                Intent i = new Intent(PositionActivity.this, addPosition.class);
                i.putExtra("phone_id",phone_id);
                i.putExtra("user_id",user_id);
                startActivity(i);
            }
        });
        refresh = (SwipeRefreshLayout) findViewById(R.id.swiperefreshposition);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDataList = dbhelper.getAllPositionsBySmartphone(phone_id);
                myAdapter = new PositionAdapter(PositionActivity.this, mDataList,dbhelper,user_id, phone_id);
                recyclerView.setAdapter(myAdapter);
                LinearLayoutManager mLinearLayoutManagert = new LinearLayoutManager(getApplicationContext());
                mLinearLayoutManagert.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(mLinearLayoutManagert);
                refresh.setRefreshing(false);
            }
        });
        myAdapter = new PositionAdapter(PositionActivity.this, mDataList,dbhelper,user_id,phone_id);
        recyclerView.setAdapter(myAdapter);
        LinearLayoutManager mLinearLayoutManagert = new LinearLayoutManager(getApplicationContext());
        mLinearLayoutManagert.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagert);
        TextInputEditText searchphone = (TextInputEditText) findViewById(R.id.searchposition);
        searchphone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (PositionActivity.this).myAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(PositionActivity.this, SmartphoneActivity.class);

        i.putExtra("user_id",user_id);
        PositionActivity.this.startActivity(i);

    }

}