package com.omar.retoandroid.ui.home;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omar.retoandroid.R;
import com.omar.retoandroid.data.room.Visit;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private List<Visit> visitList;
    private OnVisitListener onVisitListener;

    public HomeAdapter(List<Visit> visitList, OnVisitListener onVisitListener){
        this.visitList = visitList;
        this.onVisitListener = onVisitListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.visits_item,parent, false);
        return new MyViewHolder(view, onVisitListener);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Visit visit = visitList.get(position);
        if (visit.isStatus()){
            holder.tvStatus.setText("Visitada");
            holder.tvStatus.setTextColor(Color.parseColor("#3ac2c2"));
            holder.ivStatus.setImageResource(R.drawable.ic_status_visit);
        }else{
            holder.tvStatus.setText("Pendiente");
            holder.tvStatus.setTextColor(R.color.colorNotVisit);
            holder.ivStatus.setImageResource(R.drawable.ic_status_not_visit);
        }
        holder.tvStreetName.setText(visit.getStreetName());
        holder.tvSuburb.setText(visit.getSuburb());
    }

    @Override
    public int getItemCount() {
        return visitList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvStatus,tvStreetName, tvSuburb;
        ImageView ivStatus;
        OnVisitListener onVisitListener;

        public MyViewHolder(@NonNull View itemView, OnVisitListener onVisitListener) {
            super(itemView);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvStreetName = itemView.findViewById(R.id.tvStreetName);
            tvSuburb = itemView.findViewById(R.id.tvSuburb);
            ivStatus = itemView.findViewById(R.id.ivStatus);
            this.onVisitListener = onVisitListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onVisitListener.onVisitClick(getAdapterPosition());

        }
    }

    public void setData(List<Visit> visitList){
        this.visitList = visitList;
        notifyDataSetChanged();
    }

    public interface OnVisitListener{
        void onVisitClick(int position);
    }
}
