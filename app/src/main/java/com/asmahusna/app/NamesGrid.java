package com.asmahusna.app;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class NamesGrid extends Activity {
    private static Context context;
    public TextView songName, startTimeField, endTimeField;
    private android.media.MediaPlayer mediaPlayer;
    private double startTime = 0;
    private double finalTime = 0;
    private Handler myHandler = new Handler();
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    private SeekBar seekbar;
    private ImageButton playButton, pauseButton;
    public static int oneTimeOnly = 0;
    PlaylistInstance playlist= PlaylistInstance.getInstance();
    public static int songId=0;
    GridView gridView;
    GridViewCustomAdapter grisViewCustomeAdapter;
    public Map<String, Bitmap> cachedImages = new HashMap<String, Bitmap>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_names_grid);
        ListView lv = (ListView) findViewById(R.id.nasheedList);
        lv.setAdapter(new ArrayAdapter<String>(this, R.layout.playlist_item, playlist.items) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(Color.WHITE);
                return textView;
            }
        });
        playButton = (ImageButton) findViewById(R.id.playButton);
        pauseButton = (ImageButton) findViewById(R.id.imageButton2);
        pauseButton.setEnabled(false);
        playButton.setEnabled(false);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                songName = (TextView) findViewById(R.id.textView4);
                startTimeField = (TextView) findViewById(R.id.textView1);
                endTimeField = (TextView) findViewById(R.id.textView2);
                seekbar = (SeekBar) findViewById(R.id.seekBar1);

                String SongName = playlist.items.get(position);
                songName.setText(SongName);
                songId = NamesGrid.this.getResources().getIdentifier(playlist.GetSongId(SongName), "raw", NamesGrid.this.getPackageName());
                stop();
                mediaPlayer = android.media.MediaPlayer.create(NamesGrid.this, songId);
                play(v);
                seekbar.setClickable(true);
            }
        });
        gridView=(GridView)findViewById(R.id.gridViewCustom);
        // Create the Custom Adapter Object
        grisViewCustomeAdapter = new GridViewCustomAdapter(this,false);
        // Set the Adapter to GridView
        gridView.setAdapter(grisViewCustomeAdapter);
    }

    @Override
    protected void onStop( ) {
        super.onStop();
        if(mediaPlayer!=null) {
            pause(null);
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // This overrides default action
        if(mediaPlayer!=null) {
            mediaPlayer.prepareAsync();
            play(null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.names_grid, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void play(View view) {
        mediaPlayer.start();
        finalTime = mediaPlayer.getDuration();
        startTime = mediaPlayer.getCurrentPosition();
        if (oneTimeOnly == 0) {
            seekbar.setMax((int) finalTime);
            //oneTimeOnly = 1;
        }

        endTimeField.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                        toMinutes((long) finalTime))
                )
        );
        startTimeField.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                        toMinutes((long) startTime))
                )
        );
        seekbar.setProgress((int) startTime);
        myHandler.postDelayed(UpdateSongTime, 100);
        pauseButton.setEnabled(true);
        playButton.setEnabled(false);
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            startTimeField.setText(String.format("%d min, %d sec",
                            TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                            TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                            toMinutes((long) startTime))
                    )
            );
            seekbar.setProgress((int) startTime);
            myHandler.postDelayed(this, 100);
        }
    };

    public void pause(View view) {
        mediaPlayer.pause();
        pauseButton.setEnabled(false);
        playButton.setEnabled(true);
    }

    public void stop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            pauseButton.setEnabled(false);
            playButton.setEnabled(true);
        } else {
            pauseButton.setEnabled(true);
            playButton.setEnabled(false);
        }
    }

    public void forward(View view) {
        int temp = (int) startTime;
        if ((temp + forwardTime) <= finalTime) {
            startTime = startTime + forwardTime;
            mediaPlayer.seekTo((int) startTime);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Cannot jump forward 5 seconds",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void rewind(View view) {
        int temp = (int) startTime;
        if ((temp - backwardTime) > 0) {
            startTime = startTime - backwardTime;
            mediaPlayer.seekTo((int) startTime);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Cannot jump backward 5 seconds",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void ToggleUp(View view)
    {

        RelativeLayout container=(RelativeLayout) findViewById(R.id.playlistContainer);
       // Button dwnBtn=(Button) findViewById(R.id.dwnBtn);
        Button upBtn=(Button) findViewById(R.id.upBtn);


        upBtn.setVisibility(View.GONE);
        container.setVisibility(View.VISIBLE);
        //dwnBtn.setVisibility(View.VISIBLE);
        // FadeIn(container);
    }

    public void ToggleDown(View view)
    {
        RelativeLayout container=(RelativeLayout) findViewById(R.id.playlistContainer);
        //Button dwnBtn=(Button) findViewById(R.id.dwnBtn);
        Button upBtn=(Button) findViewById(R.id.upBtn);

       // dwnBtn.setVisibility(View.GONE);
        container.setVisibility(View.GONE);
        upBtn.setVisibility(View.VISIBLE);
        // FadeIn(container);


    }


    public void FadeIn(RelativeLayout layout)
    {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(200);

        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeIn);
        layout.setAnimation(animation);
    }

    public void FadeOut(RelativeLayout layout)
    {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setStartOffset(500);
        fadeOut.setDuration(200);

        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeOut);
        layout.setAnimation(animation);
    }



}


