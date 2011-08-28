package net.fushihara.yaviewer2011.adapters;

import net.fushihara.yaviewer2011.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TalkListAdapter extends BaseAdapter {

    private JSONArray      allTalks;
    private JSONArray      talks;
    private LayoutInflater inflater;
    private int            venue;
    private int            day;

    public TalkListAdapter(Context ctx) {
        inflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        allTalks = new JSONArray();
        talks = new JSONArray();
        venue = 1;
        day = 14;
    }

    public void setJSON(JSONArray json) {
        this.allTalks = json;
        notifyDataSetChanged();
    }

    public void setVenue(int newVenue) {
        venue = newVenue;
        notifyDataSetChanged();
    }

    public void setDay(int newDay) {
        day = newDay;
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        talks = new JSONArray();
        for (int i = 0; i < allTalks.length(); i++) {
            try {
                JSONObject talk = allTalks.getJSONObject(i);
                if (talk.getJSONObject("venue").getInt("id") == venue
                        && talk.getString("start_on").startsWith(
                                String.format("2011-10-%d", day))) {
                    talks.put(talk);
                }
            } catch (JSONException e) {
            }
        }
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return talks.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return talks.getJSONObject(position);
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        try {
            return talks.getJSONObject(position).getInt("id");
        } catch (JSONException e) {
            return 0;
        }
    }

    static class ViewHolder {
        TextView talkName;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        JSONObject json = (JSONObject) getItem(position);
        ViewHolder holder;

        if (view == null) {
            view = inflater.inflate(R.layout.talk_list_row, null);
            holder = new ViewHolder();
            holder.talkName = ((TextView) view.findViewById(R.id.talk_name));
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        try {
            String title = json.getString("title");
            if ("null".equals(title)) { // suck android json parser
                title = json.getString("title_en");
            }
            holder.talkName.setText(title);
        } catch (JSONException e) {
            holder.talkName.setText(e.toString());
        }

        return view;
    }

}
