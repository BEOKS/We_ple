package com.example.beoks.gameis.weple.Activity.StoreView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.beoks.gameis.weple.DataClass.Store.Article;
import com.example.beoks.gameis.weple.R;

//TODO for gowls4023
public class ArticleView extends LinearLayout implements View.OnClickListener{
    public Article article;
    TextView article_title;
    Button article_button_edit;
    TextView article_content;

    public ArticleView(Context context, Article article) {
        super(context);
        initView();
        setContent(article.description);
        setTitle(article.title);
    }

    public ArticleView(Context context, Article article, AttributeSet attrs) {
        super(context, attrs);
        initView();
        //getAttrs(attrs);
    }

    public ArticleView(Context context, Article article, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        initView();
        //getAttrs(attrs, defStyle);
    }

    private void initView() {
        String inflaterService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(inflaterService);
        View view = layoutInflater.inflate(R.layout.article_view, this, false);
        addView(view);

        article_title = findViewById(R.id.article_title);
        article_button_edit = findViewById(R.id.article_button_edit);
        article_content = findViewById(R.id.article_content);

        article_button_edit.setOnClickListener(this);
    }

    public void setTitle(String txt){
        article_title.setText(txt);
    }

    public void setContent(String txt){
        article_title.setText(txt);
    }

    public void onClick(View v){

    }
}