package com.zj970.tourism.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author: zj970
 * @date: 2023/1/3
 */
public class MyFragmentStateAdapter extends FragmentStateAdapter {

    List<Fragment> mFragments;

    public MyFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity,List<Fragment> fragments) {
        super(fragmentActivity);
        this.mFragments = fragments;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragments == null ? 0 : mFragments.size();
    }
}
