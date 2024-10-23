package nirmay.healthcare.niramayhealthcare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import nirmay.healthcare.niramayhealthcare.Pojo.Facility;

public class Hospital_Details_Adapter extends RecyclerView.Adapter<Hospital_Details_Adapter.Hospitalviewholder>{


    Context context;
    ArrayList<Facility> data;

    public Hospital_Details_Adapter(Context context, ArrayList<Facility> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public Hospital_Details_Adapter.Hospitalviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.diagnosis_hospitals_recyclerview_layout,parent,false);
        return new Hospital_Details_Adapter.Hospitalviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Hospitalviewholder holder, int position) {
        holder.hosName.setText(data.get(position).getFac_name());
        holder.hosAddress.setText(data.get(position).getFac_address());
        holder.hosContact.setText(data.get(position).getFac_contact());
//        holder.hosBedOccupacy.setText(data.get(position).getBeds_occupacy());
//        holder.hosLastUpdated.setText(data.get(position).getData_last_updated());
    }

    @Override
    public int getItemCount() {
        return  data.size();
    }

    public class Hospitalviewholder extends RecyclerView.ViewHolder{

        TextView hosName,hosAddress,hosContact,hosBedOccupacy,hosLastUpdated;
        public Hospitalviewholder(@NonNull View itemView) {
            super(itemView);

            hosName = itemView.findViewById(R.id.diagnosis_hospital_name);
            hosContact = itemView.findViewById(R.id.diagnosis_hospital_contacts);
            hosAddress = itemView.findViewById(R.id.diagnosis_hospital_address);
//            hosBedOccupacy = itemView.findViewById(R.id.diagnosis_hospital_beds);
//            hosLastUpdated = itemView.findViewById(R.id.diagnosis_beds_dataupdate);

        }
    }

}
