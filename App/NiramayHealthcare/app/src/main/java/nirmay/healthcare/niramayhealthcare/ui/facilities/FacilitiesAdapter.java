package nirmay.healthcare.niramayhealthcare.ui.facilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import nirmay.healthcare.niramayhealthcare.Pojo.Facility;
import nirmay.healthcare.niramayhealthcare.R;

public class FacilitiesAdapter extends RecyclerView.Adapter<FacilitiesAdapter.Facilitiesviewholder> {
    Context context;
    //String [] data;
    ArrayList<Facility> data;

    public FacilitiesAdapter(Context context, ArrayList<Facility> data) {
        this.context = context;
        this.data = data;
    }

     //String[] data;
//        public FacilitiesAdapter(String [] data){
//           this.data = data;
//        }
    @NonNull
    @Override
    public Facilitiesviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.facilities_details_layout,parent,false);
        return new Facilitiesviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Facilitiesviewholder holder, int position) {
//        holder.facilityname_txtview.setText(data[0]);
//        holder.facilityaddress_txtview.setText(data[1]);
//        holder.facilitytime_txtview.setText(data[2]);
//        holder.facilitycontact_txtview.setText(data[3]);

        holder.facilityname_txtview.setText(data.get(position).getFac_name());
        holder.facilityaddress_txtview.setText(data.get(position).getFac_address());
//        holder.facilitytime_txtview.setText(data.get(position).getFac_time());
        holder.facilitycontact_txtview.setText(data.get(position).getFac_contact());
    }

    @Override
    public int getItemCount() {
        //return (data.size());
        return  data.size();
    }

    public class Facilitiesviewholder extends RecyclerView.ViewHolder{

        TextView facilityname_txtview;
        TextView facilityaddress_txtview;
        TextView facilitytime_txtview;
        TextView facilitycontact_txtview;

        public Facilitiesviewholder(@NonNull View itemView) {
            super(itemView);
            facilityname_txtview = (TextView)itemView.findViewById(R.id.facility_name);
            facilityaddress_txtview = (TextView)itemView.findViewById(R.id.facility_address);
//            facilitytime_txtview = (TextView)itemView.findViewById(R.id.facility_time);
            facilitycontact_txtview = (TextView)itemView.findViewById(R.id.facility_contact);
        }
    }
}
