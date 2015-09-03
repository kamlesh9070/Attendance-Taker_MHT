/*
 * Copyright (C) 2015 Ferid Cafer
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

package com.ferid.app.classroom;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ferid.app.classroom.attendance.TakeAttendanceFragment;
import com.ferid.app.classroom.edit.EditClassroomFragment;
import com.ferid.app.classroom.statistics.StatisticsFragment;
import com.ferid.app.classroom.tabs.SlidingTabLayout;
import com.ferid.app.classroom.utility.ApplicationRating;

/**
 * Created by ferid.cafer on 4/15/2015.
 */
public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;

    private SlidingTabLayout mSlidingTabLayout;

    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        int numberOfClassrooms = 0;
        Bundle args = getIntent().getExtras();
        if (args != null) {
            numberOfClassrooms = args.getInt("numberOfClassrooms");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);

        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setDividerColors(getResources().getColor(R.color.transparent));
        mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.white));
        mSlidingTabLayout.setCustomTabView(R.layout.tab_view, R.id.tabText);
        mSlidingTabLayout.setViewPager(viewPager);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        addOnPageChangeListener();
        //if there are already entered classrooms, just show take attendance page,
        //otherwise show edit classrooms page to add a new one.
        if (numberOfClassrooms > 0) {
            viewPager.setCurrentItem(1);
        } else {
            viewPager.setCurrentItem(0);
            //make floating button available to add classrooms
            setButtonAdd();
        }


        //rate the app
        ApplicationRating.ratingPopupManager(this);
    }

    /**
     * View Pager, page change listener.
     */
    private void addOnPageChangeListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0: //editing
                        setButtonAdd();
                        break;
                    case 1: //attendance
                        setButtonHidden();
                        break;
                    case 2: //statistics
                        setButtonPublish();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * EditClassroom.<br />
     * Add a new classroom.
     */
    private void setButtonAdd() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                floatingActionButton.setImageResource(R.drawable.ic_action_add);
                floatingActionButton.show();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditClassroomFragment fragment = (EditClassroomFragment) getSupportFragmentManager()
                        .findFragmentByTag("android:switcher:" + viewPager.getId() + ":"
                                + mAdapter.getItemId(0));
                fragment.addNewItem();
            }
        });
    }

    /**
     * Attendance.<br />
     * Just hide the button.
     */
    private void setButtonHidden() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                floatingActionButton.hide();
            }
        });
    }

    /**
     * Statistics.<br />
     * Convert attendances into an excel file and share it.
     */
    private void setButtonPublish() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                floatingActionButton.setImageResource(R.drawable.ic_action_publish);
                floatingActionButton.show();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatisticsFragment fragment = (StatisticsFragment) getSupportFragmentManager()
                        .findFragmentByTag("android:switcher:" + viewPager.getId() + ":"
                                + mAdapter.getItemId(2));
                fragment.getDataForExcel();
            }
        });
    }

    public class TabsPagerAdapter extends FragmentPagerAdapter {

        public TabsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return EditClassroomFragment.newInstance();
                case 1:
                    return TakeAttendanceFragment.newInstance();
                case 2:
                    return StatisticsFragment.newInstance();
                default:
                    return TakeAttendanceFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            // get item count - equal to number of tabs
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String[] titles = getResources().getStringArray(R.array.main_page);
            return titles[position];
        }
    }
}