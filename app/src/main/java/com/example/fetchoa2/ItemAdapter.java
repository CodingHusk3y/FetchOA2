package com.example.fetchoa2;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private final List<Item> items;

    // Constructor to initialize the list of items
    public ItemAdapter(List<Item> items) {
        this.items = items;
    }

    // ViewHolder class to hold item views
    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView idTextView;
        TextView listIdTextView;
        TextView nameTextView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.item_id);
            listIdTextView = itemView.findViewById(R.id.item_listId);
            nameTextView = itemView.findViewById(R.id.item_name);
        }

        @SuppressLint("SetTextI18n")
        public void bind(Item item) {
            idTextView.setText("ID: " + item.getId());
            listIdTextView.setText("List ID: " + item.getListId());
            nameTextView.setText(item.getName());
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
