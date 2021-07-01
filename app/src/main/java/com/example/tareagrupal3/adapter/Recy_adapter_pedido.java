package com.example.tareagrupal3.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tareagrupal3.MainActivity;
import com.example.tareagrupal3.R;
import com.example.tareagrupal3.db.DBHelper;
import com.example.tareagrupal3.modelo.Cliente;
import com.example.tareagrupal3.modelo.Direccion;
import com.example.tareagrupal3.modelo.Pedido;
import com.example.tareagrupal3.ui.Pedido.PedidoFragment;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Recy_adapter_pedido extends RecyclerView.Adapter<Recy_adapter_pedido.MyViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<Pedido> datos;
    private DBHelper dbHelper;

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Recy_adapter_pedido(@NonNull Context context, ArrayList<Pedido> datos){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.datos = datos;
        dbHelper = new DBHelper(context);
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.pedido_item_list, null, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        holder.tv_codigo.setText(datos.get(position).getIdPedido() + "");
        Cliente cliente = dbHelper.Traer_Cliente(datos.get(position).getIdCliente());
        holder.tv_nombre.setText(cliente.getNombre());
        holder.tv_fecha.setText(datos.get(position).getFecha_envio());
        Direccion direccion = dbHelper.Traer_Direccion(datos.get(position).getIdDireccion());
        holder.tv_direccion.setText(direccion.getCalle() + " #" + direccion.getNumero());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getPosition());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener
    {
        public TextView tv_codigo, tv_nombre, tv_fecha, tv_direccion;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_codigo = itemView.findViewById(R.id.tv_codigo);
            tv_nombre = itemView.findViewById(R.id.tv_nombre);
            tv_fecha = itemView.findViewById(R.id.tv_fecha);
            tv_direccion = itemView.findViewById(R.id.tv_direccion);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        }
    }
}