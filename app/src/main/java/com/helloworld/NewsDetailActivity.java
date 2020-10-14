package com.helloworld;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class NewsDetailActivity extends AppCompatActivity {

    TextView TextView_detailtitle;
    ImageView ImageView_detailtitle;
    TextView TextView_detaildescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsdetail);

        TextView_detailtitle = findViewById(R.id.TextView_detailtitle);
        ImageView_detailtitle = findViewById(R.id.ImageView_detailtitle);
        TextView_detaildescription = findViewById(R.id.TextView_detaildescription);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        String title = bundle.getString("title");
        String image = bundle.getString("image");
        String description = bundle.getString("description");

        TextView_detailtitle.setText(title);
        Uri uri = Uri.parse(image);
        ImageView_detailtitle.setImageURI(uri);
        TextView_detaildescription.setText(description);
    }
}
