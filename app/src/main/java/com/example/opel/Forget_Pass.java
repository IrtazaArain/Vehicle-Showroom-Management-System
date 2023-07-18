package com.example.opel;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class Forget_Pass extends AppCompatActivity {

    TextInputLayout Email, Password;
    Button Submit;

    AlertDialog.Builder builder;

    String server_url = "http://192.168.0.103/project/Forget_Password.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        Submit = findViewById(R.id.submit_button);

        Email = findViewById(R.id.email_edittext);
        Password = findViewById(R.id.password_edittext);

        Submit.setOnClickListener(view -> Forget_Pass_Func());

        builder = new AlertDialog.Builder(Forget_Pass.this);
    }
    public void Forget_Pass_Func(){

        final String email = Email.getEditText().toString().trim();
        final String password = Password.getEditText().toString().trim();

        if(!email.isEmpty() && !password.isEmpty()) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    builder.setTitle("Server Response");
                    builder.setMessage("Response :"+response);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Email.getEditText().setText("");
                            Password.getEditText().setText("");

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

            }

                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Forget_Pass.this,"some error occurred .....", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map <String,String> Params = new HashMap<String, String>();
                    Params.put("email",email);
                    Params.put("password",password);
                    return Params;

                }
            };
            My_Singleton.getInstance(Forget_Pass.this).addToRequestQue(stringRequest);
        }
        else{
            Toast.makeText(Forget_Pass.this, "fields can't be empty", Toast.LENGTH_SHORT).show();
        }

    }

    public void Login(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}