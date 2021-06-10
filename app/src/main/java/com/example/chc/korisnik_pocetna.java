package com.example.chc;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link korisnik_pocetna#newInstance} factory method to
 * create an instance of this fragment.
 */
public class korisnik_pocetna extends Fragment {
    private Button odjava,img,uploadimg;
    private ImageButton upload;
    private FirebaseAuth mAuth;
    private ImageView mimageView;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private ProgressBar progressBar;
    private EditText ime,prezime,brtel,brtel2,prebivaliste,adresa,zip,kilometri,godiste,marka,vrijeme;
    private RadioButton pula,zagreb;
    private CalendarView dolazak;
    private RadioGroup selected;
    private static final int PICK_IMAGE_REQUEST=1;
    private StorageTask mUploadTask;
    private RadioButton radioButton;
    private CalendarView kalendar;
    private String curDate="";
    private TextInputLayout opis;
    private Long tsLong;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public korisnik_pocetna() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment korisnik_pocetna.
     */
    // TODO: Rename and change types and number of parameters
    public static korisnik_pocetna newInstance(String param1, String param2) {
        korisnik_pocetna fragment = new korisnik_pocetna();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_korisnik_pocetna, container, false);
        odjava=(Button) view.findViewById(R.id.odjavi);
        img=(Button) view.findViewById(R.id.img);
        mimageView=(ImageView) view.findViewById(R.id.mimageView);
        progressBar=(ProgressBar) view.findViewById(R.id.progressBar);
        uploadimg=(Button) view.findViewById(R.id.uploadimg);
        ime=(EditText) view.findViewById(R.id.ime_korisnikpocetna);
        prezime=(EditText) view.findViewById(R.id.prezime_korisnikpocetna);
        brtel=(EditText) view.findViewById(R.id.brtel_korisnikpocetna);
        brtel2=(EditText) view.findViewById(R.id.brtel2_korisnikpocetna);
        prebivaliste=(EditText) view.findViewById(R.id.grad_korisnikpocetna);
        adresa=(EditText) view.findViewById(R.id.adresa_korisnikpocetna);
        zip=(EditText) view.findViewById(R.id.zip_korisnikpocetna);
        kilometri=(EditText) view.findViewById(R.id.kilometri_korisnikpocetna);
        godiste=(EditText) view.findViewById(R.id.godiste_korisnikpocetna);
        marka=(EditText) view.findViewById(R.id.marka_korisnikpocetna);
        opis=(TextInputLayout) view.findViewById(R.id.opis_korisnikpocetna);
        vrijeme=(EditText) view.findViewById(R.id.vrijeme_korisnikpocetna);
        selected=(RadioGroup) view.findViewById(R.id.selected_korisnikpocetna);
        kalendar=(CalendarView) view.findViewById(R.id.dolazak_korisnikpocetna);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        odjava.setOnClickListener((v)->{
            FirebaseAuth.getInstance().signOut();
            Intent i=new Intent(getActivity(),MainActivity.class);
            startActivity(i);
        });
        img.setOnClickListener((v)->{
            openFileChooser();
        });
        kalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                String  Day = String.valueOf(dayOfMonth);
                String  Year = String.valueOf(year);
                String  Month = String.valueOf(month);
                curDate = Year+"-"+Month+"-"+Day;
            }
        });
        uploadimg.setOnClickListener((v)->{
            uploadFile();
        });

        FirebaseStorage storage = FirebaseStorage.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mStorageRef=FirebaseStorage.getInstance().getReference(user.getEmail());
        return view;
    }
    private void openFileChooser(){
        Intent i=new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, PICK_IMAGE_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode== Activity.RESULT_OK && data!=null && data.getData()!=null){
            mImageUri=data.getData();
            Picasso.get().load(mImageUri).into(mimageView);
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR=getActivity().getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadFile(){
        if(mImageUri!=null){
            StorageReference filereference=mStorageRef.child(System.currentTimeMillis()+"."+getFileExtension(mImageUri));
            UploadTask uploadTask=filereference.putFile(mImageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return filereference.getDownloadUrl();
                }
            }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(0);
                        }
                    },5000);
                    Toast.makeText(getActivity(),"Podaci poslani!", Toast.LENGTH_SHORT).show();
                }
            })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                                Upload upload = new Upload(downloadUri.toString());
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                Map<String, Object> data = new HashMap<>();
                                data.put("adresa",adresa.getText().toString());
                                data.put("auto",marka.getText().toString());
                                //if(brtel.getText().toString()!=null){
                                data.put("brtel",brtel.getText().toString());//}
                                //if(brtel2.getText().toString()!=null){
                                data.put("brtel2",brtel2.getText().toString());//}
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                data.put("email",user.getEmail());
                                data.put("grad",prebivaliste.getText().toString());
                                data.put("ime",ime.getText().toString());
                                data.put("number",kilometri.getText().toString());
                                data.put("prezime",prezime.getText().toString());
                                data.put("status","nerješeno");
                                data.put("tekst",opis.getEditText().getText().toString());
                                data.put("vrijeme",vrijeme.getText().toString());
                                data.put("year",godiste.getText().toString());
                                data.put("zip",zip.getText().toString());
                                Date date = new Date();
                                long timestamp = date.getTime();
                                data.put("posted_at",timestamp);
                                data.put("url",upload.getmImageUrl());
                                int radioid=selected.getCheckedRadioButtonId();
                                radioButton = getActivity().findViewById(radioid);
                                data.put("radiona",radioButton.getText().toString());
                                data.put("datum",curDate);

                                db.collection("noviobrazac2")
                                        .add(data)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Log.d("postavljeno", "DocumentSnapshot written with ID: " + documentReference.getId());
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("postavljeno", "Error adding document", e);
                                            }
                                        });
                            } else {
                                // Handle failures
                                // ...
                                Toast.makeText(getActivity(), "Podaci nisu poslani: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    progressBar.setProgress((int) progress);
                    Log.d("progress", "Upload is " + progress + "% done");
                }
            }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d("progress", "Upload is paused");
                }
            });
        }
        if(mImageUri==null){
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Map<String, Object> data = new HashMap<>();
            data.put("adresa",adresa.getText().toString());
            data.put("auto",marka.getText().toString());
            data.put("brtel",brtel.getText().toString());
            data.put("brtel2",brtel2.getText().toString());
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            data.put("email",user.getEmail());
            data.put("grad",prebivaliste.getText().toString());
            data.put("ime",ime.getText().toString());
            data.put("number",kilometri.getText().toString());
            data.put("prezime",prezime.getText().toString());
            data.put("status","nerješeno");
            data.put("tekst",opis.getEditText().getText().toString());
            data.put("vrijeme",vrijeme.getText().toString());
            data.put("year",godiste.getText().toString());
            data.put("zip",zip.getText().toString());
            Date date = new Date();
            long timestamp = date.getTime();
            data.put("posted_at",timestamp);
            int radioid=selected.getCheckedRadioButtonId();
            radioButton = getActivity().findViewById(radioid);
            data.put("radiona",radioButton.getText().toString());
            data.put("datum",curDate);
            db.collection("noviobrazac2")
                    .add(data)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("postavljeno", "DocumentSnapshot written with ID: " + documentReference.getId());
                            Toast.makeText(getActivity(),"Podaci poslani!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("postavljeno", "Error adding document", e);
                            Toast.makeText(getActivity(),"Podaci nisu poslani!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else{
            System.out.println("Greška");
        }
    }
}