package com.example.laion.logintestv2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends Activity {

    private Timer timer;
    private ProgressBar progressBar;
    private int i=0, j=0;
    private TextView textviewsplash;
    private String[] tareas = new String[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tareas[0]="Exentando Materias...";
        tareas[1]="Sobornando profesores...";
        tareas[2]="Faltando a clases...";
        tareas[3]="Comprando extraordinarios...";

        progressBar=(ProgressBar) this.findViewById(R.id.progressBar2);
        progressBar.setProgress(0);
        textviewsplash=(TextView) this.findViewById(R.id.TextViewSplash);

        final long intervalo=100;
        timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run() {
                if(i<100){
                    progressBar.setProgress(i);
                    i++;
                }else{
                    timer.cancel();
                    Intent intent = new Intent(SplashActivity.this, Login.class);
                    startActivity(intent);
                    finish();
                }
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if(i==1){
                            textviewsplash.setText(tareas[0]);}
                        if(i==20){
                            textviewsplash.setText(tareas[1]);}
                        if(i==60){
                            textviewsplash.setText(tareas[2]);}
                        if(i==80){
                            textviewsplash.setText(tareas[3]);}
                    }
                });
            }
        }, 0, intervalo);

    }
}
