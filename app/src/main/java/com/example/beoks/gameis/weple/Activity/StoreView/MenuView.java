package com.example.beoks.gameis.weple.Activity.StoreView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.beoks.gameis.weple.DataClass.Store.Menu;
import com.example.beoks.gameis.weple.R;

public class MenuView extends LinearLayout {
    public Menu menu;
    ImageView menu_image;
    TextView menu_name;
    TextView menu_price;

    public MenuView(Context context, Menu menu) {
        super(context);
        this.menu =menu;
        initView();
    }

    public MenuView(Context context, Menu menu, AttributeSet attrs) {
        super(context, attrs);
        this.menu =menu;
        initView();
        //getAttrs(attrs);
    }

    public MenuView(Context context, Menu menu, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        this.menu =menu;
        initView();
        //getAttrs(attrs, defStyle);
    }

    private void initView() {
        String inflaterService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(inflaterService);
        View view = layoutInflater.inflate(R.layout.menu_view, this, false);
        addView(view);

        menu_image = findViewById(R.id.menu_image);
        menu_name = findViewById(R.id.menu_name);
        menu_price = findViewById(R.id.menu_price);
    }

    public void setImage(int id){
        menu_image.setImageDrawable(getResources().getDrawable(id));
    }

    public void setName(String txt){
        menu_name.setText(txt);
    }

    public void setPrice(String txt){
        menu_price.setText(txt);
    }
}
