package com.example.picures;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowImages extends Fragment {
    private ImageView image;
    private TextView text;

    public ShowImages(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);
        text = (TextView) view.findViewById(R.id.textFragment2);
        image = (ImageView) view.findViewById(R.id.imageFragment2);
        return view;
    }

    //Changes image and description for image text.
    public void changeImage(int id, String description){
        image.setImageResource(id);
        text.setText(description);
    }
}
