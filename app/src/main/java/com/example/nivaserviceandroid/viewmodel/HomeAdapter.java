package com.example.nivaserviceandroid.viewmodel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.nivaserviceandroid.model.DashboardModel;
import com.example.nivaserviceandroid.R;

import java.util.ArrayList;

public class HomeAdapter extends ArrayAdapter<DashboardModel> {
    public HomeAdapter(@NonNull Context context, ArrayList<DashboardModel> dashboardModelArrayList) {
        super(context, 0, dashboardModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.home_list_item, parent,
                    false);
        }

        DashboardModel dashboardModel = getItem(position);
        TextView machineName = listitemView.findViewById(R.id.machineName);
        ImageView machineImage = listitemView.findViewById(R.id.machineImage);
        TextView machineValue = listitemView.findViewById(R.id.machineValue);

        machineName.setText(dashboardModel.getLabel());
        machineValue.setText(String.valueOf(dashboardModel.getValue()));
        machineImage.setImageResource(dashboardModel.getImgid());

        return listitemView;
    }
}
