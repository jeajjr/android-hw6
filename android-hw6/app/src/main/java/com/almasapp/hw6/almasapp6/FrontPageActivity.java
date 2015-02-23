package com.almasapp.hw6.almasapp6;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class FrontPageActivity extends ActionBarActivity {

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);

        actionBar = getSupportActionBar();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_front_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_always_shown:
                Toast.makeText(this, getResources().getString(R.string.front_page_action_always), Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_overflow:
                Toast.makeText(this, getResources().getString(R.string.front_page_action_overflow), Toast.LENGTH_LONG).show();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
