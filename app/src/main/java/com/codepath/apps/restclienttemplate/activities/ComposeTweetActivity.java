package com.codepath.apps.restclienttemplate.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.apps.restclienttemplate.utils.TwitterApp;
import com.codepath.apps.restclienttemplate.utils.TwitterClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

public class ComposeTweetActivity extends AppCompatActivity {

    EditText etTweet;
    TextView tvCounter;
    Button btTweet;
    TextView tvName;
    TextView tvHandle;
    ImageView ivProfileImage;

    private TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_tweet);

        client = TwitterApp.getRestClient();

        etTweet = (EditText) findViewById(R.id.etTweet);
        tvCounter = (TextView) findViewById(R.id.tvCounter);
        btTweet = (Button) findViewById(R.id.btTweet);
        tvName = (TextView) findViewById(R.id.tvName);
        tvHandle = (TextView) findViewById(R.id.tvHandle);
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);

        tvCounter.setText("140");
        btTweet.setEnabled(false);

        User user = Parcels.unwrap(getIntent().getParcelableExtra("user"));
        populateUserInfo(user);

        final ColorStateList oldColors =  etTweet.getTextColors();

        etTweet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int charsLeft = 140 - charSequence.length();

                tvCounter.setText(String.valueOf(charsLeft));
                if (charsLeft < 0) {
                    tvCounter.setTextColor(Color.RED);
                }
                else {
                    tvCounter.setTextColor(oldColors);
                }

                btTweet.setEnabled(charsLeft >= 0 && charsLeft < 140);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void populateUserInfo(User user) {
        Glide.with(this).load(user.profileImageUrl).into(ivProfileImage);
        tvName.setText(user.name);
        tvHandle.setText("@" + user.screenName);
    }

    private void postTweet(String tweet) {
        try {
            client.postTweet(tweet, new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    JSONObject response = null;
                    try {
                        response = new JSONObject(new String(responseBody, "UTF-8"));
                        Tweet tweet = Tweet.fromJSON(response);
                        Intent intent = new Intent();
                        intent.putExtra("tweet", Parcels.wrap(tweet));
                        setResult(Activity.RESULT_OK, intent);
                        finish();

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.d("POST_TWEET", "Failure");
                    error.printStackTrace();
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void didTapTweetButton(View view) {
        postTweet(etTweet.getText().toString());
    }

    public void didTapCancelButton(View view) {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }
}
