package com.fam.rodrigo.regcasaencasa;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//para activitis que se mostran como dialogos, hay que cambir solo el tema en el manifiesto a un tema de dialogo y OJO
//extender la actividad de Activity nada mas
public class DialogEliminar extends Activity implements View.OnClickListener {
    private Button bteliminar, btcancelar;
    String codigoreg;
    DataBaseManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_eliminar);
        setTitle("Eliminar Registro");
        //con esta linea de codigo evitamos que cuando se de click en un espacio fuera del dialogo activity, no se cierre el cuadro
        this.setFinishOnTouchOutside(false);

        inicializa();
        if (savedInstanceState == null) {
            //usamos el Bundle para recibir el parametro enviado
            //entonces definimos una variable tipo Bundle y con getIntent().getExtras() obtenemos el valor enviado por la actividad antetrior
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                codigoreg= "";
            } else {
                //le pasamos el valor del extra a nuestra varible con la cual trabajaremos
                codigoreg= extras.getString("codigoIdReg");
                //EliminaRegistro(codigoreg);
            }
        } else {
            codigoreg= (String) savedInstanceState.getSerializable("codigoIdReg");
        }
    }

    private void EliminaRegistro(String codigoreg) {
        //conectamos a la BD
        manager = new DataBaseManager(this);
        manager.eliminar(codigoreg);

        //cerramos BD
        manager.cerrar_BD();
    }

    private void inicializa() {
        btcancelar = (Button) findViewById(R.id.btncancelar);
        bteliminar = (Button) findViewById(R.id.btneliminar);
        bteliminar.setOnClickListener(this);
        btcancelar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case (R.id.btncancelar):
                finish();
                break;
            case (R.id.btneliminar):
                EliminaRegistro(codigoreg);
                Toast.makeText(DialogEliminar.this, "Registro de casa eliminado!!!", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }
}
