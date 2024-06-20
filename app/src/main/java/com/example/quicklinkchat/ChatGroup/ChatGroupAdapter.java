package com.example.quicklinkchat.ChatGroup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quicklinkchat.R;

import java.util.List;

public class ChatGroupAdapter extends RecyclerView.Adapter<ChatGroupAdapter.ChatGroupViewHolder> {

    private static List<String> messages;


    public  ChatGroupAdapter(List<String> message){
        messages = message;
    }
    @NonNull
    @Override
    public ChatGroupAdapter.ChatGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_chat,parent,false);
        return new ChatGroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatGroupAdapter.ChatGroupViewHolder holder, int position) {
        String massage = messages.get(position);
        holder.singleMassage.setText(massage);

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
    public static class ChatGroupViewHolder extends RecyclerView.ViewHolder {
        public TextView singleMassage;
        public View itemView;

        public ChatGroupViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            singleMassage = itemView.findViewById(R.id.singleChat);

        }
    }
}

