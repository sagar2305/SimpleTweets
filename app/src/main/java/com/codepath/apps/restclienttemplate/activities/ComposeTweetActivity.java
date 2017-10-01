package com.codepath.apps.restclienttemplate.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.R;

public class ComposeTweetActivity extends AppCompatActivity {

    EditText etTweet;
    TextView tvCounter;
    Button btTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_tweet);

        etTweet = (EditText) findViewById(R.id.etTweet);
        tvCounter = (TextView) findViewById(R.id.tvCounter);
        btTweet = (Button) findViewById(R.id.btTweet);

        tvCounter.setText("140");
        btTweet.setEnabled(false);

        etTweet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int charsLeft = 140 - charSequence.length();

                int color = charsLeft < 0 ? Color.RED : Color.BLACK;
                tvCounter.setText(String.valueOf(charsLeft));
                tvCounter.setTextColor(color);

                btTweet.setEnabled(charsLeft >= 0 && charsLeft < 140);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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
