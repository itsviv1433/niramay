package nirmay.healthcare.niramayhealthcare.ui.facilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nirmay.healthcare.niramayhealthcare.Pojo.Disease;
import nirmay.healthcare.niramayhealthcare.Pojo.Facility;
import nirmay.healthcare.niramayhealthcare.R;
import nirmay.healthcare.niramayhealthcare.ui.search.Search_Rows_Adapter;

public class Search_Rows_Adapter_Facilities extends RecyclerView.Adapter<Search_Rows_Adapter_Facilities.SearchRowsAdapterHolder> {

    Context context;
    ArrayList<Facility> data;
    private OnNoteListener monNoteListener;

    public Search_Rows_Adapter_Facilities(Context context, ArrayList<Facility> data, OnNoteListener monNoteListener) {
        this.context = context;
        this.data = data;
        this.monNoteListener = monNoteListener;
    }

    @NonNull
    @Override
    public Search_Rows_Adapter_Facilities.SearchRowsAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_of_list, parent, false);
        return new Search_Rows_Adapter_Facilities.SearchRowsAdapterHolder(view, monNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Search_Rows_Adapter_Facilities.SearchRowsAdapterHolder holder, int position) {
        holder.row.setText(data.get(position).getCities());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class SearchRowsAdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView row;
        OnNoteListener onNoteListener;

        public SearchRowsAdapterHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            row = itemView.findViewById(R.id.row_textview);
            itemView.setOnClickListener(this);
            this.onNoteListener = onNoteListener;
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener {
        void onNoteClick(int position);
    }
}

