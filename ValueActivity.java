package com.iiitd.ombrello;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ValueActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private TextView txtValue;
    private int id = -1;
    private String curUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value);

        String values = getIntent().getStringExtra("values");
        final String type = getIntent().getStringExtra("type");

        txtValue = findViewById(R.id.textView);

        if(values != null)
            id = Integer.parseInt(values);

        myRef.child("Users").child(curUser).child("rent").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int rent = dataSnapshot.getValue(Integer.class);
                if(rent == 0){
                    if(type.equals("rent")) {
                        final String[] res = {""};
                        myRef.child("stand").child(String.valueOf(id)).child(type).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                res[0] = dataSnapshot.getValue(String.class);
                                if (res[0].equals("True")) {
                                    txtValue.setText(R.string.please_try_again_after_some_time);
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            finish();
                                        }
                                    }, 2000);
                                } else {
                                    myRef.child("stand").child(String.valueOf(id)).child(type).setValue("True");
                                    txtValue.setTextColor(Color.parseColor("#0000ff"));
                                    txtValue.setText(R.string.stand_is_open_have_a_great_day);
                                    myRef.child("stand").child(String.valueOf(id)).child("who").setValue(curUser);
                                    myRef.child("Users").child(curUser).child("rent").setValue(1);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }else{
                        txtValue.setText(R.string.nothing_to_return);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        },5000);
                    }
                }else{
                    if(type.equals("return")) {
                        final String[] res = {""};
                        myRef.child("stand").child(String.valueOf(id)).child(type).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                res[0] = dataSnapshot.getValue(String.class);
                                if (res[0].equals("True")) {
                                    txtValue.setText(R.string.please_try_again_after_some_time);
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            finish();
                                        }
                                    }, 2000);
                                } else {
                                    myRef.child("stand").child(String.valueOf(id)).child(type).setValue("True");
                                    txtValue.setTextColor(Color.parseColor("#0000ff"));
                                    txtValue.setText(R.string.stand_is_open_have_a_great_day);
                                    myRef.child("stand").child(String.valueOf(id)).child("who").setValue(curUser);
//                                    myRef.child("Users").child(curUser).child("rent").setValue(1);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }else{
                        txtValue.setText(R.string.return_previous);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        },5000);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
