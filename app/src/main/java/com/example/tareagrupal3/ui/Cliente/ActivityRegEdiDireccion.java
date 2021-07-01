package com.example.tareagrupal3.ui.Cliente;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tareagrupal3.R;
import com.example.tareagrupal3.db.DBHelper;
import com.example.tareagrupal3.modelo.Cliente;
import com.example.tareagrupal3.modelo.Direccion;
import com.example.tareagrupal3.R;
import com.example.tareagrupal3.db.DBHelper;
import com.example.tareagrupal3.modelo.Direccion;

public class ActivityRegEdiDireccion extends AppCompatActivity {

    EditText et_numero, et_calle, et_comuna, et_ciudad;
    Direccion direccion;
    private DBHelper dbHelper;
    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_edi_direccion);

        dbHelper = new DBHelper(getApplicationContext());

        et_numero = findViewById(R.id.et_numero);
        et_calle = findViewById(R.id.et_calle);
        et_comuna = findViewById(R.id.et_comuna);
        et_ciudad = findViewById(R.id.et_ciudad);

        cliente = dbHelper.Traer_Clientes().get(0);
        direccion = (Direccion) getIntent().getSerializableExtra("direccion");
        if (direccion!=null){
            et_numero.setText(direccion.getNumero());
            et_calle.setText(direccion.getCalle());
            et_comuna.setText(direccion.getComuna());
            et_ciudad.setText(direccion.getCiudad());
        }

        findViewById(R.id.btn_aceptar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                if (direccion == null) direccion = new Direccion();
                direccion.setNumero(et_numero.getText().toString());
                direccion.setCalle(et_calle.getText().toString());
                direccion.setComuna(et_comuna.getText().toString());
                direccion.setCiudad(et_ciudad.getText().toString());
                direccion.setIdCliente(cliente.getIdCliente());
                data.putExtra("direccion", direccion);
                setResult(RESULT_OK, data);
                finish();
            }
        });

        findViewById(R.id.btn_cancelar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}