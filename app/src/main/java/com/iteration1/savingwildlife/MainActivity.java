package com.iteration1.savingwildlife;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.iteration1.savingwildlife.entities.Beach;
import com.iteration1.savingwildlife.utils.UIUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import timber.log.Timber;


public class MainActivity extends AppCompatActivity {

    private Button visualization;
    private Button explore;
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
        hint();
        visul();
        statistic();
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
        title = (TextView) findViewById(R.id.textView1);
        visualization = (Button) findViewById(R.id.visualization);
        explore = (Button) findViewById(R.id.explore);
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
        glow();
    }

    private void visul() {
        visualization.setOnClickListener(new View.OnClickListener() {
            public void onClick(View V) {
                Toast.makeText(MainActivity.this, "map is loading!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Visualization.class);
                startActivity(intent);
            }
        });
    }

    private void statistic() {
        explore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View V) {
                Intent intent = new Intent(MainActivity.this, Statistics.class);
                startActivity(intent);
            }
        });
    }

    private void glow() {
        title.setShadowLayer(50, 0, 0, Color.CYAN);
    }

    private void hint() {
        Snackbar.make(visualization, "Click pictures to know more", Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                })
                .setActionTextColor(getResources().getColor(R.color.theme_color)).show();
    }


    // Simulate the recomsys, but now just sort randomly instead of real algorithm
    private void applyRecommendSystem(){

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
//        beachList = (ArrayList<Beach>) bundle.getSerializable("beachlist");
//        Log.d("one beach", beachList.get(3).getName());

        // Shuffle the elements in drawable id list
        Collections.shuffle(beachList);

        for (int i = 0; i < ibList.size(); i++) {
            ImageView iv = ibList.get(i);

             //        This block is for taking picts from cloud
            StorageReference imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(beachList.get(i).getBanner());
            GlideApp.with(getApplicationContext())
                    .load(imageRef)
                    .apply((new RequestOptions().placeholder(R.drawable.common_full_open_on_phone).error(R.drawable.common_full_open_on_phone)))
                    .into(iv);
            iv.setTag(beachList.get(i).getName());

            iv.setOnClickListener(new View.OnClickListener() {
                //@Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, InfoPage.class);
                    String ns = (String) v.getTag();
                    Beach selected;
                    for (Beach b : beachList) {
                        if (b.getName().toUpperCase().replaceAll(" ", "")
                                .contains(ns.toUpperCase().trim().replaceAll(" ", ""))) {
                            selected = b;
                            // intent cannot be used to parse integer, so use bundle to pack the params
                            Bundle bundle = new Bundle();
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
                    Log.d("Added a beach", b.getBanner());
                }
                    applyRecommendSystem();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getDetails());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}

