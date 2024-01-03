package com.talabeya.talabyablue.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.talabeya.talabyablue.Domain.UsersDomain;
import com.talabeya.talabyablue.R;
import com.talabeya.talabyablue.redApp.Activities.MainActivity_2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Locale;

import io.github.muddz.styleabletoast.StyleableToast;

public class ShowUserData extends AppCompatActivity {

    Button tasahilBtn, editLocation, BlockUser, makeVisit, addItem, callClient, responseBtn, noResponseBtn;
    LinearLayout tasahilDetails, responseLY;
    TextView v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11, v12, v13, v14, v15, v16, v17;
    UsersDomain object;
    SharedPreferences userPref;
    static int PERMISSION_CODE = 100;
    double lat1, lon1, lat2, lon2;
    private final int FINE_PERMISSION_CODE = 1;
    FusedLocationProviderClient fusedLocationProviderClient;
    String inCodeImage;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_date);
        getIds();
        Clicked();
        checkClientType();
        getBundle();
    }

    private void Clicked() {
        tasahilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tasahilBtn.setVisibility(View.GONE);
                tasahilDetails.setVisibility(View.VISIBLE);
            }
        });
        editLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNewLocation();
            }
        });
        BlockUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blockUser();
            }
        });
        makeVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send shop's photo
                checkLocation();
            }
        });
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(ShowUserData.this, MainActivity_2.class));
            }
        });
        callClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeCall();
            }
        });
        responseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                okResponse();
            }
        });
        noResponseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notResponse();
            }
        });
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", lat1, lon1);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });
    }

    private void notResponse() {
        // client  لم يرد ع المكالمه
        responseLY.setVisibility(View.GONE);
        callClient.setVisibility(View.VISIBLE);
    }

    private void okResponse() {
        // client رد ع المكالمه
        callClient.setVisibility(View.GONE);
        responseLY.setVisibility(View.GONE);
        addItem.setVisibility(View.VISIBLE);
        visitDialog("تم تسجيل زيارة غير ناجحة");

    }

    private void makeCall() {
        if (ContextCompat.checkSelfPermission(ShowUserData.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ShowUserData.this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_CODE);
        }
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:+2" + v2.getText().toString()));
        startActivity(callIntent);

        responseLY.setVisibility(View.VISIBLE);

    }

    private void checkTheShopLocation(double lat1, double lon1, double lat2, double lon2) {
        double longDiff = lon1 - lon2;

        double distance = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(longDiff));

        distance = Math.acos(distance);
        distance = rad2deg(distance);
        //KiloMeter
        distance = distance * 1.609344;

        if (distance * 1000 > 20) {
            visitDialog("تم تسجيل زيارة غير ناجحة");
        } else {
            visitDialog("تم تسجيل زيارة ناجحة");
            Toast.makeText(this, distance * 1000 + "", Toast.LENGTH_SHORT).show();
        }

    }

    private void visitDialog(String text) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(ShowUserData.this);
        alert.setTitle("حالة الزيارة");
        alert.setMessage(text)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        makeImage();
                    }
                });
        alert.show();
    }

    private void makeImage() {
        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 111);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast("Permission Required");
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 111 && resultCode == RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            encodebitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encodebitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayInputStream);
        byte[] byteofImage = byteArrayInputStream.toByteArray();
        inCodeImage = android.util.Base64.encodeToString(byteofImage, Base64.DEFAULT);
        Log.e("Image----",inCodeImage);
    }

    private double rad2deg(double distance) {
        return (distance * 180.0 / Math.PI);
    }

    private double deg2rad(double lat1) {
        return (lat1 * Math.PI / 180.0);
    }

    private void blockUser() {
        //blockUser
    }

    private void setNewLocation() {
        //updateLocation
    }

    private void checkClientType() {
        int userType = userPref.getInt("role", 0);
        if (userType == 2) {
            //مندوب
            makeVisit.setVisibility(View.VISIBLE);
            callClient.setVisibility(View.GONE);
            responseLY.setVisibility(View.GONE);
            Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();

        } else if (userType == 3) {
            //telesales
            callClient.setVisibility(View.VISIBLE);
            makeVisit.setVisibility(View.GONE);
            Toast.makeText(this, "3", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, userType + "", Toast.LENGTH_SHORT).show();
        }


    }

    private void getIds() {
        userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        callClient = findViewById(R.id.makeCall);
        responseBtn = findViewById(R.id.response);
        responseLY = findViewById(R.id.responseOrNot);
        noResponseBtn = findViewById(R.id.notResponse);

        editLocation = findViewById(R.id.EditLocation);
        addItem = findViewById(R.id.addItem);
        makeVisit = findViewById(R.id.makeVisit);
        BlockUser = findViewById(R.id.BlockUser);
        tasahilBtn = findViewById(R.id.tasahilBtn);
        tasahilDetails = findViewById(R.id.tasahilDetails);
        v1 = findViewById(R.id.v1);
        v2 = findViewById(R.id.v2);
        v3 = findViewById(R.id.v3);
        v4 = findViewById(R.id.v4);
        v5 = findViewById(R.id.v5);
        v6 = findViewById(R.id.v6);
        v7 = findViewById(R.id.v7);
        v8 = findViewById(R.id.v8);
        v9 = findViewById(R.id.v9);
        v10 = findViewById(R.id.v10);
        v11 = findViewById(R.id.v11);
        v12 = findViewById(R.id.v12);
        v13 = findViewById(R.id.v13);
        v14 = findViewById(R.id.v14);
        v15 = findViewById(R.id.v15);
        v16 = findViewById(R.id.v16);
        v17 = findViewById(R.id.v17);
    }

    public void getBundle() {
        try {
            object = (UsersDomain) getIntent().getSerializableExtra("object");
            v1.setText(object.getV1());
            v2.setText(object.getV2());
            v3.setText(object.getV3());
            v4.setText(object.getV10());
            v5.setText(object.getV5());
            v6.setText(object.getV4());
            v7.setText(object.getV11());
            v8.setText(object.getV12());
            v9.setText(object.getV13());
            v10.setText(object.getV14());
            v11.setText(object.getV15());
            v12.setText(object.getV16());
            v13.setText(object.getV18());
            v14.setText(object.getV19());
            v15.setText(object.getV20());
            v16.setText(object.getV21());
            v17.setText(object.getV17());
            lat1 = Double.parseDouble(object.getV24());
            lon1 = Double.parseDouble(object.getV25());
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void Toast(String Message) {
        new StyleableToast
                .Builder(ShowUserData.this)
                .text(Message)
                .textColor(Color.WHITE)
                .backgroundColor(getResources().getColor(R.color.colorAccent))
                .show();
    }

    private void checkLocation() {
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.custom_dialog, null);
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setView(view);
            final AlertDialog test = dialog.create();
            Button open = (Button) view.findViewById(R.id.ok);
            open.setOnClickListener(new android.view.View.OnClickListener() {
                public void onClick(View v) {
                    ShowUserData.this.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    test.dismiss();
                    getLastLocation();
                }
            });
            test.setCancelable(false);
            test.show();

        } else {
            getLastLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == FINE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast("Required Permission");
                lat2 = 0.0;
                lon2 = 0.0;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        lat2 = location.getLatitude();
                        lon2 = location.getLongitude();
                        checkTheShopLocation(lat1, lon1, lat2, lon2);
                    } else {
                        checkLocation();
                    }
                }
            });
        } else {
            askPermission();
        }


    }

    private void askPermission() {
        ActivityCompat.requestPermissions(ShowUserData.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
    }


}