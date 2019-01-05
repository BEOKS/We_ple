package com.example.beoks.gameis.weple.Activity.CommonActivity;

import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beoks.gameis.weple.DataClass.GlobalData;
import com.example.beoks.gameis.weple.DataClass.Store.Store;
import com.example.beoks.gameis.weple.R;

public class StoreInfoActivity extends AppCompatActivity {

    public Store store;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private Button backButton,likeButton,allEditButton,cateEditButton;
    private ImageView imageView;
    private TextView likeTextView,categoryTextView,nameTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_info);
        getStoreData();
        initAppBar();


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }
    private void getStoreData(){
        if(StoreData.store==null){
            Toast.makeText(getApplicationContext(),"가게 정보 연결 실패",Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            this.store=StoreData.store;
        }
    }
    private Boolean likeButtonClicked=false,isEditMode=false;
    private void initAppBar(){
        backButton=findViewById(R.id.auth_backButton);
        likeButton=findViewById(R.id.storeInfo_likeButton);
        allEditButton=findViewById(R.id.storeInfo_all_editButton);
        cateEditButton=findViewById(R.id.storeInfo_cate_name_editButton);
        cateEditButton.setVisibility(View.GONE);

        imageView=findViewById(R.id.storeInfo_mainImage);

        likeTextView=findViewById(R.id.storeInfo_likeCount);
        categoryTextView=findViewById(R.id.storeInfo_categoryTextView);
        nameTextView=findViewById(R.id.storeInfo_nameTextView);
        nameTextView.setText(StoreData.store.name);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(likeButtonClicked){
                    likeButtonClicked=false;
                    store.likeCount--;
                    likeButton.setBackgroundResource(R.drawable.ic_add_black_24dp);
                    store.likeUser.remove(store.likeUser.indexOf(GlobalData.loginProfile.key));
                }
                else{
                    likeButtonClicked=true;
                    store.likeCount++;
                    likeButton.setBackgroundResource(R.drawable.ic_remove_black_24dp);
                    store.likeUser.add(GlobalData.loginProfile.key);
                    int d=0;
                }
                likeTextView.setText(""+store.likeCount);
            }
        });

        allEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(new ContextThemeWrapper(StoreInfoActivity.this, R.style.myDialog));
               if(isEditMode){
                   builder.setMessage("내용을 설정하시겠습니까?");
                   builder.setPositiveButton("예",
                           new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialogInterface, int i) {
                                   turnOFFeditMode();
                               }
                           });
                   builder.setNegativeButton("아니오",
                           new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialogInterface, int i) {

                               }
                           });
                   builder.show();
               }
               else{
                   builder.setTitle("편집모드로 전환하시겠습니까?");
                   builder.setMessage("악의적 편집은 어플 정책에 따라 제제 처리가 이루어 질 수 있습니다.");
                   builder.setPositiveButton("예",
                           new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialogInterface, int i) {
                                   turnOnEditMode();
                               }
                           });
                   builder.setNegativeButton("아니오",
                           new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialogInterface, int i) {

                               }
                           });
                   builder.show();
               }
            }
        });
    }
    public void turnOnEditMode(){
        isEditMode=true;
        allEditButton.setText("확인");
        cateEditButton.setVisibility(View.VISIBLE);
    }
    public void turnOFFeditMode(){
        isEditMode=false;
        allEditButton.setText("전체편집");
        cateEditButton.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_store_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_store_info, container, false);
            //TODO 각각의 뷰 생성후 추가하기
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }

    private long time= 0;
    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis()-time>=2000){
            time=System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"뒤로 버튼을 한번 더 누르면 종료합니다.",Toast.LENGTH_SHORT).show();
        }else if(System.currentTimeMillis()-time<2000){
            store.updateData(0);
            finish();
        }
    }
}
