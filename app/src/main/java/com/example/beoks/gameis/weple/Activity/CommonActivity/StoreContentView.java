package com.example.beoks.gameis.weple.Activity.CommonActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.beoks.gameis.weple.DataClass.Store.StoreContent;
import com.example.beoks.gameis.weple.R;

import java.util.List;

public class StoreContentView extends LinearLayout {
    public StoreContentView(Context context, StoreContent storeContent) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);

        String inflaterService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(inflaterService);
        View view = layoutInflater.inflate(R.layout.store_content_view, this, false);
        addView(view);

    }
}
