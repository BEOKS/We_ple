package com.example.beoks.gameis.weple.Activity.CustomerActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.beoks.gameis.weple.R;

import java.util.HashMap;

public class CustomerStoreListActivity extends Activity {

    Button menuButton;
    EditText searchText;
    Button searchButton;

    Button horizonButton1;
    Button horizonButton2;
    Button horizonButton3;
    Button horizonButton4;
    Button horizonButton5;
    Button horizonButton6;
    Button horizonButton7;

    TableLayout tableLayout;

    Button addButton;
    Button locateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list2);
        HashMap<String,Button> hashMap = new HashMap<String, Button>(){ };

        menuButton = findViewById(R.id.menuButton);
        searchText = findViewById(R.id.searchText);
        searchButton = findViewById(R.id.searchButton);

        horizonButton1 = findViewById(R.id.horizonButton1);
        horizonButton2 = findViewById(R.id.horizonButton2);
        horizonButton3 = findViewById(R.id.horizonButton3);
        horizonButton4 = findViewById(R.id.horizonButton4);
        horizonButton5 = findViewById(R.id.horizonButton5);
        horizonButton6 = findViewById(R.id.horizonButton6);
        horizonButton7 = findViewById(R.id.horizonButton7);

        hashMap.put("음식", horizonButton1);
        hashMap.put("카페/디저트", horizonButton2);
        hashMap.put("생활", horizonButton3);
        hashMap.put("엔터테인먼트", horizonButton4);
        hashMap.put("술집", horizonButton5);
        hashMap.put("음식", horizonButton6);
        hashMap.put("카페/디저트", horizonButton7);

        tableLayout = findViewById(R.id.cardTable);

        addButton = findViewById(R.id.addButton);
        locateButton = findViewById(R.id.locateButton);

        for(int i=0; i<4; i++) {
            CardView customView = new CardView(this);
            tableLayout.addView(customView);
        }
    }

}
