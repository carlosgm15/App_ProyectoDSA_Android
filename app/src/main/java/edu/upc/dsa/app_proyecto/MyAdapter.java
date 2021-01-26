package edu.upc.dsa.app_proyecto;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends  RecyclerView.Adapter<MyAdapter.ViewHolder> {
    public MyAdapter(ArrayList<String> listDatos) {
        ListDatos = listDatos;
    }

    ArrayList<String> ListDatos;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.asignarDatos(ListDatos.get(position));
    }

    @Override
    public int getItemCount() {
        return ListDatos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dato;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dato= itemView.findViewById(R.id.idDato);
        }

        public void asignarDatos(String datos) {
            dato.setText(datos);
        }
    }
}
