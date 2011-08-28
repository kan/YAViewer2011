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

    private JSONArray      json;
    private LayoutInflater inflater;

    public TalkListAdapter(Context ctx) {
        inflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        json = new JSONArray();
    }

    public void setJSON(JSONArray json) {
        this.json = json;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return json.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return json.getJSONObject(position);
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        try {
            return json.getJSONObject(position).getInt("id");
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
