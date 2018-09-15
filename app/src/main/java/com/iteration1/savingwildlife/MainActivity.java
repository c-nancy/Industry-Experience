package com.iteration1.savingwildlife;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private FragmentManager fragmentManager;
    private Fragment nextFragment;

    private Boolean second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarfornavigation);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        second = false;
        nextFragment = null;
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setTitle("Beach Step");
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new HomeScreenFragment()).commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (nextFragment == null || second || nextFragment.getClass() == HomeScreenFragment.class) {
            super.onBackPressed();
        } else if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (nextFragment.getClass() != HomeScreenFragment.class) {
            getSupportActionBar().setTitle("Beach Step");
            fragmentManager.beginTransaction().replace(R.id.content_frame, new HomeScreenFragment()).commit();
            second = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        second = false;
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_homepage:
                getSupportActionBar().setTitle("Beach Step");
                nextFragment = new HomeScreenFragment();
                break;
            case R.id.nav_map:
                getSupportActionBar().setTitle("Beach distribution");
                Intent intent = new Intent();
                nextFragment = new MapFragment();
                break;
            case R.id.nav_about_us:
                getSupportActionBar().setTitle("About");
                nextFragment = new AboutPage();
                break;
            case R.id.nav_fish_statistics:
                getSupportActionBar().setTitle("Fish population");
                nextFragment = new FishPopulationFragment();
                break;
            case R.id.nav_fish_image:
                getSupportActionBar().setTitle("Know fishes");
                nextFragment = new FishImageFragment();
                break;
            case R.id.nav_litters_statistics:
                getSupportActionBar().setTitle("Common litters on beach");
                nextFragment = new PollutionFragment();
                break;
        }

        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            fragmentManager.popBackStack();
        }

        if (nextFragment != null) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, nextFragment).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
