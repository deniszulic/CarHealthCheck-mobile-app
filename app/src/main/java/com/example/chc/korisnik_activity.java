package com.example.chc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class korisnik_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_korisnik_activity);
        BottomNavigationView bottomNav=findViewById(R.id.korisnik_menu);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_korisnik,new korisnik_pocetna()).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    //return false;
                    Fragment selectedfragment=null;
                    switch(item.getItemId()){
                        case R.id.obrazac_korisnik:
                            selectedfragment=new korisnik_pocetna();
                            break;
                        case R.id.mojeprijave_korisnik:
                            selectedfragment=new prijave_korisnik();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_korisnik,selectedfragment).commit();
                    return true;
                }
            };
}