package com.example.laion.logintestv2;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class User extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        MondayFragment.OnFragmentInteractionListener,
        TuesdayFragment.OnFragmentInteractionListener,
        WednesdayFragment.OnFragmentInteractionListener,
        ThursdayFragment.OnFragmentInteractionListener,
        FridayFragment.OnFragmentInteractionListener,
        SaturdayFragment.OnFragmentInteractionListener
{


    MondayFragment mondayfragment;
    TuesdayFragment tuesdayfragment;
    WednesdayFragment wednesdayfragment;
    ThursdayFragment thursdayfragment;
    FridayFragment fridayfragment;
    SaturdayFragment saturdayfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final UserDBHelper UDB = new UserDBHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mondayfragment = new MondayFragment();
        tuesdayfragment = new TuesdayFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.HorarioContainer, mondayfragment).commit();
        Toast.makeText(User.this, String.valueOf(UDB.testGetCursor()), Toast.LENGTH_LONG).show();

    }


    public void onClickHorario(View view){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();

        switch (view.getId()){
            case R.id.Lunes:
                transaction.replace(R.id.HorarioContainer, mondayfragment);
                break;
            case R.id.Martes:
                transaction.replace(R.id.HorarioContainer, tuesdayfragment);
                break;
            case R.id.Miercoles:
                transaction.replace(R.id.HorarioContainer, wednesdayfragment);
                break;
            case R.id.Jueves:
                transaction.replace(R.id.HorarioContainer, thursdayfragment);
                break;
            case R.id.Viernes:
                transaction.replace(R.id.HorarioContainer, fridayfragment);
                break;
            case R.id.Sabado:
                transaction.replace(R.id.HorarioContainer, saturdayfragment);
                break;
        }

        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void LoginGo(){
        UserDBHelper UDB = new UserDBHelper(this);
        UDB.setSession(false);
        Intent UserThenLogin = new Intent(this, Login.class);
        startActivity(UserThenLogin);
    }


    /*public void onBackPressed1() {
        new AlertDialog.Builder(this)
                .setMessage("¿Estas seguro de querer cerrar sesión?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Login.session=false;
                        User.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }*/



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_contacts) {

        }else if (id == R.id.nav_profile) {
            Intent UserThenProfile = new Intent(this, UserProfile.class);
            startActivity(UserThenProfile);
            User.this.finish();
        } else if (id == R.id.nav_exit) {
            new AlertDialog.Builder(this)
                    .setMessage("¿Estas seguro de querer cerrar sesión?")
                    .setCancelable(false)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            LoginGo();
                            User.this.finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
