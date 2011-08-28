package net.fushihara.yaviewer2011.activities;

import net.fushihara.yaviewer2011.R;
import net.fushihara.yaviewer2011.adapters.TalkListAdapter;
import net.fushihara.yaviewer2011.api.Client;

import org.json.JSONArray;

import android.app.ListActivity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class main extends ListActivity implements OnItemSelectedListener,
        OnClickListener {
    private TalkListAdapter adapter;
    private TextView        day_14;
    private TextView        day_15;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.main);

        Spinner venue = (Spinner) findViewById(R.id.venue_spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
                .createFromResource(getApplicationContext(), R.array.venues,
                        android.R.layout.simple_spinner_item);
        venue.setAdapter(spinnerAdapter);
        venue.setOnItemSelectedListener(this);

        day_14 = (TextView) findViewById(R.id.day_14);
        day_14.setOnClickListener(this);
        day_15 = (TextView) findViewById(R.id.day_15);
        day_15.setOnClickListener(this);

        adapter = new TalkListAdapter(getApplicationContext());

        setListAdapter(adapter);

        TaskListTask task = new TaskListTask();
        task.execute(getResources().getString(R.string.api_url));
    }

    private class TaskListTask extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected void onPreExecute() {
            setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected JSONArray doInBackground(String... uris) {
            Client c = new Client();
            return c.get(getResources().getString(R.string.api_url));
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            if (result != null) {
                adapter.setJSON(result);
            }
            setProgressBarIndeterminateVisibility(false);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
            long id) {
        if (adapter != null) {
            adapter.setVenue(position + 1);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.day_14:
            adapter.setDay(14);
            day_14.setBackgroundColor(Color.WHITE);
            day_15.setBackgroundColor(Color.TRANSPARENT);
            break;
        case R.id.day_15:
            adapter.setDay(15);
            day_14.setBackgroundColor(Color.TRANSPARENT);
            day_15.setBackgroundColor(Color.WHITE);
            break;
        }
    }
}