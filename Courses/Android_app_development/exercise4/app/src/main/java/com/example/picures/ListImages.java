package com.example.picures;


import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListImages extends ListFragment {
    private String[] imageTitles;
    private String[] imageDescription;
    private OnFragmentInteractionListener imageListener;

    public ListImages() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Resources res= getResources();
        imageTitles = res.getStringArray(R.array.imageTitles); // Collect all image titles from string.xml
        imageDescription = res.getStringArray(R.array.imageDescription); // Collect all image descriptions from string.xml
        setListAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, android.R.id.text1, imageTitles)); // insert all titles into list.
    }

    @Override
    public void onAttach(Activity activity) {
        //Attaches the listener to activity.

        super.onAttach(activity);
        try {
            imageListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException cce) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentMovieInteractionListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //Calls on the interaction listener interface on item click.
        imageListener.onFragmentInteraction(position, imageDescription[position]);
    }

    //A interaction listener, is implemented by main activity, and overrides method to be called.
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(int id, String tekst);
    }

}
