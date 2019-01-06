package com.example.beoks.gameis.weple.Activity.CustomerActivity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.beoks.gameis.weple.R;

public class CardView extends LinearLayout {
    ImageView cardView_Image;
    Button cardView_Button;
    TextView cardView_Title;
    RatingBar cardView_Rating;
    TextView cardView_Like;

    public CardView(Context context) {
        super(context);
        initView();
        // setting <-----
    }

    public CardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        //getAttrs(attrs);
    }

    public CardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        initView();
        //getAttrs(attrs, defStyle);
    }

    private void initView() {
        String inflaterService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(inflaterService);
        View view = layoutInflater.inflate(R.layout.card_view, this, false);
        addView(view);

        cardView_Image = findViewById(R.id.cardView_Image);
        cardView_Button = findViewById(R.id.cardView_Button);
        cardView_Title = findViewById(R.id.cardView_Title);
        cardView_Rating = findViewById(R.id.cardView_Rating);
        cardView_Like = findViewById(R.id.cardView_Like);
    }

    public void setTitle(String txt){
        cardView_Title.setText(txt);
    }

    public void setRating(float num){
        cardView_Rating.setRating(num);
    }

    public void setLike(String txt){
        cardView_Like.setText(txt);
    }

}