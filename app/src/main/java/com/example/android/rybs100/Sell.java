package com.example.android.rybs100;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Sell extends AppCompatActivity {

    private ChildEventListener mChildEventListener;
    private BooksAdapter mBookAdapter;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mBookDatabaseReference;
    private ListView mBookListView;
    private String username;
    private String mLatitude;
    private String mLongitude;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        username = getIntent().getExtras().getString("username");
        mLatitude = getIntent().getExtras().getString("mLatitude");
        mLongitude = getIntent().getExtras().getString("mLongitude");

        mProgressBar = findViewById(R.id.progress_bar_buy);
        mProgressBar.setVisibility(View.VISIBLE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sell.this, DetailActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("previousActivity", "sell");
                intent.putExtra("mLatitude", mLatitude);
                intent.putExtra("mLongitude", mLongitude);
                startActivity(intent);
            }
        });

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mBookDatabaseReference = mFirebaseDatabase.getReference().child("Books");
        mBookListView = findViewById(R.id.list);

        // Initialize message ListView and its adapter
        List<Books> books = new ArrayList<>();
        mBookAdapter = new BooksAdapter(this, R.layout.list_view_layout, books);
        mBookListView.setAdapter(mBookAdapter);

        attachDatabaseReadListener();
    }

    private void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Books books = dataSnapshot.getValue(Books.class);
                    if (books.getmUserName().equals(username)) {
                        mBookAdapter.add(books);
                        mProgressBar.setVisibility(View.GONE);
                    }
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
