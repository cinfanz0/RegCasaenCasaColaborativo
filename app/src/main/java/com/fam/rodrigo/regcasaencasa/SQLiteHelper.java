package com.fam.rodrigo.regcasaencasa;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rodrigo on 27/01/2016.
 */

//Esta clase especifica nos ayudara a abrir la base de datos e interactuar con ella.
// es importante esta clase
// 1. extendemos nuestra clase de SQLiteOpenHelper e importamos los metodos

public class SQLiteHelper extends SQLiteOpenHelper{

    //2.creamos los parametros que vamos a usar en esta clase
    private static final String DB_NAME = "registrocasa"; //este es el nombre de la base de datos
    private static final int DB_SCHEMA_VERSION = 1; //esta será la versión del esquema. si cambia seria 2

    //3. vamos a modificar el constructor. lo hacemos para que cada vez que llamos a nuestra clase SQLiteHelper
    //no le pasemos todos los parametros pues aqui ya esta definido el nombre BD y version
    //asi que comentamos la linea y cambiamos los parametros en super

    //public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
    public SQLiteHelper(Context context) {
        //super(context, name, factory, version); -- comentamos esto tambien y cambiamos por las variables definidas Bd y version
                                                //el tercer parametro siempre sera null
        super(context, DB_NAME, null, DB_SCHEMA_VERSION);
    }

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //aqui se ejecutará la creacion de la BD y tabla la prinmera vez. si al llamar a esta clase
        //la tabla ya existe entonces no hace nada

        //4. vamos a crear una clase java mas donde pondremos todas nuestras sentencias SQL en nuestro caso se llama DataBaseManager
        //5. luego de crear y codificar la clase DataBaseManager vamos a ejecutar la creación de la tabla con el siguiente codigo.
        //al parametro db de esta funcion llamamos a la funcion execSQL la cual nos ayudara a ejecutar
        //la sentencia sql en la base de datos
        //le pasamos como parametro a execSQL la variable respectiva de la clase DataBaseManager
        db.execSQL(DataBaseManager.CREATE_TABLE);

        //6. hasta aqui no se ha creado la BD ni la tabla. solo se creará cuando se ejecute la aplicacion.
        //esto lo configuramos en la funcion onCreate de la actividad principal. en nuestro caso se llama RegCasa
        //asi que ahi estara el codigo para que se cree la BD y tabla

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
