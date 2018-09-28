package jeff.com.rubyridedriver;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mindorks.placeholderview.ExpandablePlaceHolderView;

public class ClockInFragment extends Fragment {

    private Button clockinButton;

    private ExpandablePlaceHolderView mExpandableView;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_schedule_clockin_fragment, container, false);

        clockinButton = (Button) view.findViewById(R.id.clockinButton);
        clockinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ClockInFragmentInterface)getActivity()).clockinButtonClicked();

            }
        });


        mContext = getActivity().getApplicationContext();
        mExpandableView = (ExpandablePlaceHolderView)view.findViewById(R.id.expandableView);

        String[] nameArray = {"Brian Flaherty", "Brian Flaherty", "Maggie Gyllenthal", "Giovanna Bologna", "Maggie Gyllenthal"};
        Integer[] typeAray = {0, 1, 0, 1, 0};

        TaskModel task = new TaskModel();

        for(int i = 0; i < nameArray.length; i++ ){
            task.name = nameArray[i];
            task.requestType = typeAray[i];
            task.address = "1622 morningside ave";
            task.distance = "4.7 miles";
            task.period = "8 mins";
            task.requestedDate = "Requested on Tuesday at 11.15PM";
            task.instruction = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.";

            mExpandableView.addView(new ClockIn_HeadingView(mContext, task.name));
            mExpandableView.addView(new ClockIn_ChildView(mContext, task.address, task.distance, task.period));
        }

        return view;
    }




}
