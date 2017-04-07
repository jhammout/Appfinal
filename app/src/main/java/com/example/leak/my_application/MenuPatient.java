package com.example.leak.my_application;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class MenuPatient extends AppCompatActivity {
    private String  username;
    private int x,y;
    private Canvas canvas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu_patient);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent previousIntent = getIntent();

        username = previousIntent.getStringExtra("username");
        x=previousIntent.getIntExtra("x",0);
        y=previousIntent.getIntExtra("y",0);
        setTitle(username);
        onDraw(canvas ,x, y);


    }

    public void onDraw(Canvas canvas , int x, int y){
        Bitmap bitmap;
        Paint paint;
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        bitmap = Bitmap.createBitmap(2100,2100, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GREEN);
            canvas.drawCircle(x / 100000, y / 100000, 20, paint);
            imageView.setImageBitmap(bitmap);
    }
}
