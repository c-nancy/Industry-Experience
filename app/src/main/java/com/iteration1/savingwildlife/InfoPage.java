package com.iteration1.savingwildlife;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.StorageReference;
import com.iteration1.savingwildlife.entities.Beach;
import com.iteration1.savingwildlife.utils.UIUtils;

public class InfoPage extends AppCompatActivity {

    private StorageReference mStorageRef;
    private TextView txt;
    private TextView toolbar_title;
    private TextView beachtitle;
    private ImageView banner;
    private ImageView beachimg;
    private Button create_event;
    private Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_page);
        initUI();
    }

    public void initUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txt = (TextView) findViewById(R.id.beachtxt);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        beachtitle = (TextView) findViewById(R.id.beachtitle);
        banner = (ImageView) findViewById(R.id.banner);
        beachimg = (ImageView) findViewById(R.id.beachimg);
//        create_event = (Button) findViewById(R.id.create_events_button);

        toolbar.setTitle("");
        // Back to former page
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        // Use bundle to receive params
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        Beach selected = (Beach) bundle.getSerializable("beach");
        int bannerid = bundle.getInt("bannerid");
        Drawable bd = getResources().getDrawable(bannerid);
        // Adjust the size of this imageview
        ViewGroup.LayoutParams layoutParams = UIUtils.adjustImageSize(bd, banner);
        banner.setLayoutParams(layoutParams);
        banner.setImageDrawable(bd);
        assert selected != null;
        toolbar_title.setText(selected.getName());
        txt.setText(selected.getDescription());
        StringBuilder sb = new StringBuilder("Learn about ");
        sb.append(selected.getName());
        beachtitle.setText(sb);
//        create_event.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CreateEvent event = new CreateEvent();
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.create_event, event)
//                        .addToBackStack(null)
//                        .commit();
//            }
//        });

    }

    // When user click the back button of their own phone
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
