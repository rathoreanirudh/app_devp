package com.example.archie.lmdraft;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity
        implements RecyclerView.OnItemTouchListener{

    private RecyclerView recyclerView;
    private MyAdapter madapter;
    private RecyclerView.LayoutManager mlayoutmanager;
    Button add ;
    ArrayList<String>janta;
    ArrayList<Double>paisa;

    GestureDetectorCompat detector;

    ViewConfiguration vc;
    int swipeMinDistance;
    int swipeThresholdVelocity ;
    int swipeMaxOffPath ;

    private static final int SWIPE_MAX_OFFPATH = 250;
    private static final int SWIPE_MIN_DISTACE = 120;
    private static final int SWIPE_MIN_VELOCITY = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializevar();
        vc = ViewConfiguration.get(recyclerView.getContext());
        swipeMinDistance = vc.getScaledPagingTouchSlop();
        swipeThresholdVelocity = vc.getScaledMinimumFlingVelocity();
        swipeMaxOffPath = vc.getScaledTouchSlop();

        mlayoutmanager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mlayoutmanager);

        madapter = new MyAdapter(janta,paisa);
        recyclerView.setAdapter(madapter);


        //nischal bhaia's solution for motion even handling
        detector = new GestureDetectorCompat(this, new RecyclerViewOnGestureListener());
        recyclerView.addOnItemTouchListener(this);

        //stackoverflow solution one
        /*recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever
                        madapter.remove(position);
                    }
                })
        );*/


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get inputdial.xml view using layoutinflater class
                LayoutInflater layoutinflater = LayoutInflater.from(MainActivity.this);
                View promptView = layoutinflater.inflate(R.layout.inputdial,null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                alertDialogBuilder.setView(promptView);
                final EditText iname = (EditText) promptView.findViewById(R.id.pname);
                final EditText iamt = (EditText) promptView.findViewById(R.id.pamt);

                //setting up dialog window
                alertDialogBuilder.setCancelable(false).setPositiveButton("Save" , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        madapter.add(madapter.getItemCount(),iname.getText().toString(), Integer.parseInt(iamt.getText().toString()));
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertD = alertDialogBuilder.create();
                alertD.show();
                //madapter.add(madapter.getItemCount(),"newly",madapter.getItemCount());
            }
        });
    }

     // to initialize a the variables and array lists
    private void initializevar(){
        recyclerView=(RecyclerView)findViewById(R.id.my_recycler_view);
        add=(Button)findViewById(R.id.bAdd);

        janta = new ArrayList<String>();
        paisa = new ArrayList<Double>();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public class RecyclerViewOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        public RecyclerViewOnGestureListener() {
            super();
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {

            return super.onSingleTapUp(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
            int position = recyclerView.getChildPosition(view);
            madapter.add(madapter.getItemCount(),"long",position);
            super.onLongPress(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try{
                if( Math.abs(e1.getY() - e2.getY()) > swipeMaxOffPath){
                    return false;
                }
                if( e1.getX() - e2.getX()>swipeMinDistance && Math.abs(velocityX)>swipeThresholdVelocity){
                    //handle left to right swipe
                    View view = recyclerView.findChildViewUnder(e1.getX(),e1.getY());
                    int position = recyclerView.getChildPosition(view);
                    madapter.add(madapter.getItemCount(),"L2Rswipe",position);
                }
                else if(e2.getX()-e1.getX()>swipeMinDistance && Math.abs(velocityX)>swipeThresholdVelocity){
                    //handle right to left swipe
                    View view = recyclerView.findChildViewUnder(e1.getX(),e1.getY());
                    int position = recyclerView.getChildPosition(view);
                    madapter.add(madapter.getItemCount(),"R2Lswipe",position);
                }

            }catch (Exception e){
                //handle the posible exceptions
            }

            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public void onShowPress(MotionEvent e) {
            super.onShowPress(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return super.onDown(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
            int position = recyclerView.getChildPosition(view);
            madapter.add(madapter.getItemCount(),"dubtap",position);
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {

            return super.onDoubleTapEvent(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
            int position = recyclerView.getChildPosition(view);
            madapter.remove(position);
            return super.onSingleTapConfirmed(e);
        }
    }


    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        detector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }
}
