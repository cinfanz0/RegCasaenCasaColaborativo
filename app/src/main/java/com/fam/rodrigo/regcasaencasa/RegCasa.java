package com.fam.rodrigo.regcasaencasa;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.fam.rodrigo.regcasaencasa.Modelos.RegistroCasa;

import java.util.ArrayList;

public class RegCasa extends ActionBarActivity implements View.OnFocusChangeListener {
    //creo esto dos array para llenar el spinner que he creado - el arraylista y el adaptador
    private ArrayList<String> arraylist;
    private ArrayAdapter<String> adapter;
    EditText edtcalle;
    EditText edtterritorio;
    EditText edtamocasa;
    EditText edtncasa;
    EditText edtfecha;
    Spinner spsimbolo;
    EditText eddetalle;
    FloatingActionButton fab1;
    DataBaseManager manager;
    ArrayList<RegistroCasa> lstregcasa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_casa);
        //primero llenamos el spiner

        llenar_spinner();

        if (savedInstanceState == null) {
            //para recibir los valores enviados por el siguiente intent usando el Bundle:
            Bundle extras = getIntent().getExtras();
            //validamos si nos enviaron algun valor
            if (extras == null) {
                Log.d("extras", "extras es nulo");
            } else {
                //obtenemos el valor enviado segun la key envioada en este caso la key es "lstregcasas"
                //por ser arrayList  usamos getParcelableArrayList
                //para usar getParcelableArrayList y poder obtener los datos debemos implementar en nuestro modelo implements Parcelable
                //y tambien implementar todos sus metodos y listo
                lstregcasa = extras.getParcelableArrayList("lstregcasas");
            }
        } else {
            Log.d("savedInstanceState", "savedInstanceState");
        }

        //codificamos el escuchador cuando nos posicionemos en el campo fecha
        EditText txtfecha = (EditText) findViewById(R.id.edtfecha);
        //utilizamos el setOnFocusChangeListener para saber si han seleccionado la casilla de texto
        txtfecha.setOnFocusChangeListener(this);

        definirobjetos();
    }

    private void definirobjetos() {
        edtcalle = (EditText) findViewById(R.id.edtcalle);
        edtterritorio = (EditText) findViewById(R.id.edtterritorio);
        edtamocasa = (EditText) findViewById(R.id.edtamocasa);
        edtncasa = (EditText) findViewById(R.id.edtncasa);
        edtfecha = (EditText) findViewById(R.id.edtfecha);
        spsimbolo = (Spinner) findViewById(R.id.spsimbolo);
        eddetalle = (EditText) findViewById(R.id.eddetalle);
        edtcalle.requestFocus();
    }

    public void llenar_spinner() {
        Spinner spiner = (Spinner) findViewById(R.id.spsimbolo);
        //vamos a llenar el arrayList
        arraylist = new ArrayList<String>();
        arraylist.add("V");
        arraylist.add("O");
        arraylist.add("H");
        arraylist.add("NC");
        arraylist.add("N");
        arraylist.add("M");

        //ahora creamos el adapter que se llenara con los datos del arraylist
        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.fondoletraspinner, arraylist);
        //ahora a nuestro spinner o list lo llenamos con el adapter cargado
        spiner.setAdapter(adapter);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        //calidamos si la casilla de texto tiene el foco
        if (hasFocus) {
            //creamos una variable con el tipo de dato de nuestra clase creada DateDialog
            DateDialog dialog = new DateDialog(v);
            //le ponemos una transaccion al fragmen que saldra con el calendario
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            //por ultimo mostramos el dialog creado
            dialog.show(ft, "DatePiker");
        }
    }

    public void abrirConexion() {
        //para abrir la conexion llamamos a nuestro DataBaseManager quien se encarga de hacer toda
        //la conexion a la BD
        manager = new DataBaseManager(this);

    }


    //Con este metodo configramos lo que pasara cuando precionamos el boton de atras de android el que trae por defecto
    //aqui solo mostramos un mensaje
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            Toast.makeText(RegCasa.this, "No se ha guardado Ningun registro", Toast.LENGTH_SHORT).show();
        }

        return super.onKeyDown(keyCode, event);

    }

    //creamos un menu


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflar nuestro menú con los recursos mediante el menu inflater.
        //indocamos como parametro nuestro menu que se encuentra en la carpeta Menu
        //nuestro menu se llama menu_regcasa. ahi se encuentran nuestro botones a considerar con sus id y todo
        getMenuInflater().inflate(R.menu.menu_regcasa, menu);

        // También es posible añadir elementos de aquí . Use un identificador generado a partir
        // Recursos ( ids.xml ) para asegurar que todos los identificadores de menú son distintas .
        //para esto vamos a crear nuestrio archivo en values llamado ids ..que contendra los ID de iconos nuevos a ingresar cuando queramos


        return true;
    }

    //ponemos ahora el codigo para saber donde se hizo click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.guardar:
                //validamos que todos los campos esten vacios, si es asi no hara nada
                if (edtcalle.getText().toString().equals("") && edtterritorio.getText().toString().equals("")
                        && edtncasa.getText().toString().equals("") && edtfecha.getText().toString().equals("")
                        && eddetalle.getText().toString().equals("") && edtamocasa.getText().toString().equals("")) {
                    Toast.makeText(RegCasa.this, "No a ingresado información a guardar", Toast.LENGTH_SHORT).show();
                } else {
                    //abrimos primero la conexion a BD
                    abrirConexion();
                    //llamamos a la funcion insertar usando nuestra variable manager de la funcion abrirConexion
                    long res = manager.insertar(edtcalle.getText().toString(),
                            edtterritorio.getText().toString(), edtamocasa.getText().toString(), edtncasa.getText().toString(),
                            edtfecha.getText().toString(), spsimbolo.getSelectedItem().toString(), eddetalle.getText().toString());
                    //validamos la respuesta del insert
                    if (res == -1) {
                        Toast.makeText(RegCasa.this, "No se puedo guardar Registro", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegCasa.this, "Registro de Casa Guardado", Toast.LENGTH_SHORT).show();
                        //cerramos la conexion a BD
                        manager.cerrar_BD();

                        //creo una variable instanciada de mi modelo para almacenar los datos a grabar en la BD
                        RegistroCasa r = new RegistroCasa();
                        r.setCalle(edtcalle.getText().toString());
                        r.setTerritorio(edtterritorio.getText().toString());
                        r.setNcasa(edtncasa.getText().toString());
                        r.setCfecha(edtfecha.getText().toString());
                        r.setAmocasa(edtamocasa.getText().toString());

                        //el nuevo registro que hemos guardao en la tabla lo pondremos ahora en la lista envida por la primera actividad.
                        //Ojo esta lista ya contiene datos que fueron sacados de la BD al ejecutar la primera vez la APP.
                        lstregcasa.add(r);

                        //Devolvemos el foco al primera activity
                        Intent returnIntent = new Intent();
                        //Devolvemos la lista con el nuevo registro
                        returnIntent.putExtra("result", lstregcasa);
                        //al ejecutar el setResult(resutlado OK, mensaje o valores a enviar) ya estamos pasando los valores al activy inicial
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
                    }

                }

                break;

            case R.id.cancelar:
                Toast.makeText(RegCasa.this, "No se ha guardado Ningun registro", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

