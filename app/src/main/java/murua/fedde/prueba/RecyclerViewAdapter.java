package murua.fedde.prueba;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import murua.fedde.R;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private ItemData[] itemsData;

    public RecyclerViewAdapter(ItemData[] itemsData) {
        this.itemsData = itemsData;
    }

    // Crea nueva vistas (invocado por el layout manager)
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // crea nueva vista
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,
                                                                                        null);
        // crea ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Reemplaza contenido de la vista (invocado por el layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        // obtiene el dato del itemData en esa posicion
        viewHolder.txtViewTitle.setText(itemsData[position].getTitle());
        // rememplaza el contenido de la view por el contenido de itemData
        viewHolder.imgViewIcon.setImageResource(itemsData[position].getImageUrl());

    }

    // clase para mantener la referencia de cada intem con el recyclerview
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtViewTitle;
        public ImageView imgViewIcon;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.item_marca);
            imgViewIcon = (ImageView) itemLayoutView.findViewById(R.id.item_icon);
        }
    }

    // numero total de filas
    @Override
    public int getItemCount() {
        return itemsData.length;
    }

}
