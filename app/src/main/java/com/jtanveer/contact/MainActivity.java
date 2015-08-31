package com.jtanveer.contact;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Bundle>> {

    ProgressBar bar;
    ListView lsContact;
    ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bar = (ProgressBar) findViewById(R.id.progressBar);
        lsContact = (ListView) findViewById(R.id.listView);

        adapter = new ContactAdapter(this);

        lsContact.setAdapter(adapter);

        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<ArrayList<Bundle>> onCreateLoader(int id, Bundle args) {
        return new ContactLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Bundle>> loader, ArrayList<Bundle> data) {
        adapter.setData(data);
        bar.setVisibility(View.GONE);
        Toast.makeText(this, "Total " + adapter.getCount() + " contacts loaded", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Bundle>> loader) {
        adapter.clearData();
    }
}
