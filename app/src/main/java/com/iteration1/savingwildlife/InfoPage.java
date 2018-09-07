package com.iteration1.savingwildlife;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.iteration1.savingwildlife.entities.Beach;

public class InfoPage extends AppCompatActivity {

    private WebView txt;
    private TextView toolbar_title;
    private TextView beachtitle;
    private ImageView banner;
    private FloatingActionButton make_report;
    private FloatingActionButton direction;
    private FloatingActionsMenu thismenu;
    private Toolbar toolbar;
    private Beach selected;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_page);
        initUI();
    }

    public void initUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txt = (WebView) findViewById(R.id.beachtxt);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        beachtitle = (TextView) findViewById(R.id.beachtitle);
        banner = (ImageView) findViewById(R.id.banner);
        thismenu = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        make_report = (FloatingActionButton) findViewById(R.id.report_button);
        direction = (FloatingActionButton) findViewById(R.id.direction_button);

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
        selected = (Beach) bundle.getSerializable("beach");
        StorageReference imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(selected.getBanner());
        GlideApp.with(getApplicationContext())
                .load(imageRef)
                .apply((new RequestOptions().placeholder(R.drawable.common_full_open_on_phone).error(R.drawable.common_full_open_on_phone)))
                .into(banner);
        toolbar_title.setText(selected.getName());
        String putText = "<html><body><p align=\"justify\">";
        putText += selected.getDescription();
        putText += "</p></body></html>";
        txt.loadData(putText, "text/html", "utf-8");
        txt.getSettings().setTextZoom(100);
        txt.setBackgroundColor(getColor(R.color.bg_color));
        StringBuilder sb = new StringBuilder("Learn about ");
        sb.append(selected.getName());
        beachtitle.setText(sb);

        make_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(InfoPage.this, MakeReport.class);
                intent.putExtra("selected", selected);
                startActivity(intent);
            }
        });

        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "http://maps.google.com/maps?daddr=" + selected.getLatitude() + "," + selected.getLongitude() + " (" + selected.getName() + ")";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });

    }

    // When user click the back button of their own phone
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
