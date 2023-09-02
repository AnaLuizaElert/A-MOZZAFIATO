package com.amozzafiato.pages.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amozzafiato.pages.profile.ProfileContact;
import com.amozzafiato.R;
import com.amozzafiato.pages.profile.ProfileData;
import com.amozzafiato.pages.profile.ProfileFavorites;
import com.amozzafiato.pages.profile.ProfileNegotiate;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView userState, userName;
    private CircleImageView profile;
    private String mParam1;
    private String mParam2;

    public Profile() {
        // Required empty public constructor
    }

    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Configurar o clique do botão no CardView
        CardView linkContact = view.findViewById(R.id.link_contact_profile);
        CardView linkData = view.findViewById(R.id.link_data_profile);
        CardView linkNegotiate = view.findViewById(R.id.link_data_negotiate);
        CardView linkFavorites = view.findViewById(R.id.link_data_favorites);

        userState = view.findViewById(R.id.profile_state_user);
        userName = view.findViewById(R.id.profile_name_user);
        profile = view.findViewById(R.id.profile_image);


        linkContact.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), ProfileContact.class);
                startActivity(intent);
        });

        linkData.setOnClickListener(v ->  {
                Intent intent = new Intent(getActivity(), ProfileData.class);
                startActivity(intent);
        });

        linkNegotiate.setOnClickListener(v ->  {
            Intent intent = new Intent(getActivity(), ProfileNegotiate.class);
            startActivity(intent);
        });

        linkFavorites.setOnClickListener(v ->  {
            Intent intent = new Intent(getActivity(), ProfileFavorites.class);
            startActivity(intent);
        });

        /*Colocar dados do usuário*/
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("TbUser").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        userName.setText(documentSnapshot.getString("name"));
                        userState.setText(documentSnapshot.getString("state"));
                        Glide.with(this)
                                .load(documentSnapshot.getString("image"))
                                .into(profile);
                    }
                }
        );

        return view;
    }
}
