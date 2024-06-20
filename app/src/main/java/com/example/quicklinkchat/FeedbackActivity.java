package com.example.quicklinkchat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(FeedbackActivity.this,R.color.black));
        }

        EditText editTextFeedback = findViewById(R.id.editTextFeedback);
        TextView textViewCharacterLimit = findViewById(R.id.textViewCharacterLimit);
        Button btnSubmitFeedback = findViewById(R.id.btnSubmitFeedback);
        RatingBar ratingBar = findViewById(R.id.ratingBar);

        int maxLength = 120; // Maximum allowed characters

        // Set initial character count
        textViewCharacterLimit.setText(maxLength + "/120 ");

        // Add TextWatcher to monitor changes in EditText
        editTextFeedback.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Get current length of text
                int length = s.toString().length();
                // Calculate remaining characters
                int remaining = maxLength - length;
                // Update TextView to display remaining characters
                textViewCharacterLimit.setText(remaining + "/120");

                // Disable EditText when maxLength is reached
                if (remaining <= 0) {
                    editTextFeedback.setEnabled(false);
                    Toast.makeText(FeedbackActivity.this, "Your Feedback has reached 150 characters!", Toast.LENGTH_SHORT).show();
                } else {
                    editTextFeedback.setEnabled(true);
                }
            }
        });

        // Set OnClickListener for the submit button
        btnSubmitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if EditText and RatingBar are not null and not empty
                if (editTextFeedback.getText().toString().trim().isEmpty() || ratingBar.getRating() == 0) {
                    Toast.makeText(FeedbackActivity.this, "Please enter your feedback and rate us!", Toast.LENGTH_SHORT).show();
                } else {
                    // Display toast message
                    Toast.makeText(FeedbackActivity.this, "Your feedback submitted. Thanks for rating us!", Toast.LENGTH_SHORT).show();
                    // Clear the EditText
                    editTextFeedback.setText("");
                    // Clear the RatingBar
                    ratingBar.setRating(0);
                }
            }
        });

        // Set OnClickListener for the Instagram LinearLayout
        LinearLayout instagramLayout = findViewById(R.id.instagram);
        instagramLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to Instagram URL
                String url = "https://www.instagram.com/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        // Set OnClickListener for the Telegram LinearLayout

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout telegramLayout = findViewById(R.id.telegram);
        telegramLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to Telegram URL
                String url = "https://web.telegram.org/z/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        // Set OnClickListener for the Facebook LinearLayout
        LinearLayout facebookLayout = findViewById(R.id.facebook);
        facebookLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to Facebook URL
                String url = "https://www.facebook.com/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }
}
