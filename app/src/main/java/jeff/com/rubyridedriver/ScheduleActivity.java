package jeff.com.rubyridedriver;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.LinkAddress;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ScheduleActivity extends AppCompatActivity  {

    private Button clockinButton;
    private LinearLayout topView;

    private List<TaskModel> taskArray = new ArrayList<TaskModel>();
    private List<TaskView> taskViewArray = new ArrayList<TaskView>();

    public Integer activeTaskIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        //Set ActionBar theme
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_bg));

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.abs_layout);

        //set Title
        ((AppCompatTextView)actionBar.getCustomView().findViewById(R.id.titleView)).setText("Your Schedule");

        topView = (LinearLayout) findViewById(R.id.topView);

        clockinButton = (Button) findViewById(R.id.clockinButton);
        clockinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                topView.setVisibility(View.GONE);

                onClockInButtonClicked();

            }
        });


        LinearLayout contentView = (LinearLayout)findViewById(R.id.contentView);

        String[] nameArray = {"Brian Flaherty", "Brian Flaherty", "Maggie Gyllenthal", "Giovanna Bologna", "Maggie Gyllenthal"};
        Integer[] typeAray = {0, 1, 0, 1, 0};

        for(int i = 0; i < nameArray.length; i++ ){

            TaskModel task = new TaskModel();

            task.name = nameArray[i];
            task.requestType = typeAray[i];
            task.address = "1622 morningside ave";
            task.distance = "4.7 miles";
            task.period = "8 mins";
            task.requestedDate = "Requested on Tuesday at 11.15PM";
            task.instruction = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.";

            taskArray.add(task);

            TaskView taskView = new TaskView(this, task);
            taskView.setName(task.name);
            taskView.myIndex = i;
            contentView.addView(taskView);

            taskViewArray.add(taskView);
        }

    }

    public void goToNextTask(){

        //collpase previous task.
        taskViewArray.get(activeTaskIndex).headerBackground.setVisibility(View.VISIBLE);
        AppManager.getInstance().collapse(taskViewArray.get(activeTaskIndex).conetentView);


        //check if shift is over

        if (activeTaskIndex >= (taskArray.size() - 1)){
            LinearLayout shiftOverLayout = (LinearLayout)findViewById(R.id.shiftOverLayout);
            shiftOverLayout.setVisibility(View.VISIBLE);
            return;
        }

        //expand next task

        activeTaskIndex += 1;

        taskViewArray.get(activeTaskIndex).startEndButtonView.setVisibility(View.VISIBLE);
        if (taskArray.get(activeTaskIndex).requestType == 0){
            taskViewArray.get(activeTaskIndex).startEndButton.setText("START PICK UP");
        }else{
            taskViewArray.get(activeTaskIndex).startEndButton.setText("START DROP");
        }

        taskViewArray.get(activeTaskIndex).headerBackground.setVisibility(View.INVISIBLE);
        AppManager.getInstance().expand(taskViewArray.get(activeTaskIndex).conetentView);


        //remove pre-previous task.
//        if (activeTaskIndex - 2 >= 0){
//
//            LinearLayout contentView = findViewById(R.id.contentView);
//            contentView.removeView(taskViewArray.get(activeTaskIndex - 2));
//
//            taskViewArray.remove(activeTaskIndex - 2);
//            taskArray.remove(activeTaskIndex - 2);
//
//        }

    }

    public void onClockInButtonClicked(){

        AppManager.getInstance().isClockedIn = true;

        taskViewArray.get(0).startEndButtonView.setVisibility(View.VISIBLE);
        AppManager.getInstance().expand(taskViewArray.get(0).conetentView);
        taskViewArray.get(0).headerBackground.setVisibility(View.GONE);

        for (int i = 1; i <taskArray.size(); i++){
            taskViewArray.get(i).conetentView.setVisibility(View.GONE);
            taskViewArray.get(i).headerBackground.setVisibility(View.VISIBLE);
        }

        //make address clickable

        for (int i = 0; i <taskArray.size(); i++){
            taskViewArray.get(i).addressTextView.setTextColor(Color.parseColor("#3A8CD3"));
            taskViewArray.get(i).addressTextView.setOnClickListener( new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    String uri = String.format(Locale.ENGLISH, "geo:%f,%f", 40.7128, 74.0060);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(intent);

                }
            });
        }


        //make name clickable

        for (int i = 0; i <taskArray.size(); i++){

            if (taskArray.get(i).requestType == 0){

                taskViewArray.get(i).nameTextView.setTextColor(Color.parseColor("#3A8CD3"));
                taskViewArray.get(i).nameTextView.setOnClickListener( new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        callPassengerConfirmationDialog();

                    }
                });
            }

        }

    }

    public void callPassengerConfirmationDialog(){

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.alert_passenger_call);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        ImageView closeButton = (ImageView) dialog.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });

        final Button confirmButton = (Button) dialog.findViewById(R.id.confirmButton);
        final Button cancelButton = (Button) dialog.findViewById(R.id.cancelButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.hide();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.hide();
            }
        });


    }

}
