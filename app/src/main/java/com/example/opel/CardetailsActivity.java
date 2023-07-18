package com.example.opel;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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

public class CardetailsActivity extends AppCompatActivity {

    AlertDialog.Builder builder;
    Button SignupBtn;
    Bitmap bitmap;
    EditText Make, Modal;
    ImageView UploadImg,AddImage;
    String encodedImage;

    String server_url = "http://192.168.1.105/project/insert.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardetails);



        //EditText
        Make = findViewById(R.id.name_et);
        Modal = findViewById(R.id.email_et);

        //Image
        UploadImg = findViewById(R.id.profile_image);
        AddImage = findViewById(R.id.add_image);

        //Buttons
        SignupBtn = findViewById(R.id.signup_btn);

        //Function

        SignupBtn.setOnClickListener(view -> Signin_Func());
        AddImage.setOnClickListener(view -> Browse());

        builder = new AlertDialog.Builder(CardetailsActivity.this);

    }
    public  void  Browse(){
        Dexter.withActivity(CardetailsActivity.this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent, "Browse Image"), 1);

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri filepath = data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                UploadImg.setImageBitmap(bitmap);
                encodeBitmapImage(bitmap);
            }catch(Exception ex){

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, byteArrayOutputStream);

        byte[] bytesofimage = byteArrayOutputStream.toByteArray();
        encodedImage = Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }

    public void Signin_Func(){

        final String make = Make.getText().toString();
        final String modal = Modal.getText().toString();

        if(!modal.isEmpty() && !make.isEmpty()) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    builder.setTitle("Server Response");
                    builder.setMessage("Response :"+response);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Make.setText("");
                            Modal.setText("");
                            UploadImg.setImageResource(R.drawable.ic_user);

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
            }

                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(CardetailsActivity.this,"some error occurred .....", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map <String,String> Params = new HashMap<String, String>();
                    Params.put("make",make);
                    Params.put("modal",modal);
                    Params.put("upload",encodedImage);

                    return Params;

                }
            };
            My_Singleton.getInstance(CardetailsActivity.this).addToRequestQue(stringRequest);
        }
        else{
            Toast.makeText(CardetailsActivity.this, "fields can't be empty", Toast.LENGTH_SHORT).show();
        }

    }

}