package com.codepath.apps.restclienttemplate.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.fragments.UserTimelineFragment;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.apps.restclienttemplate.utils.TwitterApp;
import com.codepath.apps.restclienttemplate.utils.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {

    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String screenName = getIntent().getStringExtra("screen_name");
        //create user fragment
        UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);

        //display user timeline fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        // make change
        ft.replace(R.id.flContainer, userTimelineFragment);

        // commit transaction
        ft.commit();

        client = TwitterApp.getRestClient();
        client.getUserProfile(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                User user = null;
                try {
                    user = User.fromJSON(response);
                    getSupportActionBar().setTitle(user.screenName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
