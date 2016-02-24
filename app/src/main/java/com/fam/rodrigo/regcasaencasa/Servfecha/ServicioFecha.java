package com.fam.rodrigo.regcasaencasa.Servfecha;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.fam.rodrigo.regcasaencasa.DataBaseManager;
import com.fam.rodrigo.regcasaencasa.Inicio;
import com.fam.rodrigo.regcasaencasa.Modelos.RegistroCasa;
import com.fam.rodrigo.regcasaencasa.R;
import com.fam.rodrigo.regcasaencasa.RegCasaMante;
import com.fam.rodrigo.regcasaencasa.SQLiteHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Rodrigo on 20/02/2016.
 */
public class ServicioFecha extends Service {
    //creo una variable del tipo de mi tarea asincrona
    MyTask myTask;
    //con esta variable vamos a controlar la ejecución de la atera en segundo plano
    private boolean cent;
    private SQLiteHelper helperx;
    private SQLiteDatabase dbx;
    //estos son los campos que obtendre de la consulta a la BD
    public static final String TABLE_NAME = "registrocasacasa"; //nombre de la tabla
    public static final String CN_ID = "_id"; //ID de la tabla que servira de llave primaria
    public static final String CN_FECHA = "c_fecha"; //nombre del campo
    public static final String CN_AMOCASA = "n_amocasa"; //nombre del amo de casa

    String idReg;
    String cfecha;
    String AmoCasa;
    //AQUi
    //onCreate: Se ejecuta cuando el servicio está creado en memoria.
    // Si el servicio ya está activo, entonces se evita de nuevo su llamada.
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(null, "Servicio Iniciado!...");
        //myTask = new MyTask();
    }

    //onStartCommand():Método que ejecuta las instrucciones del servicio.
    // Se llama solo si el servicio se inició con startService().
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        myTask = new MyTask();
        myTask.execute();
        //return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    //onBind(): Solo se ejecuta si el servicio fue ligado con bindService() por un componente.
    // Retorna una interfaz Java de comunicación del tipo IBinder.
    // Este método siempre debe llamarse, incluso dentro de los started services, los cuales retornan null.
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //onDestroy(): Se llama cuando el servicio está siendo destruido.
    // Importantísimo que dentro de este método detengas los hilos iniciados.
    @Override
    public void onDestroy() {
        super.onDestroy();
        cent = false;
        Log.d(null, "Servicio destruido!...");
        myTask.cancel(true);
    }


    //CREAMOS NUESTRA TAREA ASINCRONA, LA QUE CORRERA EN SEGUNDO PLANO PARA EL PROCESO DEL SERVICIO
    private class MyTask extends AsyncTask<String, String, String> {

        private DateFormat dateFormat;
        private String date;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dateFormat = new SimpleDateFormat("dd/M/yyyy");
            cent = true;
        }

        @Override
        protected String doInBackground(String... params) {
            while (cent) {

                try {
                    //publishProgress(date);
                    validaFechaVisita();
                    //triggerNotification();
                    Thread.sleep(20000); // 1segundos
                    Log.d(null, "por aqui dara vueltas", null);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            //Toast.makeText(getApplicationContext(), "Hora actual: " + values[0], Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(getApplicationContext(), "paso a Oncancelled", Toast.LENGTH_SHORT).show();
            cent = false;
        }

        private void validaFechaVisita() {
            helperx = new SQLiteHelper(getApplicationContext());
            dbx = helperx.getWritableDatabase();

            //rawquery devuelve un cursopr y todos sus valores
            //armamos la cadena de consulta
            Cursor c = dbx.rawQuery(" SELECT " + CN_ID + "," + CN_FECHA +","+CN_AMOCASA+ " FROM " + TABLE_NAME, null);
            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya mas registros
                do {
                    //seteamos los resultados a variables
                    idReg = c.getString(0);
                    cfecha = c.getString(1);
                    AmoCasa = c.getString(2);
                    Log.d(null, "IDREGSSS: " + idReg);
                    Log.d(null, "FECHA DE REGISTRO DE BD: " + cfecha);

                    //VALIDAMOS AHORA LA FECHA ENCONTRADA CON AL FECHA DEL SISTEMA PARA EMITIR LA NOTIFICACIÓN
                    date = dateFormat.format(new Date());
                    publishProgress(date);
                    if (date.equals(cfecha)) {
                        CharSequence mensajeNotifi = "Visitar a "+AmoCasa+" - Revise su Registro de casa";
                        Notification(idReg,mensajeNotifi);
                    }


                } while (c.moveToNext());
            }
        }
    }

    private void Notification(String Idreg, CharSequence menNotifi) {
        int IDNotifi = Integer.parseInt(Idreg);
        NotificationCompat.Builder notificacion = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info) //icono pequeño que va a la derecha
                .setLargeIcon((((BitmapDrawable) getResources()
                        .getDrawable(R.mipmap.ic_launcher)).getBitmap())) //icono grande que va a la izquierda
                .setContentTitle("REG. CASA - REVISITA PENDIENTE") //titulo cuando despliegas la seccion de notificacion
                //.setContentText(menNotifi) //contenido del mensaje
                .setTicker("Registro de Casa - Revisita") //mensaje que aparece arriba en la barra antes de que bajes la seccion de notificaciones
                //.setContentInfo("2")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(menNotifi))
                .setAutoCancel(true);

        //creamos ahora el intent de notificacion para que cuando pinchemos sobre la notificacion se abra la ventana que queremos
        //vasmos a indica que ventana debe abrirse
        Intent intNotificacion = new Intent(this, RegCasaMante.class);


        //agregamos los extra que vamos a enviar a la otra actividad apra que cargue los datos
        intNotificacion.putExtra("codigoIdReg", Idreg);
        /*
        //vamos a indicar que este intent es un inten pendiente
        //para que espera a lanzarse solo cuando damos click en la notificacion - lo haemos con pendingIntent
        PendingIntent intenPendiente = PendingIntent.getActivity(this,0,intNotificacion,PendingIntent.FLAG_UPDATE_CURRENT); //parametro de PendingIntent.getActivity(contexto, flag0, inten a abrir,PendingIntent.FLAG_UPDATE_CURRENT permitira que podamos pasar los extra a la sighuiente actividad)
        //asociamos ahora nuestra notificacion  con el inten pendiente para que espere a que se abra con el click
        notificacion.setContentIntent(intenPendiente);
        //agregamos una id a nuestra notificacion para diferenciarla
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(10,notificacion.build());
        */

        // El objeto constructor pila contendrá una pila de nuevo artificial para el
        // Inicia la actividad .
        // Esto asegura que navegar hacia atrás desde la Actividad lleva a cabo de
        // Su aplicación a la pantalla de inicio .
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Añade la pila de nuevo para la Intención ( pero no el intento mismo ) - la pila de actividades se respeta segun el manifest
                stackBuilder.addParentStack(RegCasaMante.class);
        // Adds the Intent that starts the Activity to the top of the stack
                stackBuilder.addNextIntent(intNotificacion);
        //vamos a indicar que este intent es un inten pendiente
        //para que espera a lanzarse solo cuando damos click en la notificacion - lo haemos con pendingIntent
                PendingIntent intenPendiente =
                        stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        notificacion.setContentIntent(intenPendiente);
                NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
                nm.notify(IDNotifi, notificacion.build());


    }


}
