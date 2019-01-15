package com.himorfosis.presensi;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PresensiPager extends FragmentPagerAdapter {

    int mNumOfTabs;

    public PresensiPager(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                PresensiMasuk tab1 = new PresensiMasuk();
                return tab1;
            case 1:
                PresensiPulang tab2 = new PresensiPulang();
                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }


}
