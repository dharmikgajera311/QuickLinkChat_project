package com.example.quicklinkchat;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private static List<ChatItem> chatItemList;
    private Context context;
    private OnItemClickListener mListener;

    public ChatAdapter(List<ChatItem> chatItemList, Context context) {
        this.chatItemList = chatItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_layout, parent, false);
        return new ChatViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatItem currentItem = chatItemList.get(position);
        holder.profileImageView.setImageResource(currentItem.getProfileImage());
        holder.usernameTextView.setText(currentItem.getUsername());
        holder.messageTextView.setText(currentItem.getMessage());

        // Set the background color to none (transparent)
        holder.itemView.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public int getItemCount() {
        return chatItemList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        public ImageView profileImageView;
        public TextView usernameTextView;
        public TextView messageTextView;
        public View itemView;

        public ChatViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            this.itemView = itemView;
            profileImageView = itemView.findViewById(R.id.profileImageView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            messageTextView = itemView.findViewById(R.id.messageTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    // Change background color smoothly to a lighter shade
                    ObjectAnimator backgroundColorAnimator = ObjectAnimator.ofObject(itemView,
                            "backgroundColor",
                            new ArgbEvaluator(),
                            Color.DKGRAY,
                            Color.LTGRAY);
                    backgroundColorAnimator.setDuration(250);
                    backgroundColorAnimator.start();

                    // Create a handler to reset background color after a delay
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Reset background color to its original color (transparent)
                            ObjectAnimator backgroundColorAnimator = ObjectAnimator.ofObject(itemView,
                                    "backgroundColor",
                                    new ArgbEvaluator(),
                                    Color.LTGRAY,
                                    Color.TRANSPARENT);
                            backgroundColorAnimator.setDuration(250);
                            backgroundColorAnimator.start();
                        }
                    }, 260); // Adjust the delay time as needed (250 milliseconds)

                    // Redirect to ChatActivity
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        ChatItem chatItem = chatItemList.get(position);
                        Intent intent = new Intent(v.getContext(), MessageActivity.class);
                        intent.putExtra("USERNAME", chatItem.getUsername());
                        intent.putExtra("NUMBER", chatItem.getUserNumber());
                        v.getContext().startActivity(intent);
                    }
                }
            });
        }
    }
}
