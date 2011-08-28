package net.fushihara.yaviewer2011.activities;

import net.fushihara.yaviewer2011.R;
import net.fushihara.yaviewer2011.adapters.TalkListAdapter;
import net.fushihara.yaviewer2011.api.Client;

import org.json.JSONArray;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;

public class main extends ListActivity {
    private TalkListAdapter adapter;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.main);

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
}