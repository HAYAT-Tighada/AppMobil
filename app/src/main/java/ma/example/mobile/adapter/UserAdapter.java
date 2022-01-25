package ma.example.mobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ma.example.mobile.R;
import ma.example.mobile.SmartphoneActivity;
import ma.example.mobile.UserActivity;
import ma.example.mobile.database.dbhelper;
import ma.example.mobile.models.UserModel;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> implements Filterable {
    ArrayList<UserModel> mDataList;
    LayoutInflater layoutInflater;
    Context context;
    private ValueFilter valueFilter;
    private ArrayList<UserModel> mStringFilterList;
    dbhelper dbGest;

    public UserAdapter(Context context, ArrayList<UserModel> data, dbhelper db) {

        layoutInflater = LayoutInflater.from(context);
        this.mDataList = data;
        this.mStringFilterList = data;
        this.context = context;
        this.dbGest = db;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.userlistitem, parent, false);

        MyViewHolder holder = new MyViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final UserModel userModel = mDataList.get(position);
        holder.setData(userModel, position);
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbhelper dbh = new dbhelper(context.getApplicationContext());
                dbh.deleteUser(userModel.getUser_id());
             //   mDataList.remove(mDataList.get(position));

                mDataList = dbh.getAllUsers();
                notifyDataSetChanged();
              //  Toast.makeText(contex
                //  t.getApplicationContext(), "btndelete", Toast.LENGTH_LONG).show();

            }
        });

        holder.btnlistsmartphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, SmartphoneActivity.class);
                i.putExtra("user_id",userModel.getUser_id());
                context.startActivity(i);
              //  Toast.makeText(context.getApplicationContext(), "btnlistsmartphone", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView usernametxt, emailtxt, adressetxt;
        Button btndelete, btnlistsmartphone;
        CardView rootCardView;
                 public MyViewHolder(View itemView) {
        super(itemView);


                     usernametxt = (TextView) itemView.findViewById(R.id.usernametxt);
                     emailtxt = (TextView) itemView.findViewById(R.id.emailtxt);
                     adressetxt = (TextView) itemView.findViewById(R.id.adressetxt);
        rootCardView = (CardView) itemView.findViewById(R.id.rootCardView);
        btndelete = (Button) itemView.findViewById(R.id.btndeleteuser);
        btnlistsmartphone = (Button) itemView.findViewById(R.id.btnlistsmartphone);


    }


    public void setData(UserModel userModel, int position) {

        this.usernametxt.setText(userModel.getUser_name());
        this.emailtxt.setText(userModel.getUser_email());
        this.adressetxt.setText(userModel.getUser_adresse());


    }
}


    @Override
    public Filter getFilter() {
        if (valueFilter == null) {

            valueFilter = new ValueFilter();
        }

        return valueFilter;
    }

    private class ValueFilter extends Filter {


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<UserModel> filterList = new ArrayList<UserModel>();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ((mStringFilterList.get(i).getUser_name().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        UserModel user = new UserModel();
                        user.setUser_name(mStringFilterList.get(i).getUser_name());
                        user.setUser_email(mStringFilterList.get(i).getUser_email());
                        user.setUser_adresse(mStringFilterList.get(i).getUser_adresse());
                        filterList.add(user);
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;
        }


        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            mDataList = (ArrayList<UserModel>) results.values;
            notifyDataSetChanged();
        }

    }



}
