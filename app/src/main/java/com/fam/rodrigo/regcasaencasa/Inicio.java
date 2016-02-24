package com.fam.rodrigo.regcasaencasa;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.fam.rodrigo.regcasaencasa.Adaptador.Adaptador_RegistroCasa;
import com.fam.rodrigo.regcasaencasa.Modelos.RegistroCasa;
import com.fam.rodrigo.regcasaencasa.Servfecha.ServicioFecha;

import java.util.ArrayList;

public class Inicio extends AppCompatActivity {

    //creamos el recyler para mostrar los datos y el array para llenar los datos a mostrar
    RecyclerView recycler;
    ArrayList<RegistroCasa> lstregcasas;
    ArrayList<RegistroCasa> lstregcasasresponse;
    DataBaseManager manage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        Log.d(null, "Por aqui inicia");

        //iniciamos el servicio para validar la fecha y emitir notificaciones
        startService(new Intent(Inicio.this, ServicioFecha.class));


        //esto es para agregar el icono a la barra de actionbar
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //relacione el recyler con el recycler de mi modelo row_regcasa
        //aqui llenaremos por primeras vez el recyclerview
        recycler = (RecyclerView) findViewById(R.id.my_recycleview);
        setData();
        callAdapter(lstregcasas); //CAMBIO JOSE

        //codigo del boton flotante
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                //abrimos la siguiente actividad que es el registro de casa en casa
                Intent regCasaIntent = new Intent(getApplicationContext(),RegCasa.class); //CAMBIO JOSE
                //startActivity(regCasa); en lugar de staractivity vamos a usar el staractivityforresult
                //para que la siguiente actividad nos devuelva un valor
                //Enviamos entonces la lista lstregcasas que se lleno en setData() con los datos de la tabla de la BD
                // a la siguiente actividad. lo hacemos con putExtra(nombre key, conjunto de valores - lista en nuestro caso)
                regCasaIntent.putExtra("lstregcasas",lstregcasas);
                //Iniciamos ahora la actividad con startActivityForResult (pasamos el intent, un parametro para reconocer la respuesta)
                //para recibir la respuesta que nos envia el startActivityForResult debemos implementar aqui el metodo, fuera de este boton
                startActivityForResult(regCasaIntent,1);

            }
        });
    }

    //implementamos el metodo onActivityResult y aqui recibiremos el array enviado por la actividad RegCasa
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //aqui comparamos el identificador que enviamos con el requestCode. si es igual al identificardo que enviamos, entonces es la respuesta que espearamos
        if (requestCode == 1) {
            //Aqui comparamos el codigo enviando por la siguiente actividad en el setResult (ver RegCasa)
            if(resultCode == Inicio.RESULT_OK){
                //Obtenemos la lista actualizada y llamamos al metodo callAdapter para llenar de nuevo el recycler view
                //lo almacenamos en un nuevo arraylist que almacenara la respuesta
                lstregcasasresponse = data.getParcelableArrayListExtra("result");
                //llenamos nuevamente el recycler view con la nueva lista actualizada
                callAdapter(lstregcasasresponse);
                //lstregcasas = lstregcasasresponse;
                //llamamos de nuevo a setData() para cargar nuevamente el array principal lstregcasas
                // con los datos de la BD. este array se envia al siguiente activy asi que debe tener los datos de la BD
            }
            if (resultCode == Inicio.RESULT_CANCELED) {
                //Write your code if there's no result
                Log.d("log", "No hay resultado");
                callAdapter(lstregcasas);

            }
        }
    }


    private void callAdapter(ArrayList<RegistroCasa> lstregcasasResult) {
        LayoutInflater inflador = getLayoutInflater();
        //aqui le pasamos al recycler el adapter. como parametro le pasamos nuestro adaptador creado
        //con esto le pasamos valores para que se lleve el layout con los datos de la lista, luego el contexto y por ultimo el inflador para mostrar un dialog
        recycler.setAdapter(new Adaptador_RegistroCasa(lstregcasasResult, getApplicationContext(),inflador));
        //ahora le pasamos la forma en como se va a mostrar los datos en el layout manager}
        recycler.setLayoutManager(new LinearLayoutManager(this));
        //para agregarle una amimacion
        recycler.setItemAnimator(new DefaultItemAnimator());


    }

    private void setData() {
        manage = new DataBaseManager(this);
        lstregcasas = manage.Obtienedatos();
    }



    @Override
    protected void onResume() {
        super.onResume();
        setData();
        callAdapter(lstregcasas);
    }


    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */
}
