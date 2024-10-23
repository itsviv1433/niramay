package nirmay.healthcare.niramayhealthcare.ui.facilities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import nirmay.healthcare.niramayhealthcare.Pojo.Facility;
import nirmay.healthcare.niramayhealthcare.R;

public class FacilityListFragment extends Fragment {

    ArrayList<Facility> facilitycitylist = new ArrayList<>();
    RecyclerView recyclerView;
    Search_Rows_Adapter_Facilities sraf;
    public String fac_city_clicked;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_facility_list, container, false);
//        facilitycitylist.add("Jalgaon");
//        facilitycitylist.add("Pune");
//        facilitycitylist.add("Nashik");
//        facilitycitylist.add("Mumbai");
//        facilitycitylist.add("Nagpur");
//        facilitycitylist.add("Satara");
//        facilitycitylist.add("Buldhana");
//        facilitycitylist.add("Beed");
//        facilitycitylist.add("Kolhapur");

        recyclerView = (RecyclerView)view.findViewById(R.id.facilities_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        sraf = new Search_Rows_Adapter_Facilities(getContext(), facilitycitylist, new Search_Rows_Adapter_Facilities.OnNoteListener() {
            @Override
            public void onNoteClick(int position) {
                fac_city_clicked = facilitycitylist.get(position).getFac_city();
                Fragment f = new FacilityDetailsLayoutFragment(fac_city_clicked);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.facilities_layout, f);
                ft.commit();
            }
        });
        recyclerView.setAdapter(sraf);




        return view;
    }
}
