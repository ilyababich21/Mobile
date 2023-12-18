package com.example.nivaserviceandroid.view.telemetryPager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class VPAdapter extends FragmentPagerAdapter {
    ArrayList<String> tabTitles = new ArrayList<String>() {
        {
            add("Напряжения");
            add("Датчики");
            add("Токи");
            add("Моточасы");
        }
    };

    public VPAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        if (i == 0) {
            fragment = new FragmentVoltage();
        } else if (i == 1) {
            fragment = new FragmentSensor();
        } else if (i == 2) {
            fragment = new FragmentAmperage();
        } else if (i == 3) {
            fragment = new FragmentHours();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return tabTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }
}
