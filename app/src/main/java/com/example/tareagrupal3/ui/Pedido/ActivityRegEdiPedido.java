package com.example.tareagrupal3.ui.Pedido;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tareagrupal3.CuadroRegEdiDetalle;
import com.example.tareagrupal3.R;
import com.example.tareagrupal3.adapter.Lista_adapter_detalle;
import com.example.tareagrupal3.adapter.Lista_adapter_direccion;
import com.example.tareagrupal3.adapter.Recy_adapter_pedido;
import com.example.tareagrupal3.db.DBHelper;
import com.example.tareagrupal3.modelo.Detalle;
import com.example.tareagrupal3.modelo.Pedido;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ActivityRegEdiPedido extends AppCompatActivity implements CuadroRegEdiDetalle.FinalizoCuadroDetalle {

    private EditText et_cliente, et_fecha, et_direccion;
    private Pedido pedido;
    private Detalle detalle;
    private DBHelper dbHelper;
    private int dia, mes, anio;
    private boolean registro;
    private Button btn_agregar;
    private ListView lv_detalles;
    private ArrayList<Detalle> detalles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_edi_pedido);

        dbHelper = new DBHelper(getApplicationContext());

        et_cliente = findViewById(R.id.et_cliente);
        et_fecha = findViewById(R.id.et_fecha);
        et_direccion = findViewById(R.id.et_direccion);
        btn_agregar = findViewById(R.id.btn_agregar);
        lv_detalles = findViewById(R.id.lv_detalles);

        pedido = (Pedido) getIntent().getSerializableExtra("pedido");
        if (pedido!=null){
            et_cliente.setText(pedido.getIdCliente()+"");
            et_fecha.setText(pedido.getFecha_envio());
            et_direccion.setText(pedido.getIdDireccion()+"");
            btn_agregar.setVisibility(View.VISIBLE);
            lv_detalles.setVisibility(View.VISIBLE);
        }

        findViewById(R.id.btn_fecha).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c= Calendar.getInstance();
                dia= c.get(Calendar.DAY_OF_MONTH);
                mes = c.get(Calendar.MONTH);
                anio = c.get(Calendar.YEAR);

                final Calendar calendar = Calendar.getInstance();
                calendar.set(anio, mes, dia);
                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(y,m, d);
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        String strDate = format.format(calendar.getTime());
                        et_fecha.setText(strDate);
                    }
                } ,anio,mes,dia);
                datePickerDialog.show();
            }
        });

        findViewById(R.id.btn_aceptar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                int idCliente = Integer.parseInt(et_cliente.getText().toString());
                int idDireccion = Integer.parseInt(et_direccion.getText().toString());
                if (pedido == null) pedido = new Pedido();
                if (dbHelper.Traer_Cliente(idCliente) == null){
                    Toast.makeText(getApplicationContext(), "No existe un cliente con ese código", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (dbHelper.Traer_Direccion(idDireccion) == null){
                    Toast.makeText(getApplicationContext(), "No existe una dirección con ese código", Toast.LENGTH_SHORT).show();
                    return;
                }
                pedido.setIdCliente(idCliente);
                pedido.setFecha_envio(et_fecha.getText().toString());
                pedido.setIdDireccion(idDireccion);
                data.putExtra("pedido", pedido);
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

        findViewById(R.id.btn_agregar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CuadroRegEdiDetalle(ActivityRegEdiPedido.this,ActivityRegEdiPedido.this, pedido.getIdPedido(), null);
                registro = true;
            }
        });

        registerForContextMenu(lv_detalles);
        if (pedido != null) Listar_Detalles();
    }

    public void Listar_Detalles(){
        detalles = dbHelper.Traer_Detalles(pedido.getIdPedido());
        Lista_adapter_detalle adapter = new Lista_adapter_detalle(this, detalles);
        lv_detalles.setAdapter(adapter);
    }

    @Override
    public void ResultadoCuadroDetalle(int idArticulo, int cantidad) {
        if (registro) {
            Detalle detalle = new Detalle(idArticulo, pedido.getIdPedido(), cantidad);
            dbHelper.Insertar_Detalle(detalle);
        } else {
            detalle.setIdArticulo(idArticulo);
            detalle.setCantidad(cantidad);
            dbHelper.Actualizar_Detalle(detalle);
        }
        Listar_Detalles();
        detalle = null;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        detalle = (Detalle) lv_detalles.getAdapter().getItem(info.position);
        switch (item.getItemId()) {
            case R.id.eliminar:
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("¿Estás seguro?")
                        .setMessage("¿Quieres eliminar este registro?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbHelper.Eliminar_Detalle(detalle);
                                dbHelper.Actualizar_Stock(detalle.getIdArticulo(),detalle.getCantidad(),0);
                                Listar_Detalles();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            case R.id.editar:
                new CuadroRegEdiDetalle(ActivityRegEdiPedido.this,ActivityRegEdiPedido.this, pedido.getIdPedido(), detalle);
                registro = false;
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}