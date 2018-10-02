package jeff.com.rubyridedriver;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends Fragment {

    public View fragmentView;

    private List<TaskModel> taskArray = new ArrayList<TaskModel>();
    private List<TaskView> taskViewArray = new ArrayList<TaskView>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_history, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        fragmentView = view;

        LinearLayout contentView = (LinearLayout)fragmentView.findViewById(R.id.contentView);

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
}