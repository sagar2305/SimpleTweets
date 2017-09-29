package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by SagarMutha on 9/28/17.
 */

public class Tweet {

    //define all the attributes
    private String body;
    private long uid;
    private String createdAt;
    private User user;

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getRelativeTimestamp() throws ParseException {

        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        Date now = new Date();
        Date tweetDate = sf.parse(createdAt);

        long duration = now.getTime() - tweetDate.getTime();
        long timestamp = TimeUnit.MILLISECONDS.toDays(duration);
        if (timestamp > 0) {
            return "" + timestamp + "d";
        }

        timestamp = TimeUnit.MILLISECONDS.toHours(duration);
        if (timestamp > 0) {
            return "" + timestamp + "h";
        }

        timestamp = TimeUnit.MILLISECONDS.toMinutes(duration);
        if (timestamp > 0) {
            return "" + timestamp + "m";
        }

        timestamp = TimeUnit.MILLISECONDS.toSeconds(duration);
        return "" + timestamp + "s";
    }

    public User getUser() {
        return user;
    }

    //deserialize the JSON
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        return tweet;
    }
}
