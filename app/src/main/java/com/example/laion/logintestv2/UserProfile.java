package com.example.laion.logintestv2;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        ProfileFragment.OnFragmentInteractionListener
        {

    ProfileFragment profileFragment;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UserDBHelper UDB = new UserDBHelper(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView NombrePrincipal = (TextView) this.findViewById(R.id.NombrePrincipal);
        TextView UsuarioSecundario = (TextView) this.findViewById(R.id.UsuarioSecundario);
        String [] PersonalInfo = UDB.getPersonalInfo();

        NombrePrincipal.setText(PersonalInfo[2]+" "+PersonalInfo[3]);
        UsuarioSecundario.setText("@"+PersonalInfo[0]);

        profileFragment = new ProfileFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.PersonalContainer, profileFragment).commit();

        Button ModificarButton = (Button) this.findViewById(R.id.ModificarButton);
        Button GuardarButton = (Button) this.findViewById(R.id.GuardarButton);
        Button CancelarButton = (Button) this.findViewById(R.id.CancelarButton);
        Button EliminarButton = (Button) this.findViewById(R.id.EliminarButton);

        ModificarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileFragment.modify();
            }
        });

        CancelarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileFragment.cancel();
            }
        });

        GuardarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileFragment.save();
            }
        });

    }

            public void LoginGo(){
        UserDBHelper UDB = new UserDBHelper(this);
        UDB.setSession(false);
        Intent UserThenLogin = new Intent(this, Login.class);
        startActivity(UserThenLogin);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent ProfileThenUser = new Intent(this, User.class);
            startActivity(ProfileThenUser);
            UserProfile.this.finish();
        } else if (id == R.id.nav_contacts) {
            Intent profileThenContacts = new Intent(this, UserContacts.class);
            startActivity(profileThenContacts);
            UserProfile.this.finish();
        }else if (id == R.id.nav_profile) {
            /*Intent UserThenProfile = new Intent(this, UserProfile.class);
            startActivity(UserThenProfile);*/
        } else if (id == R.id.nav_exit) {
            new AlertDialog.Builder(this)
                    .setMessage("¿Estas seguro de querer cerrar sesión?")
                    .setCancelable(false)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            LoginGo();
                            UserProfile.this.finish();
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