package nirmay.healthcare.niramayhealthcare.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import nirmay.healthcare.niramayhealthcare.R;

import static java.util.Objects.isNull;


public class VaccineAdapter extends RecyclerView.Adapter<VaccineAdapter.Vacccinesviewholder>{
    Context context;
    JSONArray data;
    JSONObject jsonObject;
    JSONObject sessionObject,feesObject;

    public VaccineAdapter(Context context, JSONArray data) throws JSONException {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public VaccineAdapter.Vacccinesviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.vaccine_layout,parent,false);
        return new VaccineAdapter.Vacccinesviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vacccinesviewholder holder, int position) {
        try {
            jsonObject = data.getJSONObject(position);
            JSONArray session = jsonObject.getJSONArray("sessions");
            if(!(jsonObject.isNull("vaccine_fees"))){
                JSONArray fees = jsonObject.getJSONArray(("vaccine_fees"));
                feesObject = fees.getJSONObject(0);
                holder.price.setText(("VACCINE PRICE :- "+feesObject.get("fee")).toString());
            }else{
                holder.price.setText("VACCINE PRICE :- FREE");
            }
            holder.centername.setText((String) jsonObject.get("name"));
            holder.centername45.setText((String) jsonObject.get("name"));
            holder.address.setText((String) "ADDRESS :- "+jsonObject.get("address"));

            for(int i=0;i<session.length();i++){
                sessionObject = session.getJSONObject(i);
                if(((Integer) sessionObject.get("min_age_limit")) == 18){
                    holder.age.setText(((Integer) sessionObject.get("min_age_limit")+"+").toString());
                    holder.dose1.setText(((Integer) sessionObject.get("available_capacity_dose1")).toString());
                    holder.dose2.setText(((Integer) sessionObject.get("available_capacity_dose2")).toString());
                }else{
                    holder.age45.setText(((Integer) sessionObject.get("min_age_limit")+"+").toString());
                    holder.dose1_45.setText(((Integer) sessionObject.get("available_capacity_dose1")).toString());
                    holder.dose2_45.setText(((Integer) sessionObject.get("available_capacity_dose2")).toString());
                }
            }
            holder.vaccineName.setText((String) "VACCINE NAME :- "+sessionObject.get("vaccine"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return  data.length();
    }

    public class Vacccinesviewholder extends RecyclerView.ViewHolder{

        TextView centername,age,dose1,dose2;
        TextView centername45,age45,dose1_45,dose2_45;
        TextView vaccineName,address,price;


        public Vacccinesviewholder(@NonNull View itemView) {
            super(itemView);
            centername = itemView.findViewById(R.id.center_name);
            age = itemView.findViewById(R.id.age_Textview);
            dose1 = itemView.findViewById(R.id.dose1_Textview);
            dose2 = itemView.findViewById(R.id.dose2_Textview);
            centername45 = itemView.findViewById(R.id.center_name45);
            age45 = itemView.findViewById(R.id.age_Textview45);
            dose1_45 = itemView.findViewById(R.id.dose1_Textview45);
            dose2_45 = itemView.findViewById(R.id.dose2_Textview45);
            vaccineName = itemView.findViewById(R.id.vaccine_name_txtview);
            address = itemView.findViewById(R.id.address_txtview);
            price = itemView.findViewById(R.id.price_txtview);

        }
    }
}
