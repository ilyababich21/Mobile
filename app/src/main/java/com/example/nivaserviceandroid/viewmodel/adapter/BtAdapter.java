package com.example.nivaserviceandroid.viewmodel.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nivaserviceandroid.R;

import java.util.ArrayList;
import java.util.List;

public class BtAdapter extends ArrayAdapter<BluetoothListItem> {

    public static final String DEF_ITEM_TYPE = "normal";
    public static final String TITLE_ITEM_TYPE = "title";
    public static final String DISCOVERY_ITEM_TYPE = "discovery";

    private final List<BluetoothListItem> mainList;
    private final List<ViewHolder> listViewHolders;
    private final SharedPreferences pref;

    private boolean isDiscoveryType = false;

    public BtAdapter(@NonNull Context context, int resource, List<BluetoothListItem> bluetoothList) {
        super(context, resource, bluetoothList);
        mainList = bluetoothList;
        listViewHolders = new ArrayList<>();
        pref = context.getSharedPreferences(BluetoothConsts.MY_PREF, Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        switch(mainList.get(position).getItemType()) {
            case TITLE_ITEM_TYPE: convertView = titleItem(convertView, parent);
                break;

            default: convertView = defaultItem(convertView, position, parent);
            break;
        }

        return convertView;
    }

    private void savePref(int pos) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(BluetoothConsts.MAC_KEY, mainList.get(pos).getBluetoothDevice().getAddress());
        editor.apply();
    }

    class ViewHolder {
        TextView tvBluetoothName;
        CheckBox checkBluetoothSelected;
    }

    @SuppressLint("MissingPermission")
    private View defaultItem(View convertView, int position, ViewGroup parent){
        ViewHolder viewHolder;
        boolean hasViewHolder = false;

        if(convertView != null) hasViewHolder = (convertView.getTag() instanceof ViewHolder);
        if(convertView == null || !hasViewHolder) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bluetooth_list_item,
                    null,false);
            viewHolder.tvBluetoothName = convertView.findViewById(R.id.tvBluetoothName);
            viewHolder.checkBluetoothSelected = convertView.findViewById(R.id.checkBox);
            convertView.setTag(viewHolder);
            listViewHolders.add(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
            viewHolder.checkBluetoothSelected.setChecked(false);
        }

        if(mainList.get(position).getItemType().equals(BtAdapter.DISCOVERY_ITEM_TYPE)){
            viewHolder.checkBluetoothSelected.setVisibility(View.GONE);
            isDiscoveryType = true;
        } else {
            viewHolder.checkBluetoothSelected.setVisibility(View.VISIBLE);
            isDiscoveryType = false;
        }
        viewHolder.tvBluetoothName.setText(mainList.get(position).getBluetoothDevice().getName());
        viewHolder.checkBluetoothSelected.setOnClickListener(v -> {
            if(!isDiscoveryType){
            for(ViewHolder holder : listViewHolders) {
                holder.checkBluetoothSelected.setChecked(false);
            }
            viewHolder.checkBluetoothSelected.setChecked(true);
            savePref(position);
            }
        });
        if (pref.getString(BluetoothConsts.MAC_KEY, "no bt selected").equals(mainList.get(position)
                .getBluetoothDevice().getAddress()))viewHolder.checkBluetoothSelected.setChecked(true);
        isDiscoveryType = false;

        return convertView;
    }

    private View titleItem(View convertView, ViewGroup parent){
        boolean hasViewHolder = false;
        if(convertView != null) hasViewHolder = (convertView.getTag() instanceof ViewHolder);
        if(convertView == null || hasViewHolder) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bluetooth_list_item_title,
                    null, false);
        }
        return convertView;
    }
}
