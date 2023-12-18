package com.example.nivaserviceandroid.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nivaserviceandroid.R;
import com.example.nivaserviceandroid.viewmodel.AlarmAdapter;
import com.example.nivaserviceandroid.viewmodel.MainViewModel;
import com.example.nivaserviceandroid.viewmodel.EventAdapter;

public class AlarmFragment extends Fragment {
    private MainViewModel mainViewModel;
    private RecyclerView recyclerView;
    private AlarmAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);

        recyclerView = view.findViewById(R.id.alarm_list);
        mainViewModel.getAlarms().observe(requireActivity(), machineModels -> {
            adapter = new AlarmAdapter();
            adapter.setData(machineModels);
            recyclerView.setAdapter(adapter);
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (mainViewModel.getAlarms().getValue() != null) {
            inflater.inflate(R.menu.message_menu, menu);
            MenuItem item = menu.findItem(R.id.id_message_search);

            SearchView searchView = (SearchView) item.getActionView();
            searchView.setMaxWidth(Integer.MAX_VALUE);
            searchView.setQueryHint("Поиск аварий");

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.getFilter().filter(newText);
                    return false;
                }
            });
        }

        super.onCreateOptionsMenu(menu, inflater);
    }
}
