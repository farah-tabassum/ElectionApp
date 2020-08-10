package com.example.electionapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity {
    TextView fullName,email,voterId;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=findViewById(R.id.profileEmail);
        fullName=findViewById(R.id.profileName);
        voterId=findViewById(R.id.profileVoterId);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        userId=fAuth.getCurrentUser().getUid();

        DocumentReference documentReference=fStore.collection("electors").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                email.setText(documentSnapshot.getString("email"));
                fullName.setText(documentSnapshot.getString("fName"));
                voterId.setText(documentSnapshot.getString("voterId"));
            }
        });

    }

    public void logout(View view){
        //logout
        FirebaseAuth.getInstance().signOut();
        //sending user to login class
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}