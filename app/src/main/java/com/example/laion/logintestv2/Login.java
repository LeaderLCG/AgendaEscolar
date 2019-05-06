package com.example.laion.logintestv2;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class Login extends AppCompatActivity {

    Connection con=null;
    Conexion conectar = new Conexion();

    Button IniciarSesion, Registrarse;
    EditText UserName, Password;
    ResultSet rs;
    Statement st;
    String user, pass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final UserDBHelper UDB = new UserDBHelper(this);
        if(UDB.getSession()==true){
            Intent logged = new Intent(this, User.class);
            startActivity(logged);
            Login.this.finish();
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        UserName = (EditText) this.findViewById(R.id.UserNameText);
        Password = (EditText) this.findViewById(R.id.PasswordText);
        IniciarSesion = (Button) this.findViewById(R.id.Ingresar);
        Registrarse = (Button) this.findViewById(R.id.Registrar);


        Registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent transicion1 = new Intent(v.getContext(), RegisterOne.class);
                startActivity(transicion1);
            }
        });

        try{
            con=conectar.conectar();
        }catch(Exception e){
            con=null;
            Toast.makeText(Login.this, "Sin Conexion", Toast.LENGTH_LONG).show();
        }

        IniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    con=conectar.conectar();
                }catch(Exception e){
                    con=null;
                }
                if("".equals(UserName.getText()) || "".equals(Password.getText())){
                    Toast.makeText(Login.this, "No dejes campos vacíos", Toast.LENGTH_LONG).show();
                }else{
                    user = String.valueOf(UserName.getText());
                    pass = UDB.getMD5(String.valueOf(Password.getText()));
                    if(con==null){
                        if(UDB.LogTry(user, pass)){
                            Intent transicion2 = new Intent(v.getContext(), User.class);
                            startActivity(transicion2);
                        }else {
                            Toast.makeText(Login.this, "Usuario o Contraseña Incorrectos", Toast.LENGTH_LONG).show();
                            UserName.setText(null);
                            Password.setText(null);
                        }
                    }else{
                        try{
                            st = con.createStatement();
                            rs = st.executeQuery("SELECT * FROM usuarios WHERE NombreUsuario='" + user + "' AND Contrasena='" + pass + "'");
                            rs.next();
                            if (rs.next()) {
                                UDB.newUser(user, pass);
                                UDB.newHorario(user);
                                Intent transicion2 = new Intent(v.getContext(), User.class);
                                startActivity(transicion2);
                                Login.this.finish();
                            }else{
                                Toast.makeText(Login.this, "Usuario o Contraseña Incorrectos", Toast.LENGTH_LONG).show();
                                UserName.setText(null);
                                Password.setText(null);
                            }
                        }catch(Exception e){
                            Toast.makeText(Login.this, "ERROR: "+e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });

        }
    }
