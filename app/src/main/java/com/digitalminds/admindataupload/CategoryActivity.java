package com.digitalminds.admindataupload;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class CategoryActivity extends AppCompatActivity {

    EditText titleCategoryEN;
    EditText titleCategoryAR;
    EditText titleCategoryKU;
    Button addBtn;
    ImageView addImage;

    private Uri imageUri;
    String imageurl = "";
    private StorageReference storageReference;

    //realtime database references...
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;

    ProgressDialog progressDialog;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        titleCategoryEN = findViewById(R.id.titleCatEN);
        titleCategoryAR = findViewById(R.id.titleCatAR);
        titleCategoryKU = findViewById(R.id.titleCatKU);
        addBtn = findViewById(R.id.addBtn);
        addImage = findViewById(R.id.categoryImage);

        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("Category");
        addImage.setOnClickListener(view -> openFileChooser());
        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        addBtn.setOnClickListener(v -> fileUpload());
    }

    //..................Methods for File Chooser.................
    public void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            addImage.setBackgroundResource(R.drawable.whitebackground);

            Glide.with(this)
                    .load(imageUri)
                    .into(addImage);
        }
    }
    //..............................................................................


    //.................Methods for File Upload to Firebase Storage..................
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    public void fileUpload() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        if (imageUri != null) {
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

            fileReference.putFile(imageUri)

                    .addOnSuccessListener(taskSnapshot -> taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageurl = uri.toString();
                            sendData();
                            //clear();
                        }
                    }))
                    .addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Toast.makeText(CategoryActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    })
                    .addOnProgressListener(taskSnapshot -> progressDialog.show());

        } else {
            Toast.makeText(this, "no Image", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }

    public void sendData() {

        String id = ref.push().getKey();

        String titleCatEN = titleCategoryEN.getText().toString();
        String titleCatAR = titleCategoryAR.getText().toString();
        String titleCatKU = titleCategoryKU.getText().toString();


        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("categoryId", id);
        hashMap.put("categoryNameEN", titleCatEN);
        hashMap.put("categoryNameAR", titleCatAR);
        hashMap.put("categoryNameKU", titleCatKU);
        hashMap.put("categoryIcon", imageurl);
        ref.child(id).setValue(hashMap);

        Toast.makeText(CategoryActivity.this, "successfully added category", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
        Intent i = new Intent(CategoryActivity.this, MainActivity.class);
        startActivity(i);
    }
}