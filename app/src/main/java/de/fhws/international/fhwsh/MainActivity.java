package de.fhws.international.fhwsh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import de.fhws.international.fhwsh.fragments.AccountFragment;
import de.fhws.international.fhwsh.fragments.ChatFragment;
import de.fhws.international.fhwsh.fragments.CityFragment;
import de.fhws.international.fhwsh.fragments.DormFragment;
import de.fhws.international.fhwsh.fragments.InfoFragment;
import de.fhws.international.fhwsh.fragments.LecuteFragment;
import de.fhws.international.fhwsh.fragments.NewsFragment;
import de.fhws.international.fhwsh.fragments.PeopleFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AccountFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        sharedpreferences = getSharedPreferences(getString(R.string.refName),
                Context.MODE_PRIVATE);

        View hView = navigationView.getHeaderView(0);

        ((TextView) hView.findViewById(R.id.userName)).setText(sharedpreferences.getString("userName", ""));
        ((TextView) hView.findViewById(R.id.email)).setText(sharedpreferences.getString("email", ""));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AccountFragment()).commit();
                break;
            case R.id.nav_info:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new InfoFragment()).commit();
                break;
            case R.id.nav_city:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CityFragment()).commit();
                break;
            case R.id.nav_dorm:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new DormFragment()).commit();
                break;
            case R.id.nav_news:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new NewsFragment()).commit();
                break;
            case R.id.nav_lecture:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new LecuteFragment()).commit();
                break;
            case R.id.nav_chat:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ChatFragment()).commit();
                break;
            case R.id.nav_people:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new PeopleFragment()).commit();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
