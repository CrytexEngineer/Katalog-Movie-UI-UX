/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.aqil.katalogfilmuiux;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;


/**
 * Provides the appropriate {@link Fragment} for a view pager.
 */
public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
    private Context context;
    String[] tabTitles;

    public FragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        tabTitles = new String[]{context.getResources().getString(R.string.now_playing), context.getResources().getString(R.string.uo_coming), context.getResources().getString(R.string.search)};

    }

    @Override

    public Fragment getItem(int position) {
        if (position == 0) {
            return new NowPlayingFragment();
        } else if (position == 1) {
            return new UpcomingFragment();
        } else {
            return new SearchFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }


     @Override
     public CharSequence getPageTitle(int position) {
    return tabTitles[position];
       }
}
