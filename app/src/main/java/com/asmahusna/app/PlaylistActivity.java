package com.asmahusna.app;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.util.concurrent.TimeUnit;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class PlaylistActivity extends Activity {

    public TextView songName, startTimeField, endTimeField;
    private MediaPlayer mediaPlayer;
    private double startTime = 0;
    private double finalTime = 0;
    private Handler myHandler = new Handler();
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    private SeekBar seekbar;
    private ImageButton playButton, pauseButton;
    public static int oneTimeOnly = 0;
    public static int songId=0;
    PlaylistInstance playlist= PlaylistInstance.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, playlist.items);
        ListView lv = (ListView) findViewById(R.id.nasheedList);
        lv.setAdapter(adapter);
        playButton = (ImageButton) findViewById(R.id.imageButton1);
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
                songId = PlaylistActivity.this.getResources().getIdentifier(playlist.GetSongId(SongName), "raw", PlaylistActivity.this.getPackageName());
                stop();
                mediaPlayer = MediaPlayer.create(PlaylistActivity.this, songId);
                play(v);
                seekbar.setClickable(true);
            }
        });
    }

    @Override
    protected void onStop( ) {
        super.onStop();
        stop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mediaPlayer.prepareAsync();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.playlist, menu);
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
        //Toast.makeText(getApplicationContext(), "Pausing sound",
        //Toast.LENGTH_SHORT).show();
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

}
