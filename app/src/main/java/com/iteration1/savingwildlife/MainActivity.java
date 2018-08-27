package com.iteration1.savingwildlife;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iteration1.savingwildlife.entities.Beach;
import com.iteration1.savingwildlife.utils.UIUtils;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends AppCompatActivity {

    private Button visualization;
    private Button recommendation;
    private TextView hint;
    private TextView title;


    private ArrayList<Beach> beachList;
    private ArrayList<ImageView> ibList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        beachList = new ArrayList<>();
        ibList = new ArrayList<>();
        connectDatabase();
        initUI();
//        blink();
        visul();
        recom();
    }


    private void initUI() {
        ImageView ib1 = (ImageView) findViewById(R.id.image1);
        ImageView ib2 = (ImageView) findViewById(R.id.image2);
        ImageView ib3 = (ImageView) findViewById(R.id.image3);
        ImageView ib4 = (ImageView) findViewById(R.id.image4);
        ImageView ib5 = (ImageView) findViewById(R.id.image5);
        ImageView ib6 = (ImageView) findViewById(R.id.image6);
        ImageView ib7 = (ImageView) findViewById(R.id.image7);
        ImageView ib8 = (ImageView) findViewById(R.id.image8);
        ImageView ib9 = (ImageView) findViewById(R.id.image9);
        ImageView ib10 = (ImageView) findViewById(R.id.image10);
//        hint = (TextView) findViewById(R.id.textView3);
        title = (TextView) findViewById(R.id.textView1);
        visualization = (Button)findViewById(R.id.visualization);
        recommendation = (Button)findViewById(R.id.recommendation);
        ibList.add(ib1);
        ibList.add(ib2);
        ibList.add(ib3);
        ibList.add(ib4);
        ibList.add(ib5);
        ibList.add(ib6);
        ibList.add(ib7);
        ibList.add(ib8);
        ibList.add(ib9);
        ibList.add(ib10);
        applyRecommendSystem();
        glow();
        Toast.makeText(getApplicationContext(), "Click the pictures to know more",
                Toast.LENGTH_LONG).show();
    }

    private void visul(){
        visualization.setOnClickListener(new View.OnClickListener() {
            public void onClick(View V) {
               Intent intent = new Intent(MainActivity.this,Visualization.class);
               startActivity(intent);
            }
        });
    }

    private void recom(){
        recommendation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View V) {
               Intent intent = new Intent(MainActivity.this,Recommendation.class);
               startActivity(intent);
            }
        });
    }

    private  void glow(){
        title.setShadowLayer(50, 0, 0, Color.YELLOW);
    }

//    private void blink() {
//        Animation anim = new AlphaAnimation(0.0f, 1.0f);
//        anim.setDuration(2000); //You can manage the blinking time with this parameter
//        anim.setStartOffset(20);
//        anim.setRepeatMode(Animation.REVERSE);
//        anim.setRepeatCount(Animation.INFINITE);
//        hint.startAnimation(anim);
//    }



    // Simulate the recommendation sys, but now just sort randomly instead of real algorithm
    private void applyRecommendSystem() {
        // A list of drawable ids
        ArrayList<Integer> s = new ArrayList<>();
        s.add(R.drawable.brighton);
        s.add(R.drawable.stkilda);
        s.add(R.drawable.sorrento);
        s.add(R.drawable.williamstown);
        s.add(R.drawable.mordialloc);
        s.add(R.drawable.altona);
        s.add(R.drawable.elwood);
        s.add(R.drawable.halfmoonbay);
        s.add(R.drawable.hampton);
        s.add(R.drawable.mothers);
        // Shuffle the elements in drawable id list
        Collections.shuffle(s);

        for (int i = 0; i < ibList.size(); i++) {
            Drawable r = getResources().getDrawable(s.get(i));
            ImageView iv = ibList.get(i);
            // Adjust the size of this imageview
            ViewGroup.LayoutParams layoutParams = UIUtils.adjustImageSize(r, iv);
            iv.setLayoutParams(layoutParams);
            // Put the drawable into this imageview
            iv.setImageDrawable(r);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            // Set the id of selected image to this view as a tag, to parse to infopage when clicked
            iv.setTag(s.get(i));
            iv.setOnClickListener(new View.OnClickListener() {
                //@Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, InfoPage.class);
                    String ns = getResources().getResourceEntryName((int) v.getTag());
                    Beach selected;
                    for (Beach b : beachList) {
                        if (b.getName().toUpperCase().replaceAll(" ", "").contains(ns.toUpperCase().trim())) {
                            selected = b;

                            // intent cannot be used to parse integer, so use bundle to pack the params
                            Bundle bundle = new Bundle();
                            bundle.putInt("bannerid", (int) v.getTag());
                            bundle.putSerializable("beach", selected);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }
                }
            });
        }
    }

    // Start the connection with firebase realtime database
    private void connectDatabase() {
        // Get the reference of firebase instance
        DatabaseReference mReference = FirebaseDatabase.getInstance().getReference("beaches");

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Beach b = child.getValue(Beach.class);
                    beachList.add(b);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getDetails());
            }
        });
    }

}

