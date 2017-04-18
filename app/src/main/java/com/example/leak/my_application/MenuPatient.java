package com.example.leak.my_application;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;

public class MenuPatient extends AppCompatActivity {
    private String  username;
    private int x,y;
    private Canvas canvas;

    int width,height;
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

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
         width = size.x;
        height = size.y;

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
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        canvas = new Canvas(bitmap);
        float scale = getResources().getDisplayMetrics().density;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GREEN);
        paint.setTextSize(10*scale);
            canvas.drawCircle((float) (x / 50 + 592), (float) (y / 50 +480+811), 20, paint);
            canvas.drawText(username, (float) (x / 50 + 592 -20), (float) (y/ 50 +480 +800-30),paint);
        imageView.setImageBitmap(bitmap);

        imageView.invalidate();
    }

    public void onCofigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);

        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){

            //canvas.b;
        }

    }
}
