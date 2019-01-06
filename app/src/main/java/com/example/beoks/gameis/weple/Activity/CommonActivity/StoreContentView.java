package com.example.beoks.gameis.weple.Activity.CommonActivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.beoks.gameis.weple.Activity.StoreView.ArticleView;
import com.example.beoks.gameis.weple.Activity.StoreView.MenuView;
import com.example.beoks.gameis.weple.DataClass.Store.Article;
import com.example.beoks.gameis.weple.DataClass.Store.Menu;
import com.example.beoks.gameis.weple.DataClass.Store.StoreContent;
import com.example.beoks.gameis.weple.R;

public class StoreContentView extends LinearLayout {
    private StoreContent storeContent;

    public EditText editText;
    public ConstraintLayout menuWindowLayout;
    public LinearLayout hashTagLayout,menuLinearLayout,articleLayout;
    public Button menuEditButton,addInfoEditButton;
    public TextView addInfoTextView;

    public StoreContentView(Context context, StoreContent storeContent) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);
        this.storeContent=storeContent;
        String inflaterService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(inflaterService);
        View view = layoutInflater.inflate(R.layout.store_content_view, this, false);
        addView(view);
        initView();

    }
    private void initView(){
        editText=findViewById(R.id.content_descriptionEditText);
        editText.setText(storeContent.description);

        hashTagLayout=findViewById(R.id.content_hashTag_LinearLayout);
        for(String hashTag : storeContent.hashTag){
            hashTagLayout.addView(new HashTagTextView(getContext(),hashTag));
        }

        menuLinearLayout =findViewById(R.id.content_menuLinearLayout);
        for(Menu menu : storeContent.menus){
            menuLinearLayout.addView(new MenuView(getContext(),menu));
        }
        articleLayout=findViewById(R.id.content_article_layout);
        for(Article article :  storeContent.articles){
            articleLayout.addView(new ArticleView(getContext(),article));
        }
        menuWindowLayout =findViewById(R.id.content_menuLayout);
        if(storeContent.menus.size()==0){
            menuWindowLayout.setVisibility(GONE);
        }
        menuEditButton=findViewById(R.id.content_edit_menu_button);
        menuEditButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 메뉴 변경창으로 이동
            }
        });
        addInfoEditButton=findViewById(R.id.content_info_edit_button);
        addInfoEditButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 부가정보 변경 창으로 이동
            }
        });

        addInfoTextView=findViewById(R.id.content_addInfo_TextView);
        String info="";
        for(String string : storeContent.additionalInfo){
            info+="   ▶  "+string+"\n";
        }
        addInfoTextView.setText(info);

    }
    public void setEditable(EditText editText,boolean b){
        editText.setClickable(b);
        editText.setCursorVisible(b);
        editText.setFocusable(b);
        editText.setFocusableInTouchMode(b);
    }
}
class HashTagTextView extends android.support.v7.widget.AppCompatTextView{
        public String text;

    public HashTagTextView(Context context, String text) {
        super(context);
        this.text=text;
        Drawable d = getResources().getDrawable(R.drawable.round_fore_ground);
        setBackgroundDrawable(d);
        setPadding(5,5,5,5);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)getLayoutParams();
        params.setMargins(10, 0, 10, 0); //substitute parameters for left, top, right, bottom
        setLayoutParams(params);
        setText(text);
    }
}