package com.codepath.apps.restclienttemplate.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.text.ParseException;
import java.util.List;

/**
 * Created by SagarMutha on 9/28/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    private List<Tweet> mTweets;
    Context context;
    private TweetAdapterListener mListener;

    public interface TweetAdapterListener {
        public void onItemSelected(View view, int position);
    }

    //pass tweet array
    public TweetAdapter(List<Tweet> tweets, TweetAdapterListener listener) {
        mTweets = tweets;
        mListener = listener;
    }

    //for each row, inflat layout and pass into viewholder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get tweet obj
        Tweet tweet = mTweets.get(position);

        // populate views
        holder.tvUsername.setText(tweet.getUser().name);
        holder.tvBody.setText(tweet.getBody());
        holder.tvHandle.setText("@" + tweet.getUser().screenName);
        try {
            holder.tvRelativeTimestamp.setText(tweet.getRelativeTimestamp());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Glide.with(context).load(tweet.getUser().profileImageUrl).into(holder.ivProfileImage);
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvRelativeTimestamp;
        public TextView tvHandle;

        public ViewHolder(View itemView) {
            super(itemView);

            // perform findviewbyid
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUserName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvRelativeTimestamp = (TextView) itemView.findViewById(R.id.tvTimestamp);
            tvHandle = (TextView) itemView.findViewById(R.id.tvHandle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // get the position of the row element
                    if (mListener != null) {
                        //get row pos
                        //call listener
                        int position  = getAdapterPosition();
                        mListener.onItemSelected(view, position);
                    }
                }
            });
        }
    }
}
