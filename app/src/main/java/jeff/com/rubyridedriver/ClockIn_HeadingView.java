package jeff.com.rubyridedriver;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.Collapse;
import com.mindorks.placeholderview.annotations.expand.Expand;
import com.mindorks.placeholderview.annotations.expand.Parent;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;
import com.mindorks.placeholderview.annotations.expand.Toggle;


@Parent

@Layout(R.layout.activity_schedule_clockin_headingview)
public class ClockIn_HeadingView {

    @View(R.id.name)
    TextView nameTextView;


    @ParentPosition
    int mParentPosition;

    private Context mContext;
    private String mName;

    public ClockIn_HeadingView(Context context, String name) {
        mContext = context;
        mName = name;
    }

    @Resolve
    public void onResolved() {
        nameTextView.setText(mName);
    }

    @Expand
    public void onExpand() {
    }

    @Collapse
    public void onCollapse() {

    }
}
