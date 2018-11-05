package com.myousufkhan360.opencvapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";
    ImageView imageView ;
    Uri ImageUri;
    Bitmap imageBitMap;
    Bitmap grayBitMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.image1);

        if(!OpenCVLoader.initDebug()){
           Log.d(TAG, "OpenCV not loaded");
            //Toast.makeText(getApplicationContext(),"Open cv not loaded",Toast.LENGTH_SHORT).show();
        } else {
            Log.d(TAG, "OpenCV loaded");
            //Toast.makeText(getApplicationContext(),"Open cv loaded successfully",Toast.LENGTH_SHORT).show();
        }



    }

    public void OpenGallery(View view) {

        Intent intent = new Intent(Intent.ACTION_PICK , MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent , 100);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100 && resultCode == RESULT_OK && data != null ){

            ImageUri = data.getData();

            try{

                imageBitMap = MediaStore.Images.Media.getBitmap(this.getContentResolver() , ImageUri);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            imageView.setImageBitmap(imageBitMap);

        }
    }

    public void GrayImage(View view) {

        Mat Rgba = new Mat();
        Mat gray = new Mat();

        BitmapFactory.Options o = new BitmapFactory.Options();

        o.inDither = false;
        o.inSampleSize = 4;

        int width = imageBitMap.getWidth();
        int height = imageBitMap.getHeight();

        grayBitMap = Bitmap.createBitmap(width,height, Bitmap.Config.RGB_565);

        //convert bitmap to Mat

        Utils.bitmapToMat(imageBitMap , Rgba);

        //To convert in to gray

        Imgproc.cvtColor(Rgba , gray ,Imgproc.COLOR_RGB2GRAY);


        //convert Mat to bitmap

        Utils.matToBitmap(gray , grayBitMap);

        imageView.setImageBitmap(grayBitMap);

    }
}








