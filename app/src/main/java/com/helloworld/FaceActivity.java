package com.helloworld;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionPoint;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceContour;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark;

import java.util.List;

public class FaceActivity extends Activity {
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_face);

        mContext = this;

        final RelativeLayout RelativeLayout = findViewById(R.id.RelativeLayout_main);

        FirebaseVisionFaceDetectorOptions options =
                new FirebaseVisionFaceDetectorOptions.Builder()
                        .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
                        .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                        .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                        .build();

        final Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.man);

        final FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionFaceDetector detector = FirebaseVision.getInstance()
                .getVisionFaceDetector(options);
        Task<List<FirebaseVisionFace>> result =
                detector.detectInImage(image)
                        .addOnSuccessListener(
                                new OnSuccessListener<List<FirebaseVisionFace>>() {
                                    @Override
                                    public void onSuccess(List<FirebaseVisionFace> faces) {
                                        // Task completed successfully
                                        // ...
                                        Log.d("FACES", faces.toString());

                                        Point p = new Point();
                                        Display display = getWindowManager().getDefaultDisplay();
                                        display.getSize(p);
                                        for (FirebaseVisionFace face : faces) {

                                            // If landmark detection was enabled (mouth, ears, eyes, cheeks, and
                                            // nose available):
                                            FirebaseVisionFaceLandmark leftEar = face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EAR);
                                            float lex = leftEar.getPosition().getX();
                                            float ley = leftEar.getPosition().getY();
                                            FirebaseVisionFaceLandmark leftCheek = face.getLandmark(FirebaseVisionFaceLandmark.LEFT_CHEEK);
                                            float lcx = leftCheek.getPosition().getX();
                                            float lcy = leftCheek.getPosition().getY();
                                            FirebaseVisionFaceLandmark rightCheek = face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_CHEEK);
                                            float rcx = rightCheek.getPosition().getX();
                                            float rcy = rightCheek.getPosition().getY();

                                            ImageView imageLE = new ImageView(mContext);
                                            imageLE.setImageResource(R.drawable.bruise);
                                            imageLE.setX(p.x * lex / bitmap.getWidth()-100);
                                            imageLE.setY(p.y * ley / bitmap.getHeight()-100);

                                            imageLE.setLayoutParams(new RelativeLayout.LayoutParams(200,200));

                                            RelativeLayout.addView(imageLE);
                                            ImageView imageLC = new ImageView(mContext);
                                            imageLC.setImageResource(R.drawable.left_cat);
                                            imageLC.setX(p.x * lcx / bitmap.getWidth()-100);
                                            imageLC.setY(p.y * lcy / bitmap.getHeight()-100);

                                            imageLC.setLayoutParams(new RelativeLayout.LayoutParams(200,200));

                                            RelativeLayout.addView(imageLC);
                                            ImageView imageRC = new ImageView(mContext);
                                            imageRC.setImageResource(R.drawable.right_cat);
                                            imageRC.setX(p.x * rcx / bitmap.getWidth()-100);
                                            imageRC.setY(p.y * rcy / bitmap.getHeight()-100);

                                            imageRC.setLayoutParams(new RelativeLayout.LayoutParams(200,200));

                                            RelativeLayout.addView(imageRC);


                                        }
                                    }
                                })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Task failed with an exception
                                        // ...
                                    }
                                });
    }
}
