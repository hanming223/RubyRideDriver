package jeff.com.rubyridedriver;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.zendrive.sdk.ZendriveLocationSettingsResult;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jeff.com.rubyridedriver.utils.Constants;
import jeff.com.rubyridedriver.utils.TripManager;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements DrawerLayout.DrawerListener {

    DrawerLayout drawer;

    Runnable mPendingRunnable;
    Handler mHandler;

    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set status bar gradient color
//        AppManager.getInstance().setStatusBarGradiant(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.menu_icon_color));

//        drawer.addDrawerListener(toggle);
        drawer.addDrawerListener(this);
        toggle.syncState();



//        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        mDrawerToggle.setDrawerIndicatorEnabled(false);
//        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                drawer.openDrawer(GravityCompat.START);
//            }
//        });
//        mDrawerToggle.setHomeAsUpIndicator(R.drawable.menu_icon);




        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        TextView scheduleTextView = (TextView) findViewById(R.id.scheduleTextView);
        TextView historyTextView = (TextView) findViewById(R.id.historyTextView);

        RelativeLayout clockInOutLayout = (RelativeLayout)findViewById(R.id.clockInOutLayout);

        Fragment fragment = null;
        Class fragmentClass;
        fragmentClass = ScheduleFragment.class;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();



        scheduleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView titleView = (TextView)findViewById(R.id.titleView);
                titleView.setText("Your Schedule");

                mPendingRunnable = new Runnable() {
                    @Override
                    public void run() {

                        Fragment fragment = null;
                        Class fragmentClass;
                        fragmentClass = ScheduleFragment.class;


                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

                    }
                };


                drawer.closeDrawers();
            }
        });

        historyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView titleView = (TextView)findViewById(R.id.titleView);
                titleView.setText("Your Past Trips");

                mPendingRunnable = new Runnable() {
                    @Override
                    public void run() {

                        Fragment fragment = null;
                        Class fragmentClass;
                        fragmentClass = HistoryFragment.class;


                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

                    }
                };

                drawer.closeDrawers();

            }
        });

        clockInOutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.closeDrawers();

                TripManager.State tripManagerState = TripManager.sharedInstance().getTripManagerState();
                int passengersInCar = tripManagerState.getPassengersInCar();
                int passengerWaitingForPickup = tripManagerState.getPassengersWaitingForPickup();
                if (passengersInCar == 0 && passengerWaitingForPickup == 0){
                    Timber.i("offDutyButton tapped");
                    TripManager.sharedInstance().goOffDuty();
                }

            }
        });

        mHandler = new Handler();

        registerForBroadcasts();
    }

    private void registerForBroadcasts() {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.
                getInstance(this.getApplicationContext());
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                processIntent(intent);
            }
        };
        localBroadcastManager.registerReceiver(receiver, getIntentFilterForLocalBroadcast());
    }

    private static IntentFilter getIntentFilterForLocalBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.LOCATION_PERMISSION_CHANGE_BROADCAST);
        intentFilter.addAction(Constants.LOCATION_SETTINGS_CHANGE_BROADCAST);
        return intentFilter;
    }

    private void processIntent(Intent intent) {
        String action = intent.getAction();
        if (action != null) {
            switch (action) {
                case Constants.LOCATION_SETTINGS_CHANGE_BROADCAST:
                    ZendriveLocationSettingsResult result =
                            intent.getParcelableExtra(Constants.DATA);
                    if (result != null && !result.isSuccess()) {
                        resolveLocationSettings(this, result);
                    }
                    // Maybe ask user to re-enable the location settings.
                    break;
                case Constants.LOCATION_PERMISSION_CHANGE_BROADCAST:
                    boolean granted =
                            intent.getBooleanExtra(Constants.LOCATION_PERMISSION_CHANGE_BROADCAST,
                                    false);
                    requestLocationPermission(granted);
                    break;
            }
        }
    }

    private void requestLocationPermission(boolean granted) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!granted) {
                requestPermissions(
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        Constants.LOCATION_PERMISSION_REQUEST);
            }
        }
    }

    private void resolveLocationSettings(Activity activity, ZendriveLocationSettingsResult locationSettingsResult) {
        if (locationSettingsResult.isSuccess()) {
            return;
        }
        for (ZendriveLocationSettingsResult.Error error : locationSettingsResult.errors) {
            switch (error) {
                case GOOGLE_PLAY_SERVICES_ERROR_RESULT: {
                    LocationSettingsResult result = locationSettingsResult.locationSettingsResultFromGooglePlayService;
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            // Should not happen
                            Timber.e("Success received when expected" +
                                    "error from Google Play " +
                                    "Services");
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the user
                            // a dialog.
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(
                                        activity,
                                        Constants.GOOGLE_PLAY_SERVICES_REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            break;
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onDrawerSlide(@NonNull View view, float v) {

    }

    @Override
    public void onDrawerOpened(@NonNull View view) {

    }

    @Override
    public void onDrawerClosed(@NonNull View view) {

        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
            mPendingRunnable = null;
        }

    }

    @Override
    public void onDrawerStateChanged(int i) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this.getApplicationContext()).unregisterReceiver(receiver);
    }
}
