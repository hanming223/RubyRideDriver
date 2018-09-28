package jeff.com.rubyridedriver;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.ChildPosition;
import com.mindorks.placeholderview.annotations.expand.Collapse;
import com.mindorks.placeholderview.annotations.expand.Expand;
import com.mindorks.placeholderview.annotations.expand.Parent;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;



@Layout(R.layout.activity_schedule_clockin_childview)
public class ClockIn_ChildView {

    @ParentPosition
    int mParentPosition;

    @ChildPosition
    int mChildPosition;

    @View(R.id.address)
    TextView addressTextView;

    @View(R.id.distance)
    TextView distanceTextView;

    @View(R.id.period)
    TextView periodTextView;

    Context mContext;

    private String mAddress;
    private String mDistance;
    private String mPeriod;

    public ClockIn_ChildView(Context context, String address, String distance, String period) {
        mContext = context;

        mAddress = address;
        mDistance = distance;
        mPeriod = period;
    }

    @Resolve
    public void onResolved() {
        addressTextView.setText(mAddress);
        distanceTextView.setText(mDistance);
        periodTextView.setText(mPeriod);

    }
}
