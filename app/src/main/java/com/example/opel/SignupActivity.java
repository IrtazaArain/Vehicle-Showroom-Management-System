package com.example.opel;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    TextInputLayout F_name, L_name, Address, Email, Password;
    TextView Login;

    Button create_account;

    AlertDialog.Builder builder;

    String server_url = "http://10.57.16.231/project/upload.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Login = findViewById(R.id.login_textVew);

        F_name = findViewById(R.id.firstname_edittext);
        L_name = findViewById(R.id.lastname_edittext);
        Email = findViewById(R.id.email_edittext);
        Address = findViewById(R.id.address_edittext);

        Password = findViewById(R.id.password_edittext);

        create_account =findViewById(R.id.create_button);


        Login.setOnClickListener(view -> Login());
        create_account.setOnClickListener(view -> Sign_in_Func());

        builder = new AlertDialog.Builder(SignupActivity.this);
    }
    public void Sign_in_Func(){

        final String f_name = F_name.getEditText().toString();
        final String l_name = L_name.getEditText().toString();
        final String address = Address.getEditText().toString();
        final String email = Email.getEditText().toString();
        final String password = Password.getEditText().toString().trim();

        if(!email.isEmpty() && !password.isEmpty() && !f_name.isEmpty() && !l_name.isEmpty()) {


            StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, response -> {

                builder.setTitle("Server Response");
                builder.setMessage("Response :" + response);
                builder.setPositiveButton("OK", (dialog, which) -> {
                    F_name.getEditText().setText("");
                    L_name.getEditText().setText("");
                    Address.getEditText().setText("");
                    Email.getEditText().setText("");
                    Password.getEditText().setText("");

                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }

                    , error -> {
                Toast.makeText(SignupActivity.this, "some error occurred .....", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> Params = new HashMap<>();
                    Params.put("f_name", f_name);
                    Params.put("l_name", l_name);
                    Params.put("address", address);
                    Params.put("email", email);
                    Params.put("password", password);

                    return Params;

                }
            };
            My_Singleton.getInstance(SignupActivity.this).addToRequestQue(stringRequest);
        }
        else {
            Toast.makeText(SignupActivity.this, "field can't be empty", Toast.LENGTH_SHORT).show();
        }
    }


    public void Login(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }

}