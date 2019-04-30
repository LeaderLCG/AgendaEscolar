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
import java.sql.SQLException;
import java.sql.Statement;


public class Login extends AppCompatActivity {

    Connection con=null;
    Conexion conectar = new Conexion();

    Button IniciarSesion, Registrarse;
    EditText UserName, Password;
    ResultSet rs;
    Statement st;



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

        try {
            con=conectar.conectar();
            IniciarSesion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String user = String.valueOf(UserName.getText());
                    String pass = UDB.getMD5(String.valueOf(Password.getText()));
                    if(!"".equals(user) || !"".equals(pass)){
                        try {
                            st = con.createStatement();
                            rs = st.executeQuery("SELECT * FROM usuarios WHERE NombreUsuario='" + user + "' AND Contrasena='" + pass + "'");

                            if (rs.next()) {
                                Intent transicion2 = new Intent(v.getContext(), User.class);
                                startActivity(transicion2);
                                UDB.newUser(user, pass);
                                UDB.newHorario(user);
                                Login.this.finish();
                            }else{
                                if(UDB.LogTry(user, pass)== true){
                                    Intent transicion2 = new Intent(v.getContext(), User.class);
                                    startActivity(transicion2);
                                }else {
                                    Toast.makeText(Login.this, "Usuario o Contraseña Incorrectos", Toast.LENGTH_LONG).show();
                                    UserName.setText(null);
                                    Password.setText(null);
                                }
                            }
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                    else{
                        Toast.makeText(Login.this, "No dejes campos vacíos", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }catch (Exception e) {
            Toast.makeText(Login.this, "Sin conexión", Toast.LENGTH_LONG).show();
            IniciarSesion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String user = String.valueOf(UserName.getText());
                    String pass = String.valueOf(Password.getText());
                    if(!"".equals(user) || !"".equals(pass)) {
                        if(UDB.LogTry(user, pass)==true){
                            Intent logged = new Intent(v.getContext(), User.class);
                            startActivity(logged);
                            Login.this.finish();
                        }else{
                            Toast.makeText(Login.this, "Usuario o Contraseña Incorrectos", Toast.LENGTH_LONG).show();
                            UserName.setText(null);
                            Password.setText(null);
                        }
                    }else{
                        Toast.makeText(Login.this, "No dejes campos vacíos", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
