package com.samapps.restituo.ui.view.uinav.home;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.samapps.restituo.R;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Recycler_View_Adapter extends RecyclerView.Adapter<View_Holder> {
    CardData cardData = new CardData();

    List<CardData> list = Collections.emptyList();
    Context context;
    //CountDownTimer countDownTimer;

    public Recycler_View_Adapter(final List<CardData> list, Context context) {
        this.list = list;
        this.context = context;

    }



    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(final View_Holder holder, final int position) {

        //notifyDataSetChanged();
        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        holder.mobileNo.setText(list.get(position).getMobileNo());
        holder.textMail.setText(list.get(position).getTextMail());

        Date today = new Date();
        final long currentTime = today.getHours();
        final long timerr = today.getHours() + 4;
        System.out.println(timerr);





        if (holder.timerr != null) {
            holder.timerr.cancel();
        }

        holder.timerr = new CountDownTimer(14400000, 500) {
                public void onTick(long millisUntilFinished) {
                    long seconds = millisUntilFinished / 1000;

                    long minutes = seconds / 60;
                    long hours = minutes / 60;
                    //long days = hours / 24;
                    String time = hours % 24 + ":" + minutes % 60 + ":" + seconds % 60;
                    holder.timer.setText(time);

                }

                @Override
                public void onFinish() {
                    holder.timer.setText("Time Over" );
                }

            }.start();



        }




    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        CountDownTimer timer;

        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, CardData data) {
        list.add(position, data);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(CardData data) {
        int position = list.indexOf(data);
        list.remove(position);
        notifyItemRemoved(position);
    }

}
