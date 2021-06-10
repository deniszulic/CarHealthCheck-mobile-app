package com.example.chc;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link rj_prijave_admin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class rj_prijave_admin extends Fragment {

    private Button odjava;
    private static final String TAG="postavljeno";
    private RecyclerView recyclerView;
    private List<ListItem> listItems;
    private RecyclerView.Adapter adapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public rj_prijave_admin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment rj_prijave_admin.
     */
    // TODO: Rename and change types and number of parameters
    public static rj_prijave_admin newInstance(String param1, String param2) {
        rj_prijave_admin fragment = new rj_prijave_admin();
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
        View view=inflater.inflate(R.layout.fragment_rj_prijave_admin, container, false);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        odjava=(Button) view.findViewById(R.id.odjava_rj);
        odjava.setOnClickListener((v)->{
            FirebaseAuth.getInstance().signOut();
            Intent i=new Intent(getActivity(),MainActivity.class);
            startActivity(i);
        });
        db.collection("noviobrazac2").orderBy("posted_at")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }
                        listItems=new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("posted_at") != null && doc.get("status").equals("rijeseno")) {

                                ListItem listItem=new ListItem(doc.getString("adresa"), doc.getString("auto"),
                                        doc.getString("brtel"),doc.getString("brtel2"),doc.getString("datum"),
                                        doc.getString("email"),doc.getString("grad"),doc.getString("ime"),
                                        doc.getString("number"),doc.getLong("posted_at").toString(),doc.getString("prezime"),
                                        doc.getString("radiona"),doc.getString("status"),doc.getString("tekst"),
                                        doc.getString("vrijeme"),doc.getString("year"),doc.getString("zip"),doc.getString("url"), doc.getString("prihvacennovitermin"), doc.getId());
                                listItems.add(listItem);
                            }
                        }
                        recyclerView = (RecyclerView) view.findViewById(R.id.rj_prijave_admin);
                        recyclerView. setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        adapter = new MyAdapter(listItems, getContext());
                        recyclerView.setAdapter(adapter);
                    }
                });
        return view;
    }
}