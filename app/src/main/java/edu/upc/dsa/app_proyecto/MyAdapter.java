package edu.upc.dsa.app_proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
/*
public class MyAdapter extends  RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Objetos> objetosList;

    public MyAdapter(List<Objetos> myDataset)
    {
        objetosList = myDataset;
    }

    //PAra coger el valor de la posicion
    private OnItemClickListener mListener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener ;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNombre;
        public TextView txtCoste;
        public ImageView imageDelete;
        public View layout;
        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            //primera linea de las row, segunda y boton
            txtNombre = itemView.findViewById(R.id.firstLine);
            txtCoste = itemView.findViewById(R.id.secondLine);
            imageDelete = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }

                }
            });



        }
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from( parent.getContext());
        //false es que no es el raiz
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, padding and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        final ViewHolder myHolder = holder;

        holder.txtNombre.setText(objetosList.get(position).getNombre());
        holder.txtCoste.setText(String.valueOf(objetosList.get(position).getCoste()));
    }

    @Override
    public int getItemCount() {
        return objetosList.size();
    }
}*/