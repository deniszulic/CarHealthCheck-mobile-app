package com.example.chc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class admin_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_activity);
        BottomNavigationView bottomNav=findViewById(R.id.admin_menu);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,new admin_pocetna()).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    //return false;
                    Fragment selectedfragment=null;
                    switch(item.getItemId()){
                        case R.id.home_admin:
                            selectedfragment=new admin_pocetna();
                            break;
                        case R.id.rjprijave_admin:
                            selectedfragment=new rj_prijave_admin();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,selectedfragment).commit();
                    return true;
                }
            };
}