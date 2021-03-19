package com.example.picures;



import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements ListImages.OnFragmentInteractionListener{
    private int selectedImage = 0;
    private TypedArray images;
    private String[] imageTitles;
    private String[] imageDescriptions;
    private ShowImages rightFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources resources = getResources();
        imageTitles = resources.getStringArray(R.array.imageTitles);
        imageDescriptions = resources.getStringArray(R.array.imageDescription);
        images = resources.obtainTypedArray(R.array.animalImages);
        rightFragment = (ShowImages) getFragmentManager().findFragmentById(R.id.viewer);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Get menu from xml file and add to actionbar.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        //On menu option do one of these options

        int id = menuItem.getItemId();
        switch (id) {
            case R.id.previousImage:
                if(selectedImage>=1){
                    selectedImage--;
                    onFragmentInteraction(selectedImage, imageDescriptions[selectedImage]);
                }
                return true;
            case R.id.nextImage:
                if(selectedImage<imageTitles.length-1){
                    selectedImage++;
                    onFragmentInteraction(selectedImage, imageDescriptions[selectedImage]);
                }
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onFragmentInteraction(int id, String tekst) {
        //This metod is called when a item is clicked and the event fires for the listener to invoke this method.

        selectedImage = id; // Set selected image as current id. array values 0-n.
        int resourceId = images.peekValue(id).resourceId; // id is different from resource id, need to specify resource id.
        rightFragment.changeImage(resourceId, tekst);
    }
}

