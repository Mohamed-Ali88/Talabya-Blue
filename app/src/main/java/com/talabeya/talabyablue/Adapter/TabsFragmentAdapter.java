package com.talabeya.talabyablue.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.talabeya.talabyablue.Fragments.RetentionDay;
import com.talabeya.talabyablue.Fragments.RetentionMonth;


public class TabsFragmentAdapter extends FragmentStateAdapter {
    public TabsFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 1) {
            return new RetentionDay();
        }
        return new RetentionMonth();
    }


    @Override
    public int getItemCount() {
        return 2;
    }
}
