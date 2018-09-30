package jeff.com.rubyridedriver;

import android.net.LinkAddress;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity  {

    private Button clockinButton;
    private LinearLayout topView;

    private List<TaskModel> taskArray = new ArrayList<TaskModel>();
    private List<TaskView> taskViewArray = new ArrayList<TaskView>();

    private Integer activeTaskIndex = 0;


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

        activeTaskIndex += 1;
        

    }

    public void onClockInButtonClicked(){

        taskViewArray.get(0).startEndButtonView.setVisibility(View.VISIBLE);
        AppManager.getInstance().expand(taskViewArray.get(0).conetentView);

        taskViewArray.get(0).headerBackground.setVisibility(View.GONE);

        for (int i = 1; i <taskArray.size(); i++){

            taskViewArray.get(i).conetentView.setVisibility(View.GONE);
            taskViewArray.get(i).headerBackground.setVisibility(View.VISIBLE);

        }

    }



}
