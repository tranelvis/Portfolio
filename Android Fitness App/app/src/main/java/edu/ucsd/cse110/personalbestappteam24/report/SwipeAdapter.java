package edu.ucsd.cse110.personalbestappteam24.report;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SwipeAdapter extends FragmentPagerAdapter {


    public SwipeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
//        Fragment week1 = new Week1();
//        Bundle bundle = new Bundle();
//        bundle.putInt("Week1", i);
//        week1.setArguments(bundle);
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
