package com.example.chc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav=findViewById(R.id.nouser);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        navigation navigation=new navigation();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new home()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    //return false;
                    Fragment selectedfragment=null;
                    switch(item.getItemId()){
                        case R.id.home:
                            selectedfragment= new home();
                            break;
                        case R.id.register_nouser:
                            selectedfragment=new register();
                            break;
                        case R.id.login_nouser:
                            selectedfragment=new login();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedfragment).commit();
                    return true;
                }
            };
}