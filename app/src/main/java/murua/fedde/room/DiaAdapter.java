package murua.fedde.room;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.List;

import murua.fedde.R;

public class DiaAdapter extends RecyclerView.Adapter<DiaAdapter.BeanHolder> {

    private List<Dia> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnDiaItemClick OnDiaItemClick;

    public DiaAdapter(List<Dia> list,Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        this.OnDiaItemClick = (OnDiaItemClick) context;
    }


    @Override
    public BeanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_dia_list,parent,false);
        return new BeanHolder(view);
    }

    @Override
    public void onBindViewHolder(BeanHolder holder, int position) {
        Log.e("bind", "onBindViewHolder: "+ list.get(position));
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");
        holder.textViewTitle.setText(targetFormat.format(list.get(position).getTitulo()) + ", " + list.get(position).getVasos().toString() + " vasos");
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
            OnDiaItemClick.OnDiaItemClick(getAdapterPosition());
        }
    }

    public interface OnDiaItemClick{
        void OnDiaItemClick(int pos);
    }
}
