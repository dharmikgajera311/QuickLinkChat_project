package com.example.quicklinkchat;

import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StatusActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ChatAdapter adapter;
    private List<ChatItem> chatItemStatusList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(StatusActivity.this,R.color.black));
        }
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        chatItemStatusList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            int minutes = random.nextInt(60) + 1; // Random minutes between 1 and 60 (inclusive)
            String message = minutes + " minutes ago";
            chatItemStatusList.add(new ChatItem(R.drawable.refresh, "User" + (i + 1), message));
        }

        adapter = new ChatAdapter(chatItemStatusList, this);
        recyclerView.setAdapter(adapter);
    }
}
