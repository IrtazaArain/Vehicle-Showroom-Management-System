package com.example.opel;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {


    TextView Forget, SignUp;
    TextInputLayout Email, Password;
    Button LoginBtn;

    MaterialAlertDialogBuilder builder, progressDialog;

    public static String PREFS_NAME = "credentials";

    String server_url = "http://192.168.0.102/project/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //ClickableText
        SignUp = findViewById(R.id.sign_up_textview);
        Forget = findViewById(R.id.help_login_textview);

        //EditText
        Email = findViewById(R.id.email_edittext);
        Password = findViewById(R.id.password_edittext);


        //Buttons
        LoginBtn = findViewById(R.id.login_button);


        //Function

        SignUp.setOnClickListener(view -> Signup_Func());
        Forget.setOnClickListener(view -> Forget_Func());
        LoginBtn.setOnClickListener(view -> Login_Func());


    }
    public void tem(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);

    }

    public void Login_Func(){

        final String email = Email.getEditText().getText().toString().trim();
        final String password = Password.getEditText().getText().toString().trim();

        if(!email.isEmpty() && !password.isEmpty()) {
            if (email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+") &&email.length() > 0){
                if (password.length() >= 4){

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, response -> {

                        builder = new MaterialAlertDialogBuilder(LoginActivity.this, R.style.CustomMaterialDialog)

                                .setMessage(response);
                        final ProgressBar progressBar = new ProgressBar(this);

                        builder.setView(progressBar);
                        if(response.equals("success")){
                            // Delay for 2 seconds (2000 milliseconds) before launching the intent
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.GONE);

                                    Email.getEditText().setText("");
                                    Password.getEditText().setText("");

                                    SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("useremail", Email.getEditText().toString());
                                    editor.apply();

                                    // Launch the intent to the home activity
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }, 2000); // Change the delay duration as per your requirement

                        }
                        builder.create();
                        builder.show();
                    }

                            , error -> {
                        Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        error.printStackTrace();

                    }){
                        @Override
                        protected Map<String, String> getParams() {
                            Map <String,String> Params = new HashMap<>();
                            Params.put("email",email);
                            Params.put("password",password);
                            return Params;

                        }
                    };
                    My_Singleton.getInstance(LoginActivity.this).addToRequestQue(stringRequest);

                }
                else{
                    Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(LoginActivity.this, "Invalid email address", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(LoginActivity.this, "Fields can't be empty", Toast.LENGTH_SHORT).show();
        }
    }

    public void Signup_Func(){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    public void Forget_Func(){
        Intent intent = new Intent(this, Forget_Pass.class);
        startActivity(intent);
    }

}