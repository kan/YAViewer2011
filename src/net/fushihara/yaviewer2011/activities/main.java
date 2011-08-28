package net.fushihara.yaviewer2011.activities;

import net.fushihara.yaviewer2011.R;
import net.fushihara.yaviewer2011.adapters.TalkListAdapter;
import net.fushihara.yaviewer2011.api.Client;

import org.json.JSONArray;

import android.app.ListActivity;
import android.os.Bundle;

public class main extends ListActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Client c = new Client();
        JSONArray json = c.get(getResources().getString(R.string.api_url));
        TalkListAdapter adapter = new TalkListAdapter(getApplicationContext(),
                json);

        setListAdapter(adapter);
    }
}