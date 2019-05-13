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

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            final Interpolator interpolador = AnimationUtils.loadInterpolator(getBaseContext(),
                    android.R.interpolator.fast_out_slow_in);

            fabAgregarHorario = findViewById(R.id.fabAgregarHorario);
            fabAgregarHorario.setScaleX(0);
            fabAgregarHorario.setScaleY(0);
            fabAgregarHorario.animate()
                    .scaleX(1)
                    .scaleY(1)
                    .setInterpolator(interpolador)
                    .setDuration(600)
                    .setStartDelay(800)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            if (fabAgregarHorario.isAccessibilityFocused()) {
                                fabAgregarHorario.animate()
                                        .scaleY(0)
                                        .scaleX(0)
                                        .setInterpolator(interpolador)
                                        .setDuration(600)
                                        .start();
                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                            fabAgregarHorario.animate()
                                    .scaleX(1)
                                    .scaleY(1)
                                    .setInterpolator(interpolador)
                                    .setDuration(600)
                                    .setStartDelay(1500)
                                    .start();
                        }
                    });


        }

        fabAgregarHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"¿Quieres agregar una materia?",Snackbar.LENGTH_LONG)
                        .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                        .setAction("SI", new
                        View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.i("Snackbar", "Pulsa si o no pulses");
                                Intent intentSchedule = new Intent(User.this, RegisterMatter.class);
                                startActivity(intentSchedule);
                            }
                        }).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mondayfragment = new MondayFragment();
        tuesdayfragment = new TuesdayFragment();
        wednesdayfragment = new WednesdayFragment();
        thursdayfragment = new ThursdayFragment();
        fridayfragment = new FridayFragment();
        saturdayfragment = new SaturdayFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.HorarioContainer, mondayfragment).commit();

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
            Intent UserThenContacts = new Intent(this, UserContacts.class);
            startActivity(UserThenContacts);
            User.this.finish();
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
