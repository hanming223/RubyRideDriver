package jeff.com.rubyridedriver;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jeff.com.rubyridedriver.utils.TripManager;
import timber.log.Timber;


public class ScheduleFragment extends Fragment {

    private Button clockinButton;
    private LinearLayout topView;

    LinearLayout contentView;

    private List<TaskModel> taskArray = new ArrayList<TaskModel>();
    private List<TaskView> taskViewArray = new ArrayList<TaskView>();

    public Integer activeTaskIndex = 0;
    public View fragmentView;

    RelativeLayout clockOutLayout, clockOutConfirmLayout;
    LinearLayout shiftOverLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_schedule, parent, false);

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        fragmentView = view;

        topView = (LinearLayout) fragmentView.findViewById(R.id.topView);

        shiftOverLayout = (LinearLayout)fragmentView.findViewById(R.id.shiftOverLayout);

        clockinButton = (Button) fragmentView.findViewById(R.id.clockinButton);
        clockinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onClockInButtonClicked();

            }
        });


        contentView = (LinearLayout)fragmentView.findViewById(R.id.contentView);

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

            TaskView taskView = new TaskView(getActivity(), this, task);
            taskView.setName(task.name);
            taskView.myIndex = i;
            contentView.addView(taskView);

            taskViewArray.add(taskView);
        }


        //clock out action
        clockOutLayout = (RelativeLayout) view.findViewById(R.id.clockOutLayout);
        clockOutConfirmLayout = (RelativeLayout) view.findViewById(R.id.clockOutConfirmLayout);
        ImageView clockOutButton = (ImageView) view.findViewById(R.id.clockOutButton);
        TextView clockOutConfirmButton = (TextView) view.findViewById(R.id.clockOutConfirmButton);

        clockOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (clockOutConfirmLayout.getVisibility() == View.GONE){
                    clockOutConfirmLayout.setVisibility(View.VISIBLE);
                }else{
                    clockOutConfirmLayout.setVisibility(View.GONE);
                }

            }
        });

        clockOutConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onClockOutButtonClicked();

            }
        });

        Button shiftOverClockOutButton = (Button)view.findViewById(R.id.shiftOverButton);
        shiftOverClockOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onClockOutButtonClicked();

            }
        });

    }


    public void onClockInButtonClicked(){

        AppManager.getInstance().isClockedIn = true;

        topView.setVisibility(View.GONE);

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

        if (taskArray.get(0).requestType == 0){

            taskViewArray.get(0).nameTextView.setTextColor(Color.parseColor("#3A8CD3"));
            taskViewArray.get(0).nameTextView.setOnClickListener( new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    callPassengerConfirmationDialog();

                }
            });
        }

        clockOutLayout.setVisibility(View.VISIBLE);

        //insurance SDK

        Timber.i("goOnDutyButtonClicked");
        TripManager.sharedInstance().goOnDuty();

    }

    public void onClockOutButtonClicked(){

        topView.setVisibility(View.VISIBLE);

        AppManager.getInstance().isClockedIn = false;

        clockOutLayout.setVisibility(View.GONE);

        shiftOverLayout.setVisibility(View.GONE);
        clockOutConfirmLayout.setVisibility(View.GONE);

        activeTaskIndex = 0;

        //remove all subview in contentview

        contentView.removeAllViews();
        taskArray.clear();
        taskViewArray.clear();


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

            TaskView taskView = new TaskView(getActivity(), this, task);
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

        //remove name clickable
        taskViewArray.get(activeTaskIndex).nameTextView.setTextColor(Color.parseColor("#242B38"));
        taskViewArray.get(activeTaskIndex).nameTextView.setOnClickListener(null);

        //check if shift is over
        if (activeTaskIndex >= (taskArray.size() - 1)){
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


        //make name clickable
        if (taskArray.get(activeTaskIndex).requestType == 0){

            taskViewArray.get(activeTaskIndex).nameTextView.setTextColor(Color.parseColor("#3A8CD3"));
            taskViewArray.get(activeTaskIndex).nameTextView.setOnClickListener( new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    callPassengerConfirmationDialog();

                }
            });
        }

    }


    public void callPassengerConfirmationDialog(){

        final Dialog dialog = new Dialog(getActivity());
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