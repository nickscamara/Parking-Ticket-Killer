package com.example.nick.ptk;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.List;

/**
 * Created by nick on 1/25/18.
 */

public class BgdAdapter extends PagerAdapter {

    List<Integer> imgss;
    Context context;
    LayoutInflater layoutInflater;
    String aaa;
    //new
    Activity activity;

    private int count;

    private TextView nname, uusername, uuusername;
    private DatabaseReference myUserData;
    private FirebaseAuth myAuth;

    protected boolean enable = false;

    private Button finish;

    private FirebaseUser myCurrentUser;
    //end
//new
    public BgdAdapter(List<Integer> img, Context context, Activity activity){
        this.imgss = img;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.activity = activity;
        //end

    }



    @Override
    public int getCount() {
        return imgss.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);

    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = layoutInflater.inflate(R.layout.card_item,container,false);
        final ImageView imgv = (ImageView)view.findViewById(R.id.imgRoll);
        imgv.setImageResource(imgss.get(position));
        //new


        imgv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final FrameLayout rootLayout = (FrameLayout) activity.findViewById(android.R.id.content);

                if (position == 2) {
                    View.inflate(context, R.layout.reportboard, rootLayout);
                    ConstraintLayout informationboard = activity.findViewById(R.id.reportboard);
                    Button first = activity.findViewById(R.id.help);
                    Button second = activity.findViewById(R.id.report);
                    first.setText("Report Illegal Parking");
                    first.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rootLayout.removeView(activity.findViewById(R.id.reportboard));
                            View.inflate(context, R.layout.illegle, rootLayout);
                            Button submit = activity.findViewById(R.id.submit);
                            final EditText comment = activity.findViewById(R.id.comment);
                            submit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(comment.getText().toString().equals(""))
                                    {
                                        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                                        alertDialog.setTitle("Empty text");
                                        alertDialog.setMessage("Please Fill in the comment");
                                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                        alertDialog.show();
                                    }
                                    else {
                                        FirebaseDatabase.getInstance().getReference().child("IllegalReport").child(System.currentTimeMillis()+"").setValue(comment.getText().toString());
                                        rootLayout.removeView(activity.findViewById(R.id.illigle));
                                    }
                                }
                            });
                            ConstraintLayout informationboard = activity.findViewById(R.id.illigle);
                            informationboard.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    rootLayout.removeView(activity.findViewById(R.id.illigle));
                                }
                            });
                        }
                    });
                    second.setText("Problem About Your Parking");
                    second.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rootLayout.removeView(activity.findViewById(R.id.reportboard));
                            View.inflate(context, R.layout.illegle, rootLayout);
                            Button submit = activity.findViewById(R.id.submit);
                            final EditText comment = activity.findViewById(R.id.comment);
                            comment.setHint("Please Discribe the detail of your problem");
                            submit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(comment.getText().toString().equals(""))
                                    {
                                        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                                        alertDialog.setTitle("Empty text");
                                        alertDialog.setMessage("Please Fill in the comment");
                                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                        alertDialog.show();
                                    }
                                    else {
                                        FirebaseDatabase.getInstance().getReference().child("CustomerSupport").child(System.currentTimeMillis()+"").setValue(comment.getText().toString());
                                        rootLayout.removeView(activity.findViewById(R.id.illigle));
                                    }
                                }
                            });
                            ConstraintLayout informationboard = activity.findViewById(R.id.illigle);
                            informationboard.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    rootLayout.removeView(activity.findViewById(R.id.illigle));
                                }
                            });
                        }
                    });
                    informationboard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            rootLayout.removeView(activity.findViewById(R.id.reportboard));
                        }
                    });
                } else if (position == 1) {
                    View.inflate(context, R.layout.reportboard, rootLayout);
                    ConstraintLayout informationboard = activity.findViewById(R.id.reportboard);
                    Button first = activity.findViewById(R.id.help);
                    Button second = activity.findViewById(R.id.report);
                    first.setText("Danger Zone");
                    second.setText("Stats");
                    informationboard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            rootLayout.removeView(activity.findViewById(R.id.reportboard));
                        }
                    });
                } else {

                    if (enable == true) {

                        count++;
                        if (count < 2) {
                            View.inflate(context, R.layout.datacollection, rootLayout);
                            ConstraintLayout informationboard5 = activity.findViewById(R.id.informationboard5);
                            final AppCompatEditText txt = activity.findViewById(R.id.edit3333333);
                            final ImageView img = activity.findViewById(R.id.imageView17);
                            final Button btn = activity.findViewById(R.id.okbtnnnnnnnn);
                            rootLayout.removeView(activity.findViewById(R.id.reportboard));


                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String hisinput = txt.getText().toString();

                                    if (!TextUtils.isEmpty(hisinput)) {

                                        rootLayout.removeView(activity.findViewById(R.id.informationboard55));
                                        View.inflate(context, R.layout.datacollection_two, rootLayout);
                                        final ConstraintLayout informationboard55 = activity.findViewById(R.id.informationboard55);
                                        myAuth = FirebaseAuth.getInstance();
                                        myCurrentUser = myAuth.getCurrentUser();


                                        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                                        String uid = current_user.getUid();

                                        myUserData = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("coins");

                                        //Toast..makeText(BgdAdapter.this, "You have earned +100 coins!", Toast.LENGTH_SHORT).show();


                                        myUserData.runTransaction(new Transaction.Handler() {
                                            @Override
                                            public Transaction.Result doTransaction(final MutableData mutableData) {
                                                long value = 0;

                                                value = mutableData.getValue(Long.class);

                                                value = value + 100;
                                                mutableData.setValue(value);

                                                informationboard55.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        rootLayout.removeView(activity.findViewById(R.id.informationboard55));
                                                        rootLayout.removeView(activity.findViewById(R.id.informationboard5));

                                                    }
                                                });


                                                return Transaction.success(mutableData);
                                            }


                                            @Override
                                            public void onComplete(DatabaseError databaseError, boolean b,
                                                                   DataSnapshot dataSnapshot) {



                                            }
                                        });

                                    } else {
                                        rootLayout.removeView(activity.findViewById(R.id.informationboard5));
                                    }
                                }
                            });


                            informationboard5.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    rootLayout.removeView(activity.findViewById(R.id.informationboard5));
                                }
                            });

                        }else{

                            Toast.makeText(v.getContext(), "Try in another parking slot", Toast.LENGTH_SHORT).show();

                        }
                    }

                }
            }

        });

        //end

        //getCurrentList(imgss.get(position));







        Log.v(aaa,"position" + position);


        container.addView(view);
        return view;
    }

    public void setEnableOn()
    {

        enable = true;
    }




}