package jeff.com.rubyridedriver;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

interface ClockInFragmentInterface {
    public void clockinButtonClicked();
}

public class ScheduleActivity extends AppCompatActivity implements ClockInFragmentInterface {

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

    }

    public void clockinButtonClicked(){

        Log.d("xxx", "sss");

        Fragment clockinFragment = getSupportFragmentManager().findFragmentById(R.id.clockin_fragment);
        Fragment scheduleFragment = getSupportFragmentManager().findFragmentById(R.id.schedule_fragment);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .hide(clockinFragment)
                .commit();

    }


}
