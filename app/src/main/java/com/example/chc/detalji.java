package com.example.chc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class detalji extends AppCompatActivity {
    private EditText datum,vrijeme;
    private TextView adresa,auto,brtel,brtel2,email,grad,ime,broj,prezime,opis,godina,zip,postavljeno,id,novitermin_txtview;
    private EditText name_details,surname_details;
    private Button spremi;
    private ImageView img;
    private RadioButton pula,zagreb, odobreno,odbijeno,promjenatermina,rijeseno,prihvacennovitermin_ne,prihvacennovitermin_da,nerješeno_activity_detalji;
    private static final String TAG="postavljeno";
    private RadioGroup selected_activity_detalji,status_activity_detalji,novitermin_activity_detalji;
    private RadioButton radioButton,radioButton1, radioButton2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalji);
        datum=(EditText) findViewById(R.id.datum_detalji_admin); 
        vrijeme=(EditText) findViewById(R.id.vrijeme_detalji_admin);
        adresa=(TextView) findViewById(R.id.adresa_detalji_admin);
        auto=(TextView) findViewById(R.id.auto_detalji_admin);
        brtel=(TextView) findViewById(R.id.brtel_detalji_admin);
        brtel2=(TextView) findViewById(R.id.brtel2_detalji_admin);
        email=(TextView) findViewById(R.id.email_detalji_admin);
        grad=(TextView) findViewById(R.id.grad_detalji_admin);
        ime=(TextView) findViewById(R.id.ime_detalji_admin);
        broj=(TextView) findViewById(R.id.number_detalji_admin);
        prezime=(TextView) findViewById(R.id.prezime_detalji_admin);
        opis=(TextView) findViewById(R.id.tekst_detalji_admin);
        godina=(TextView) findViewById(R.id.year_detalji_admin);
        zip=(TextView) findViewById(R.id.zip_detalji_admin);
        postavljeno=(TextView) findViewById(R.id.posted_at_detalji_admin);
        id=(TextView) findViewById(R.id.id_detalji_admin);
        pula=(RadioButton) findViewById(R.id.pula_activity_detalji);
        zagreb=(RadioButton) findViewById(R.id.zagreb_activity_detalji);
        selected_activity_detalji=(RadioGroup) findViewById(R.id.selected_activity_detalji);
        odobreno=(RadioButton) findViewById(R.id.odobreno_activity_detalji);
        odbijeno=(RadioButton) findViewById(R.id.odbijeno_activity_detalji);
        promjenatermina=(RadioButton) findViewById(R.id.promjenatermina_activity_detalji);
        rijeseno=(RadioButton) findViewById(R.id.rijeseno_activity_detalji);
        status_activity_detalji=(RadioGroup) findViewById(R.id.status_activity_detalji);
        prihvacennovitermin_ne=(RadioButton) findViewById(R.id.prihvacennovitermin_ne);
        prihvacennovitermin_da=(RadioButton) findViewById(R.id.prihvacennovitermin_da);
        novitermin_txtview=(TextView) findViewById(R.id.novitermin_txtview);
        novitermin_activity_detalji=(RadioGroup) findViewById(R.id.novitermin_activity_detalji);
        nerješeno_activity_detalji=(RadioButton) findViewById(R.id.nerješeno_activity_detalji);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        /*ime=(TextView) findViewById(R.id.ime);
        prezime=(TextView) findViewById(R.id.prezime);
        name_details=(EditText) findViewById(R.id.name_details);
        surname_details=(EditText) findViewById(R.id.surname_details);*/
        spremi=(Button) findViewById(R.id.spremi);
        img=(ImageView) findViewById(R.id.imageView);
        /*String imee=getIntent().getStringExtra("name");
        String prezimee=getIntent().getStringExtra("surname");
        String kljuc=getIntent().getStringExtra("kljuc");
        ime.setText(imee);
        //prezime.setText(prezimee);
        name_details.setText(imee);
        surname_details.setText(prezimee);*/
        datum.setText(getIntent().getStringExtra("datum"));
        vrijeme.setText(getIntent().getStringExtra("vrijeme"));
        //status.setText(getIntent().getStringExtra("status"));
        //radiona.setText(getIntent().getStringExtra("radiona"));
        adresa.setText(getIntent().getStringExtra("adresa"));
        auto.setText(getIntent().getStringExtra("auto"));
        brtel.setText(getIntent().getStringExtra("brtel"));
        brtel2.setText(getIntent().getStringExtra("brtel2"));
        email.setText(getIntent().getStringExtra("email"));
        grad.setText(getIntent().getStringExtra("grad"));
        ime.setText(getIntent().getStringExtra("ime"));
        broj.setText(getIntent().getStringExtra("number"));
        prezime.setText(getIntent().getStringExtra("prezime"));
        opis.setText(getIntent().getStringExtra("tekst"));
        godina.setText(getIntent().getStringExtra("year"));
        zip.setText(getIntent().getStringExtra("zip"));
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(Long.parseLong(getIntent().getStringExtra("posted_at")));
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        postavljeno.setText(sdf.format(d));
        id.setText(getIntent().getStringExtra("kljuc"));
        Glide.with(this).load(getIntent().getStringExtra("url")).into(img);
        String radionabtn=getIntent().getStringExtra("radiona");
        if(radionabtn.equals("Pula")){
            pula.setChecked(true);
        }
        if(radionabtn.equals("Zagreb")){
            zagreb.setChecked(true);
        }
        String statusbtn=getIntent().getStringExtra("status");
        if(statusbtn.equals("odobreno")){
            odobreno.setChecked(true);
        }
        if(statusbtn.equals("odbijeno")){
            odbijeno.setChecked(true);
        }
        if(statusbtn.equals("promjenatermina")){
            promjenatermina.setChecked(true);
        }
        if(statusbtn.equals("rijeseno")){
            rijeseno.setChecked(true);
        }
        if(statusbtn.equals("nerješeno")){
            nerješeno_activity_detalji.setChecked(true);
        }
        String getprihvaceno=getIntent().getStringExtra("prihvacennovitermin");
        if(getprihvaceno==null){
            prihvacennovitermin_da.setVisibility(View.GONE);
            prihvacennovitermin_ne.setVisibility(View.GONE);
            novitermin_txtview.setVisibility(View.GONE);
        }
        if(getprihvaceno!=null /*&&(getprihvaceno.equals("da") || getprihvaceno.equals("ne"))*/){
            prihvacennovitermin_da.setEnabled(false);
            prihvacennovitermin_ne.setEnabled(false);
            prihvacennovitermin_da.setVisibility(View.VISIBLE);
            prihvacennovitermin_ne.setVisibility(View.VISIBLE);
            novitermin_txtview.setVisibility(View.VISIBLE);
            if(getprihvaceno.equals("da")){
                prihvacennovitermin_da.setChecked(true);
            }
            if(getprihvaceno.equals("ne")){
                prihvacennovitermin_ne.setChecked(true);
            }
        }
        spremi.setOnClickListener((v)->{
            int radioid=selected_activity_detalji.getCheckedRadioButtonId();
            radioButton = findViewById(radioid);
            int radioid1=status_activity_detalji.getCheckedRadioButtonId();
            radioButton1 = findViewById(radioid1);
            int radioid2=novitermin_activity_detalji.getCheckedRadioButtonId();
            radioButton2 = findViewById(radioid2);

            DocumentReference washingtonRef = db.collection("noviobrazac2").document(id.getText().toString());
            if(getIntent().getStringExtra("prihvacennovitermin")!=null) {
                washingtonRef
                        .update("datum", datum.getText().toString(), "vrijeme", vrijeme.getText().toString(), "status", radioButton1.getText().toString(), "radiona", radioButton.getText().toString(), "prihvacennovitermin", radioButton2.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully updated!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error updating document", e);
                            }
                        });
            }
            else{
                washingtonRef
                        .update("datum", datum.getText().toString(),"vrijeme",vrijeme.getText().toString(),"status",radioButton1.getText().toString(),"radiona",radioButton.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully updated!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error updating document", e);
                            }
                        });
            }
        });
    }
}