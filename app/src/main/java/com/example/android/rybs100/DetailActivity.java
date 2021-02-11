package com.example.android.rybs100;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class DetailActivity extends FragmentActivity implements OnMapReadyCallback  {



    public static final int RC_SIGN_IN = 1;
    private static final int RC_PHOTO_PICKER = 2;
    private GoogleMap mMap;
    private ListView mBookListView;
    private BooksAdapter mBookAdapter;
    private Button mPhotoPickerButton;
    private EditText mNameEditText;
    private EditText mPriceEditText;
    private EditText mEditionEditText;
    private EditText mAuthorEditText;
    private EditText mContactEditText;
    private EditText mCityEditText;
    private Button mUploadButton;
    private Button mSendButton;
    private ImageView mImage;
    private Button mCall;

    private String mUsername;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mBookDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mPhotosStorageReference;
    private String mLatitude;
    private String mLongitude;

    private ProgressBar mProgressBar;

    private ScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mScrollView = findViewById(R.id.detail_scroll);
        mCall = findViewById(R.id.call);

        mProgressBar = findViewById(R.id.progress_bar_detail);
        mProgressBar.setVisibility(View.GONE);

        mLatitude = getIntent().getExtras().getString("mLatitude");
        mLongitude = getIntent().getExtras().getString("mLongitude");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        mImage = findViewById(R.id.image);
        mNameEditText = findViewById(R.id.name_edit_text);
        mPriceEditText = findViewById(R.id.price_edit_text);
        mEditionEditText = findViewById(R.id.edition_edit_text);
        mAuthorEditText = findViewById(R.id.author_edit_text);
        mContactEditText = findViewById(R.id.contact_edit_text);
        mCityEditText = findViewById(R.id.city_edit_text);
        mSendButton = findViewById(R.id.sendButton);
        mSendButton.setVisibility(View.GONE);
        mUsername = getIntent().getExtras().getString("username");

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mBookDatabaseReference = mFirebaseDatabase.getReference().child("Books");
        mPhotosStorageReference = mFirebaseStorage.getReference().child("photos");
        mPhotoPickerButton = (Button) findViewById(R.id.photoPickerButton);

        String previousIntent = getIntent().getExtras().getString("previousActivity");
        if (previousIntent.equals("sell")) {
            mCall.setVisibility(View.GONE);
            mImage.setVisibility(View.GONE);
            mapFragment.getView().setVisibility(View.GONE);
            // ImagePickerButton shows an image picker to upload a image for a message
            mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/jpeg");
                    intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                    startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
                }
            });
        }
        else{
            // Check if no view has focus:

            String mUserName = getIntent().getExtras().getString("mUserName");
            String mBookName = getIntent().getExtras().getString("mBookName");
            String mBookPrice = getIntent().getExtras().getString("mBookPrice");
            String mBookEdition = getIntent().getExtras().getString("mBookEdition");
            String mBookAuthor = getIntent().getExtras().getString("mBookAuthor");
            final String mContact = getIntent().getExtras().getString("mContact");
            String mCity = getIntent().getExtras().getString("mCity");
            String mPhotoUrl = getIntent().getExtras().getString("mPhotoUrl");

            mNameEditText.setText(mBookName);
            mNameEditText.setEnabled(false);

            mPriceEditText.setText(mBookPrice);
            mPriceEditText.setEnabled(false);

            mEditionEditText.setText(mBookEdition);
            mEditionEditText.setEnabled(false);

            mAuthorEditText.setText(mBookAuthor);
            mAuthorEditText.setEnabled(false);

            mContactEditText.setText(mContact);
            mContactEditText.setEnabled(false);

            mCityEditText.setText(mCity);
            mCityEditText.setEnabled(false);

            mPhotoPickerButton.setVisibility(View.GONE);

            Glide.with(mImage.getContext())
                    .load(mPhotoUrl)
                    .into(mImage);


            mCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String uri = "tel:" + mContact.trim() ;
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(uri));
                    startActivity(intent);
                }
            });

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            final String name = mNameEditText.getText().toString();
            final String price = mPriceEditText.getText().toString();
            final String edition = mEditionEditText.getText().toString();
            final String author = mAuthorEditText.getText().toString();
            final String contact = mContactEditText.getText().toString();
            final String city = mCityEditText.getText().toString();
            final String latitude = mLatitude;
            final String longitude = mLongitude;
            if (name.equals("")||price.equals("")||edition.equals("")||author.equals("")||contact.equals("")||city.equals("")){
                Toast.makeText(DetailActivity.this, "Please fill out the above fields",Toast.LENGTH_SHORT).show();
            }
            else {
                mScrollView.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
                Uri selectedImageUri = data.getData();
                StorageReference photoRef = mPhotosStorageReference.child(selectedImageUri.getLastPathSegment());

                photoRef.putFile(selectedImageUri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();

                        Books books = new Books(latitude,longitude,mUsername, name, price, edition, author, contact, city, downloadUrl.toString());
                        mBookDatabaseReference.push().setValue(books);

                        mNameEditText.setText("");
                        mPriceEditText.setText("");
                        mEditionEditText.setText("");
                        mAuthorEditText.setText("");
                        mContactEditText.setText("");
                        mCityEditText.setText("");
                        Toast.makeText(DetailActivity.this,"Details Saved Successfully!",Toast.LENGTH_SHORT).show();
                        mScrollView.setVisibility(View.VISIBLE);
                        mProgressBar.setVisibility(View.GONE);

                    }
                });
            }
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng userLocation = new LatLng(Double.parseDouble(mLatitude), Double.parseDouble(mLongitude));
        mMap.addMarker(new MarkerOptions().position(userLocation).title("Seller is here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
    }
}
