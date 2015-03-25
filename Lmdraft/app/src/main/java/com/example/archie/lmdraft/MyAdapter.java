package com.example.archie.lmdraft;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;

/**
 * Created by archie on 1/29/15.
 */
class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<String> people;
    private ArrayList<Double> lamount;

    // Providing a suitable constructor (depends on the kind of dataset)
    //constructor that takes in the array lists and initalizes them to people and lamount array list respectivelly
    public MyAdapter(ArrayList<String> people, ArrayList<Double> lamount) {
        this.people = people;
        this.lamount = lamount;
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView pName;
        public TextView lAmt;

        public ViewHolder(View v){
            super(v);
            pName=(TextView)v.findViewById(R.id.name_tv);
            lAmt=(TextView)v.findViewById((R.id.amt_tv));
        }
    }

    //adding a new entry to the array list
    public void add(int position,String name,double amount){
        people.add(position,name);
        lamount.add(position,amount);
        notifyItemInserted(position);
    }

    //removing an entry from the list
    public void remove(int position){
        //int position=people.indexOf(name);
        people.remove(position);
        lamount.remove(position);
        notifyItemRemoved(position);
    }

    //create new view invoked by a layout manager
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // create a new view
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    public void onBindViewHolder(ViewHolder holder,int position){
        //final String pname= people.get(position);
        //final int pamt = lamount.get(position);

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.pName.setText(people.get(position));
        holder.lAmt.setText("$ "+lamount.get(position).toString());
    }

    // Return the size of your dataset (invoked by the layout manager)
    //return the number of items in the list
    public int getItemCount(){
        return people.size();
    }
}
