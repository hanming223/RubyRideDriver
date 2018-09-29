package jeff.com.rubyridedriver;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class TaskView extends RelativeLayout {

    private ImageView photoImageView;
    private TextView nameTextView;

    public RelativeLayout headerView;
    public LinearLayout conetentView;
    public RelativeLayout bottomView;
    public RelativeLayout headerBackground;

    public Button startEndButton;

    public Integer myIndex;

    Context mContext;

    public static final int START_PICKUP = 100;
    public static final int ARRIVED_PICKUP = 101;


    public TaskView(Context context) {

        super(context);

        mContext = context;

        init();
    }

    private void init() {

        inflate(mContext, R.layout.layout_taskview, this);

        photoImageView = (ImageView) findViewById(R.id.photo);
        nameTextView = (TextView) findViewById(R.id.name);

        headerView = (RelativeLayout)findViewById(R.id.headerView);
        conetentView = (LinearLayout)findViewById(R.id.contentView);
        bottomView = (RelativeLayout)findViewById(R.id.bottomView);
        headerBackground = (RelativeLayout)findViewById(R.id.headerBackground);

        startEndButton = (Button)findViewById(R.id.startEndButton);

        conetentView.setVisibility(View.GONE);
        bottomView.setVisibility(View.GONE);

        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                expand();

            }
        });



        startEndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("xxxx", startEndButton.getText().toString());
                if (startEndButton.getText().toString().equals("START PICK UP")){
                    startEndButton.setText("ARRIVED AT PICK UP");
                }else {

                }

            }
        });

    }

    public void expand(){

        if (conetentView.getVisibility() == View.GONE){
            AppManager.getInstance().expand(conetentView);
        }else{
            AppManager.getInstance().collapse(conetentView);
            headerBackground.setVisibility(View.VISIBLE);
        }

    }


    public void setName(String name){
        nameTextView.setText(name);
    }

}
