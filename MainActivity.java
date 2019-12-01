package com.iiitd.ombrello;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.Manifest.permission.CAMERA;
import static com.iiitd.ombrello.ScannerActivity.RequestPermission;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private FirebaseAuth firebaseAuth;
    private Button btnRent, btnReturn;
    private TextView textEmail, textPoints, textAddMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        btnRent = findViewById(R.id.btnRent);
        btnReturn = findViewById(R.id.btnReturn);
        textEmail = findViewById(R.id.textEmail);
        textPoints = findViewById(R.id.textPoints);
        textAddMoney = findViewById(R.id.textAddMoney);

        String email =  "";
        if(firebaseAuth.getCurrentUser().getEmail() != null)
            email = firebaseAuth.getCurrentUser().getEmail();
        else
            signOut();

        textEmail.setText("Hi "+email);
        myRef.child("Users").child(firebaseAuth.getCurrentUser().getUid()).child("points").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int curPoints = dataSnapshot.getValue(Integer.class);
                textPoints.setText(" "+String.valueOf(curPoints));
                if(curPoints<20) {
                    textAddMoney.setVisibility(View.VISIBLE);
                    btnRent.setEnabled(false);
                    btnReturn.setEnabled(false);
                }else {
                    textAddMoney.setVisibility(View.INVISIBLE);
                    btnRent.setEnabled(true);
                    btnReturn.setEnabled(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!CheckPermission()){
                    RequestPermissions();
                    if(CheckPermission()) {
                        Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
                        intent.putExtra("type","rent");
                        startActivity(intent);
                    }
                }else{
                    Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
                    intent.putExtra("type","rent");
                    startActivity(intent);
                }
            }
        });

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!CheckPermission()){
                    RequestPermissions();
                    if(CheckPermission()) {
                        Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
                        intent.putExtra("type","return");
                        startActivity(intent);
                    }
                }else{
                    Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
                    intent.putExtra("type","return");
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public void signOut(){
        firebaseAuth.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void RequestPermissions(){
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                CAMERA
        }, RequestPermission);
    }

    public boolean CheckPermission(){
        int CameraPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(),CAMERA);
        return CameraPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.addMoney:
                startActivity(new Intent(this,AddMoneyActivity.class));
                return true;
            case R.id.logout:
                signOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
