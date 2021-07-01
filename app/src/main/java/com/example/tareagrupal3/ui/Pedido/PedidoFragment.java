package com.example.tareagrupal3.ui.Pedido;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tareagrupal3.adapter.Recy_adapter_pedido;
import com.example.tareagrupal3.databinding.FragmentPedidoBinding;
import com.example.tareagrupal3.db.DBHelper;
import com.example.tareagrupal3.modelo.Pedido;
import com.example.tareagrupal3.R;
import com.example.tareagrupal3.adapter.Recy_adapter_pedido;
import com.example.tareagrupal3.ui.Pedido.ActivityRegEdiPedido;

import java.util.ArrayList;

public class PedidoFragment extends Fragment {

    private PedidoViewModel pedidoViewModel;
    private FragmentPedidoBinding binding;

    private EditText et_buscar;
    private RecyclerView rv_pedidos;
    private DBHelper dbHelper;
    private ArrayList<Pedido> datos;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        pedidoViewModel =
                new ViewModelProvider(this).get(PedidoViewModel.class);

        binding = FragmentPedidoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dbHelper = new DBHelper(getActivity().getBaseContext());
        et_buscar = root.findViewById(R.id.et_buscar);
        rv_pedidos = root.findViewById(R.id.rv_pedidos);
        rv_pedidos.setLayoutManager(new LinearLayoutManager(getContext()));

        root.findViewById(R.id.btn_registrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityRegEdiPedido.class);
                startActivityForResult(intent, 1);
            }
        });

        Listar_Pedidos();

        et_buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals("")){
                    ArrayList<Pedido> pedidos = new ArrayList<>();
                    Pedido pedido = dbHelper.Traer_Pedido(Integer.parseInt(charSequence.toString()));
                    if (pedido != null) pedidos.add(pedido);
                    else Toast.makeText(getActivity(), "Pedido no existe", Toast.LENGTH_SHORT).show();
                    Recy_adapter_pedido adaptador = new Recy_adapter_pedido(getActivity(), pedidos);
                    rv_pedidos.setAdapter(adaptador);
                } else {
                    Listar_Pedidos();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        registerForContextMenu(rv_pedidos);

        return root;
    }

    public void Listar_Pedidos(){
        datos = dbHelper.Traer_Pedidos();
        Recy_adapter_pedido adapter = new Recy_adapter_pedido(getContext(), datos);
        rv_pedidos.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Pedido pedido = (Pedido) data.getSerializableExtra("pedido");
            switch (requestCode) {
                case 1:
                    dbHelper.Insertar_Pedido(pedido);
                    break;
                case 2:
                    dbHelper.Actualizar_Pedido(pedido);
            }
            Listar_Pedidos();
            Toast.makeText(getActivity(), "Lista actualizado", Toast.LENGTH_SHORT).show();
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(getActivity(), "Cancelado", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        int position = -1;
        try {
            position = ((Recy_adapter_pedido)rv_pedidos.getAdapter()).getPosition();
        } catch (Exception e) {
            Log.d("TAG", e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }
        Pedido ped = datos.get(position);
        switch (item.getItemId()) {
            case R.id.eliminar:
                new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("¿Estás seguro?")
                        .setMessage("¿Quieres eliminar este registro?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbHelper.Eliminar_Pedido(ped);
                                Listar_Pedidos();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            case R.id.editar:
                Intent intent = new Intent(getActivity(), ActivityRegEdiPedido.class);
                intent.putExtra("pedido", ped);
                startActivityForResult(intent, 2);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}