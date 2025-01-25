package com.example.tiendaonline.SOPORTE;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tiendaonline.R;

import java.util.List;

public class CarritoListAdapter extends ArrayAdapter<Producto> {

    private Context mContext;
    private int mResource;
    private CBaseDatos baseDatos;
    public CarritoListAdapter(@NonNull Context context, int resource, @NonNull List<Producto> objects, CBaseDatos baseDatos) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        this.baseDatos = baseDatos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            listItemView = inflater.inflate(mResource, parent, false);
        }

        // Obtener referencias a las vistas
        ImageView imageViewProducto = listItemView.findViewById(R.id.item_image);
        TextView textViewNombreProducto = listItemView.findViewById(R.id.item_name);
        TextView textViewPrecioProducto = listItemView.findViewById(R.id.item_price);
        TextView textViewTallaProducto = listItemView.findViewById(R.id.item_size);
        Button btnEliminar = listItemView.findViewById(R.id.btn_eliminar);
        // Obtener el producto en la posición actual
        Producto producto = getItem(position);

        if (producto != null) {
            textViewNombreProducto.setText(producto.getNombreProducto());
            textViewPrecioProducto.setText(String.valueOf(producto.getPrecioProducto()));
            textViewTallaProducto.setText(producto.getTalla());

            int idImagen = mContext.getResources().getIdentifier(producto.getNombreImagen(), "drawable", mContext.getPackageName());

            // Verificar si el ID del recurso es válido
            if (idImagen != 0) {
                // Cargar la imagen del producto en el ImageView
                imageViewProducto.setImageResource(idImagen);
            } else {
                // Si el ID del recurso no es válido, puedes mostrar una imagen de error o dejar el ImageView vacío
                imageViewProducto.setImageResource(R.drawable.default_image); // Por ejemplo, carga una imagen predeterminada
            }
            // Agregar OnClickListener al botón de eliminación
            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SQLiteDatabase db = baseDatos.getWritableDatabase();
                    db.delete("Carrito", "codProducto = ?", new String[]{String.valueOf(producto.getCodProducto())});
                    db.close();

                    remove(producto); // Eliminar el producto de la lista
                    notifyDataSetChanged();
                }
            });

        }

        return listItemView;
    }
    
}