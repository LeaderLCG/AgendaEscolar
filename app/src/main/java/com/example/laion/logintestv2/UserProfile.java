package com.example.laion.logintestv2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        ProfileFragment.OnFragmentInteractionListener
        {

    ProfileFragment profileFragment;
    private CircleImageView perfil;
    private Bitmap imagencargada;
    private String imageaddres="https://raw.githubusercontent.com/Sacreblu/AgendaEscolarWeb/master/AgendaEscolar/Complementos/FotoPerfil/Sacreblu.jpg";
    private static final int SELECT_FILE = 1;

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

        Switch switchhorario = (Switch) this.findViewById(R.id.switch1);
        try {
            switchhorario.setChecked(UDB.checkPrivacidadHorario());
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error cargando privacidad de horario: "+e.getMessage(), Toast.LENGTH_LONG).show();
        }

        NombrePrincipal.setText(PersonalInfo[2]+" "+PersonalInfo[3]);
        UsuarioSecundario.setText("@"+PersonalInfo[0]);

        profileFragment = new ProfileFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.PersonalContainer, profileFragment).commit();

        perfil = (CircleImageView) this.findViewById(R.id.FotoPerfil);
        perfil.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(intent, "Seleccione una imagen"), SELECT_FILE);
            }
        });
        downloadProfilePicture(imageaddres);

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
                try {
                    profileFragment.save();
                    Toast.makeText(getApplicationContext(), "Actualizado con éxito.", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    /* Toast.makeText(getApplicationContext(), "Error al guardar tus Datos.", Toast.LENGTH_LONG).show(); */
                }
            }
        });

    }

            public void LoginGo(){
        UserDBHelper UDB = new UserDBHelper(this);
        UDB.setSession(false);
        Intent UserThenLogin = new Intent(this, Login.class);
        startActivity(UserThenLogin);
    }

    public void downloadProfilePicture(String imageaddres){
        URL imageURL=null;
        try {
            imageURL = new URL(imageaddres);
            HttpURLConnection conn = (HttpURLConnection) imageURL.openConnection();
            conn.connect();
            imagencargada = BitmapFactory.decodeStream(conn.getInputStream());
            perfil.setImageBitmap(imagencargada);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Error cargando la imagen: "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
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

            protected void onActivityResult(int requestCode, int resultCode,
                                            Intent imageReturnedIntent) {
                super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
                Uri selectedImageUri = null;
                Uri selectedImage;

                String filePath = null;
                switch (requestCode) {
                    case SELECT_FILE:
                        if (resultCode == UserProfile.RESULT_OK) {
                            selectedImage = imageReturnedIntent.getData();
                            String selectedPath=selectedImage.getPath();
                            if (requestCode == SELECT_FILE) {

                                if (selectedPath != null) {
                                    InputStream imageStream = null;
                                    try {
                                        imageStream = getContentResolver().openInputStream(
                                                selectedImage);
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }

                                    // Transformamos la URI de la imagen a inputStream y este a un Bitmap
                                    Bitmap bmp = BitmapFactory.decodeStream(imageStream);

                                    // Ponemos nuestro bitmap en un ImageView que tengamos en la vista
                                    perfil.setImageBitmap(bmp);

                                }
                            }
                        }
                        break;
                }
            }
        }