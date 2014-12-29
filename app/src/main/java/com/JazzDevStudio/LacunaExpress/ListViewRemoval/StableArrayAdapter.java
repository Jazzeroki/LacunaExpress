/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.JazzDevStudio.LacunaExpress.ListViewRemoval;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.JazzDevStudio.LacunaExpress.R;
import com.JazzDevStudio.LacunaExpress.SelectMessageActivity2;
import com.JazzDevStudio.LacunaExpress.MISCClasses.L;
import com.JazzDevStudio.LacunaExpress.MISCClasses.LetterTileProvider;

public class StableArrayAdapter extends ArrayAdapter<String> {

	ArrayList<String> first_letter_of_from = new ArrayList<String>();
    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
    View.OnTouchListener mTouchListener;
    Context context;
    TextView tv;

    public StableArrayAdapter(Context context, int textViewResourceId,
            List<String> objects, View.OnTouchListener listener, ArrayList<String> for_first_letter) {
        super(context, textViewResourceId, objects);
        mTouchListener = listener;
        this.context = context;

	    //first_letter_of_from.clear();

	    for (int x = 0; x<for_first_letter.size(); x++){
		    //populates the local arraylist with the one passed in from the Select message activity
		    first_letter_of_from.add(for_first_letter.get(x));
		    Log.d("Filling Array", Integer.toString(first_letter_of_from.size()));
	    }
	    //Log.d("Filled Array", first_letter_of_from.get(24));

        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i), i);
        }
    }

    @Override
    public long getItemId(int position) {
        String item = getItem(position);
        return mIdMap.get(item);
    }

    //Dependent upon the arrayAdapter having stable IDs via Unique IDs
    public boolean hasStableIds() {
        return true;
    }

    //Need to know the view as where things are going when moved
    public View getView(int position, View convertView, ViewGroup parent) {
    	View view = super.getView(position, convertView, parent);
    	
    	//Match the textView to the one in the layout passed via the view (opaque_text_view)
    	tv = (TextView) view.findViewById(R.id.image_text_vew_list);
    	//Get resources for access
        final Resources res = getContext().getResources();
        //Sets the tile size on the left side of the screen
        final int tileSize = res.getDimensionPixelSize(R.dimen.letter_tile_size);
        //LetterTileProvider object for creating the actual tiles
        LetterTileProvider tileProvider = new LetterTileProvider(getContext());
		//Testing code, commented out for now, but left in for reference
        //Bitmap testingBitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.testing);
        //Drawable testingDrawable = new BitmapDrawable(testingBitmap);

	    //This creates a string pulled from within the local array via referencing the position variable that is passed into the view here
	    String aName = first_letter_of_from.get(position);

	    /*
	    Creates the bitmap.
	    @Param: First param first letter is the letter the picture will show (IE Bob will show B)
	    Second Param will hash the string passed in and match it to a color. (IE Bob will be blue, but Bill will be Yellow)
	    This means that names should maintain their same color although many different names may have the same color
	     */
	    Bitmap letterTile = tileProvider.getLetterTile(aName, aName, tileSize, tileSize); //First Parameter is prefix letter
        //Create the drawable by referencing the bitmap
        Drawable testingDrawable2 = new BitmapDrawable(letterTile);
        //Set bounds
        testingDrawable2.setBounds( 0, 0, 60, 60 );
        //Draw the image to the left-side box
        tv.setCompoundDrawables(testingDrawable2, null, null, null);

        if (view != convertView) {
            // Add touch listener to every new view to track swipe motion
            view.setOnTouchListener(mTouchListener);
        }
        return view;
    }
}
