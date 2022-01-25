package edu.ucsd.cse110.personalbestappteam24.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;


import edu.ucsd.cse110.personalbestappteam24.R;

public class HeightActivity extends AppCompatActivity {

    private Spinner footDropDown;
    private Spinner inchDropDown;
    private Button submit;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_height);

        footDropDown = (Spinner) findViewById(R.id.footDropDown);
        inchDropDown = (Spinner) findViewById(R.id.inchDropDown);
        submit = (Button) findViewById(R.id.submitHeight);
        intent = getIntent();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int feet = Integer.parseInt(footDropDown.getSelectedItem().toString());
                int inches = Integer.parseInt(inchDropDown.getSelectedItem().toString());

                int height = 12 * feet + inches;
                MainActivity.user.setHeight(height);

                System.err.println("The user's height is " + MainActivity.user.getHeight());
                finish();
            }
        });
    }
}

