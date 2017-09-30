package com.github.rxinfo.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.rxinfo.R;

public class InputActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CAMERA = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        setTitle(getString(R.string.input_activity_name));

        Button btnCamera = findViewById(R.id.btnCamera);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(
                        InputActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(InputActivity.this,
                            new String[]{Manifest.permission.CAMERA},
                            PERMISSION_REQUEST_CAMERA);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }

                } else {
                    Toast.makeText(
                            InputActivity.this,
                            getString(R.string.permission_camery_denied),
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
