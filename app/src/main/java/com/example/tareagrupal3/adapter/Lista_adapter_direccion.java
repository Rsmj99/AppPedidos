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
import com.example.tareagrupal3.db.DBHelper;
import com.example.tareagrupal3.modelo.Direccion;
import com.example.tareagrupal3.R;
import com.example.tareagrupal3.ui.Cliente.ClienteFragment;
import com.example.tareagrupal3.ui.Cliente.ActivityRegEdiDireccion;

import java.util.ArrayList;

public class Lista_adapter_direccion extends ArrayAdapter {
    private Context context;
    private ArrayList<Direccion> datos;
    private DBHelper dbHelper;

    public Lista_adapter_direccion(@NonNull Context context, ArrayList<Direccion> datos){
        super(context, R.layout.direccion_item_list, datos);
        this.context = context;
        this.datos = datos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.direccion_item_list, null);

        dbHelper = new DBHelper(context);

        TextView tv_codigo = view.findViewById(R.id.tv_codigo);
        TextView tv_numero = view.findViewById(R.id.tv_numero);
        TextView tv_calle = view.findViewById(R.id.tv_calle);
        TextView tv_comuna = view.findViewById(R.id.tv_comuna);
        TextView tv_ciudad = view.findViewById(R.id.tv_ciudad);

        tv_codigo.setText(datos.get(position).getIdDireccion() + "");
        tv_numero.setText(datos.get(position).getNumero());
        tv_calle.setText(datos.get(position).getCalle());
        tv_comuna.setText(datos.get(position).getComuna());
        tv_ciudad.setText(datos.get(position).getCiudad());

        view.findViewById(R.id.btn_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityRegEdiDireccion.class);
                Direccion direccion = dbHelper.Traer_Direccion(Integer.parseInt(tv_codigo.getText().toString()));
                intent.putExtra("direccion", direccion);
                ((MainActivity) context).startActivityForResult(intent, 2);
            }
        });

        view.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("¿Estás seguro?")
                        .setMessage("¿Quieres eliminar este registro?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Direccion direccion = dbHelper.Traer_Direccion(Integer.parseInt(tv_codigo.getText().toString()));
                                dbHelper.Eliminar_Direccion(direccion);
                                new ClienteFragment().Listar_Direcciones(dbHelper.Traer_Direcciones(),context,(ListView) parent);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        return view;
    }
}