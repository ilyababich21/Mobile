package com.example.nivaserviceandroid.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nivaserviceandroid.R;
import com.example.nivaserviceandroid.viewmodel.MachineDataAdapter;
import com.example.nivaserviceandroid.viewmodel.MainViewModel;

public class MachineDataFragment extends Fragment {
    private MainViewModel mainViewModel;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private MachineDataAdapter adapter;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        view = inflater.inflate(R.layout.fragment_machine_data, container,
                false);

        SetupTabhost(view);

        recyclerView = view.findViewById(R.id.machine_data_list);
        recyclerView1 = view.findViewById(R.id.machine_sensor_data);
        //recyclerView2 = view.findViewById(R.id.machine_engine_amperage);
        //recyclerView3 = view.findViewById(R.id.machine_engine_hours);

        mainViewModel.getMachines().observe(requireActivity(), machineModels -> {
            adapter = new MachineDataAdapter();
            adapter.setData(machineModels);

            recyclerView.setAdapter(adapter);
            recyclerView1.setAdapter(adapter);
          //  recyclerView2.setAdapter(adapter);
            //recyclerView3.setAdapter(adapter);
        });
        return view;
    }

    private void SetupTabhost(View rootView) {
        TabHost tabhost = rootView.findViewById(R.id.tabhost);
        tabhost.setup();

        TabHost.TabSpec spec = tabhost.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Напряж-я");
        tabhost.addTab(spec);

        spec = tabhost.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Датчики");
        tabhost.addTab(spec);

        spec = tabhost.newTabSpec("Tab Three");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Токи");
        tabhost.addTab(spec);

        spec = tabhost.newTabSpec("Tab Four");
        spec.setContent(R.id.tab4);
        spec.setIndicator("Моточасы");
        tabhost.addTab(spec);

        for(int i = 0; i < 4; i++) {
            TextView textview = tabhost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            textview.setTextSize(12);
        }

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (mainViewModel.getMachines().getValue() != null) {
            inflater.inflate(R.menu.machine_data_menu, menu);
            MenuItem item = menu.findItem(R.id.machine_data_search);

            SearchView searchView = (SearchView) item.getActionView();
            searchView.setMaxWidth(Integer.MAX_VALUE);
            searchView.setQueryHint("Поиск данных по ошибкам");

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
