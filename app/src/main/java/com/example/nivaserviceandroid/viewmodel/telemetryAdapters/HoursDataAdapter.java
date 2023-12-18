package com.example.nivaserviceandroid.viewmodel.telemetryAdapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nivaserviceandroid.R;
import com.example.nivaserviceandroid.databinding.AdapterEngineHoursBinding;
import com.example.nivaserviceandroid.model.HarvesterModel;

import java.util.ArrayList;

public class HoursDataAdapter extends RecyclerView.Adapter<HoursDataAdapter.ViewHolder> implements Filterable {

    private ArrayList<HarvesterModel> machines = new ArrayList<>();
    private ArrayList<HarvesterModel> machinesFull;

    public void setData(ArrayList<HarvesterModel> machines){
        this.machinesFull = machines;
        this.machines = new ArrayList<>(machinesFull);
    }

    @Override
    public HoursDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AdapterEngineHoursBinding binding = DataBindingUtil.inflate(inflater, R.layout.adapter_engine_hours,
                parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(HoursDataAdapter.ViewHolder holder, int position) {
        holder.bind(machines.get(position));
    }

    @Override
    public int getItemCount() {
        return machines.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    protected final Filter filter = new Filter() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<HarvesterModel> filteredList = new ArrayList<>();

            if(constraint.toString().isEmpty() || constraint == null){
                filteredList.addAll(machinesFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(HarvesterModel machine : machinesFull){
                    assert machine.getDate() != null;
                    if(machine.getDate().toLowerCase().contains(filterPattern)) {
                        filteredList.add(machine);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            filterResults.count = filteredList.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            machines.clear();
            machines.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final AdapterEngineHoursBinding binding;

        public ViewHolder(AdapterEngineHoursBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(HarvesterModel machineModel) {
            binding.setMachine(machineModel);
            binding.executePendingBindings();
        }
    }
}
