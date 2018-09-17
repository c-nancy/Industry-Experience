package com.iteration1.savingwildlife;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.HashMap;

public class FishImageFragment extends Fragment {
    private View fView;
    private Banner banner;
    private ArrayList<String> names;
    private ArrayList<String> counts;
    private ArrayList<String> images;
    private ArrayList<String> details;
    private View line;
    private TextView dt;
    private WebView wv;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        fView = inflater.inflate(R.layout.fish_image_fragment, container, false);
        banner = fView.findViewById(R.id.banner);
        names = new ArrayList<>();
        counts = new ArrayList<>();
        images = new ArrayList<>();
        details = new ArrayList<>();
        line = fView.findViewById(R.id.line);
        dt = fView.findViewById(R.id.detail);
        line.setVisibility(View.INVISIBLE);
        dt.setVisibility(View.INVISIBLE);
        wv = fView.findViewById(R.id.fishtxt);
        wv.setVisibility(View.INVISIBLE);
        connectDatabase();
        return fView;
    }

    private void connectDatabase() {
        // Get the reference of firebase instance
        DatabaseReference mReference = FirebaseDatabase.getInstance().getReference("top_fishes");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.d("inside", "vrcgesx");
                    HashMap<String, String> map = (HashMap<String, String>) child.getValue();
                    String a = (String) map.get("scientificName");
                    String b = (String) map.get("image");
                    String c = (String) map.get("count");
                    String d = (String) map.get("detail");
                    Log.d("value", child.getValue().toString());

                    names.add(a);
                    images.add(b);
                    counts.add(c);
                    details.add(d);
                }
                banner.setBannerStyle(Banner.CIRCLE_INDICATOR_TITLE);
                banner.setIndicatorGravity(Banner.RIGHT);
                banner.setBannerTitle(names.toArray(new String[0]));
                banner.isAutoPlay(true);
                banner.setDelayTime(5000);
                banner.setImages(images.toArray(new String[0]), new Banner.OnLoadImageListener() {
                    @Override
                    public void OnLoadImage(ImageView view, Object url) {
                        StorageReference r = FirebaseStorage.getInstance().getReferenceFromUrl(url.toString());
                        Log.d("image", r.toString());
                        GlideApp.with(getActivity().getApplicationContext())
                                .load(r)
                                .placeholder(R.drawable.common_full_open_on_phone)
                                .error(R.drawable.common_full_open_on_phone)
                                .into(view);
                    }
                });
                banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {
                    @Override
                    public void OnBannerClick(View view, int position) {
                        line.setVisibility(View.INVISIBLE);
                        dt.setVisibility(View.INVISIBLE);
//                        wv.setVisibility(View.INVISIBLE);
//                        Toast.makeText(getContext(), names.get(position - 1) + ", "
//                                        + counts.get(position - 1) + " has been found in the past years",
//                                Toast.LENGTH_SHORT).show();
                        line.setVisibility(View.VISIBLE);
                        dt.setVisibility(View.VISIBLE);
                        StringBuilder sb = new StringBuilder(names.get(position - 1) + ", "
                                + counts.get(position - 1) + " has been found in the past years");
                        sb.append("\n\n");
                        sb.append(details.get(position - 1));
//                        StringBuilder sb = new StringBuilder("<html><body style='text-align:justify;' bgcolor=\"#F3F7F7\">" + names.get(position - 1) + ", "
//                                + counts.get(position - 1) + " has been found in the past years");
//                        sb.append("\n\n");
//                        sb.append(details.get(position - 1) + "</body></html>");
//                        wv.loadData(sb.toString(), "text/html", "UTF-8");
                        dt.setText(sb);
                        dt.setVisibility(View.VISIBLE);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getDetails());
            }
        });


    }
}
