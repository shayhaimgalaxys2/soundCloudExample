package com.example.shay.soundcloudexample.project.activities;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.shay.soundcloudexample.R;
import com.example.shay.soundcloudexample.project.app.AppController;
import com.example.shay.soundcloudexample.project.dataObjects.Collection;

import java.io.IOException;
import java.util.List;

public class StreamSongActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {


    public static String LIST_KEY = "list_key";
    public static String POSITION_KEY = "position_key";

    private NetworkImageView imageIv;
    private Button previusBtn;
    private Button playBtn;
    private Button nextBtn;

    private List<Collection> onlyTracksCollectionList;
    private int songPositionInList;

    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream_song);

        initViews();
        getDataFromBundle();
        setImage(onlyTracksCollectionList.get(songPositionInList));
        setMedia(onlyTracksCollectionList.get(songPositionInList));
        initListeners();
    }

    private void initListeners() {

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                    playBtn.setText("play");
                } else {
                    mMediaPlayer.start();
                    playBtn.setText("pause");
                }

            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMediaPlayer.stop();
                setNextSongPositionInList();
                setMedia(onlyTracksCollectionList.get(songPositionInList));
                setImage(onlyTracksCollectionList.get(songPositionInList));

            }
        });

        previusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaPlayer.stop();
                setPrivSongPositionInList();
                setMedia(onlyTracksCollectionList.get(songPositionInList));
                setImage(onlyTracksCollectionList.get(songPositionInList));
            }
        });
    }

    private void setNextSongPositionInList() {

        int maxSongs = onlyTracksCollectionList.size() - 1;
        if (songPositionInList < maxSongs) {
            songPositionInList++;
        } else {
            songPositionInList = 0;
        }


    }

    private void setPrivSongPositionInList() {


        if (songPositionInList > 0) {
            songPositionInList--;
        } else {
            songPositionInList = onlyTracksCollectionList.size() - 1;
        }


    }

    private void setMedia(Collection collection) {


        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnPreparedListener(StreamSongActivity.this);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mMediaPlayer.setDataSource(collection.getStreamUrl() + "?client_id=d652006c469530a4a7d6184b18e16c81");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        }
        mMediaPlayer.prepareAsync();

    }

    private void setImage(Collection collection) {


        ImageLoader mImageLoader = AppController.getInstance().getImageLoader();
        mImageLoader.get(collection.getArtworkUrl(), ImageLoader.getImageListener(imageIv,
                R.drawable.placeholder, R.drawable.placeholder));
        imageIv.setImageUrl(collection.getArtworkUrl(), mImageLoader);
    }

    private void getDataFromBundle() {

        onlyTracksCollectionList = getIntent().getParcelableArrayListExtra(LIST_KEY);
        songPositionInList = getIntent().getIntExtra(POSITION_KEY, 0);
    }

    private void initViews() {

        imageIv = (NetworkImageView) findViewById(R.id.imageIv);
        previusBtn = (Button) findViewById(R.id.previusBtn);
        playBtn = (Button) findViewById(R.id.playBtn);
        nextBtn = (Button) findViewById(R.id.nextBtn);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        stopMedia();

    }

    @Override
    protected void onPause() {
        super.onPause();
        stopMedia();
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopMedia();
    }
    private void stopMedia() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer = null;
        }
    }
}
