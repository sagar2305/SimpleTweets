package com.codepath.apps.restclienttemplate.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.codepath.apps.restclienttemplate.R;

public class ComposeTweetActivity extends AppCompatActivity {

    EditText etTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_tweet);

        etTweet = (EditText) findViewById(R.id.etTweet);
    }

    public void didTapTweetButton(View view) {
        Intent intent = new Intent();
        intent.putExtra("tweet", etTweet.getText().toString());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void didTapCancelButton(View view) {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }
}
