package com.example.laion.logintestv2;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.laion.logintestv2.adapter.PageAdapter;
import com.example.laion.logintestv2.fragment.RegisterMatterFragment;
import com.example.laion.logintestv2.fragment.ShowMatterFragment;
import com.example.laion.logintestv2.pojo.Horario;

import java.util.ArrayList;


public class RegisterMatter extends AppCompatActivity {

    private ArrayList<Horario> horarios;
    private RecyclerView rvHorarios;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matter_register);

        toolbar =  findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.pager);
        setUpViewPager();

        if(toolbar != null){
            setSupportActionBar(toolbar);
        }

    }



    private ArrayList<Fragment> agregarFragments(){
        ArrayList<Fragment> fragments = new ArrayList<>();

        fragments.add(new RegisterMatterFragment());
        fragments.add(new ShowMatterFragment());

        return fragments;
    }

    private void setUpViewPager(){
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(), agregarFragments()));
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("CRUD de Materias");
        tabLayout.getTabAt(1).setText("Materias registradas");
    }

}
