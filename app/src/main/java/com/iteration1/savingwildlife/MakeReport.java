package com.iteration1.savingwildlife;

<<<<<<< HEAD
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.iteration1.savingwildlife.entities.Beach;
import com.iteration1.savingwildlife.entities.Event;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class MakeReport extends AppCompatActivity {
    private Toolbar toolbar;
    private Spinner eventType;
    private TextView toolbar_title;
    private ImageView imageUploaded;
    private DatePicker eventDate;
    private Button btnSave;
    private Button btnUpload;
    private Beach option;
    private String beachname;
    private long startDate;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
=======
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.support.v7.widget.Toolbar;

public class MakeReport extends AppCompatActivity {
    private Toolbar toolbar;
>>>>>>> origin/master

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_page);
<<<<<<< HEAD
        covertToLongType();
        initUI();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveInfo();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

    }

    private void chooseImage() {
        Intent newIntent = new Intent();
        newIntent.setType("image/*");
        newIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(newIntent, "Select Picture"),PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageUploaded.setImageBitmap(bitmap);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

//    private void addArrayList(){
//        String type = eventType.getSelectedItem().toString();
//        Integer year = eventDate.getYear();
//        Integer month = eventDate.getMonth();
//        Integer Day = eventDate.getDayOfMonth();
//        String event_date = Day.toString() + "-" + month.toString() + "-" + year.toString();
//        String id = databaseReference.push().getKey();
//        Event event = new Event(type,event_date);
//        databaseReference.child(id).child("beach_name").setValue(beachname);
//        databaseReference.child(id).child("event_type").setValue(type);
//        databaseReference.child(id).child("event_date").setValue(event_date);
//    }

    private void saveInfo() {

        if(filePath != null && !eventType.getSelectedItem().toString().equals("select one"))
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference ref = storageReference.child("event images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(MakeReport.this, "the report has been uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(MakeReport.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
            String type = eventType.getSelectedItem().toString();
            Integer year = eventDate.getYear();
            Integer month = eventDate.getMonth();
            Integer Day = eventDate.getDayOfMonth();
            String event_date = Day.toString() + "-" + month.toString() + "-" + year.toString();
            String id = databaseReference.push().getKey();
            Event event = new Event(type,event_date, beachname, ref.toString());
            databaseReference.child(id).child("beach_name").setValue(beachname);
            databaseReference.child(id).child("event_type").setValue(type);
            databaseReference.child(id).child("event_date").setValue(event_date);
            databaseReference.child(id).child("reference").setValue(ref.toString());
        }
        else{
            Toast.makeText(MakeReport.this, "You miss type or image!", Toast.LENGTH_SHORT).show();
        }
    }


    private void initUI(){
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        imageUploaded = (ImageView) findViewById(R.id.imageView1);
        eventType = (Spinner) findViewById(R.id.spinner1);
        eventDate = (DatePicker) findViewById(R.id.datePicker1);
        eventDate.setMaxDate(new Date().getTime());
        eventDate.setMinDate(startDate);
        btnUpload = (Button) findViewById(R.id.uploadbtn);
        btnSave = (Button) findViewById(R.id.savebtn);
        databaseReference = FirebaseDatabase.getInstance().getReference("report");
        Intent intent1 = getIntent();
        Bundle bundle = intent1.getExtras();
        assert bundle != null;
        option = (Beach) bundle.getSerializable("selected");
        Log.d("selected inside", option.getName());
        assert option != null;
        beachname = option.getName();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        String titlePlus = "Report of ";
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        toolbar_title.setText(titlePlus + option.getName());
=======
        initUI();
    }

    private void initUI(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
>>>>>>> origin/master
        // Back to former page
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

<<<<<<< HEAD
    private void covertToLongType(){
        try {
            String dateString = "01/01/2018";
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = sdf.parse(dateString);

            startDate = date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

=======
>>>>>>> origin/master
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
