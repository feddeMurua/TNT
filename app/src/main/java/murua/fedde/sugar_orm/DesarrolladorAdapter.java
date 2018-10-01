package murua.fedde.sugar_orm;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import murua.fedde.R;

public class DesarrolladorAdapter extends RecyclerView.Adapter<DesarrolladorAdapter.BeanHolder> {

    private List<Desarrollador> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnDevItemClick OnDevItemClick;

    public DesarrolladorAdapter(List<Desarrollador> list,Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        this.OnDevItemClick = (OnDevItemClick) context;
    }


    @Override
    public BeanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_dia_list,parent,false);
        return new BeanHolder(view);
    }

    @Override
    public void onBindViewHolder(BeanHolder holder, int position) {
        Log.e("bind", "onBindViewHolder: "+ list.get(position));
        holder.textViewTitle.setText(list.get(position).getApellido() + ", " + list.get(position).getNombre());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BeanHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewContent;
        TextView textViewTitle;
        public BeanHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textViewContent = itemView.findViewById(R.id.item_text);
            textViewTitle = itemView.findViewById(R.id.tv_title);
        }

        @Override
        public void onClick(View view) {
            OnDevItemClick.OnDevItemClick(getAdapterPosition());
        }
    }

    public interface OnDevItemClick{
        void OnDevItemClick(int pos);
    }
}
