package com.example.quicklinkchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class registration extends AppCompatActivity {

    TextView login;
    EditText rg_username, rg_email, rg_password, rg_repassword;
    String Emailpattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]";
    Uri imageURI;
    String imageuri;
    FirebaseDatabase database;
    FirebaseDatabase storage;
    Button rg_signup;
    FirebaseAuth auth;
    private ImageView profileImage;
    private static final int GALLERY_REQUEST_CODE = 100;
    private static final int CROP_REQUEST_CODE = 101;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(registration.this,R.color.black));
        }
        login = findViewById(R.id.login);
        rg_signup = findViewById(R.id.signupbutton);
        rg_username = findViewById(R.id.rgusername);
        rg_email = findViewById(R.id.rgemail);
        rg_password = findViewById(R.id.rgpassword);
        rg_repassword = findViewById(R.id.rgpassword);
        profileImage = findViewById(R.id.profile001);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Establishing your account...");

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        login.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), login.class);
            startActivity(intent);
            finish();
        });

        rg_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namev = rg_username.getText().toString();
                String emailv = rg_email.getText().toString();
                String passwordv = rg_password.getText().toString();
                String repasswordv = rg_repassword.getText().toString();
                String status = "Welcome !!";
                if (TextUtils.isEmpty(namev) || TextUtils.isEmpty(passwordv) || TextUtils.isEmpty(repasswordv)) {
                    Toast.makeText(registration.this, "Please, Enter the required information !!", Toast.LENGTH_SHORT).show();
                } else if (profileImage.getDrawable() == null) {
                    Toast.makeText(registration.this, "Select your profile image", Toast.LENGTH_SHORT).show();
                } else if (passwordv.length() < 6) {
                    rg_password.setError("Password must be more than 6 characters");
                } else {
                    // Show progressDialog
                    progressDialog.show();

                    // Create a handler to dismiss progressDialog after 1 second
                    new android.os.Handler().postDelayed(new Runnable() {
                        public void run() {
                            // Dismiss progressDialog
                            progressDialog.dismiss();

                            // After 1 second, perform your registration logic here
                            // For demonstration purposes, I'm starting the login activity
                            Intent intent = new Intent(registration.this, login.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 1000); // 1000 milliseconds = 1 second
                }
            }
        });
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            cropImage(imageUri);
        } else if (requestCode == CROP_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap croppedBitmap = extras.getParcelable("data");
                Bitmap circularBitmap = getCircularBitmap(croppedBitmap);
                profileImage.setImageBitmap(circularBitmap);
            }
        }
    }

    private void cropImage(Uri imageUri) {
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(imageUri, "image/*");
        cropIntent.putExtra("crop", "true");
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        cropIntent.putExtra("outputX", 256); // adjust output size as needed
        cropIntent.putExtra("outputY", 256);
        cropIntent.putExtra("return-data", true);
        startActivityForResult(cropIntent, CROP_REQUEST_CODE);
    }

    private Bitmap getCircularBitmap(Bitmap bitmap) {
        Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        BitmapShader shader = new BitmapShader(bitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(shader);

        Canvas canvas = new Canvas(circleBitmap);
        float radius = Math.min(bitmap.getWidth(), bitmap.getHeight()) / 2f;
        canvas.drawCircle(radius, radius, radius, paint);

        return circleBitmap;
    }
}
