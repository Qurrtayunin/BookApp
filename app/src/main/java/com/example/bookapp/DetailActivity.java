package com.example.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class DetailActivity extends AppCompatActivity {
    private TextView mText1;
    private TextView mText2;
    private ImageView mImage;
    private TextView mTextDesc;
    ItemData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mText1 = findViewById(R.id.text1);
        mText2 = findViewById(R.id.text2);
        mImage = findViewById(R.id.image);
        mTextDesc = findViewById(R.id.textDesc);

        Intent intent = getIntent();
        if(intent.hasExtra("DATA")){
            data= intent.getParcelableExtra("DATA");
            mText1.setText(data.title);
            mText2.setText(data.subtitle);
            mTextDesc.setText(data.itemDescription);
            new LoadImage(mImage).execute(data.itemImage); //mendownload dan menampilkan imagenya
        }
    }

    private class LoadImage extends AsyncTask<String,Void,Bitmap>{
        private ImageView imageView;
        public LoadImage(ImageView imageView){
            this.imageView=imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            URL url=null;
            Bitmap bmp=null;
            try {
                url=new URL(strings[0]);
                bmp= BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmp;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }

}
