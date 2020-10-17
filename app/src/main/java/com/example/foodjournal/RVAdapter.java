package com.example.foodjournal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVViewHolder> {

    ArrayList<Experience> al;
    private onItemClickListener mListener;

    public interface onItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        mListener = listener;
    }

    public RVAdapter(ArrayList<Experience> al) {
        this.al = al;
    }

    @NonNull
    @Override
    public RVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View journal_view = inflater.inflate(R.layout.listview_template, parent, false);


        RVViewHolder rvViewHolder = new RVViewHolder(journal_view, mListener);
        return rvViewHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull RVViewHolder holder, int position) {
        Experience exp = al.get(position);
        holder.title_tv.setText(exp.getTitle());
        holder.date_tv.setText(exp.getDate());
        holder.food_img.setImageBitmap(exp.getImg());


    }

    @Override
    public int getItemCount() {
        return al.size();
    }


    public static class RVViewHolder extends RecyclerView.ViewHolder
    {
        TextView title_tv, date_tv;
        ImageView food_img;

        public RVViewHolder(@NonNull View itemView, final onItemClickListener listener) {
            super(itemView);
            title_tv = itemView.findViewById(R.id.list_title);
            date_tv = itemView.findViewById(R.id.list_date);
            food_img = itemView.findViewById(R.id.show_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener !=null){
                        int pos = getAdapterPosition();
                        if(pos!= RecyclerView.NO_POSITION){
                            listener.onItemClick(pos);
                        }
                    }
                }
            });
        }

    }


}
