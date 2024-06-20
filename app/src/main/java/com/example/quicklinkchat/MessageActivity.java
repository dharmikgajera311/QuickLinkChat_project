package com.example.quicklinkchat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quicklinkchat.ChatGroup.ChatGroupAdapter;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String PREF_PREFIX_KEY_MESSAGES = "Messages_";

    String name;
    long number;
    TextView nameTitle;
    EditText msg;
    ImageView sendMessage;
    ImageView optionofmenu;
    ImageView callogo;
    ImageView videologo;
    RecyclerView recyclerView;
    List<String> messages;
    ChatGroupAdapter adapter;
    SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(MessageActivity.this,R.color.black));
        }
        nameTitle = findViewById(R.id.nameTitle);
        msg = findViewById(R.id.msg);
        sendMessage = findViewById(R.id.sendMessage);
        recyclerView = findViewById(R.id.chatGroup);
        optionofmenu = findViewById(R.id.menuid);
        callogo = findViewById(R.id.callicon);
        videologo = findViewById(R.id.videoicon);

        Intent intent = getIntent();
        name = intent.getStringExtra("USERNAME");
        number = intent.getLongExtra("NUMBER", 0);
        nameTitle.setText(name);

        messages = new ArrayList<>();
        adapter = new ChatGroupAdapter(messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        sharedPreferences = getSharedPreferences(PREF_PREFIX_KEY_MESSAGES + name, MODE_PRIVATE);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = msg.getText().toString();
                if (!messageText.isEmpty()) {
                    messages.add(messageText);
                    adapter.notifyItemInserted(messages.size() - 1);
                    recyclerView.smoothScrollToPosition(messages.size() - 1);
                    msg.setText(""); // Clear the EditText after sending the message
                    saveMessagesToPrefs();
                } else {
                    Toast.makeText(MessageActivity.this, "Please enter a message", Toast.LENGTH_SHORT).show();
                }
            }
        });

        optionofmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                // Permission not granted, request it
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_CODE);
            } else {
                // Permission already granted, send SMS
                sendSMS(number);
            }
        } else {
            // For devices below Android 6.0, send SMS
            sendSMS(number);
        }

        // Load messages from SharedPreferences
        loadMessagesFromPrefs();
    }

    private void sendSMS(long number) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(String.valueOf(number), null, "Hello, br" +
                    "other", null, null);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void clearSavedMessagesFromPrefs(String name) {
        sharedPreferences.edit().remove(PREF_PREFIX_KEY_MESSAGES + name).apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearSavedMessagesFromPrefs(name);
    }

    private void saveMessagesToPrefs() {
        StringBuilder messageStringBuilder = new StringBuilder();
        for (String message : messages) {
            messageStringBuilder.append(message).append(",");
        }
        String messageString = messageStringBuilder.toString();
        sharedPreferences.edit().putString(PREF_PREFIX_KEY_MESSAGES + name, messageString).apply();
    }

    private void loadMessagesFromPrefs() {
        String messageString = sharedPreferences.getString(PREF_PREFIX_KEY_MESSAGES + name, "");
        if (!messageString.isEmpty()) {
            String[] messageArray = messageString.split(",");
            for (String message : messageArray) {
                messages.add(message);
            }
            adapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(messages.size() - 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, send SMS
                sendSMS(number);
            } else {
                Toast.makeText(this, "SMS permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(this, optionofmenu);
        popupMenu.inflate(R.menu.main_menu); // Assuming you have a menu file named main_menu.xml
        videologo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MessageActivity.this, "Video Calling...", Toast.LENGTH_SHORT).show();
            }
        });
        callogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MessageActivity.this, "Voice Calling...", Toast.LENGTH_SHORT).show();
            }
        });
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.new_group) {
                    Toast.makeText(MessageActivity.this, "New group clicked", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.new_broadcast) {
                    Toast.makeText(MessageActivity.this, "New broadcast clicked", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.linked_devices) {
                    Toast.makeText(MessageActivity.this, "Linked devices clicked", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.starred_messages) {
                    Toast.makeText(MessageActivity.this, "Starred messages clicked", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.payments) {
                    Toast.makeText(MessageActivity.this, "Payments clicked", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.settings) {
                    Toast.makeText(MessageActivity.this, "Settings clicked", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }
}
