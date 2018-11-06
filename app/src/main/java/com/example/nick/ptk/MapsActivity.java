package com.example.nick.ptk;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.camera2.CameraCharacteristics;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private View mapView;
    GoogleMap mGoogleMap;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation = null;
    Marker mCurrLocationMarker;
    Marker mparkingLocationMarker;
    private final static int MY_PERMISSIONS_REQUEST_LOCATION = 0;
    private final static int MY_PERMISSIONS_REQUEST_LOCATIONgetmylocation = 1;
    private RequestQueue requestqueue;
    private ArrayList<Marker> parkngmarkerlist = new ArrayList<>();
    private long lastparkingtime;
    private TextView display_coins;
    private Context context = this;

    private DatabaseReference myUserData;
    private FirebaseAuth myAuth;

    private ImageView configg;

    private Button finish;

    private FirebaseUser myCurrentUser;

    private ImageView info_btn, profileee;

    protected BgdAdapter adapter1;

    private Map<String, Object> personolinformationmap = new HashMap<>();
    String useruid;

    List<Integer> imgs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        myAuth = FirebaseAuth.getInstance();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initData();

        final HorizontalInfiniteCycleViewPager hz = findViewById(R.id.horizontal);
        adapter1 = new BgdAdapter(imgs, this, this);
        hz.setAdapter(adapter1);


        final Button parkingbutton = findViewById(R.id.parkingbutton),leavebutton = findViewById(R.id.leavebutton);//,addbtn = findViewById(R.id.addbutton);
        display_coins = findViewById(R.id.displaycoins);
        info_btn = findViewById(R.id.info_ddd);
        profileee = findViewById(R.id.profile_click);

        profileee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n = new Intent(MapsActivity.this, ProfileActivity.class);
                startActivity(n);
            }
        });




        info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FrameLayout rootLayout = (FrameLayout) findViewById(android.R.id.content);
                View.inflate(context, R.layout.information_app, rootLayout);
                ConstraintLayout informationboard = findViewById(R.id.informationboardd);

                TextView ifff = findViewById(R.id.infoooo);

                ifff.setMovementMethod(new ScrollingMovementMethod());
                Typeface young = Typeface.createFromAsset(getAssets(), "fonts/Young.ttf");
                ifff.setTypeface(young);
                informationboard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rootLayout.removeView(findViewById(R.id.informationboardd));

                    }
                });
            }
        });


        parkingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLastLocation != null)
                    requestparking(mLastLocation.getLatitude(), mLastLocation.getLongitude(), true);
                else {
                    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setTitle("Whoops something went wrong");
                    alertDialog.setMessage("It seems your gps does not return the location");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }
        });
//        addbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final FrameLayout rootLayout = (FrameLayout) findViewById(android.R.id.content);
//                View.inflate(context, R.layout.reportboard, rootLayout);
//                ConstraintLayout informationboard = findViewById(R.id.reportboard);
//                informationboard.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        rootLayout.removeView(findViewById(R.id.reportboard));
//                    }
//                });
//            }
//        });


        final NumberPicker hour = findViewById(R.id.singlenumberPicker);
        hour.setMinValue(0);
        hour.setMaxValue(24);
        hour.setDisplayedValues(new String[]{"∞","1","2","3","4","5","6","7","8","9","10","11","12",
                "13","14","15","16","17","18","19","20","21","22","23","24"});
        final TextView rate = findViewById(R.id.rate),reward = findViewById(R.id.reward),startingtime = findViewById(R.id.startingtime),
                endtime = findViewById(R.id.endtime),estimatecost = findViewById(R.id.estimatecost), hors = findViewById(R.id.hour);

        Typeface youngg = Typeface.createFromAsset(getAssets(), "fonts/Young.ttf");
        rate.setTypeface(youngg);
        estimatecost.setTypeface(youngg);
        reward.setTypeface(youngg);


        leavebutton.setVisibility(View.GONE);
        leavebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("dddddddd",""+(System.currentTimeMillis()-lastparkingtime));
                int hours = (int) (((System.currentTimeMillis()-lastparkingtime) / (1000 * 60 * 60)) % 24);
                int minute = (int) (((System.currentTimeMillis()-lastparkingtime) / (1000 * 60 )) % 60);


                final FrameLayout rootLayout = (FrameLayout) findViewById(android.R.id.content);
                View.inflate(context, R.layout.finishp, rootLayout);
                ConstraintLayout informationboard = findViewById(R.id.informationboard4);





                TextView first4 = findViewById(R.id.textView3333);
                TextView first = findViewById(R.id.hourly);
                TextView first2 = findViewById(R.id.hourly2);
                TextView first3 = findViewById(R.id.hourly3);
                TextView parktxt = findViewById(R.id.parkidtxt);
                Typeface young = Typeface.createFromAsset(getAssets(), "fonts/Young.ttf");

                parktxt.setTypeface(young);

                if(rate.getText().toString().equals("Rate: N/A/hours")) {
                    first.setText(hours + " hr(s) " + minute + "min");
                    first2.setText("$ N/A");

                }

                    //alertDialog.setMessage("You Park " +hours+" hours " +minute+ "minutes"+"\n Total cost: $ N/A");
                else{

                    first.setText(hours + " hr(s) " + minute + "min");
                    first2.setText(Integer.valueOf(rate.getText().toString()) * (hours + 1));
                    //alertDialog.setMessage("You Park " + hours + " hours " + minute + "minutes" + "\n Total cost: $" + Integer.valueOf(rate.getText().toString()) * (hours + 1));
                }
                    //alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",




                Button btn = findViewById(R.id.okbtnnnn);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rootLayout.removeView(findViewById(R.id.informationboard4));
                    }
                });


                informationboard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rootLayout.removeView(findViewById(R.id.informationboard4));

                    }
                });
                /**
                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("You Have Left The Parking Spot");
                if(rate.getText().toString().equals("Rate: N/A/hours"))
                    alertDialog.setMessage("You Park " +hours+" hours " +minute+ "minutes"+"\n Total cost: $ N/A");
                else
                    alertDialog.setMessage("You Park " +hours+" hours " +minute+ "minutes"+"\n Total cost: $" + Integer.valueOf(rate.getText().toString())*(hours+1));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                 */

                ConstraintLayout bottommanageboard = findViewById(R.id.bottommanageboard);
                bottommanageboard.setBackgroundColor(Color.parseColor("#33000000"));

                FirebaseDatabase.getInstance().getReference().child("Users").child(useruid).child("Parking").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                        String meterid = dataSnapshot.child("meterid").getValue(String.class);
                        String state = dataSnapshot.child("state").getValue(String.class);
                        String plate = dataSnapshot.child("plate").getValue(String.class);
                        Long startingtime = dataSnapshot.child("starttime").getValue(long.class);


                        FirebaseDatabase.getInstance().getReference().child("Users").child(useruid).child("PreviousParking").child(startingtime.toString()).setValue(map);
                        FirebaseDatabase.getInstance().getReference().child("Parking").child(meterid).child(state + plate).removeValue();
                        FirebaseDatabase.getInstance().getReference().child("Users").child(useruid).child("Parking").removeValue();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                rate.setText("");

                reward.setText("");

                startingtime.setText("");

                endtime.setText("");
                estimatecost.setText("");

                hour.setVisibility(View.VISIBLE);
                hors.setVisibility(View.VISIBLE);
                leavebutton.setVisibility(View.GONE);
                parkingbutton.setVisibility(View.VISIBLE);
                mparkingLocationMarker.remove();
                mparkingLocationMarker = null;
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }


    }

    ArrayList<LatLng> meterlatlnglist = new ArrayList<LatLng>();
    LinkedList<LatLng> waitq = new LinkedList<LatLng>();
    Map<Marker, Map<String, String>> markermap = new HashMap<>();
    int limit = 0;

    private void requestparkinglot(final double lat, final double lon, int requestdistance, final int degree) {

        String url = "https://apis.solarialabs.com/shine/v1/parking-rules/meters?lat=" + lat + "&long=" + lon + "&max-distance=" + requestdistance + "&max-results=" + 10 + "&apikey=QSktxWgxZAGVbvFsq7mtOPx8Y4gcmSVE";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("response", response.toString());

                        if (parkngmarkerlist.size() != 0 && limit == 0) {
                            int i1 = parkngmarkerlist.size() - 1;
                            for (int i = i1; i >= 0; i--) {
                                parkngmarkerlist.get(i).remove();
                                parkngmarkerlist.remove(i);
                            }
                        }
                        for (int i = 0; i < response.length(); i++) {

                            if (meterlatlnglist.size() >= 10) {
                                continue;
                            }

                            try {
                                JSONObject object = response.getJSONObject(i);
                                String City = object.getString("City");
                                String Meter_ID = object.getString("Meter_ID");
                                String State = object.getString("State");
                                String Area = object.getString("Area");
                                String Time_Limits = object.getString("Time_Limits");
                                String Hours_of_Operation = object.getString("Hours_of_Operation");
                                String Exceptions_Location = object.getString("Exceptions_Location");
                                String Peak_Time = object.getString("Peak_Time");
                                double Latitude = object.getDouble("Latitude");
                                double Longitude = object.getDouble("Longitude");
                                String Model = object.getString("Model");
                                String Smart_Meter = object.getString("Smart_Meter");
                                String Cap_Color = object.getString("Cap_Color");
                                String Rate = object.getString("Rate");

                                int size = 85;
                                Location mylocation = new Location("");
                                Location dest_location = new Location("");
                                dest_location.setLatitude(Latitude);
                                dest_location.setLongitude(Longitude);
                                boolean continue1 = false;
                                for (int i1 = 0; i1 < meterlatlnglist.size(); i1++) {
                                    mylocation.setLatitude(meterlatlnglist.get(i1).latitude);
                                    mylocation.setLongitude(meterlatlnglist.get(i1).longitude);
                                    if (mylocation.distanceTo(dest_location) < 40) {
                                        continue1 = true;
                                        break;
                                    }
                                }
                                if (continue1) {
                                    continue;
                                }
                                meterlatlnglist.add(new LatLng(Latitude, Longitude));
                                int a = meterlatlnglist.size();
                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.position(new LatLng(Latitude, Longitude));
                                markerOptions.title("ID: " + Meter_ID);
                                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(
                                        Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                                                getResources(), R.drawable.parking_icon), 112, 180, false)));
                                Marker add = mGoogleMap.addMarker(markerOptions);
                                Map<String, String> add1 = new HashMap<>();
                                add1.put("METERID", Meter_ID);
                                add1.put("REWARD", "" + a * 50);
                                add1.put("RATE", Rate);
                                markermap.put(add, add1);
                                parkngmarkerlist.add(add);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        int bearing = 0;
                        int count = 0;
                        while (meterlatlnglist.size() < 10 && limit < 18 && bearing != 360 && count < 4) {
                            limit++;
                            count++;


                            LatLng latlng1 = Utils.GetDestinationPoint(lat, lon, bearing, 100);
                            bearing += 90;
                            if (((degree + 180) % 360) == bearing) {
                                limit--;

                            } else {
                                requestparkinglot(latlng1.latitude, latlng1.longitude, 1000, bearing);
                            }


                        }




                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("json request error", "Error: " + error.getMessage());
            }
        });
        vollyhelper.getInstance().addToRequestQueue(request, "" + lat + lon);
    }

    public void requestparking(final double lat, final double lon, boolean a) {
        final Context context = this;

        adapter1.setEnableOn();


            String url = "https://apis.solarialabs.com/shine/v1/parking-rules/meters?lat=" + lat + "&long=" + lon + "&max-distance=" + 20 + "&max-results=" + 1 + "&apikey=QSktxWgxZAGVbvFsq7mtOPx8Y4gcmSVE";

            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                    url, null,
                    new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d("response", response.toString());


                            if (response.length() == 0) {
                                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                                alertDialog.setTitle("You seems not close enough");
                                alertDialog.setMessage("Make sure you are in a parking slot");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();

                            } else {

                                try {
                                    JSONObject object = response.getJSONObject(0);
                                    String City = object.getString("City");
                                    String Meter_ID = object.getString("Meter_ID");
                                    String State = object.getString("State");
                                    String Area = object.getString("Area");
                                    String Time_Limits = object.getString("Time_Limits");
                                    String Hours_of_Operation = object.getString("Hours_of_Operation");
                                    String Exceptions_Location = object.getString("Exceptions_Location");
                                    String Peak_Time = object.getString("Peak_Time");
                                    double Latitude = object.getDouble("Latitude");
                                    double Longitude = object.getDouble("Longitude");
                                    String Model = object.getString("Model");
                                    String Smart_Meter = object.getString("Smart_Meter");
                                    String Cap_Color = object.getString("Cap_Color");
                                    String Rate = object.getString("Rate");


                                    final FrameLayout rootLayout = (FrameLayout) findViewById(android.R.id.content);
                                    View.inflate(context, R.layout.success, rootLayout);
                                    ConstraintLayout informationboard = findViewById(R.id.informationboard3);


                                    TextView first4 = findViewById(R.id.textView3333);
                                    TextView first = findViewById(R.id.meterid);
                                    TextView first2 = findViewById(R.id.meterid2);
                                    TextView first3 = findViewById(R.id.meterid3);
                                    TextView parktxt = findViewById(R.id.parkidtxt);
                                    Typeface young = Typeface.createFromAsset(getAssets(), "fonts/Young.ttf");

                                    parktxt.setTypeface(young);


                                    if (Time_Limits.equals("N/A")) {
                                        first.setText(Meter_ID.toString());
                                        first3.setText(Rate.toString());
                                        first2.setText("50 points");
                                    } else {
                                        first.setText(Meter_ID.toString());
                                        first2.setText(Rate.toString());

                                        first4.setText("You can only park for:");
                                        first3.setText(Time_Limits + "hours");


                                    }

                                    Button btn = findViewById(R.id.okbtnnn);
                                    btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            rootLayout.removeView(findViewById(R.id.informationboard3));
                                        }
                                    });


                                    informationboard.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            rootLayout.removeView(findViewById(R.id.informationboard3));

                                        }
                                    });
                                    //***


/**
 AlertDialog alertDialog = new AlertDialog.Builder(context).create();
 alertDialog.setTitle("Parking Successful");
 if(Time_Limits.equals("N/A"))
 alertDialog.setMessage("Your Meter_ID:"+Meter_ID+"\nHourly rate:"+Rate+"\nYou will earn 50 Reward/hours:");
 else
 alertDialog.setMessage("Your Meter_ID:"+Meter_ID+"\nHourly rate:"+Rate+"\nYou will earn 50 Reward/hours:" + "\nYou can only park" + Time_Limits +"hours");
 alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
 new DialogInterface.OnClickListener() {
 public void onClick(DialogInterface dialog, int which) {
 dialog.dismiss();
 }
 });
 alertDialog.show();
 */

                                    final NumberPicker hour = findViewById(R.id.singlenumberPicker);
                                    final TextView rate = findViewById(R.id.rate), reward = findViewById(R.id.reward), startingtime = findViewById(R.id.startingtime),
                                            endtime = findViewById(R.id.endtime), estimatecost = findViewById(R.id.estimatecost), hors = findViewById(R.id.hour);
                                    ;
                                    rate.setText("Rate: " + Rate + "/hours");
                                    reward.setText("Reward: " + "50 points");

                                    long timestamp = System.currentTimeMillis();
                                    lastparkingtime = timestamp;

                                    SimpleDateFormat dateFormat1 = new SimpleDateFormat("MMM/dd/yyyy hh:mm a");
                                    String myTime = dateFormat1.format(new Date(timestamp));
                                    startingtime.setText(myTime);

                                    if (hour.getValue() == 0) {
                                        endtime.setText("Endtime: " + "Until you leave");
                                        estimatecost.setText("Estimate cost: " + Rate + "/hours");
                                    } else {
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM/dd/yyyy hh:mm a");
                                        String myTime1 = dateFormat.format(new Date(timestamp + 1000 * 60 * 60 * hour.getValue()));

                                        endtime.setText("Endtime: " + myTime1);
                                        if (Rate.equals("N/A"))
                                            estimatecost.setText("Estimate cost: " + Rate);
                                        else {
                                            estimatecost.setText("Estimate cost: " + Integer.valueOf(Rate) * hour.getValue());
                                        }
                                    }
                                    ConstraintLayout bottommanageboard = findViewById(R.id.bottommanageboard);
                                    bottommanageboard.setBackgroundColor(Color.parseColor("#aa000000"));

                                    Map<String, Object> addmap = new HashMap<>();
                                    addmap.put("latitude", lat);
                                    addmap.put("longtitude", lon);
                                    addmap.put("meterid", Meter_ID);
                                    addmap.put("name", personolinformationmap.get("name"));
                                    addmap.put("state", personolinformationmap.get("state"));
                                    addmap.put("plate", personolinformationmap.get("plate"));
                                    addmap.put("starttime", timestamp);
                                    if (hour.getValue() == 0)
                                        addmap.put("endtime", 0);
                                    else
                                        addmap.put("endtime", (timestamp + 1000 * 60 * 60 * hour.getValue()));

                                    String stateplate = personolinformationmap.get("state").toString() + personolinformationmap.get("plate").toString();

                                    FirebaseDatabase.getInstance().getReference().child("Users").child(useruid).child("Parking").setValue(addmap);
                                    FirebaseDatabase.getInstance().getReference().child("Parking").child(Meter_ID).child(stateplate).setValue(addmap);
                                    FirebaseDatabase.getInstance().getReference().child("Parking").child(Meter_ID).child("meterlatitude").setValue(Latitude);
                                    FirebaseDatabase.getInstance().getReference().child("Parking").child(Meter_ID).child("meterlongtitude").setValue(Longitude);


                                    Button parkingbutton = findViewById(R.id.parkingbutton), leavebutton = findViewById(R.id.leavebutton);
                                    parkingbutton.setVisibility(View.GONE);
                                    hour.setVisibility(View.GONE);
                                    hors.setVisibility(View.GONE);
                                    leavebutton.setVisibility(View.VISIBLE);


                                    MarkerOptions markerOptions = new MarkerOptions();
                                    markerOptions.position(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
                                    markerOptions.title("Your Parking Location");
                                    markerOptions.icon(BitmapDescriptorFactory.fromBitmap(
                                            Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                                                    getResources(), R.drawable.parking_parked), 112, 180, false)));

                                    mparkingLocationMarker = mGoogleMap.addMarker(markerOptions);
                                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                            new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 15));


                                    if (mCurrLocationMarker != null) {
                                        mCurrLocationMarker.remove();
                                        mCurrLocationMarker = null;
                                    }
                                    if (parkngmarkerlist.size() != 0) {
                                        int i1 = parkngmarkerlist.size() - 1;
                                        for (int i = i1; i >= 0; i--) {
                                            parkngmarkerlist.get(i).remove();
                                            parkngmarkerlist.remove(i);
                                        }
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                                    alertDialog.setTitle("Whooops");
                                    alertDialog.setMessage("Something went wrong");
                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                    alertDialog.show();
                                }
                            }


                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("json request error", "Error: " + error.getMessage());
                    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setTitle("Whooops");
                    alertDialog.setMessage("Something went wrong");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            });
            vollyhelper.getInstance().addToRequestQueue(request, "" + lat + lon);

    }


    //google map
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        //chage map style

        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
            } else {
                //Request Location Permission
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)) {

                        // Show an expanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_REQUEST_LOCATION);

                    } else {

                        // No explanation needed, we can request the permission.

                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_REQUEST_LOCATION);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                }

                //mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
            }
            //mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        } else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
            //mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
        final Context context = this;
        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (markermap.get(marker) != null) {

                    final FrameLayout rootLayout = (FrameLayout) findViewById(android.R.id.content);
                    View.inflate(context, R.layout.informationmarker, rootLayout);
                    ConstraintLayout informationboard = findViewById(R.id.informationboard);
                    informationboard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rootLayout.removeView(findViewById(R.id.informationboard));
                        }
                    });
                    TextView meterid = findViewById(R.id.meterid);
                    TextView reward = findViewById(R.id.informationreward);
                    TextView rate = findViewById(R.id.informationrate);
                    meterid.setText("Meter ID: " + markermap.get(marker).get("METERID"));
                    reward.setText("Reward: " + markermap.get(marker).get("REWARD"));
                    rate.setText("Rate: " + markermap.get(marker).get("RATE"));


                    return true;

                }

                return false;
            }
        });
    }

    public void onStart() {
        super.onStart();

        /**

         ConstraintLayout ct = findViewById(R.id.activity_main);

         SharedPreferences sharedPref = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
         int bg = sharedPref.getInt("acitivity_main", R.drawable.bglegendary); // the second parameter will be fallback if the preference is not found
         ct.setBackgroundResource(bg);

         */

        if(!isConnected(MapsActivity.this)) buildDialog(MapsActivity.this).show();
        else {


            FirebaseUser currentUser = myAuth.getCurrentUser();


            if (currentUser == null) {

                sendToStart();
            } else {

                myCurrentUser = myAuth.getCurrentUser();

                String current_uid = myCurrentUser.getUid();

                useruid = current_uid;



                myUserData = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);
                myUserData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        Integer coins = dataSnapshot.child("coins").getValue(Integer.class);

                        personolinformationmap.put("coins", coins);
                        personolinformationmap.put("email", dataSnapshot.child("email").getValue(String.class));
                        personolinformationmap.put("name", dataSnapshot.child("name").getValue(String.class));
                        personolinformationmap.put("plate", dataSnapshot.child("plate").getValue(String.class));
                        personolinformationmap.put("state", dataSnapshot.child("state").getValue(String.class));





                        display_coins.setText(coins.toString());


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });



            }
        }
    }

    private void sendToStart() {

        Intent startIntent = new Intent(MapsActivity.this, StartActivity.class);
        startActivity(startIntent);



        finish();

    }

    //CONNECTIVITY
    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else
            return false;
    }

    public android.app.AlertDialog.Builder buildDialog(Context c) {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You are not connected to the internet. Connect to continue.");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                System.exit(0);
            }
        });

        return builder;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(60000);
        mLocationRequest.setFastestInterval(15000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        Button locationbutton = findViewById(R.id.locationbutton);
        locationbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMyLocation();
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        String a = connectionResult.toString();
        String b = a;
    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;


        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());


        //move map camera and set the zoom in percentage
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),
                location.getLongitude()), 15));

    }
    private void initData() {

        imgs.add(R.drawable.icon1);
        imgs.add(R.drawable.icon2);
        imgs.add(R.drawable.icon3);






    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied location", Toast.LENGTH_LONG).show();
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_LOCATIONgetmylocation: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        helpgetmylocation();
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied location", Toast.LENGTH_LONG).show();
                }
                return;
            }


            // other 'case' lines to check for other
            // permissions this app might request
        }


    }

    private void helpgetmylocation() {
        if (mLastLocation == null) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Cannot get location");
            alertDialog.setMessage("Make sure your gps is functional");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        } else {

            if (mCurrLocationMarker != null) {
                mCurrLocationMarker.remove();
            }

            //Place current location marker
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
            markerOptions.title("Your Location");

            mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 15));

            requestparkinglot(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1000, -1);
            meterlatlnglist.clear();
            waitq.clear();
            limit = 0;
        }
    }
    @Override
    public void onBackPressed() {

    }


    private void getMyLocation() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                helpgetmylocation();

            } else {
                //Request Location Permission
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)) {


                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_REQUEST_LOCATIONgetmylocation);

                    } else {

                        // No explanation needed, we can request the permission.

                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_REQUEST_LOCATIONgetmylocation);


                    }
                }


            }

        } else {
            helpgetmylocation();
        }

    }

}

