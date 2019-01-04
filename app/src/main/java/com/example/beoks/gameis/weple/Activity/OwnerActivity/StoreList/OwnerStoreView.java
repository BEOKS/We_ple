package com.example.beoks.gameis.weple.Activity.OwnerActivity.StoreList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.beoks.gameis.weple.DataClass.Store.StoreContent;
import com.example.beoks.gameis.weple.R;

import org.w3c.dom.Text;

public class OwnerStoreView extends LinearLayout {
    public ImageView imageView;
    public TextView storeNameTextView, statusTextView;
    public Button editButton,deleteButton;

    private String waitingStatus="off",seatStatus="off";

    public OwnerStoreView(Context context,StoreContent storeContent) {
        super(context);
        initView();

    }

    public OwnerStoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OwnerStoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void initView(){
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.activity_store_list_store_format, this, false);
        addView(v);

        imageView=(ImageView)findViewById(R.id.format_customImageView);
        storeNameTextView=(TextView)findViewById(R.id.format_storeNameTextView);
        statusTextView=(TextView)findViewById(R.id.format_statusTextView);
        editButton=(Button)findViewById(R.id.format_editButton);
        deleteButton=(Button)findViewById(R.id.format_deleteButton);
        initButton();
    }
    public void turnOnWaiting(){
        waitingStatus="ON";
        setStatus();
    }
    public void turnOffWaiting(){
        waitingStatus="OFF";
        setStatus();
    }
    public void turnOnSeat(){
        seatStatus="ON";
        setStatus();
    }
    public void turnOffSeat(){
        seatStatus="OFF";
        setStatus();
    }
    private void setStatus(){
        statusTextView.setText("웨이팅 : "+waitingStatus+"\n빈좌석 수 표시 : "+seatStatus);
    }
    private void initButton(){
        deleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
