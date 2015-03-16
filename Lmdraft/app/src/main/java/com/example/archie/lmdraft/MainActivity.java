package com.example.archie.lmdraft;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private RecyclerView recyclerView;
    private MyAdapter madapter;
    private RecyclerView.LayoutManager mlayoutmanager;
    Button add ;
    ArrayList<String>janta;
    ArrayList<Integer>paisa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializevar();

        mlayoutmanager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mlayoutmanager);

        madapter = new MyAdapter(janta,paisa);
        recyclerView.setAdapter(madapter);

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
                alertDialogBuilder.setCancelable(false).setPositiveButton("Ok" , new DialogInterface.OnClickListener() {
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
        paisa = new ArrayList<Integer>();
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
}
