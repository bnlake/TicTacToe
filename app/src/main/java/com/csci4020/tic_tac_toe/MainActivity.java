package com.csci4020.tic_tac_toe;

/*
CSCI 4020
Assignment 1
Han Kim
Brian Lake
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnMenuAboutApp).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                gotoAboutApp();
            }
        });
    }

    /**
     * Create intent to open about app activity
     */
    private void gotoAboutApp()
    {
        // NOTE: JUST SETTING CONTENT VIEW ELIMINATES USE OF BACK BUTTON
        Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
        startActivity(intent);
    }
}
