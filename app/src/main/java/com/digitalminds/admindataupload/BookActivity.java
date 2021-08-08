package com.digitalminds.admindataupload;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.digitalminds.admindataupload.Model.Category;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

public class BookActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // for binding
    EditText title;
    ImageView bookImage;
    EditText authorName;
    EditText pdfUrl;
    EditText descriptionEN;
    EditText descriptionAR;
    EditText descriptionKU;
    ImageView adImage;
    Button submit;
    EditText language;
    EditText pagesNumber;


    String titlet;
    String authorNamet;
    String pdfUrlt;
    String pages;
    String lang;
    String uploadDate;

    String spinnerText;
    String text;


    private Uri imageUri;
    String imageurl = "";
    ProgressDialog progressDialog;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;
    private StorageReference storageReference;
    DatabaseReference refCat;

    ValueEventListener eventListener;
    ArrayAdapter<String> adapter;
    ArrayList<String> spinnerDataList;
    ArrayList<Category> categories;

    Category chosenCategory;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        //binding
        title = findViewById(R.id.title);
        authorName = findViewById(R.id.authorName);
        bookImage = findViewById(R.id.browse);
        pdfUrl = findViewById(R.id.pdfUrl);
        descriptionEN = findViewById(R.id.descriptionEn);
        descriptionAR = findViewById(R.id.descriptionAR);
        descriptionKU = findViewById(R.id.descriptionKU);
        pagesNumber = findViewById(R.id.pagesNumber);
        language = findViewById(R.id.language);
        Spinner spinner = (Spinner) findViewById(R.id.spinnerCategory);
        adImage = findViewById(R.id.bookImage);
        submit = findViewById(R.id.continueBtn);

        //firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        ref = firebaseDatabase.getReference("Books");
        refCat = firebaseDatabase.getReference("Category");

        //firebase storage (storing pictures)
        storageReference = FirebaseStorage.getInstance().getReference("uploads");


        //*******************************************For Spinner Initialization and Code Only*************************************************

        spinnerDataList = new ArrayList<>();
        categories = new ArrayList<>();
        adapter = new ArrayAdapter<String>(BookActivity.this, android.R.layout.simple_spinner_dropdown_item, spinnerDataList);
        spinner.setAdapter(adapter);
        retrieveData();


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Choose Month")) {
                    //no operation needed here.
                } else {
                    text = parent.getItemAtPosition(position).toString();
                    for(Category x:categories){
                        if(text.equals(x.getCategoryNameEN())){
                            chosenCategory = x;
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //no operation needed here
            }
        });


        //***************************************************___________*************************************************************


        //Image get ...
        adImage.setOnClickListener(view -> openFileChooser());

        submit.setOnClickListener(v -> fileUpload());


    }

    public void retrieveData() {
        eventListener = refCat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()) {
                    Category category = item.getValue(Category.class);
                    //displaying english as default language for admin app
                    spinnerDataList.add(category.getCategoryNameEN());
                    categories.add(category);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //no operation needed here
            }
        });
    }

    //******************************Overriding Methods For Spinner Data*****************************
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerText = spinnerDataList.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //no operation needed here
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

            adImage.setBackgroundResource(R.drawable.whitebackground);

            Glide.with(this)
                    .load(imageUri)
                    .into(adImage);
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
                        Toast.makeText(BookActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    })
                    .addOnProgressListener(taskSnapshot -> progressDialog.show());

        } else {
            Toast.makeText(this, "no Image", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }
    //......................................................................................

    public void sendData() {

        String id = ref.push().getKey();
        String categoryEN = chosenCategory.getCategoryNameEN();
        String categoryAR = chosenCategory.getCategoryNameAR();
        String categoryKU = chosenCategory.getCategoryNameKU();

        titlet = title.getText().toString();
        authorNamet = authorName.getText().toString();
        pdfUrlt = pdfUrl.getText().toString();
        pages = pagesNumber.getText().toString();
        lang = language.getText().toString();
        uploadDate = DateNTimeUtils.getTodayDate();


        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("bookId", id);
        hashMap.put("bookTitle", titlet);
        hashMap.put("bookCategoryNameEN", categoryEN);
        hashMap.put("bookCategoryNameAR", categoryAR);
        hashMap.put("bookCategoryNameKU", categoryKU);
        hashMap.put("bookAuthorName", authorNamet);
        hashMap.put("bookPdfUrl", pdfUrlt);
        hashMap.put("bookImagePath", imageurl);
        hashMap.put("bookDescriptionEN", descriptionEN.getText().toString());
        hashMap.put("bookDescriptionAR", descriptionAR.getText().toString());
        hashMap.put("bookDescriptionKU", descriptionKU.getText().toString());
        hashMap.put("bookShares", "0");
        hashMap.put("bookReaders", "0");
        hashMap.put("bookDownloads", "0");
        hashMap.put("bookRating", "0.0");
        hashMap.put("bookPagesNumber", pages);
        hashMap.put("bookLanguage", lang);
        hashMap.put("uploadDate", uploadDate);

        ref.child(id).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //Do what you need to do
                    Toast.makeText(BookActivity.this, "successfully added book", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Intent i = new Intent(BookActivity.this, MainActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(BookActivity.this, "failed to upload data please try again!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(BookActivity.this, "failed to upload data please try again!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });





    }
}