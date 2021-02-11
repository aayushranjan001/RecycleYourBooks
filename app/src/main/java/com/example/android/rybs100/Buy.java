package com.example.android.rybs100;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Buy extends AppCompatActivity {

    private ChildEventListener mChildEventListener;
    private BooksAdapter mBookAdapter;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mBookDatabaseReference;
    private ListView mBookListView;
    private String username;
    private ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        username = getIntent().getExtras().getString("username");


        mProgressBar = findViewById(R.id.progress_bar_buy);
        mProgressBar.setVisibility(View.VISIBLE);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mBookDatabaseReference = mFirebaseDatabase.getReference().child("Books");
        mBookListView = findViewById(R.id.list);

        // Initialize message ListView and its adapter
        final List<Books> books = new ArrayList<>();
        mBookAdapter = new BooksAdapter(this,R.layout.list_view_layout,books);
        mBookListView.setAdapter(mBookAdapter);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setVisibility(View.GONE);

        //set an onitemclick listener
        mBookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get position of the current list item clicked
                Books currentBookObjectData = books.get(position);
                //create new intent
                Intent descriptionInfo = new Intent(Buy.this, DetailActivity.class);
                descriptionInfo.putExtra("username",username);
                descriptionInfo.putExtra("previousActivity","buy");
                descriptionInfo.putExtra("mUserName",currentBookObjectData.getmUserName());
                descriptionInfo.putExtra("mBookName",currentBookObjectData.getmBookName());
                descriptionInfo.putExtra("mBookPrice",currentBookObjectData.getmBookPrice());
                descriptionInfo.putExtra("mBookEdition",currentBookObjectData.getmBookEdition());
                descriptionInfo.putExtra("mBookAuthor",currentBookObjectData.getmBookAuthor());
                descriptionInfo.putExtra("mContact",currentBookObjectData.getmContact());
                descriptionInfo.putExtra("mCity",currentBookObjectData.getmCity());
                descriptionInfo.putExtra("mPhotoUrl",currentBookObjectData.getmPhotoUrl());
                descriptionInfo.putExtra("mLatitude",currentBookObjectData.getmLatitude());
                descriptionInfo.putExtra("mLongitude",currentBookObjectData.getmLongitude());
                //pass object clicked to the other activity

                startActivity(descriptionInfo);
            }
        });
        attachDatabaseReadListener();

    }

    private void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Books books = dataSnapshot.getValue(Books.class);
                    mBookAdapter.add(books);
                    mProgressBar.setVisibility(View.GONE);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            mBookDatabaseReference.addChildEventListener(mChildEventListener);

        }
    }




}
