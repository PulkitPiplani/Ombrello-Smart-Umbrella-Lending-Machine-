package com.iiitd.ombrello;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import static android.Manifest.permission.CAMERA;

public class ScannerActivity extends AppCompatActivity {

    public static final int RequestPermission = 7;
    private CodeScannerView codeScannerView;
    private CodeScanner codeScanner;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        type = getIntent().getStringExtra("type");


        codeScannerView = findViewById(R.id.scanner_view);
        codeScanner = new CodeScanner(this, codeScannerView);

        if(!CheckPermission()){
            RequestPermissios();
        }else{
            codeScanner.startPreview();
            codeScanner.setDecodeCallback(new DecodeCallback() {
                @Override
                public void onDecoded(@NonNull final Result result) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            Log.d("result", result.getText());
                            Intent intent = new Intent(ScannerActivity.this, ValueActivity.class);
                            intent.putExtra("values", result.getText());
                            intent.putExtra("type", type);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            });
        }
    }

    private void RequestPermissios(){
        ActivityCompat.requestPermissions(ScannerActivity.this, new String[]{
                CAMERA
        }, RequestPermission);
    }

    public boolean CheckPermission(){
        int CameraPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(),CAMERA);
        return CameraPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }
}
