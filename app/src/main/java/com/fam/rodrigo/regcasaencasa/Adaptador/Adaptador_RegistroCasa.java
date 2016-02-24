package com.fam.rodrigo.regcasaencasa.Adaptador;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;
import android.widget.Toast;

import com.fam.rodrigo.regcasaencasa.DataBaseManager;
import com.fam.rodrigo.regcasaencasa.DialogEliminar;
import com.fam.rodrigo.regcasaencasa.ItemClickListener;
import com.fam.rodrigo.regcasaencasa.Modelos.RegistroCasa;
import com.fam.rodrigo.regcasaencasa.R;
import com.fam.rodrigo.regcasaencasa.RegCasaMante;

import java.util.ArrayList;

/**
 * Created by Rodrigo on 28/01/2016.
 */
//5. completamos el extend y entre los <> colocamos Adaptador_RegistroCasa. y el viewholder creado. por ultimo implementamos los metodos
//1.creamos la primera clase extendiendo de reciler adapter
public class Adaptador_RegistroCasa extends RecyclerView.Adapter<Adaptador_RegistroCasa.registroCasaviewHolder>{

    //8. creamos el constructos de la clase al cual le pasaremos como parametro la lista con los datos a mostrar
    ArrayList<RegistroCasa> lstregCasaCasa;
    private Context mContext;
    private Activity mactiviti;
    private LayoutInflater layo;
    RegistroCasa feedItem;



    public Adaptador_RegistroCasa(ArrayList<RegistroCasa> lstregCasa,Context context, LayoutInflater layoo) {
        this.lstregCasaCasa = lstregCasa;
        this.mContext = context;
        this.layo = layoo;
    }

    @Override
    public registroCasaviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //6. creamos una view para pasarle nuestro layout creado: row_regcasa
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_regcasa,parent,false);

        //7. retornamos la vista creada
        return new registroCasaviewHolder(vista);
    }

    @Override
    public void onBindViewHolder(final registroCasaviewHolder holder, int position) {
        //9. creamos aqui una variable instanciada en nuestro modelo RegistroCasa que creamos.
        //Esta variable tomara la posición de la lista que nos pasan al invocar al dapter. para esto se usa get(position)
        //donde position es la posicion del registro que se muestra en pantalla
        //que nos ha pasado al invocar al adaptador
        RegistroCasa regcasa = lstregCasaCasa.get(position);

        //10. ahora a nuestro modelo row_regcasa le vamos a pasar los valores a mostrar
        //según la posicion de la lista de datos que nos han enviado.
        holder.calle.setText(regcasa.getCalle());
        holder.ncasa.setText(regcasa.getNcasa());
        holder.territorio.setText(regcasa.getTerritorio());
        holder.cfecha.setText(regcasa.getCfecha());
        holder.amocasa.setText(regcasa.getAmocasa());

        //G: ahora usaremos en el holder nuestra funcion creada setClickListener y como
        // parametro le pasaremos una nueva instancia de nuetra interface. automaticamente se generara el metodo onclick anidado
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {

                //ahora vamos con la posicion obtenida, vamos a bsucar esa posicion en nuestro array de datos y vamos a estar la infomación que tenemos.
                //entonces definimos otra variable instanciada con mi modelo RegistroCasa
                 feedItem = lstregCasaCasa.get(position);
                //H: Aqui pondremos toda nuestra loquica según los click que queramos ahcer
                if (isLongClick)
                {
                    //Toast.makeText(mContext, "#" + position + " - " + position + " (Long click)", Toast.LENGTH_SHORT).show();
                    //aqui vamos a llamar a un Alert Dialog personalizado
                    //ahora creamos una vista que sera llenada con nuestro layout de eliminar
                   Intent intent2 = new Intent(mContext, DialogEliminar.class);
                    //ahora le pasamos a la nueva actividad el codigo ID del registro para luego buscar los datos
                    intent2.putExtra("codigoIdReg", feedItem.getIdreg().toString());
                    //a veces sale errro porque tratamos de abrir una actividad desde una clase java y no desde otra actividad.
                    //en ese caso debemos setear un flag a nuestro intent para que pueda abrir otra actividad
                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent2);

                   /*INICIO CODIGO ELIMINAR ALERTDIALOG
                    View dialogLayout = layo.inflate(R.layout.eliminaregistro,null);

                    //ahora mostramos el dialogo
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext()); //en alertdialog.builder, debe ir el contexto de donde estamos. usar el de la vista actual
                    //seteamos el valor de la vista creada al dialogo
                    builder.setView(dialogLayout).setPositiveButton("ELIMINAR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DataBaseManager manager = new DataBaseManager(mContext);
                            manager.eliminar(feedItem.getIdreg().toString());
                            Toast.makeText(mContext, "Registro de casa eliminado!!!", Toast.LENGTH_SHORT).show();

                        }
                    }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setCancelable (true);
                    builder.show();
                    FIN */ //CODIGO ELIMINAR ALERTDIALOG
                }
                else
                {
                    //Toast.makeText(mContext, "#" + position + " - " + position, Toast.LENGTH_SHORT).show();
                    //ahora vamos con la posicion obtenida, vamos a bsucar esa posicion en nuestro array de datos y vamos a estar la infomación que tenemos.
                    //entonces definimos otra variable instanciada con mi modelo RegistroCasa
                    //RegistroCasa feedItem = lstregCasaCasa.get(position);
                    //Toast.makeText(mContext, "" + feedItem.getCalle().toString(), Toast.LENGTH_SHORT).show();
                    //ahora abrimos la nueva actividad con un intent
                    Intent i = new Intent(mContext, RegCasaMante.class);
                    //ahora le pasamos a la nueva actividad el codigo ID del registro para luego buscar los datos
                    i.putExtra("codigoIdReg", feedItem.getIdreg().toString());
                    //i.putExtra("nombreCancion", feedItem.getNombreCancion().toString());

                    //a veces sale errro porque tratamos de abrir una actividad desde una clase java y no desde otra actividad.
                    //en ese caso debemos setear un flag a nuestro intent para que pueda abrir otra actividad
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //ahora si lanzamos la nueva actividad
                    mContext.startActivity(i);
                }

            }
        });



        /* comentado por pruebas para onclik del recybclerview
        //a los elementos de nuestro viewHolder le haremos el onclicklistener para determinar donde a precionado el usuario
        //primero definimos el listener a utilizar:
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Primero obtengo la posicionm del holder que es mi conjunto de datos cargado que es lo que se muestra en pantalla.
                int position = holder.getPosition();
                //ahora vamos con la posicion obtenida, vamos a bsucar esa posicion en nuestro array de datos y vamos a estar la infomación que tenemos.
                //entonces definimos otra variable instanciada con mi modelo RegistroCasa
                RegistroCasa feedItem = lstregCasaCasa.get(position);
                //Toast.makeText(mContext, "" + feedItem.getCalle().toString(), Toast.LENGTH_SHORT).show();
                //ahora abrimos la nueva actividad con un intent
                Intent i = new Intent(mContext, RegCasaMante.class);
                //ahora le pasamos a la nueva actividad el codigo ID del registro para luego buscar los datos
                i.putExtra("codigoIdReg", feedItem.getIdreg().toString());
                //i.putExtra("nombreCancion", feedItem.getNombreCancion().toString());

                //a veces sale errro porque tratamos de abrir una actividad desde una clase java y no desde otra actividad.
                //en ese caso debemos setear un flag a nuestro intent para que pueda abrir otra actividad
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //ahora si lanzamos la nueva actividad
                mContext.startActivity(i);

            }
        };

        //definimos aqui los setOnclickListener para todos los objetos que se llenaran en nuestro recyclerview.
        //le pasamos como parametro el escuchador que hemos creado, en este caso es clickLiestenner
        holder.calle.setOnClickListener(clickListener);
        holder.ncasa.setOnClickListener(clickListener);
        holder.territorio.setOnClickListener(clickListener);
        holder.cfecha.setOnClickListener(clickListener);
        holder.amocasa.setOnClickListener(clickListener);
        */
        //12. esto es todo. de aqui ya solo se tiene que invocar al adapter en la funcion principal
    }

    @Override
    public int getItemCount() {
        //11. aqui le mandamos el tamaño del listado de datos que nos enviaron
        return lstregCasaCasa.size();
    }

    //2.creamos la clase recicler vieholder y la extendemos de RecyclerView.ViewHolder
    //implmentamos los metodos con alt + enter
    public class registroCasaviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        //3. definimos nuestros objetos
        TextView calle;
        TextView ncasa;
        TextView territorio;
        TextView cfecha;
        TextView amocasa;
        //C. creamos una varuiable clickListener instanciada de nuestra interface
        private ItemClickListener clickListener;

        public registroCasaviewHolder(View itemView) {
            super(itemView);

            //4. hacemos el match con nuestros botones del layout
            //si no reconoce la R hay que importantlo con alt + enter
            calle = (TextView) itemView.findViewById(R.id.txtcalle);
            ncasa = (TextView) itemView.findViewById(R.id.txtncasa);
            territorio = (TextView) itemView.findViewById(R.id.txtterritorio);
            cfecha = (TextView) itemView.findViewById(R.id.txtfecha);
            amocasa = (TextView) itemView.findViewById(R.id.txtamocasa);

            //A. para hacer onclicklistener en el recyclerview vamos a generar aqui las funciones setOnClickListener
            // para estro utilizamos el itenView que tenemos como parametro
            //le vamos a definir tanto el click corto como el largo e implementamos los metodos
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);


        }


        @Override
        public void onClick(View v) {
            //E: colocamos aqui nuesra variable de nuestra interface y llamamos al metodo que esta dentro de la interface
            clickListener.onClick(v, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            //F: colocamos aqui nuesra variable de nuestra interface y llamamos al metodo que esta dentro de la interface
            clickListener.onClick(v, getPosition(), true);
            return true;
        }

        //B. creamos aqui nuestro metdo setClickListener el cual tendra como parametro una variable instanciada de nuestra interface creada
        public void setClickListener (ItemClickListener itemClickListener)
        {
            //D: a nuestra variable clickListener instanciada de nuestro interface le pasamos el valor del parametro de este metodo
            this.clickListener = itemClickListener;
        }
    }


}
