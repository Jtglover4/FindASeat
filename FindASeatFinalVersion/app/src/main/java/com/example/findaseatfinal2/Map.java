package com.example.findaseatfinal2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class Map extends AppCompatActivity implements OnMapReadyCallback {

    public static HashMap<String, Integer> BuildingID = new HashMap<>();
    HashMap<String, String> BuildingDesc = new HashMap<>();

    private GoogleMap myMap;
    private SupportMapFragment mapFragment;

    LoggedIn currUser = LoggedIn.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.googlemaps_layout);
        AppCompatActivity this_class = this;
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        TextView leftTab = findViewById(R.id.l_tab);
        TextView rightTab = findViewById(R.id.r_tab);

        // Add click listeners to the TextViews
        leftTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click on the left tab
                // For example, switch to a different tab or perform some action
                // Here, we'll show a toast message as an example
                System.out.println("LEFT TAB CLICKED");
                Intent intent = new Intent(this_class, Map.class);
                this_class.startActivity(intent);
            }
        });

        rightTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click on the right tab
                // For example, switch to a different tab or perform some action
                // Here, we'll show a toast message as an example
                System.out.println("RIGHT TAB CLICKED");
                Intent intent = new Intent(this_class, ProfileActivity.class);
                this_class.startActivity(intent);
            }
        });
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String imageUrl = urls[0];
            Bitmap bitmap = null;

            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                // Display the downloaded image in the ImageView
                imageView.setImageBitmap(result);
            }
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;
        LatLng Leavey, Fertitta, Doheny, Mudd, Taper, Annenberg, Norris, Seaver, Arch, Film;
        Leavey = new LatLng(34.02166,-118.28263);
        Fertitta = new LatLng(34.0187, -118.2824);
        Doheny = new LatLng(34.02016,-118.28377);
        Mudd = new LatLng(34.01864,-118.28643);
        Taper = new LatLng(34.02252,-118.28403);
        Annenberg = new LatLng(34.02105,-118.28719);
        Norris = new LatLng(34.02387,-118.28630);
        Seaver = new LatLng(34.01971,-118.28894);
        Arch = new LatLng(34.01924,-118.28765);
        Film = new LatLng(34.02368,-118.28715);

        BuildingID.put("Leavey Library", 1);
        BuildingID.put("Fertitta Hall", 2);
        BuildingID.put("Doheny Library", 3);
        BuildingID.put("Mudd Hall", 4);
        BuildingID.put("Taper Hall", 5);
        BuildingID.put("Annenberg Hall", 6);
        BuildingID.put("Norris Dental Center", 7);
        BuildingID.put("Seaver Science Center", 8);
        BuildingID.put("School of Architecture", 9);
        BuildingID.put("School of Cinema", 10);



        BuildingDesc.put("Leavey Library", "Built in the mid-1990s, Leavey Library is one of the two main undergraduate libraries on campus. Whether you need to just finish your homework or study for a midterm, Leavey Library is the place to go.");
        BuildingDesc.put("Fertitta Hall", "Completed in 2016, Fertitta Hall is the latest addition to the Marshall School of Business. Its modern architecture and chic interior create a welcoming environment for students and faculty alike.");
        BuildingDesc.put("Doheny Library", "At the center of the campus, Doheny Library was built in 1932 and is the other main undergraduate library. It is favored by students who enjoy being surrounded by history and vintage furniture while studying.");
        BuildingDesc.put("Mudd Hall", "All the way at the end of Trousdale, Mudd Hall, and its 146-foot clock tower are visible from quite some distance. It houses the quiet and sophisticated Philosophy Library along with lecture halls with intricate woodwork.");
        BuildingDesc.put("Taper Hall", "On the other end of Trousdale, Taper Hall has been the location of many students’ classes. Its two main lecture halls can house up to 300 students each and are a prime location for many student organizations to hold their meetings.");
        BuildingDesc.put("Annenberg Hall", "The news and media center of the university, Annenberg Hall is almost indistinguishable from a newsroom of major news outlets. It's where the school Newspaper is edited and published and students can reserve rooms to work on their projects.");
        BuildingDesc.put("Norris Dental Center", "For those with the school insurance plan, the Norris Dental Center houses many clinics that are available to students as well as a library that is available to everyone. The library is a small yet comfortable place for students to study.");
        BuildingDesc.put("Seaver Science Center", "The Seaver Science Center is often passed up as students rush past yet it has many things that all engineering students want. The Makerspace on the first floor is where USC’s Formula SAE team meets to perfect their vehicle and the second floor contains the Engineering library.");
        BuildingDesc.put("School of Architecture", "One of the top architecture schools in the United States, the School of Architecture contains a library and access to everything that designers of the future need to shape the future. It is a hotbed for innovative design and all USC students can explore these designs of the future.");
        BuildingDesc.put("School of Cinema", "Perhaps USC’s most famous school, the School of Cinema has churned out actors and filmmakers like George Lucas and Will Ferrell. Students can sit and study in places like the Cinema library where the upper echelon of Hollywood once studied.");

        MarkerOptions LeaveyMarker = new MarkerOptions().position(Leavey).title("Leavey Library");

        myMap.addMarker(LeaveyMarker);
        myMap.addMarker(new MarkerOptions().position(Fertitta).title("Fertitta Hall"));
        myMap.addMarker(new MarkerOptions().position(Doheny).title("Doheny Library"));
        myMap.addMarker(new MarkerOptions().position(Mudd).title("Mudd Hall"));
        myMap.addMarker(new MarkerOptions().position(Taper).title("Taper Hall"));
        myMap.addMarker(new MarkerOptions().position(Annenberg).title("Annenberg Hall"));
        myMap.addMarker(new MarkerOptions().position(Norris).title("Norris Dental Center"));
        myMap.addMarker(new MarkerOptions().position(Seaver).title("Seaver Science Center"));
        myMap.addMarker(new MarkerOptions().position(Arch).title("School of Architecture"));
        myMap.addMarker(new MarkerOptions().position(Film).title("School of Cinema"));

        myMap.moveCamera(CameraUpdateFactory.newLatLng(Leavey));
        myMap.moveCamera(CameraUpdateFactory.newLatLng(Fertitta));
        myMap.moveCamera(CameraUpdateFactory.newLatLng(Doheny));
        myMap.moveCamera(CameraUpdateFactory.newLatLng(Mudd));
        myMap.moveCamera(CameraUpdateFactory.newLatLng(Taper));
        myMap.moveCamera(CameraUpdateFactory.newLatLng(Annenberg));
        myMap.moveCamera(CameraUpdateFactory.newLatLng(Norris));
        myMap.moveCamera(CameraUpdateFactory.newLatLng(Seaver));
        myMap.moveCamera(CameraUpdateFactory.newLatLng(Arch));
        myMap.moveCamera(CameraUpdateFactory.newLatLng(Film));

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(Leavey);
        builder.include(Fertitta);
        builder.include(Doheny);
        builder.include(Mudd);
        builder.include(Taper);
        builder.include(Annenberg);
        builder.include(Norris);
        builder.include(Seaver);
        builder.include(Arch);
        builder.include(Film);
        LatLngBounds bounds = builder.build();




        int padding = 50; // Adjust the padding as needed

        // Add an OnGlobalLayoutListener to wait for map layout
        ViewTreeObserver vto = mapFragment.getView().getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Once the map layout is ready, remove the listener and set the camera to show all the markers
                mapFragment.getView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                myMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
            }
        });
        myMap.getUiSettings().setZoomControlsEnabled(true);
        myMap.getUiSettings().setCompassEnabled(true);

        //TextView view = (TextView) TommyMarker;

        myMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // Create a PopupWindow with the custom layout
                View customView = getLayoutInflater().inflate(R.layout.marker_popup_layout, null);
                PopupWindow popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                System.out.println("The Building ID of " + marker.getTitle() + " is " + BuildingID.get(marker.getTitle()));

                // Find the TextView and Buttons in the custom layout
                TextView popupTitle = customView.findViewById(R.id.popup_title);
                TextView BuildDesc = customView.findViewById(R.id.build_disc);
                Button CreateReservation = customView.findViewById(R.id.popup_option1);
                Button Quit = customView.findViewById(R.id.reg_submit_tv);
                ImageView popupImage = customView.findViewById(R.id.imageView3);

                View custView = getLayoutInflater().inflate(R.layout.resmade, null);
                PopupWindow PopWind = new PopupWindow(custView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                Button ResQuit = custView.findViewById(R.id.ResX);

                // Set click listeners for the buttons in the popup menu
                if(!ReservationViewModelStaticParams.canModorCancel) {
                    CreateReservation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Handle Option 1 click
                            //LoginActivity loggedIn = new LoginActivity();
                            boolean show = currUser.isLoggedIn();
                            System.out.println(show);
                            if (!show) {
                                popupWindow.dismiss();
                                Intent intent = new Intent(Map.this, LoginActivity.class);
                                Map.this.startActivity(intent);
                            } else {
                                ReservationViewModelStaticParams.buildingID = (Integer) BuildingID.get(marker.getTitle());
                                Intent intent = new Intent(Map.this, ReservationActivity.class);
                                Map.this.startActivity(intent);
                            }
                        }
                    });
                }
                else{
                    CreateReservation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PopWind.showAtLocation(mapFragment.getView(), Gravity.CENTER, 0, 0);
                        }
                    });
                    ResQuit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View V){
                            PopWind.dismiss();
                        }
                    });
                }

                Quit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Handle Option 2 click
                        popupWindow.dismiss(); // Close the popup men
                    }
                });

                // Update the title of the popup menu
                popupTitle.setText(marker.getTitle());
                BuildDesc.setText(BuildingDesc.get(marker.getTitle()));

                // Define the image URL (replace with your actual image URL)
                String Leavey = "https://libraries.usc.edu/sites/default/files/styles/16_9_large_2x/public/2019-07/leavey-dsc_0106.jpg?itok=5SPgA4w-";
                String Fertitta = "https://today.usc.edu/wp-content/uploads/2016/09/Fertitta_toned_web2-1280x720.jpg";
                String Doheny = "https://libraries.usc.edu/sites/default/files/styles/16_9_large_2x/public/2019-08/dml-front.jpg?itok=nPHy_km1";
                String Mudd = "https://libraries.usc.edu/sites/default/files/styles/16_9_large_2x/public/2019-08/hoose-interior.jpg?itok=i8UVKSXu";
                String Taper = "https://dornsife.usc.edu/engl/wp-content/uploads/sites/85/2023/01/USC_DCLAS_Building_Details_Taper_Hall_of_Humanities_20221021_01-768x432.png";
                String Annenberg = "https://live.staticflickr.com/7408/9623034119_1eb42aff7e_b.jpg";
                String Norris = "https://lpa-design-studios.imgix.net/content/heros/Projects/Higher-Education/USCDentistry1.jpg?auto=format&crop=focalpoint&domain=lpa-design-studios.imgix.net&fit=crop&fp-x=0.5&fp-y=0.5&h=1080&ixlib=php-3.3.0&q=60&w=1920";
                String Seaver = "https://libraries.usc.edu/sites/default/files/styles/16_9_large_2x/public/2019-08/se_outside.jpg?itok=TOfeK4OO";
                String Arch = "https://ca-times.brightspotcdn.com/dims4/default/588fa9d/2147483647/strip/false/crop/2048x1152+0+0/resize/1486x836!/quality/75/?url=https%3A%2F%2Fcalifornia-times-brightspot.s3.amazonaws.com%2F35%2F04%2F1926df7026322e6a73795ecf14ec%2Fla-1467129427-snap-photo";
                String Film = "https://d26oc3sg82pgk3.cloudfront.net/files/media/edit/image/43234/article_full%401x.jpg";

                // Use an AsyncTask to download the image and display it in the ImageView
                if(popupTitle.getText().equals("Leavey Library")){
                    new DownloadImageTask(popupImage).execute(Leavey);
                }
                else if(popupTitle.getText().equals("Fertitta Hall")){
                    new DownloadImageTask(popupImage).execute(Fertitta);
                }
                else if(popupTitle.getText().equals("Doheny Library")){
                    new DownloadImageTask(popupImage).execute(Doheny);
                    new DownloadImageTask(popupImage).execute(Doheny);
                }
                else if(popupTitle.getText().equals("Mudd Hall")){
                    new DownloadImageTask(popupImage).execute(Mudd);
                }
                else if(popupTitle.getText().equals("Taper Hall")){
                    new DownloadImageTask(popupImage).execute(Taper);
                }
                else if(popupTitle.getText().equals("Annenberg Hall")){
                    new DownloadImageTask(popupImage).execute(Annenberg);
                }
                else if(popupTitle.getText().equals("Norris Dental Center")){
                    new DownloadImageTask(popupImage).execute(Norris);
                }
                else if(popupTitle.getText().equals("Seaver Science Center")){
                    new DownloadImageTask(popupImage).execute(Seaver);
                }
                else if(popupTitle.getText().equals("School of Architecture")){
                    new DownloadImageTask(popupImage).execute(Arch);
                }
                else if(popupTitle.getText().equals("School of Cinema")){
                    new DownloadImageTask(popupImage).execute(Film);
                }



                // Show the popup menu anchored to the marker's position
                popupWindow.showAtLocation(mapFragment.getView(), Gravity.CENTER, 0, 0);

                return true;
            }
        });
    }
}