package com.example.laion.logintestv2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class UserContacts extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_contacts);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
    }

    public void LoginGo(){
        UserDBHelper UDB = new UserDBHelper(this);
        UDB.setSession(false);
        Intent UserThenLogin = new Intent(this, Login.class);
        startActivity(UserThenLogin);
    }


    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent ProfileThenUser = new Intent(this, User.class);
            startActivity(ProfileThenUser);
            UserContacts.this.finish();
        } else if (id == R.id.nav_contacts) {

        }else if (id == R.id.nav_profile) {
            Intent contactsThenProfile = new Intent(this, UserProfile.class);
            startActivity(contactsThenProfile);
            UserContacts.this.finish();
        } else if (id == R.id.nav_exit) {
            new AlertDialog.Builder(this)
                    .setMessage("¿Estas seguro de querer cerrar sesión?")
                    .setCancelable(false)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            LoginGo();
                            UserContacts.this.finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
