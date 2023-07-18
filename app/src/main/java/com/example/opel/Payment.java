package com.example.opel;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;


public class Payment extends AppCompatActivity {

    TextInputLayout SelectType,Year,Month;

    EditText CardNumber,ExpiryDate,Cvv,Name,Amount;
    Button btn;

    AutoCompleteTextView Tax_Type,Tax_Year,Tax_month;

    MaterialAlertDialogBuilder builder;
    String server_url = "http://192.168.90.123/project/payment.php";

    public Payment() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        SelectType =findViewById(R.id.select_type_TextInputLayout);
        Year = findViewById(R.id.year_TextInputLayout);
        Month = findViewById(R.id.month_TextInputLayout);

        CardNumber = findViewById(R.id.card_number_edittext);
        ExpiryDate = findViewById(R.id.expiry_date_edittext);
        Cvv = findViewById(R.id.cvv_edittext);
        Name = findViewById(R.id.name_edittext);
        Amount = findViewById(R.id.amount_edittext);

        btn = findViewById(R.id.pay_now_button);

        Tax_Type = findViewById(R.id.select_type_autoComplete);


        Tax_Year = findViewById(R.id.year_autoComplete);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Payment_Func();
            }
        });
    }

    public void Payment_Func(){

        final String select_type = SelectType.getEditText().getText().toString();
        final String year = Year.getEditText().getText().toString();
        final String month = Month.getEditText().getText().toString();


        final String card_number = CardNumber.getText().toString();
        final String expiry_date = ExpiryDate.getText().toString();
        final String cvv = Cvv.getText().toString();
        final String name = Name.getText().toString();
        final String amount = Amount.getText().toString();

        String method = "Credit Card";


        if(!select_type.isEmpty() && !year.isEmpty()  && !month.isEmpty() && !name.isEmpty()){
            if(card_number.length() == 16){
                if(cvv.length() == 3){
                    if(expiry_date.matches("([0-9]{2})/([0-9]{4})") && (expiry_date.startsWith("0") ||
                            expiry_date.startsWith("1"))) {
                        if (amount.length() >= 4) {
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, response -> {
                                builder = new MaterialAlertDialogBuilder(Payment.this)

                                        .setTitle("Server Response")
                                        .setMessage("Response :"+response)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                Tax_Type.setText("");
                                                Tax_Year.setText("");
                                                Tax_month.setText("");
                                                CardNumber.setText("");
                                                ExpiryDate.setText("");
                                                Cvv.setText("");
                                                Name.setText("");
                                                Amount.setText("");

                                                dialog.dismiss();
                                            }
                                        })
                                        .setNegativeButton("close", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                builder.create();
                                builder.show();
                            }

                                    , error -> {
                                Toast.makeText(Payment.this, "some error occurred .....", Toast.LENGTH_SHORT).show();
                                error.printStackTrace();

                            }) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> Params = new HashMap<>();

                                    Params.put("tax_type", select_type);
                                    Params.put("year", year);
                                    Params.put("month", month);

                                    Params.put("card_number", card_number);
                                    Params.put("expiry_date", expiry_date);
                                    Params.put("cvv", cvv);
                                    Params.put("name", name);
                                    Params.put("amount", amount);
                                    Params.put("method", method);

                                    return Params;

                                }
                            };
                            My_Singleton.getInstance(Payment.this).addToRequestQue(stringRequest);



                        }
                        else{
                            Toast.makeText(Payment.this, "invalid amount", Toast.LENGTH_SHORT).show();
                        }
                    }

                    else{
                    Toast.makeText(Payment.this, "invalid expiry date", Toast.LENGTH_SHORT).show();
                    }
                }

                else{
                    Toast.makeText(Payment.this, "invalid cvv", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(Payment.this, "invalid card number", Toast.LENGTH_SHORT).show();
            }
        }

        else{
            Toast.makeText(Payment.this, "field can't be empty", Toast.LENGTH_SHORT).show();
        }
    }
}