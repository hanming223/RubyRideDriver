package jeff.com.rubyridedriver;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;


public class TaskView extends RelativeLayout{

    public ImageView photoImageView;
    public TextView nameTextView;
    public TextView addressTextView;

    public RelativeLayout headerView;
    public LinearLayout conetentView;
    public RelativeLayout headerBackground;
    public RelativeLayout startEndButtonView;
    public LinearLayout callOnBoardView;

    public ImageButton passengerCallButton, passengerOnBoardButton, passengerNotFoundButton;
    public RelativeLayout passengerNotFoundButtonView, passengerCallButtonView;

    public Button startEndButton;

    public Integer myIndex;

    Context mContext;
    TaskModel mTask;


    public TaskView(Context context, TaskModel task) {

        super(context);

        mContext = context;
        mTask = task;

        init();
    }

    private void init() {

        inflate(mContext, R.layout.layout_taskview, this);

        photoImageView = (ImageView) findViewById(R.id.photo);
        nameTextView = (TextView) findViewById(R.id.name);
        addressTextView = (TextView) findViewById(R.id.addressTextView);

        headerView = (RelativeLayout)findViewById(R.id.headerView);
        conetentView = (LinearLayout)findViewById(R.id.contentView);
        headerBackground = (RelativeLayout)findViewById(R.id.headerBackground);
        startEndButtonView = (RelativeLayout)findViewById(R.id.startEndButtonView);
        callOnBoardView = (LinearLayout)findViewById(R.id.callOnBoardView);

        startEndButton = (Button)findViewById(R.id.startEndButton);

        passengerCallButton = (ImageButton) findViewById(R.id.passengerCallButton);
        passengerOnBoardButton = (ImageButton) findViewById(R.id.passengerOnBoardButton);
        passengerNotFoundButton = (ImageButton) findViewById(R.id.passengerNotFoundButton);
        passengerNotFoundButtonView = (RelativeLayout) findViewById(R.id.passengerNotFoundButtonView);
        passengerCallButtonView = (RelativeLayout) findViewById(R.id.passengerCallButtonView);


        conetentView.setVisibility(View.GONE);
        startEndButtonView.setVisibility(View.GONE);
        callOnBoardView.setVisibility(View.GONE);

        TextView taskTypeTextView = (TextView)findViewById(R.id.taskTypeTextView);
        if (mTask.requestType == 0){
            taskTypeTextView.setText("PICK UP");
        }else{
            taskTypeTextView.setText("DROP OFF");
        }


        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                expandContent();

            }
        });


        startEndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (startEndButton.getText().toString().equals("START PICK UP")){
                    startEndButton.setText("ARRIVED AT PICK UP");

                    //make name unclickable

                    nameTextView.setTextColor(Color.parseColor("#242B38"));
                    nameTextView.setOnClickListener(null);


                }else if(startEndButton.getText().toString().equals("ARRIVED AT PICK UP")){
                    startEndButtonView.setVisibility(View.GONE);
                    callOnBoardView.setVisibility(View.VISIBLE);
                }else if (startEndButton.getText().toString().equals("START DROP")){
                    startEndButton.setText("END DROP OFF");
                }else if (startEndButton.getText().toString().equals("END DROP OFF")){
                    ScheduleActivity scheduleActivity = (ScheduleActivity)mContext;
                    scheduleActivity.goToNextTask();
                }

            }
        });

        passengerCallButton.setOnClickListener( new OnClickListener() {

            @Override
            public void onClick(View v) {

                passengerCallButtonView.setVisibility(View.INVISIBLE);
                passengerNotFoundButtonView.setVisibility(View.VISIBLE);

            }
        });

        passengerNotFoundButton.setOnClickListener( new OnClickListener() {

            @Override
            public void onClick(View v) {

                showPassengerNotFoundDialog();

            }
        });


        passengerOnBoardButton.setOnClickListener( new OnClickListener() {

            @Override
            public void onClick(View v) {

                ScheduleActivity scheduleActivity = (ScheduleActivity)mContext;
                scheduleActivity.goToNextTask();

            }
        });



    }

    public void expandContent(){

        if (AppManager.getInstance().isClockedIn == false) {

            if (conetentView.getVisibility() == View.GONE) {
                AppManager.getInstance().expand(conetentView);
                headerBackground.setVisibility(View.GONE);
            } else {
                AppManager.getInstance().collapse(conetentView);
                headerBackground.setVisibility(View.VISIBLE);
            }
        }else{

            ScheduleActivity scheduleActivity = (ScheduleActivity)mContext;
            if (myIndex > scheduleActivity.activeTaskIndex){

            }


        }

    }

    public void showPassengerNotFoundDialog(){

        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.alert_passenger_not_found);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        ImageView closeButton = (ImageView) dialog.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });

        final RelativeLayout cancelLayout = (RelativeLayout) dialog.findViewById(R.id.cancelLayout);
        final TextView cancelTextView = (TextView) dialog.findViewById(R.id.cancelTextView);

        final RelativeLayout wasNotThereLayout = (RelativeLayout) dialog.findViewById(R.id.wasNotThereLayout);
        final TextView wasNotThereTextView = (TextView) dialog.findViewById(R.id.wasNotThereTextView);

        final RelativeLayout wasLateLayout = (RelativeLayout) dialog.findViewById(R.id.wasLateLayout);
        final TextView wasLateTextView = (TextView) dialog.findViewById(R.id.wasLateTextView);


        cancelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cancelLayout.setBackgroundColor(Color.parseColor("#3A8CD3"));
                cancelTextView.setTextColor(Color.parseColor("#ffffff"));

                wasNotThereLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                wasNotThereTextView.setTextColor(Color.parseColor("#242b38"));

                wasLateLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                wasLateTextView.setTextColor(Color.parseColor("#242b38"));

            }
        });

        wasNotThereLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cancelLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                cancelTextView.setTextColor(Color.parseColor("#242b38"));

                wasNotThereLayout.setBackgroundColor(Color.parseColor("#3A8CD3"));
                wasNotThereTextView.setTextColor(Color.parseColor("#ffffff"));

                wasLateLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                wasLateTextView.setTextColor(Color.parseColor("#242b38"));

            }
        });

        wasLateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cancelLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                cancelTextView.setTextColor(Color.parseColor("#242b38"));

                wasNotThereLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                wasNotThereTextView.setTextColor(Color.parseColor("#242b38"));

                wasLateLayout.setBackgroundColor(Color.parseColor("#3A8CD3"));
                wasLateTextView.setTextColor(Color.parseColor("#ffffff"));

            }
        });

        final Button cancelButton = (Button) dialog.findViewById(R.id.cancelButton);
        final Button confirmButton = (Button) dialog.findViewById(R.id.confirmButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.hide();

            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.hide();

                ScheduleActivity scheduleActivity = (ScheduleActivity)mContext;
                scheduleActivity.goToNextTask();

            }
        });


    }


    public void setName(String name){
        nameTextView.setText(name);
    }


}
