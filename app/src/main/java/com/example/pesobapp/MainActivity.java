package com.example.pesobapp;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MainActivity";
  private SectionsPageAdapter mSectionsPageAdapter;
  private ViewPager mViewPager;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Log.d(TAG, "onCreate: Starting");

    mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
    //Defindo o ViewPager com os SectionsAdapter
    mViewPager = (ViewPager) findViewById(R.id.container);
    setupViewPager(mViewPager);

    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
    tabLayout.setupWithViewPager(mViewPager);
  }
  private void setupViewPager(ViewPager viewPager){
    SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
    adapter.addFragment(new HomeFragment(), "Home");
    adapter.addFragment(new TestFragment(), "Teste");
    viewPager.setAdapter(adapter);
  }
}