package com.github.rxinfo.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.rxinfo.R;
import com.github.rxinfo.util.NdcUtils;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class ScanActivity extends AppCompatActivity {

    private static String upc;
    private static String ndc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        setTitle(getString(R.string.scan_activity_name));

        final SurfaceView cameraPreview = findViewById(R.id.view_camera);
        final TextView txtBarcode = findViewById(R.id.txt_barcode);
        final Button btnGo = findViewById(R.id.btn_go);

        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        final CameraSource cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setAutoFocusEnabled(true)
                .build();

        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @SuppressLint("MissingPermission") // Permission accepted before activity launches
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                try {
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {

            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() > 0) {
                    Barcode barcode = barcodes.valueAt(0); // Get the first one

                    upc = barcode.rawValue;
                    ndc = NdcUtils.upcToNdc(upc);

                    txtBarcode.post(new Runnable() {
                        @Override
                        public void run() {
                            String text = String.format(
                                    getString(R.string.scanner_barcode_detected),
                                    upc
                            );

                            txtBarcode.setText(text);
                            btnGo.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Return result
                Bundle data = new Bundle();

                data.putString("upc", upc);
                data.putString("ndc", ndc);
                Intent intent = new Intent();
                intent.putExtras(data);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
