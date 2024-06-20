package com.example.quicklinkchat;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CallHistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ChatAdapter adapter;
    private List<ChatItem> chatItemHistoryList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_history);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(CallHistoryActivity.this,R.color.black));
        }
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        chatItemHistoryList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            String message;
            int imageResource;
            if (random.nextBoolean()) {
                message = "Incoming call";
                imageResource = R.drawable.incoming_logo; // Set incoming call logo
            } else {
                message = "Outgoing call";
                imageResource = R.drawable.outgoing_logo; // Set outgoing call logo
            }
            chatItemHistoryList.add(new ChatItem(imageResource, "User" + (i + 1), message));
        }

        adapter = new ChatAdapter(chatItemHistoryList, this);
        recyclerView.setAdapter(adapter);

        // Set item click listener for the adapter
        adapter.setOnItemClickListener(new ChatAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Handle item click here
                ChatItem chatItem = chatItemHistoryList.get(position);
                // Example: Show a toast with the message type
                String message = chatItem.getMessage();
                Toast.makeText(CallHistoryActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}