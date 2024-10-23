package nirmay.healthcare.niramayhealthcare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import nirmay.healthcare.niramayhealthcare.Pojo.Medicine;

public class Medicine_Details_Adapter extends RecyclerView.Adapter<Medicine_Details_Adapter.Medsviewholder> {

    Context context;
    ArrayList<Medicine> data;

    public Medicine_Details_Adapter(Context context, ArrayList<Medicine> data) {
        this.context = context;
        this.data = data;
    }

    //String[] data;
//        public FacilitiesAdapter(String [] data){
//           this.data = data;
//        }
    @NonNull
    @Override
    public Medicine_Details_Adapter.Medsviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.diagnosis_meds_recyclerview_layout,parent,false);
        return new Medsviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Medicine_Details_Adapter.Medsviewholder holder, int position) {
        holder.medname.setText(data.get(position).getMed_name());
        holder.medprice.setText(data.get(position).getMed_price());
    }

    @Override
    public int getItemCount() {
        return  data.size();
    }

    public class Medsviewholder extends RecyclerView.ViewHolder{

        TextView medname,medprice;
        public Medsviewholder(@NonNull View itemView) {
            super(itemView);

            medname = itemView.findViewById(R.id.meds_name);
            medprice = itemView.findViewById(R.id.meds_price);
        }
    }

}

