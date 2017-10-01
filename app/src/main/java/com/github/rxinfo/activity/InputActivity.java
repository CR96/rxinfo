package com.github.rxinfo.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rxinfo.R;

public class InputActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CAMERA = 1;
    private static final int REQUEST_BARCODE_TEXT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        setTitle(getString(R.string.input_activity_name));

        Button btnCamera = findViewById(R.id.btn_camera);
        final Button btnGo = findViewById(R.id.btn_go);
        final EditText txtNdc = findViewById(R.id.txt_ndc);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(
                        InputActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(InputActivity.this,
                            new String[]{Manifest.permission.CAMERA},
                            PERMISSION_REQUEST_CAMERA);
                } else {
                    Intent scanBarcodeIntent = new Intent(
                            InputActivity.this,
                            ScanActivity.class);

                    startActivityForResult(scanBarcodeIntent, REQUEST_BARCODE_TEXT);
                }
            }
        });

        txtNdc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() != 10) {
                    btnGo.setEnabled(false);
                } else {
                    btnGo.setEnabled(true);
                }
            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Return result to MainActivity
                Bundle data = new Bundle();
                data.putString("ndc", txtNdc.getText().toString());
                Intent intent = new Intent();
                intent.putExtras(data);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_BARCODE_TEXT && resultCode == Activity.RESULT_OK) {
            // Return result to MainActivity
            Intent intent = new Intent();
            intent.putExtras(data);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CAMERA: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent scanBarcodeIntent = new Intent(
                            InputActivity.this,
                            ScanActivity.class);
                    startActivityForResult(scanBarcodeIntent, REQUEST_BARCODE_TEXT);
                } else {
                    Toast.makeText(
                            InputActivity.this,
                            getString(R.string.permission_camera_denied),
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
