package com.example.leak.my_application;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MenuPersonnel extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static int [] idPatient;
    private String username;
    private String [] users;
    private static Patient [] p;
    private int  nbPatient=0;
    private int[] x,y;
    private Canvas canvas;
    //private UserLoginTask mAuthTask = null;
    private Handler handler;

    Point size = new Point();
    int xMapScale, yMapScale, yMapRation, xMapRation;
    MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_personnel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //you can keep adding code to change myTextView
        Intent previousIntent = getIntent();

        username = previousIntent.getStringExtra("username");
        users= previousIntent.getStringArrayExtra("users");
       x=previousIntent.getIntArrayExtra("x");
        y=previousIntent.getIntArrayExtra("y");


        nbPatient=previousIntent.getIntExtra("number",0);

        int i;

        idPatient= new int[nbPatient];

        p = new Patient[nbPatient];

        /*Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        width = size.x;
        height = size.y;
*/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View view_text = navigationView.getHeaderView(0);

        TextView user = (TextView) view_text.findViewById(R.id.menuUsername);
        user.setText(username);

        Menu menu=navigationView.getMenu();
        SubMenu subMenu_patient = menu.addSubMenu("Patients");


        for(i=0;i<nbPatient;i++) {
            //p[i]=new Patient();
            idPatient[i]=Menu.FIRST + i;
            subMenu_patient.add(Menu.NONE, idPatient[i], Menu.NONE, users[i]).setIcon(R.drawable.user);
            //p[i].setPatientName(users[i]);
            //p[i].setX(x[i]);
            //p[i].setY(y[i]);


        }
        //onDraw(canvas, p);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

            }
        });

        // update

        navigationView.setNavigationItemSelectedListener(this);

       /* handler=new Handler();

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {

                mAuthTask = new UserLoginTask(users);
                mAuthTask.execute((Void) null);

                handler.postDelayed(this, 1000);

            }


        };
        handler.postDelayed(runnable, 1000);*/


    }
    public void onDraw(Canvas canvas , Patient [] p) {
        Bitmap bitmap;
        Paint paint;

        ImageView imageView = (ImageView) findViewById(R.id.imageView5);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        bitmap = Bitmap.createBitmap(241, 151, Bitmap.Config.ARGB_8888);
        xMapScale=bitmap.getWidth();
        yMapScale=bitmap.getHeight();
        xMapRation=xMapScale/241;
        yMapRation=yMapScale/151;
        canvas = new Canvas(bitmap);
        float scale = getResources().getDisplayMetrics().density;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GREEN);
        paint.setTextSize(10*scale);
        for (int i = 0; i < nbPatient; i++){
            canvas.drawCircle((float) (2), (float) (3), 20, paint);
            //canvas.drawText(p[i].getPatientName(), (float) (p[i].getX() / 50 + 592 -20), (float) (p[i].getY() / 50 +480 +800-30),paint);
    }
       imageView.setImageBitmap(bitmap);

        imageView.invalidate();

    }
    protected void onStart() {
        // TODO Auto-generated method stub

        //Register BroadcastReceiver
        //to receive event from our service
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RefreshCoordinate.MY_ACTION);
        registerReceiver(myReceiver, intentFilter);

        //Start our own service
        Intent intent = new Intent(MenuPersonnel.this,
                RefreshCoordinate.class);
        intent.putExtra("users",users);
        intent.putExtra("nbrP", nbPatient);
        startService(intent);

        super.onStart();
    }
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        unregisterReceiver(myReceiver);
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_personnel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
       int id = item.getItemId();
        ImageView imageView = (ImageView) findViewById(R.id.imageView5);
        for (int i=0;i< nbPatient;i++){
        if (id == idPatient[i]) {
            Toast.makeText(getApplicationContext(),users[i], Toast.LENGTH_SHORT).show();
            //canvas.drawCircle(x[i]/10000, y[i]/10000, 20, paint);
            Intent intent = new Intent(this,MenuPatient.class);

            intent.putExtra("username", users[i]);
            intent.putExtra("x", p[i].getX());
            intent.putExtra("y", p[i].getY());
            this.startActivity(intent);
        }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public void onCofigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);

        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            //canvas.b;
        }

    }

   /* public class UserLoginTask extends AsyncTask<Void, Void, Patient[]> {

        //private Patient [] patient = new Patient[nbPatient];

        UserLoginTask(String [] patientName) {
            for(int i=0;i<nbPatient;i++) {
                //p[i]=new Patient();
                p[i].setPatientName(patientName[i]);
            }
        }

        @Override
        protected Patient [] doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                boolean finished = false;
                //while (! finished) {
                    for(int i=0;i<nbPatient;i++) {
                        p[i].update();
                        //p[i].setX(p[i].getX());
                        //p[i].setY(p[i].getY());
                       // p[i].setX(50000000);

                    }
                    Thread.sleep(2000);
               // }
            } catch (InterruptedException e) {
                return p;
            }
            return p;
        }


        @Override
        protected void onPostExecute(final Patient[] patient) {
            //mAuthTask = null;
            for(int i=0;i<nbPatient;i++) {
                p[i].setX(patient[i].getX());
                p[i].setY(patient[i].getY());
            }

            onDraw(canvas, p);

        }


    }*/
    private class MyReceiver extends BroadcastReceiver {

               @Override
               public void onReceive (Context arg0, Intent arg1){
                   // TODO Auto-generated method stub
                   Bundle extras = arg1.getExtras();
                   int[] xs = extras.getIntArray("x");
                   int[] ys = extras.getIntArray("y");

                   for (int i = 0; i < nbPatient; i++) {
                       p[i] = new Patient();
                       p[i].setPatientName(users[i]);
                       p[i].setX(xs[i]);
                       p[i].setY(ys[i]);
                   }

                   onDraw(canvas, p);

               }

   }
}



