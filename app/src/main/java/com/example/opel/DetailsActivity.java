package com.example.opel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    ImageView image;
    TextView modal, make, price;
    Button button;

    RecyclerView recyclerView;
    ArrayList<String> dataSource;
    LinearLayoutManager linearLayoutManager;
    MyRvAdapter myRvAdapter;

    BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //Setting the data source
        dataSource = new ArrayList<>();
        dataSource.add("Hello");
        dataSource.add("World");
        dataSource.add("To");
        dataSource.add("The");
        dataSource.add("Code");
        dataSource.add("City");
        dataSource.add("******");


        image = findViewById(R.id.imageView);
        modal = findViewById(R.id.textViewTitle);
        make = findViewById(R.id.textViewShortDesc);
        price = findViewById(R.id.textViewPrice);

        button = findViewById(R.id.book_button);
        recyclerView = findViewById(R.id.recylcerView_H);

        linearLayoutManager = new LinearLayoutManager(DetailsActivity.this, LinearLayoutManager.HORIZONTAL, false);
        myRvAdapter = new MyRvAdapter(dataSource);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myRvAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book();

            }
        });

        //image.setImageResource(getIntent().getIntExtra("imageview",R.drawable.ic_broken_image));

        Glide.with(this).load(getIntent().getStringExtra("imageview")).placeholder(R.drawable.ic_broken_image).into(image);
        modal.setText(getIntent().getStringExtra("modal"));
        make.setText(getIntent().getStringExtra("make"));
        price.setText(getIntent().getStringExtra("price"));
    }
    public void Book(){
        Intent intent = new Intent(this, Payment.class);
        startActivity(intent);
    }
    class MyRvAdapter extends RecyclerView.Adapter<MyRvAdapter.MyHolder> {
        ArrayList<String> data;

        public MyRvAdapter(ArrayList<String> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(DetailsActivity.this).inflate(R.layout.single_col_design, parent, false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            holder.tvTitle.setText(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {
            TextView tvTitle;

            public MyHolder(@NonNull View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.textViewTitle);
            }
        }

    }
}