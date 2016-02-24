package com.fam.rodrigo.regcasaencasa;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.logging.Handler;

public class Bienvenida extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);

        new Thread(new Runnable() {
            public void run() {
                //Aquí ejecutamos nuestras tareas costosas
                EjecutaBienvenida();
            }
        }).start();

    }

    private void EjecutaBienvenida() {
        try {
            Thread.sleep(1000);
            Intent i = new Intent(getApplicationContext(),Inicio.class);
            startActivity(i);
            finish();
        }catch (Exception e) {
            Log.e(Bienvenida.class.toString(), e.getMessage());
        }


    }

  }
