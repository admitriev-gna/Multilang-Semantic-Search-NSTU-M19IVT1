package com.example.multilangsemanticsearch;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MassageViewHolder>{
    public static class MassageViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView massage_send;
        TextView lang_send;
        TextView message_get;
        TextView lang_get;
        Button delete;

        MassageViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.card1);
            massage_send = itemView.findViewById(R.id.person_name);
            lang_send = itemView.findViewById(R.id.person_age);
            message_get = itemView.findViewById(R.id.response_name);
            lang_get = itemView.findViewById(R.id.response_lang);
            delete = itemView.findViewById(R.id.delete);
        }
    }
    List<Massage> massages;
    Context context;
    DBHelper dbHelper;
    RVAdapter(List<Massage> massages, Context context){
        this.context = context;
        this.massages = massages;
        dbHelper = new DBHelper(context);
    }

    @Override
    public int getItemCount() {
        return massages.size();
    }

    @Override
    public MassageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        MassageViewHolder pvh = new MassageViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(MassageViewHolder massageViewHolder, int i) {
        massageViewHolder.massage_send.setText(massages.get(i).message);
        massageViewHolder.lang_send.setText(massages.get(i).lang);
        massageViewHolder.message_get.setText(massages.get(i).message_res);
        massageViewHolder.lang_get.setText(massages.get(i).lang_res);
        massageViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("mytable", "id=" + massages.get(massageViewHolder.getAdapterPosition()).id, null) ;
                massages.remove(massageViewHolder.getAdapterPosition());
                notifyItemRemoved(massageViewHolder.getAdapterPosition());
                notifyItemRangeChanged(massageViewHolder.getAdapterPosition(), massages.size());
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
