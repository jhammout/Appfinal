package com.example.leak.my_application;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
    private Patient [] p;
    private int  nbPatient=0;
    private Canvas canvas;
    private UserLoginTask mAuthTask = null;
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
        //x=previousIntent.getIntArrayExtra("x");
        //y=previousIntent.getIntArrayExtra("y");


        nbPatient=previousIntent.getIntExtra("number",0);

        int i;
        int[] pos= new int[2];

        idPatient= new int[nbPatient];

        p = new Patient[nbPatient];

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


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
            p[i]=new Patient();
            idPatient[i]=Menu.FIRST + i;
            subMenu_patient.add(Menu.NONE, idPatient[i], Menu.NONE, users[i]).setIcon(R.drawable.user);
            p[i].setPatientName(users[i]);
            p[i].update();

        }
        mAuthTask = new UserLoginTask(users);
        mAuthTask.execute((Void) null);
        // update

        navigationView.setNavigationItemSelectedListener(this);


    }
    public void onDraw(Canvas canvas , Patient [] p){
        Bitmap bitmap;
        Paint paint;
        ImageView imageView = (ImageView) findViewById(R.id.imageView5);
        bitmap = Bitmap.createBitmap(2100,2100, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GREEN);
        for (int i=0;i<nbPatient;i++)
        canvas.drawCircle(p[i].getX() / 100000, p[i].getY() / 100000, 20, paint);
        imageView.setImageBitmap(bitmap);

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
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        //private Patient [] patient = new Patient[nbPatient];

        UserLoginTask(String [] patientName) {
            for(int i=0;i<nbPatient;i++) {
                p[i]=new Patient();
                p[i].setPatientName(patientName[i]);
            }
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                boolean finished = false;
                //while (! finished) {
                    for(int i=0;i<nbPatient;i++) {
                        p[i].update();

                    }
                    Thread.sleep(2000);
               // }
            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }


        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
                    onDraw(canvas, p);
        }


    }
}



