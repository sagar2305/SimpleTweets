package com.codepath.apps.restclienttemplate.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.fragments.TweetsPagerAdapter;
import com.codepath.apps.restclienttemplate.models.User;

import org.parceler.Parcels;

public class TimelineActivity extends AppCompatActivity {

    static final int TWEET_REQUEST = 1;  // The request code

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

//        getUserProfile();

        //get the view pager
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);

        //set the adapter
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager(), this));

        //setup tab layou
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

//    private void getUserProfile() {
//        client.getUserProfile(new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                try {
//                    user = User.fromJSON(response);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                Log.d("TwitterClient", errorResponse.toString());
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
//                Log.d("TwitterClient", errorResponse.toString());
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                Log.d("TwitterClient", responseString);
//            }
//        });
//    }


//    private void postTweet(String tweet) {
//        try {
//            client.postTweet(tweet, new AsyncHttpResponseHandler() {
//
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                    JSONObject response = null;
//                    try {
//                        response = new JSONObject(new String(responseBody, "UTF-8"));
//                        Tweet tweet1 = Tweet.fromJSON(response);
//                        tweets.add(0, tweet1);
//                        tweetAdapter.notifyItemInserted(0);
//                        rvTweets.smoothScrollToPosition(0);
//
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                    Log.d("POST_TWEET", "Failure");
//                    error.printStackTrace();
//                }
//            });
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }

    public void onComposeTweet(MenuItem item) {
        Intent intent = new Intent(this, ComposeTweetActivity.class);
        intent.putExtra("user", Parcels.wrap(user));
        startActivityForResult(intent, TWEET_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TWEET_REQUEST && resultCode == RESULT_OK) {
            //insert the tweet
            String tweet = data.getStringExtra("tweet");
            Log.d("RESULTTWEET", tweet);
//            postTweet(tweet);
        }
    }
}
