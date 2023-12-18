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
import com.example.nivaserviceandroid.databinding.AdapterAlarmBinding;
import com.example.nivaserviceandroid.model.AlarmModel;
import java.util.ArrayList;

    public class AlarmAdapter extends RecyclerView.Adapter<com.example.nivaserviceandroid.viewmodel.AlarmAdapter.ViewHolder> implements Filterable {

        private ArrayList<AlarmModel> alarms = new ArrayList<>();
        private ArrayList<AlarmModel> alarmsFull;

        public void setData(ArrayList<AlarmModel> alarms) {
            this.alarmsFull = alarms;
            this.alarms = new ArrayList<>(alarmsFull);
        }

        @Override
        public com.example.nivaserviceandroid.viewmodel.AlarmAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            AdapterAlarmBinding binding = DataBindingUtil.inflate(inflater, R.layout.adapter_alarm,
                    parent, false);
            return new com.example.nivaserviceandroid.viewmodel.AlarmAdapter.ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(com.example.nivaserviceandroid.viewmodel.AlarmAdapter.ViewHolder holder, int position) {
            holder.bind(alarms.get(position));
        }

        @Override
        public int getItemCount() {
            return alarms.size();
        }

        @Override
        public Filter getFilter() {
            return filter;
        }

        protected final Filter filter = new Filter() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<AlarmModel> filteredList = new ArrayList<>();

                if(constraint.toString().isEmpty() || constraint == null){
                    filteredList.addAll(alarmsFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for(AlarmModel alarm : alarmsFull){
                        if(alarm.getAlarm().toLowerCase().contains(filterPattern) ||
                                alarm.getDate().toLowerCase().contains(filterPattern)) {
                            filteredList.add(alarm);
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
                alarms.clear();
                alarms.addAll((ArrayList)results.values);
                notifyDataSetChanged();
            }
        };

        public static class ViewHolder extends RecyclerView.ViewHolder {
            private final AdapterAlarmBinding binding;

            public ViewHolder(AdapterAlarmBinding binding){
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind(AlarmModel alarmModel) {
                binding.setAlarm(alarmModel);
                binding.executePendingBindings();
            }
        }
    }
