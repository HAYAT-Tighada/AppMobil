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

import  ma.example.mobile.database.dbhelper;
import java.util.ArrayList;

import ma.example.mobile.adapter.UserAdapter;

import ma.example.mobile.models.UserModel;
import ma.example.mobile.utils.SharedPref;

public class UserActivity extends AppCompatActivity {
    FloatingActionButton fab;
    RecyclerView recyclerView;
    SwipeRefreshLayout refresh ;
    UserAdapter myAdapter;

    dbhelper dbhelper;
    public ArrayList<UserModel> mDataList= new ArrayList<UserModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        dbhelper = new dbhelper(getApplicationContext());
        recyclerView=(RecyclerView) findViewById(R.id.recyclerViewuser);
        mDataList = dbhelper.getAllUsers();
        fab  = findViewById(R.id.fabuser);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Snackbar.make(v, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();
                startActivity(new Intent(getApplicationContext(),addUser.class));
            }
        });
        refresh = (SwipeRefreshLayout) findViewById(R.id.swiperefreshuser);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDataList = dbhelper.getAllUsers();
                myAdapter = new UserAdapter(UserActivity.this, mDataList,dbhelper);
                recyclerView.setAdapter(myAdapter);
                LinearLayoutManager mLinearLayoutManagert = new LinearLayoutManager(getApplicationContext());
                mLinearLayoutManagert.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(mLinearLayoutManagert);
                refresh.setRefreshing(false);
            }
        });
        myAdapter = new UserAdapter(UserActivity.this, mDataList,dbhelper);
        recyclerView.setAdapter(myAdapter);
        LinearLayoutManager mLinearLayoutManagert = new LinearLayoutManager(getApplicationContext());
        mLinearLayoutManagert.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagert);
        TextInputEditText searchuser = (TextInputEditText) findViewById(R.id.searchuser);
        searchuser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (UserActivity.this).myAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    @Override
    public void onBackPressed() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("are you sure want to exit the application?")
                .setCancelable(false)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}