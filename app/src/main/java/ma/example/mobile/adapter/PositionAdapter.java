package ma.example.mobile.adapter;

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
import ma.example.mobile.ShowPositionActivity;
import ma.example.mobile.database.dbhelper;
import ma.example.mobile.models.PositionModel;
import ma.example.mobile.models.SmartphoneModel;
import ma.example.mobile.models.UserModel;

public class PositionAdapter extends RecyclerView.Adapter<PositionAdapter.MyViewHolder> implements Filterable {
    ArrayList<PositionModel> mDataList;
    LayoutInflater layoutInflater;
    Context context;
    private ValueFilter valueFilter;
    private ArrayList<PositionModel> mStringFilterList;
    dbhelper dbGest;
    int user_id ;
    int phone_id;
    public PositionAdapter(Context context, ArrayList<PositionModel> data, dbhelper db, int user_id, int phone_id) {

        layoutInflater = LayoutInflater.from(context);
        this.mDataList = data;
        this.mStringFilterList = data;
        this.context = context;
        this.dbGest = db;
        this.user_id = user_id;
        this.phone_id = phone_id;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.positionlistitem, parent, false);

        MyViewHolder holder = new MyViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final PositionModel positionModel = mDataList.get(position);
        holder.setData(positionModel, position);
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbhelper dbh = new dbhelper(context.getApplicationContext());
                dbh.deletePosition(positionModel.getPositin_id());
                mDataList.remove(mDataList.get(position));

           //     mDataList = dbh.get();
                notifyDataSetChanged();
              //  Toast.makeText(contex
                //  t.getApplicationContext(), "btndelete", Toast.LENGTH_LONG).show();

            }
        });

        holder.btnshowposition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ShowPositionActivity.class);

                i.putExtra("longitude",positionModel.getLongitude());
                i.putExtra("latitude",positionModel.getLatitude());
                i.putExtra("user_id",user_id);
                i.putExtra("phone_id",phone_id);
                context.startActivity(i);
               // Toast.makeText(context.getApplicationContext(), "btnlistsmartphone", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView latitudetxt, longitudetxt;
        Button btndelete, btnshowposition;
        CardView rootCardView;
                 public MyViewHolder(View itemView) {
        super(itemView);


                     latitudetxt = (TextView) itemView.findViewById(R.id.latitudetxt);
                     longitudetxt = (TextView) itemView.findViewById(R.id.longitudetxt);

   //     rootCardView = (CardView) itemView.findViewById(R.id.rootCardView);
        btndelete = (Button) itemView.findViewById(R.id.btndeleteposition);
        btnshowposition = (Button) itemView.findViewById(R.id.btnshowposition);


    }


    public void setData(PositionModel positionModel, int position) {

        this.latitudetxt.setText(positionModel.getLatitude());
        this.longitudetxt.setText(positionModel.getLongitude());



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
                ArrayList<PositionModel> filterList = new ArrayList<PositionModel>();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ((mStringFilterList.get(i).getLatitude().toUpperCase())
                            .contains(constraint.toString().toUpperCase()) || (mStringFilterList.get(i).getLongitude().toUpperCase())
                            .contains(constraint.toString().toUpperCase())  ) {
                        PositionModel position = new PositionModel();
                        position.setLongitude(mStringFilterList.get(i).getLongitude());
                        position.setLatitude(mStringFilterList.get(i).getLatitude());

                        filterList.add(position);
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
            mDataList = (ArrayList<PositionModel>) results.values;
            notifyDataSetChanged();
        }

    }



}
