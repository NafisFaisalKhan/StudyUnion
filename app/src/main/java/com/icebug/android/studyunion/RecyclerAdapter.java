package com.icebug.android.studyunion;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nafis on 23-Aug-17.
 */

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

   private ArrayList<String> arrayList;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_child,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        holder.cardType.setText(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public RecyclerAdapter(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView cardType;
        ListView list;

        public ViewHolder(View itemView) {
            super(itemView);

            cardType = (TextView)itemView.findViewById(R.id.card_type);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if(arrayList.get(getAdapterPosition()).equals("Assignments") ){

                Intent intent = new Intent(v.getContext(), card_list_activity.class);
                intent.putExtra("CardHeader","Assignments");

                v.getContext().startActivity(intent);

            };

            if(arrayList.get(getAdapterPosition()).equals("Exams") ){

                Intent intent = new Intent(v.getContext(), card_list_activity.class);
                intent.putExtra("CardHeader","Exams");

                v.getContext().startActivity(intent);

            };

            if(arrayList.get(getAdapterPosition()).equals("Calender") ){



            };

        }


    }

}
