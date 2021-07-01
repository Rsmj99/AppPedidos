package com.example.tareagrupal3.ui.Cliente;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tareagrupal3.R;
import com.example.tareagrupal3.adapter.Lista_adapter_direccion;
import com.example.tareagrupal3.databinding.FragmentClienteBinding;
import com.example.tareagrupal3.db.DBHelper;
import com.example.tareagrupal3.modelo.Cliente;
import com.example.tareagrupal3.modelo.Direccion;

import java.util.ArrayList;

public class ClienteFragment extends Fragment {

    private ClienteViewModel clienteViewModel;
    private FragmentClienteBinding binding;

    private ListView lv_direcciones;
    private ArrayList<Direccion> datos;
    private DBHelper dbHelper;
    private Cliente cliente;
    private EditText et_nombre;
    private Button btn_registrar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        clienteViewModel =
                new ViewModelProvider(this).get(ClienteViewModel.class);

        binding = FragmentClienteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dbHelper = new DBHelper(getActivity().getBaseContext());
        lv_direcciones = root.findViewById(R.id.lv_direcciones);
        et_nombre = root.findViewById(R.id.et_nombre);
        btn_registrar = root.findViewById(R.id.btn_registrar);
        cliente = dbHelper.Traer_Clientes().get(0);
        et_nombre.setText(cliente.getNombre());

        root.findViewById(R.id.btn_actualizar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cliente.setNombre(et_nombre.getText().toString());
                dbHelper.Actualizar_Cliente(cliente);
                Toast.makeText(getActivity(), "Cliente Actualizado", Toast.LENGTH_SHORT).show();
            }
        });

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dbHelper.Traer_Direcciones().size()==3) {
                    Toast.makeText(getActivity(), "Solo se pueden registrar hasta 3 direcciones", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(getActivity(), ActivityRegEdiDireccion.class);
                startActivityForResult(intent, 1);
            }
        });

        Listar_Direcciones();

        registerForContextMenu(lv_direcciones);

        return root;
    }

    public void Listar_Direcciones(){
        datos = dbHelper.Traer_Direcciones();
        Lista_adapter_direccion adapter = new Lista_adapter_direccion(getContext(), datos);
        lv_direcciones.setAdapter(adapter);
    }

    public void Listar_Direcciones(ArrayList<Direccion> datos, Context context, ListView lv_direcciones){
        Lista_adapter_direccion adapter = new Lista_adapter_direccion(context, datos);
        lv_direcciones.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Direccion direccion = (Direccion) data.getSerializableExtra("direccion");
            switch (requestCode) {
                case 1:
                    dbHelper.Insertar_Direccion(direccion);
                    break;
                case 2:
                    dbHelper.Actualizar_Direccion(direccion);
            }
            Listar_Direcciones();
            Toast.makeText(getActivity(), "Lista actualizada", Toast.LENGTH_SHORT).show();
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(getActivity(), "Cancelado", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}