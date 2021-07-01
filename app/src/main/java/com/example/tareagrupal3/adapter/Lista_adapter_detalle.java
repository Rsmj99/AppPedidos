package com.example.tareagrupal3.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.tareagrupal3.MainActivity;
import com.example.tareagrupal3.R;
import com.example.tareagrupal3.db.DBHelper;
import com.example.tareagrupal3.modelo.Articulo;
import com.example.tareagrupal3.modelo.Detalle;
import com.example.tareagrupal3.modelo.Direccion;
import com.example.tareagrupal3.ui.Cliente.ActivityRegEdiDireccion;
import com.example.tareagrupal3.ui.Cliente.ClienteFragment;

import java.util.ArrayList;

public class Lista_adapter_detalle extends ArrayAdapter {

    private Context context;
    private ArrayList<Detalle> detalles;
    private DBHelper dbHelper;

    public Lista_adapter_detalle(@NonNull Context context, ArrayList<Detalle> detalles){
        super(context, R.layout.detalle_item_list, detalles);
        this.context = context;
        this.detalles = detalles;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.detalle_item_list, null);

        dbHelper = new DBHelper(context);

        TextView tv_articulo = view.findViewById(R.id.tv_articulo);
        TextView tv_cantidad = view.findViewById(R.id.tv_cantidad);

        Articulo articulo = dbHelper.Traer_Articulo(detalles.get(position).getIdArticulo());
        tv_articulo.setText(articulo.getDescripcion());
        tv_cantidad.setText(detalles.get(position).getCantidad()+"");

        return view;
    }
}
