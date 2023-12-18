package com.example.nivaserviceandroid.viewmodel;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nivaserviceandroid.R;
import com.example.nivaserviceandroid.databinding.AdapterEventBinding;
import com.example.nivaserviceandroid.model.EventModel;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> implements Filterable {

    private ArrayList<EventModel> events = new ArrayList<>();
    private ArrayList<EventModel> eventsFull;

    public void setData(ArrayList<EventModel> events) {
        this.eventsFull = events;
        this.events = new ArrayList<>(eventsFull);
    }

    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AdapterEventBinding binding = DataBindingUtil.inflate(inflater, R.layout.adapter_event,
                parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(EventAdapter.ViewHolder holder, int position) {
        holder.bind(events.get(position));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    protected final Filter filter = new Filter() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<EventModel> filteredList = new ArrayList<>();

            if(constraint.toString().isEmpty() || constraint == null){
                filteredList.addAll(eventsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(EventModel machine : eventsFull){
                    if(machine.getEvent().toLowerCase().contains(filterPattern) ||
                        machine.getDate().toLowerCase().contains(filterPattern)) {
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
            events.clear();
            events.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final AdapterEventBinding binding;

        public ViewHolder(AdapterEventBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(EventModel machineModel) {
            binding.setEvent(machineModel);
            binding.executePendingBindings();
        }
    }
}