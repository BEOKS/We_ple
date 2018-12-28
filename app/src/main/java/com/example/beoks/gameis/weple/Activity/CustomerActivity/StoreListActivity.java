package com.example.beoks.gameis.weple.Activity.CustomerActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;

import com.example.beoks.gameis.weple.R;

public class StoreListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list2);
        initViewinstance();

    }

    public Button menuButton;
    public AutoCompleteTextView autoCompleteTextView;
    public Button checkButotn;
    public LinearLayout searchedTagLayout;
    public ImageButton foodButton,cafeButton,lifeButton,entertainmentButton,drinkButton;
    public Spinner orderSpinenr;
    public TableLayout tableLayout;
    private void initViewinstance(){

    }
    private void refreshSearchResult(String category,String[] hashTagList,String orderLevel,TableLayout tableLayout){

    }
}
