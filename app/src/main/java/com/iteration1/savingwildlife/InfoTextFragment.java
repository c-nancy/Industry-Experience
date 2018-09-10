package com.iteration1.savingwildlife;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.iteration1.savingwildlife.R;
import com.iteration1.savingwildlife.entities.Beach;

import java.util.Objects;


public class InfoTextFragment extends Fragment {
    private View parentView;
    private WebView txt;
    private Beach selected;
    private ImageView banner;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        parentView = inflater.inflate(R.layout.beach_text_fragment,container,false);
        Bundle bundle = this.getArguments();
        assert bundle != null;
        selected = (Beach) bundle.getSerializable("selected");
        banner = (ImageView) parentView.findViewById(R.id.banner);
        StorageReference imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(selected.getBanner());
        GlideApp.with(parentView.getContext())
                .load(imageRef)
                .apply((new RequestOptions().placeholder(R.drawable.common_full_open_on_phone).error(R.drawable.common_full_open_on_phone)))
                .into(banner);
        txt = (WebView) parentView.findViewById(R.id.beachtxt);
        String putText = "<html><body><p align=\"justify\">";
        putText += selected.getDescription();
        putText += "</p></body></html>";
        txt.loadData(putText, "text/html", "utf-8");
        txt.getSettings().setTextZoom(100);
        txt.setBackgroundColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.bg_color));
        return parentView;
    }
}
