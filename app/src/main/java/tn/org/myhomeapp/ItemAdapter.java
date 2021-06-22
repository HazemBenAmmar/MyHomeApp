package tn.org.myhomeapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    private List<String> data;
    private ItemClick itemClick;

    public ItemAdapter(List<String> data) {
        this.data = data;
    }

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.terminal_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        String item = data.get(position);
        holder.bind(item);
        holder.itemView.setOnClickListener(v -> itemClick.click(item));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemId;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemId = itemView.findViewById(R.id.item_id);
        }

        public void bind(String item) {
            itemId.setText(item);
        }
    }

    interface ItemClick {
        public void click(String id);
    }
}
