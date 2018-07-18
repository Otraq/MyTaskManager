package com.example.hosse.mytaskmanager;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.hosse.mytaskmanager.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton addBtn;
    public final static String TAB_MODE_KEY = "TAB_MODE";
    public final static int M = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        DatabaseHelper db = new DatabaseHelper(this);
        NoteList.getInstance().setNoteList(db.getNoteList());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        addBtn = findViewById(R.id.add_floating_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddDialog dialog = AddDialog.newInstance(M, 0);
                dialog.show(getSupportFragmentManager(), "MyAddDialog");
            }
        });

        //Fragment listFragment=new ListFragment();
        //FragmentManager fragmentManager=getSupportFragmentManager();

        //fragmentManager.beginTransaction().add(R.id.list_container,listFragment).commit();
    }


    private void setupViewPager(ViewPager viewPager) {
        Bundle b1 = new Bundle();
        Bundle b2 = new Bundle();
        Bundle b3 = new Bundle();


        b1.putInt(TAB_MODE_KEY, 1);
        b2.putInt(TAB_MODE_KEY, 2);
        b3.putInt(TAB_MODE_KEY, 3);

        Fragment f1 = new ListFragment();
        Fragment f2 = new ListFragment();
        Fragment f3 = new ListFragment();


        f1.setArguments(b1);
        f2.setArguments(b2);
        f3.setArguments(b3);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(f1, "All");
        adapter.addFragment(f2, "Done");
        adapter.addFragment(f3, "UnDone");

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.mFragmentList.size() - 1);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

    }

}
