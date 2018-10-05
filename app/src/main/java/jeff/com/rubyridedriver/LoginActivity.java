package jeff.com.rubyridedriver;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import jeff.com.rubyridedriver.utils.SharedPrefsManager;
import jeff.com.rubyridedriver.utils.ZendriveManager;
import jeff.com.rubyridedriver.utils.logging.Logger;

public class LoginActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set status bar gradient color
        AppManager.getInstance().setStatusBarGradiant(this);

        setContentView(R.layout.activity_login);

        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);


        Button loginButton = (Button)findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final String driverId = emailEditText.getText().toString();
                if (!driverId.equals("")) {
                    // Initialize Logger

                    Logger.initializeLogglyLogger(App.instance.getApplicationContext(), driverId);

                    // Save driver information
                    SharedPrefsManager.sharedInstance().setDriverId(driverId);

                    // Initialize ZendriveSDK
                    ZendriveManager.sharedInstance().initializeZendriveSDK(driverId, null);

                    // Load UI
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(intent);
                }


            }
        });

    }



}
