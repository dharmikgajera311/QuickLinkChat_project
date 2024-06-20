package com.example.quicklinkchat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ChatAdapter adapter;
    private List<ChatItem> chatItemList;
    private List<ChatItem> originalChatItemList; // Store the original list
    private FloatingActionButton fabMessage, fabFeedback, fabStatus, fabCall;
    private boolean isMenuOpen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.black));
        }
        fabMessage = findViewById(R.id.fabMessage);
        fabFeedback = findViewById(R.id.fabFeedback);
        fabStatus = findViewById(R.id.fabStatus);
        fabCall = findViewById(R.id.fabCall);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        chatItemList = new ArrayList<>();
        // Add users with a constant message
        String constantMessage = "Hey, I'm Using this Application"; // Constant message
        for (int i = 0; i < 15; i++) {
            chatItemList.add(new ChatItem(R.drawable.user_11663136, "User" + (i + 1), constantMessage));
        }

        originalChatItemList = new ArrayList<>(chatItemList); // Save a copy of the original list

        adapter = new ChatAdapter(originalChatItemList, this);

        recyclerView.setAdapter(adapter);

        fabMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMenuOpen) {
                    closeMenu();
                } else {
                    openMenu();
                }
            }
        });

        fabCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass a copy of the chatItemList to CallHistoryActivity
                Intent intent = new Intent(MainActivity.this, CallHistoryActivity.class);
                startActivity(intent);


            }
        });

        fabStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on fabStatus button
                Intent intent = new Intent(MainActivity.this, StatusActivity.class);
                startActivity(intent);

            }
        });
        fabFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on fabFeedback button
                Intent intent = new Intent(MainActivity.this, FeedbackActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        chatItemList = new ArrayList<>();
        String constantMessage = "Hey, I'm Using this Application";
        for (int i = 0; i < 15; i++) {
            chatItemList.add(new ChatItem(R.drawable.user_11663136, "User" + (i + 1), constantMessage));
        }

        originalChatItemList = new ArrayList<>(chatItemList); // Save a copy of the original list

        adapter = new ChatAdapter(originalChatItemList, this);

        recyclerView.setAdapter(adapter);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            ArrayList<ChatItem> modifiedList = data.getParcelableArrayListExtra("modifiedChatItemList");
            if (modifiedList != null) {
                // Update the original list with the modified list
                chatItemList.clear();
                chatItemList.addAll(modifiedList);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void openMenu() {
        isMenuOpen = true;
        if (fabFeedback != null && fabStatus != null && fabCall != null) {
            fabFeedback.setVisibility(View.VISIBLE);
            fabStatus.setVisibility(View.VISIBLE);
            fabCall.setVisibility(View.VISIBLE);
        }

        ObjectAnimator fabFeedbackAnimator = ObjectAnimator.ofFloat(fabFeedback, "translationX", 0f, -getResources().getDimension(R.dimen.fab_feedback_translation_y));
        ObjectAnimator fabStatusAnimator = ObjectAnimator.ofFloat(fabStatus, "translationX", 0f, -getResources().getDimension(R.dimen.fab_status_translation_y));
        ObjectAnimator fabCallAnimator = ObjectAnimator.ofFloat(fabCall, "translationX", 0f, -getResources().getDimension(R.dimen.fab_call_translation_y));

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(fabFeedbackAnimator, fabStatusAnimator, fabCallAnimator);
        animatorSet.setDuration(300); // Set duration for the animation
        animatorSet.start();
    }

    private void closeMenu() {
        isMenuOpen = false;

        ObjectAnimator fabFeedbackAnimator = ObjectAnimator.ofFloat(fabFeedback, "translationX", -getResources().getDimension(R.dimen.fab_feedback_translation_y), 0f);
        ObjectAnimator fabStatusAnimator = ObjectAnimator.ofFloat(fabStatus, "translationX", -getResources().getDimension(R.dimen.fab_status_translation_y), 0f);
        ObjectAnimator fabCallAnimator = ObjectAnimator.ofFloat(fabCall, "translationX", -getResources().getDimension(R.dimen.fab_call_translation_y), 0f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(fabFeedbackAnimator, fabStatusAnimator, fabCallAnimator);
        animatorSet.setDuration(300); // Set duration for the animation
        animatorSet.start();

        // Set buttons invisible after animation completes
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (fabFeedback != null && fabStatus != null && fabCall != null) {
                    fabFeedback.setVisibility(View.INVISIBLE);
                    fabStatus.setVisibility(View.INVISIBLE);
                    fabCall.setVisibility(View.INVISIBLE);
                }
            }
        });
    }


}
