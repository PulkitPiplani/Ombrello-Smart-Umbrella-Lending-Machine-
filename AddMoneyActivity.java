package com.iiitd.ombrello;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddMoneyActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private EditText editPoints;
    private Button btnAdd;
    private TextView textPoints;
    private int curPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        btnAdd = findViewById(R.id.btnAdd);
        editPoints = findViewById(R.id.editAmount);
        textPoints = findViewById(R.id.textPoints);

        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        myRef.child("Users").child(uid).child("points").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                curPoints = dataSnapshot.getValue(Integer.class);
                textPoints.setText(" "+String.valueOf(curPoints));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = editPoints.getText().toString();
                Log.d("amount",amount);
                if(!amount.equals("")){
                    myRef.child("Users").child(uid).child("points").setValue(curPoints+Integer.parseInt(amount));
                    Toast.makeText(AddMoneyActivity.this, "Added money.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
