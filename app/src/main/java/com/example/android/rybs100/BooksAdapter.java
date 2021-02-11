package com.example.android.rybs100;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by aayush on 17-03-2018.
 */

public class BooksAdapter extends ArrayAdapter<Books> {

    /*
     * Create a new {@link TourAdapter} object.
     *
     * @param context is the current context (i.e. Activity) that the adapter is being created in.
     * @param tour is the list of {@link Tour}s to be displayed.
     */
    public BooksAdapter(Context context, int resource, List<Books> book) {
        super(context, resource, book);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_view_layout, parent, false);
        }

        //Find the current tour object
        Books currentBookObject = getItem(position);

        // Find the TextView in the list_view_layout.xml layout with the ID list_item_text_view.
        TextView listItemTextViewBookName = (TextView) listItemView.findViewById(R.id.list_item_text_view_book_name);
        // Get the text string from the currentTour object and set this text on
        // the list_item_text_view.
        listItemTextViewBookName.setText(currentBookObject.getmBookName());

        //Find the ImageView in the list_view_layout.xml layout with ID list_item_image_view.
        ImageView listItemImageViewBook = (ImageView) listItemView.findViewById(R.id.list_item_image_view_book);
        //Get the image id from the currentTourObject and set this image on
        //the list_item_image_view

        Glide.with(listItemImageViewBook.getContext())
                .load(currentBookObject.getmPhotoUrl())
                .into(listItemImageViewBook);


        TextView listItemTextViewAuthor = (TextView) listItemView.findViewById(R.id.list_item_text_view_author);
        // Get the text string from the currentTour object and set this text on
        // the list_item_text_view.
        listItemTextViewAuthor.setText(currentBookObject.getmBookAuthor());

        TextView listItemTextViewPrice = (TextView) listItemView.findViewById(R.id.list_item_text_view_book_price);
        listItemTextViewPrice.setText(currentBookObject.getmBookPrice());

        //Return the listItemView
        return listItemView;
    }
}