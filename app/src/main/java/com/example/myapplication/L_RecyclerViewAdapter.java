package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class L_RecyclerViewAdapter extends RecyclerView.Adapter<L_RecyclerViewAdapter.MyViewHolder>{

    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<Locations> locations;
    public L_RecyclerViewAdapter(Context context, ArrayList<Locations> locations, RecyclerViewInterface recyclerViewInterface){
        this.context=context;
        this.locations=locations;
        this.recyclerViewInterface=recyclerViewInterface;
    }
    @NonNull
    @Override
    public L_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.recycler_view_row, parent,false);
        return new L_RecyclerViewAdapter.MyViewHolder(view,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull L_RecyclerViewAdapter.MyViewHolder holder, int position) {
        Locations location= locations.get(position);
        holder.tvLocationName.setText(location.getLocation());
        holder.tvKey.setText(location.getKey());
        //use setImageResource if we plan on displaying an image in the list
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvLocationName,tvKey;
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            tvLocationName=itemView.findViewById(R.id.LocationName);
            tvKey=itemView.findViewById(R.id.LocationKey);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface!=null){
                        int pos=getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
