package com.fam.rodrigo.regcasaencasa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.fam.rodrigo.regcasaencasa.Modelos.RegistroCasa;

import java.util.ArrayList;

/**
 * Created by Rodrigo on 27/01/2016.
 */

public class DataBaseManager {

    ArrayList<RegistroCasa> lstregcas;

    //A. creamos las variables del nombre de la tabla y el nombre de los campos
    public static final String TABLE_NAME = "registrocasacasa"; //nombre de la tabla
    public static final String CN_ID = "_id"; //ID de la tabla que servira de llave primaria
    public static final String CN_NCALLE = "nom_calle"; //nombre del campo
    public static final String CN_NTERRITO = "n_territorio"; //nombre del campo
    public static final String CN_AMOCASA = "n_amocasa"; //nombre del amo de casa
    public static final String CN_NCASA = "n_casa"; //nombre del campo
    public static final String CN_FECHA = "c_fecha"; //nombre del campo
    public static final String CN_SIMBOL = "c_simbolo"; //nombre del campo
    public static final String CN_DETALLE = "c_detalle"; //nombre del campo

    //B. creamos ahora el string para la creacion de al tabla

    //create table registrocasacasa (_id integer primary key autoincrement,
    //                               nom_calle text,
    //                               n_territorio text not null,
    //                               n_casa text,
    //                               c_fecha text not null,
    //                               c_simbolo text not null,
    //                               c_detalle text not null);
    public static final String CREATE_TABLE =
            "create table " +TABLE_NAME + " ("
            +CN_ID+ " integer primary key autoincrement,"
            +CN_NCALLE+ " text,"
            +CN_NTERRITO+ " text not null,"
            +CN_AMOCASA+ " text,"
            +CN_NCASA+ " text,"
            +CN_FECHA+ " text not null,"
            +CN_SIMBOL+ " text not null,"
            +CN_DETALLE+ " text not null);";

    //C. ahora vamos a volver a nuestra clase SQLiteHelper y desde ahi llamaremos a nuestra variable
    //CREATE_TABLE para ejecutar la creación de la tabla en la funcion onCreate

    //hemos creado el constructor de nuestra clase DatabaseManager
    //para aqui poner nuestro codigo de instanciar el acceso de escritura a BD
    //definimos objetos privados que seran los que nos permitiran ingresar a la BD
    private SQLiteHelper helper;
    private SQLiteDatabase db;
    //estos los usaremos en nuestro contructopr para acceder a BD
    public DataBaseManager(Context context) {
        //Aqui vamos a abrir la BD en moto escritura
        // Primero vamos a crear una variable instanciada de nuestra clase SQLiteHelper.
        //solo le pasamos el contexto porque lo cambiamos en la respectiva clase
        helper = new SQLiteHelper(context);

        //ahora creamos una variable instanciada de SQLiteDatabase. esto nos permitira conectar a la BD y crearla o escribir en ella
        //es como si definieramos una BD copn este comando. Aqui le pasamos el valor de nuestro helper
        //quien tiene la logica para crear la Bd y tabla
        //llamamos a su funcion getWritableDatabase quien creara la tabla, y la bd. si existe la BD solo nos devuelve información.
        //si no existe crea la BD y tabla y nos devuele la información.

        db = helper.getWritableDatabase();
    }

    //esta funcion generara los contenedores
    private ContentValues generarContentValues(String nom_calle,String n_territorio,String nom_amocasa, String n_casa,String c_fecha,String c_simbolo,String c_detalle)
    {
        //generamos el contenedor de valores con ContentValues
        ContentValues valores = new ContentValues();
        //ahora con el metodo put, le indicaremos siempre un campo y su valor a cargar o llenar
        valores.put(CN_NCALLE,nom_calle);
        valores.put(CN_NTERRITO,n_territorio);
        valores.put(CN_AMOCASA,nom_amocasa);
        valores.put(CN_NCASA,n_casa);
        valores.put(CN_FECHA,c_fecha);
        valores.put(CN_SIMBOL,c_simbolo);
        valores.put(CN_DETALLE,c_detalle);

        //devolvemos el contenedor cargado
        return valores;
    }

    //creamos ahora los metodos para insertar
    //recuerda en java -- cuando idnicas VOID es porque ese metodo no devuelve nada
    public long insertar (String nom_calle,String n_territorio,String nom_amocasa, String n_casa,String c_fecha,String c_simbolo,String c_detalle)
    {
        //el metodo insertar de android necesita los siguiente parametros
        //db.insert(TABLA,NullColumnHack, ContentValues)
        //como el tercer parametro es un contenedor de valores, lo vamos a generar usando ContentValues
        //esto se encuentra en nuestra funcion generarContentValues


        //Aqui le pasamos los 3 parametros que solicita el metodo insert
        //el tercer parametro lo obtenemos de nuestra funcion generarContentValues
        long lon =
        db.insert(TABLE_NAME,null,generarContentValues(nom_calle,n_territorio,nom_amocasa,n_casa,c_fecha,c_simbolo,c_detalle)); //este metodo insert devuelve un long
        //si es -1 el valor devuelto hay error. mayor a eso es ok

        return lon;
    }

    public void eliminar(String cid)
    {
        //la sentencia eliminar en slqite tiene la sigueinte estructura:
        //db.delete(Tabla,Clausura where,argumentos where como vector string)
        db.delete(TABLE_NAME,CN_ID+"=?",new String[]{cid});
    }

    public void cerrar_BD() {
        db.close();
    }

    public ArrayList<RegistroCasa> Obtienedatos ()
    {
        lstregcas = new ArrayList<RegistroCasa>();
        //rawquery devuelve un cursopr y todos sus valores
        //armamos la cadena de consulta
        Cursor c = db.rawQuery(" SELECT "+CN_ID+","+CN_NCALLE+","+CN_NTERRITO+","+CN_AMOCASA+","+CN_NCASA+","+CN_FECHA+" FROM "+TABLE_NAME, null);
        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya m�s registros
            do {
                //seteamos los resultados a variables
                String idReg = c.getString(0);
                String calle= c.getString(1);
                String nterritorio = c.getString(2);
                String amocasa = c.getString(3);
                String ncasa = c.getString(4);
                String cfecha = c.getString(5);


                //creo una variable instanciada de mi modelo para almacenar los datos
                RegistroCasa r = new RegistroCasa();
                r.setIdreg(idReg);
                r.setCalle(calle);
                r.setTerritorio(nterritorio);
                r.setAmocasa(amocasa);
                r.setNcasa(ncasa);
                r.setCfecha(cfecha);

                lstregcas.add(r);


            } while(c.moveToNext());
        }
        return lstregcas;
    }

    public Cursor ObtienedatosPuntual (String codigoreg)
    {

        //rawquery devuelve un cursopr y todos sus valores
        //armamos la cadena de consulta
        Cursor c = db.rawQuery(" SELECT "+CN_NCALLE+","+CN_NTERRITO+","+CN_AMOCASA+","+CN_NCASA+","+CN_FECHA+
                ","+CN_SIMBOL+","+CN_DETALLE+" FROM "+TABLE_NAME + " where "+CN_ID+"="+codigoreg, null);

        c.moveToFirst();
        //Nos aseguramos de que existe al menos un registro y como es uan consulta puntual nos tremos el primer registro
         /*if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya m�s registros
            do {
                //seteamos los resultados a variables
                String scalle= c.getString(0);
                String snterritorio = c.getString(1);
                String samocasa = c.getString(2);
                String sncasa = c.getString(3);
                String scfecha = c.getString(4);
                String ssimbol = c.getString(5);
                String sdetalle = c.getString(6);

            } while(c.moveToNext());
        } */
        return c;
    }

    public long actualizar (String cod_id,String nom_calle,String n_territorio,String nom_amocasa, String n_casa,String c_fecha,String c_simbolo,String c_detalle)
    {
        //el metodo update de android necesita los siguiente parametros
        //db.insert(TABLA, ContentValues, Clausula Where, Argumento Where)

        //como el segundo parametro es un contenedor de valores, lo vamos a generar usando ContentValues
        //esto se encuentra en nuestra funcion generarContentValues


        //Aqui le pasamos los 4 parametros que solicita el metodo insert
        //el segundo parametro lo obtenemos de nuestra funcion generarContentValues
        long lon =
                db.update(TABLE_NAME, generarContentValues(nom_calle, n_territorio, nom_amocasa, n_casa, c_fecha, c_simbolo, c_detalle),
                        CN_ID+"=?",new String[]{cod_id}); //este metodo insert devuelve un long
        //si es -1 el valor devuelto hay error. mayor a eso es ok

        return lon;
    }


}
