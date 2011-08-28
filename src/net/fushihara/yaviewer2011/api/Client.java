package net.fushihara.yaviewer2011.api;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

public class Client {
    public JSONArray get(String url) {
        HttpGet request = new HttpGet(url);
        DefaultHttpClient client = new DefaultHttpClient();

        try {
            HttpResponse response = client.execute(request);
            return new JSONArray(EntityUtils.toString(response.getEntity(),
                    "UTF-8"));
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
