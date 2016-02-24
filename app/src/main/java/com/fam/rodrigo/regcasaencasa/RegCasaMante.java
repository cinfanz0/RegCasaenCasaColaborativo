package com.fam.rodrigo.regcasaencasa;

import android.app.FragmentTransaction;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class RegCasaMante extends AppCompatActivity implements View.OnFocusChangeListener {
    DataBaseManager manager;
    String codigoreg;
    private Cursor cursor;
    EditText xcalle;
    EditText xterritorio;
    EditText xamocasa;
    EditText xnrocasa;
    EditText xfecha;
    Spinner xsimbolo;
    EditText xdetalle;
    FloatingActionButton fab1;
    FloatingActionButton fab2;
    Menu menugeneral;

    private ArrayList<String> arraylist;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_casa_mante);

        inicializar();
        bloquearCampos();

        if (savedInstanceState == null) {
            //usamos el Bundle para recibir el parametro enviado
            //entonces definimos una variable tipo Bundle y con getIntent().getExtras() obtenemos el valor enviado por la actividad antetrior
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                codigoreg = "";
                Toast.makeText(RegCasaMante.this, "codreg= "+codigoreg, Toast.LENGTH_SHORT).show();
            } else {
                //le pasamos el valor del extra a nuestra varible con la cual trabajaremos
                codigoreg = extras.getString("codigoIdReg");
                Toast.makeText(RegCasaMante.this, "codreg= "+codigoreg, Toast.LENGTH_SHORT).show();
                buscaRegistro(codigoreg);
            }
        } else {
            codigoreg = (String) savedInstanceState.getSerializable("codigoIdReg");
        }
    }

    private void inicializar() {
        xcalle = (EditText) findViewById(R.id.edtcalle);
        xterritorio = (EditText) findViewById(R.id.edtterritorio);
        xamocasa = (EditText) findViewById(R.id.edtamocasa);
        xnrocasa = (EditText) findViewById(R.id.edtncasa);
        xfecha = (EditText) findViewById(R.id.edtfecha);
        xsimbolo = (Spinner) findViewById(R.id.spsimbolo);
        xdetalle = (EditText) findViewById(R.id.eddetalle);

        xfecha.setOnFocusChangeListener(this);
    }

    private void bloquearCampos() {
        xcalle.setEnabled(false);
        xcalle.setTextColor(this.getResources().getColor(R.color.letrasnegras));
        xterritorio.setEnabled(false);
        xterritorio.setTextColor(this.getResources().getColor(R.color.letrasnegras));
        xamocasa.setEnabled(false);
        xamocasa.setTextColor(this.getResources().getColor(R.color.letrasnegras));
        xnrocasa.setEnabled(false);
        xnrocasa.setTextColor(this.getResources().getColor(R.color.letrasnegras));
        xfecha.setEnabled(false);
        xfecha.setTextColor(this.getResources().getColor(R.color.letrasnegras));
        xsimbolo.setEnabled(false);
        xdetalle.setEnabled(false);
        xdetalle.setTextColor(this.getResources().getColor(R.color.letrasnegras));
    }


    private void buscaRegistro(String codigoreg) {
        manager = new DataBaseManager(this);
        cursor = manager.ObtienedatosPuntual(codigoreg);
        //seteamos los resultados a variables
        String scalle = cursor.getString(0);
        String snterritorio = cursor.getString(1);
        String samocasa = cursor.getString(2);
        String sncasa = cursor.getString(3);
        String scfecha = cursor.getString(4);
        String ssimbol = cursor.getString(5);
        String sdetalle = cursor.getString(6);

        //ahora le pasamos los valores recuperados a las casillas de texto del layout
        xcalle.setText(scalle);
        xterritorio.setText(snterritorio);
        xamocasa.setText(samocasa);
        xnrocasa.setText(sncasa);
        xfecha.setText(scfecha);
        llenar_spinnerConsul(ssimbol);
        xdetalle.setText(sdetalle);

        //cerramos la BD
        manager.cerrar_BD();
    }

    private void llenar_spinnerConsul(String dato) {
        //vamos a llenar el arrayList
        arraylist = new ArrayList<String>();
        arraylist.add(dato);
        //ahora creamos el adapter que se llenara con los datos del arraylist
        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.fondoletraspinner, arraylist);
        //ahora a nuestro spinner o list lo llenamos con el adapter cargado
        xsimbolo.setAdapter(adapter);
    }


    private void activarcampos() {
        xcalle.setEnabled(true);
        xcalle.setTextColor(this.getResources().getColor(R.color.letrasnegras));
        xterritorio.setEnabled(true);
        xterritorio.setTextColor(this.getResources().getColor(R.color.letrasnegras));
        xamocasa.setEnabled(true);
        xamocasa.setTextColor(this.getResources().getColor(R.color.letrasnegras));
        xnrocasa.setEnabled(true);
        xnrocasa.setTextColor(this.getResources().getColor(R.color.letrasnegras));
        xfecha.setEnabled(true);
        xfecha.setTextColor(this.getResources().getColor(R.color.letrasnegras));
        xsimbolo.setEnabled(true);
        xdetalle.setEnabled(true);
        xdetalle.setTextColor(this.getResources().getColor(R.color.letrasnegras));
        xcalle.setFocusable(true);
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
        ;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflar nuestro menú con los recursos mediante el menu inflater.
        //indocamos como parametro nuestro menu que se encuentra en la carpeta Menu
        //nuestro menu se llama menu_regcasa. ahi se encuentran nuestro botones a considerar con sus id y todo
        this.menugeneral = menu;
        getMenuInflater().inflate(R.menu.menu_regcasa_mante, menu);

        // También es posible añadir elementos de aquí . Use un identificador generado a partir
        // Recursos ( ids.xml ) para asegurar que todos los identificadores de menú son distintas .
        //para esto vamos a crear nuestrio archivo en values llamado ids ..que contendra los ID de iconos nuevos a ingresar cuando queramos

        return true;
    }

    //ponemos ahora el codigo para saber donde se hizo click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editar:
                // También es posible añadir elementos de aquí . Use un identificador generado a partir
                // Recursos ( ids.xml ) para asegurar que todos los identificadores de menú son distintas .
                //para esto vamos a crear nuestrio archivo en values llamado ids ..que contendra los ID de iconos nuevos a ingresar cuando queramos

                // Usaremos el metodo MenuItem: este metodo nos permitira identificar los item que ya exiten en la barra de menu o crear nuevos item para el menu
                //como veremos ahora.
                // 1:adicionaremos primero el boton Actualizar
                // parametros del metodo menu.add(pos grupo,id de item,nro de orden, titulo en texto)
                MenuItem ItemActualizar = menugeneral.add(0, R.id.actualizar, 1, "ACTUALIZAR");
                //le adicionamos el icono al nuevo item del menu
                ItemActualizar.setIcon(R.drawable.save);
                //ahora necesitamos utilizar el método MenuItemCompat para llamar aal icono que hemos definido y realizara la accion que deseamos
                //con esto se mostrara el nuevo item con su icono en el menu
                //OJO: con MenuItemCompat podemos definir cualquier accion a realizar con los botones del menu
                MenuItemCompat.setShowAsAction(ItemActualizar, MenuItem.SHOW_AS_ACTION_IF_ROOM);

                //2. Ahora vamos a adicionar el boton cancelar
                MenuItem ItemCancel = menugeneral.add(0, R.id.cancel, 0, "CANC.");
                //le adicionamos el icono al nuevo item del menu
                ItemCancel.setIcon(R.drawable.cancel);
                //OJO: con MenuItemCompat podemos definir cualquier accion a realizar con los botones del menu
                MenuItemCompat.setShowAsAction(ItemCancel, MenuItem.SHOW_AS_ACTION_IF_ROOM);

                //3. ahora, como al precionar el boton editar queremos aparecera el boton actualizar, vamos a ocultar el boton editar
                //para esto usamos MenuItem para identificar el boton editar.
                //entonces a nuestro menu (menugeneral) le pasamos el metodo findItem(id de item a buscar) y con esto ya tenemos control del item editar
                MenuItem ItemEditar = menugeneral.findItem(R.id.editar);
                //ahora lo escondemos con setVisible
                ItemEditar.setVisible(false);

                //4. ahora activamos los campos del formulario para editar los valores
                activarcampos();
                llenar_spinner();
                //5. le damos el foco al primer editText: edtcalle
                xcalle.requestFocus();

                break;

            case R.id.actualizar:
                manager = new DataBaseManager(this);
                //llamamos a la funcion insertar usando nuestra variable manager de la funcion abrirConexion
                long res = manager.actualizar(codigoreg, xcalle.getText().toString(),
                        xterritorio.getText().toString(), xamocasa.getText().toString(), xnrocasa.getText().toString(),
                        xfecha.getText().toString(), xsimbolo.getSelectedItem().toString(), xdetalle.getText().toString());
                //validamos la respuesta del insert
                if (res == -1) {
                    Toast.makeText(RegCasaMante.this, "No se puedo actualizar Registro", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegCasaMante.this, "Registro Actualizado", Toast.LENGTH_SHORT).show();
                    finish();
                }

                break;

            case R.id.cancel:
                //1. devolvemos los valores de los campos para borar los cambios hechos porqueno se ha grabado nada
                buscaRegistro(codigoreg);
                //2. bloqueamos todos los campos
                bloquearCampos();

                //3.Eliminamos los iconos de cancelar y actualizar
                //Eliminamos ITEM CANCELAR
                menugeneral.removeItem(R.id.cancel);

                //Eliminamos ITEM ACTUALIZAR
                menugeneral.removeItem(R.id.actualizar);

                //4. ahora mostramos de nuevo el item Editar
                MenuItem Itedit = menugeneral.findItem(R.id.editar);
                //ahora lo escondemos con setVisible
                Itedit.setVisible(true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
