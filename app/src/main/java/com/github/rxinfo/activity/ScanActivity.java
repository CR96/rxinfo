package com.github.rxinfo.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.rxinfo.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class ScanActivity extends AppCompatActivity {

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
                    final Barcode barcode = barcodes.valueAt(0); // Get the first one
                    txtBarcode.post(new Runnable() {
                        @Override
                        public void run() {
                            String ndc = correctNdc(barcode.rawValue);
                            String text = String.format(
                                    getString(R.string.scanner_barcode_detected),
                                    ndc
                            );

                            txtBarcode.setText(text);
                            btnGo.setVisibility(View.VISIBLE);

                        }
                    });
                }
            }
        });

    }

    /**
     * Remove the first and last digit from the barcode, if needed.
     *
     * @param rawValue The value read from the barcode
     */
    private String correctNdc(String rawValue) {
        try {
            if (rawValue.length() > 10) { // Contains system character and check digit
                return rawValue.substring(1, rawValue.length() - 1)
                        .substring(0, rawValue.length() - 2); // Remove first and last digit
            } else {
                return rawValue;
            }
        } catch (IndexOutOfBoundsException e) {
            // Something's weird with this barcode, just leave it as is
            return rawValue;
        }
    }
}
