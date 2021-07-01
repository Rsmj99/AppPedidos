package com.example.tareagrupal3;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tareagrupal3.db.DBHelper;
import com.example.tareagrupal3.modelo.Articulo;
import com.example.tareagrupal3.modelo.Detalle;

import java.util.ArrayList;

public class CuadroRegEdiDetalle {

    final Dialog dialogo;
    private Spinner sp_articulo;
    private EditText et_cantidad;
    private DBHelper dbHelper;

    public interface FinalizoCuadroDetalle{
        void ResultadoCuadroDetalle(int idArticulo, int cantidad);
    }
    private FinalizoCuadroDetalle interfaz;

    public CuadroRegEdiDetalle(Context contexto, FinalizoCuadroDetalle actividad,int idPedido, Detalle detalle) {
        interfaz = actividad;

        dialogo = new Dialog(contexto);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.cuadro_regedidetalle);
        dbHelper = new DBHelper(contexto);

        et_cantidad = dialogo.findViewById(R.id.et_cantidad);
        sp_articulo = dialogo.findViewById(R.id.sp_articulo);
        ArrayList<Articulo> articulos = new DBHelper(contexto).Traer_Articulos();
        ArrayAdapter<Articulo> adapter = new ArrayAdapter<Articulo>(contexto, android.R.layout.simple_spinner_item, articulos);
        sp_articulo.setAdapter(adapter);

        if (detalle != null){
            et_cantidad.setText(detalle.getCantidad()+"");
            sp_articulo.setSelection(detalle.getIdArticulo()-1);
        }

        dialogo.findViewById(R.id.btn_aceptar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int articuloActual = sp_articulo.getSelectedItemPosition()+1;
                int cantidadActual = Integer.parseInt(et_cantidad.getText().toString());
                if (detalle!=null){
                    int articuloAntes = detalle.getIdArticulo();
                    Detalle det = dbHelper.Traer_Detalle(articuloActual, idPedido);
                    if (det != null){
                        if (!((det.getIdArticulo() == detalle.getIdArticulo()) && (det.getIdPedido() == detalle.getIdPedido()) && (det.getCantidad() == detalle.getCantidad()))){
                            Toast.makeText(contexto, "Ya existe tal detalle de pedido con ese artículo", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    if (articuloAntes == articuloActual){
                        if (!dbHelper.Actualizar_Stock(articuloAntes, detalle.getCantidad(), cantidadActual)){
                            Toast.makeText(contexto, "No hay esa cantidad de artículos en stock", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } else {
                        if (!dbHelper.Actualizar_Stock(articuloActual, 0, cantidadActual)){
                            Toast.makeText(contexto, "No hay esa cantidad de artículos en stock", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        dbHelper.Actualizar_Stock(articuloAntes, detalle.getCantidad(), 0);
                    }

                } else {
                    if (dbHelper.Traer_Detalle(articuloActual, idPedido) != null){
                        Toast.makeText(contexto, "Ya existe tal detalle de pedido con ese artículo", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!dbHelper.Actualizar_Stock(articuloActual, 0, cantidadActual)){
                        Toast.makeText(contexto, "No hay esa cantidad de artículos en stock", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                interfaz.ResultadoCuadroDetalle(articuloActual, cantidadActual);
                dialogo.dismiss();
            }
        });
        dialogo.findViewById(R.id.btn_cancelar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
            }
        });

        dialogo.show();
    }
}
