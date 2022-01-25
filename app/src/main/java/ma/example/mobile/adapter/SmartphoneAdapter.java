package ma.example.mobile.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ma.example.mobile.PositionActivity;
import ma.example.mobile.R;
import ma.example.mobile.SmartphoneActivity;
import ma.example.mobile.database.dbhelper;
import ma.example.mobile.models.SmartphoneModel;
import ma.example.mobile.models.UserModel;

public class SmartphoneAdapter extends RecyclerView.Adapter<SmartphoneAdapter.MyViewHolder> implements Filterable {
    ArrayList<SmartphoneModel> mDataList;
    LayoutInflater layoutInflater;
    Context context;
    private ValueFilter valueFilter;
    private ArrayList<SmartphoneModel> mStringFilterList;
    dbhelper dbGest;

    public SmartphoneAdapter(Context context, ArrayList<SmartphoneModel> data, dbhelper db) {

        layoutInflater = LayoutInflater.from(context);
        this.mDataList = data;
        this.mStringFilterList = data;
        this.context = context;
        this.dbGest = db;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.smartphonelistitem, parent, false);

        MyViewHolder holder = new MyViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,  int position) {

        final SmartphoneModel smartphoneModel = mDataList.get(position);
        holder.setData(smartphoneModel, position);
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbhelper dbh = new dbhelper(context.getApplicationContext());
                dbh.deleteSmartphone(smartphoneModel.getPhone_id());
                mDataList.remove(mDataList.get(position));

             //   mDataList = dbh.getAllSmartphoneByUserId();
                notifyDataSetChanged();
              //  Toast.makeText(contex
                //  t.getApplicationContext(), "btndelete", Toast.LENGTH_LONG).show();

            }
        });

        holder.btnlistposition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PositionActivity.class);
                i.putExtra("phone_id",smartphoneModel.getPhone_id());
                int user_id = smartphoneModel.getUser_id();
                i.putExtra("user_id",smartphoneModel.getUser_id());
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

        TextView phone_name;
        Button btndelete, btnlistposition;
        CardView rootCardView;
                 public MyViewHolder(View itemView) {
        super(itemView);


                     phone_name = (TextView) itemView.findViewById(R.id.phonetxt);

     //   rootCardView = (CardView) itemView.findViewById(R.id.rootCardView);
        btndelete = (Button) itemView.findViewById(R.id.btndeletesmartphone);
        btnlistposition = (Button) itemView.findViewById(R.id.btnlistpositions);


    }


    public void setData(SmartphoneModel smartphoneModel, int position) {

        this.phone_name.setText(smartphoneModel.getPhone_name());


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
                ArrayList<SmartphoneModel> filterList = new ArrayList<SmartphoneModel>();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ((mStringFilterList.get(i).getPhone_name().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        SmartphoneModel phone = new SmartphoneModel();
                        phone.setPhone_name(mStringFilterList.get(i).getPhone_name());

                        filterList.add(phone);
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
            mDataList = (ArrayList<SmartphoneModel>) results.values;
            notifyDataSetChanged();
        }

    }



}
