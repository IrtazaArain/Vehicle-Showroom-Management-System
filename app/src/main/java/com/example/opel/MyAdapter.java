package com.example.opel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    Context context;
    ArrayList<DataModal> dataModals;

    public MyAdapter(Context context, ArrayList<DataModal> dataModals){

        this.context = context;
        this.dataModals = dataModals;


    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_row_design, parent, false);

        return new MyViewHolder(view,context);

    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        DataModal dataModal = dataModals.get(position);

        holder.modal.setText(dataModals.get(position).getModal());
        holder.make.setText(dataModals.get(position).getMake());
        holder.price.setText(dataModals.get(position).getPrice());

        String url = "http://10.57.16.231/project/images/IMG1320579639.jpg";

        Glide.with(context).load(dataModals.get(position).getImage()).placeholder(R.drawable.image_fill).into(holder.image);

        //holder.image.setImageResource(dataModals.get(position).getImage());
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("imageview", dataModal.getImage());
                intent.putExtra("modal",dataModal.getModal());
                intent.putExtra("make", dataModal.getMake());
                intent.putExtra("price",dataModal.getPrice());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataModals.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView modal, make, price;

        public MyViewHolder(@NonNull View itemView,Context context) {
            super(itemView);

            image = itemView.findViewById(R.id.imageView);
            modal = itemView.findViewById(R.id.textViewTitle);
            make = itemView.findViewById(R.id.textViewShortDesc);
            price = itemView.findViewById(R.id.textViewPrice);

            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, DetailsActivity.class);
                context.startActivity(intent);
            });

        }
    }
}

