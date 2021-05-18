package com.samapps.restituo.ui.view.uinav.home;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.samapps.restituo.R;

public class View_Holder extends RecyclerView.ViewHolder {

    CardView cv;
    TextView mobileNo;
    TextView textMail;
    TextView timer;
    ImageView imageView;
    CountDownTimer timerr;

    View_Holder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.cardView);
        mobileNo = (TextView) itemView.findViewById(R.id.title);
        textMail = (TextView) itemView.findViewById(R.id.description);
        timer = (TextView) itemView.findViewById(R.id.tv_timer);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }
}
