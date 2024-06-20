package com.example.quicklinkchat;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    TextView signUp;
    Button button;
    EditText email, password;
    FirebaseAuth auth;
    String EmailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(login.this,R.color.black));
        }

        signUp = findViewById(R.id.singup);
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.logbutton);
        email = findViewById(R.id.editTextlogEmail);
        password = findViewById(R.id.editTextlogPassword);

        signUp.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), registration.class);
            startActivity(intent, ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.left_to_right, R.anim.left_to_right).toBundle());
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString();
                String pass = password.getText().toString();
                if ((TextUtils.isEmpty(Email))) {
                    Toast.makeText(login.this, "Enter the email", Toast.LENGTH_SHORT).show();
                } else if ((TextUtils.isEmpty(pass))) {
                    Toast.makeText(login.this, "Enter the password", Toast.LENGTH_SHORT).show();
                } else if (!Email.matches(EmailPattern)) {
                    email.setError("Give proper Email Address");
                } else if (pass.length() < 6) {
                    password.setError("More than six characters !");
                    Toast.makeText(login.this, "Password needs to be longer than six Characters", Toast.LENGTH_SHORT).show();
                } else {
                    // Add your authentication logic here
                    // For example, you can use Firebase Authentication to authenticate the user
                    // Once authenticated, you can start MainActivity
                    progressDialog.show();

                    // Delay the dismissal of progressDialog for 1 second
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            Intent intent = new Intent(login.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 1000); // 1000 milliseconds = 1 second
                }
            }
        });
    }
}
