package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.VH> {

    public interface Listener {
        void onEdit(int position, Product product);
        void onDelete(int position);
    }

    private final List<Product> items;
    private final Listener listener;

    public ProductAdapter(List<Product> items, Listener listener) {
        this.items = items;
        this.listener = listener;
    }

       @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Product p = items.get(position);
        holder.tvName.setText(p.getName() != null ? p.getName() : "");
        holder.tvPrice.setText(String.format("%.2f", p.getPrice()));
        holder.btnEdit.setOnClickListener(v -> listener.onEdit(holder.getAdapterPosition(), p));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(holder.getAdapterPosition()));
    }


     @Override
    public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
        ImageButton btnEdit, btnDelete;
        VH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}