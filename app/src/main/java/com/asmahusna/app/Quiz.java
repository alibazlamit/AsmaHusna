package com.asmahusna.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Map;


public class Quiz extends Activity {

    GridView gridView;
    GridViewCustomAdapter grisViewCustomeAdapter;
    int countHinted = 0;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_quiz);
        Intent a = new Intent(Quiz.this, SplashActivity.class);
        startActivity(a);

        EditText editText = (EditText) findViewById(R.id.enterText);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    SubmitNameValue(v);
                    return true;
                }
                return false;
            }
        });

        gridView = (GridView) findViewById(R.id.gridViewCustom);
        // Create the Custom Adapter Object
        grisViewCustomeAdapter = new GridViewCustomAdapter(this, true);
        // Set the Adapter to GridView
        gridView.setAdapter(grisViewCustomeAdapter);
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<Integer, Integer> result = grisViewCustomeAdapter.RefreshImage(Integer.toString(i + 1), gridView, Boolean.TRUE);
                for (Map.Entry<Integer, Integer> entry : result.entrySet()) {
                    countHinted = entry.getValue();
                    SetCountText(entry.getKey());
                }
                SetHintCountText(countHinted);
                return false;
            }
        });
        SetCountText(grisViewCustomeAdapter.count);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quiz, menu);
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


    public void ShowInfoDialog(View view) {
        TextView found = (TextView) findViewById(R.id.foundCount);
        TextView hinstUsed = (TextView) findViewById(R.id.hintsCount);
        float foundCount = Float.parseFloat(found.getText().toString());
        float memorized = 0;
        float hinstUsedCount = 0;
        if (hinstUsed.getText().toString() != "") {
            hinstUsedCount = Float.parseFloat(hinstUsed.getText().toString());
            foundCount = foundCount - hinstUsedCount;
        }
        if (foundCount != 0) {
            memorized = (foundCount / 99) * 100;
        }

        dialog = new Dialog(Quiz.this);

        dialog.setContentView(R.layout.progress_dialog);
        Window window = dialog.getWindow();
        window.setLayout(FlowLayout.LayoutParams.MATCH_PARENT, FlowLayout.LayoutParams.MATCH_PARENT);

        TextView mainInfo = (TextView) dialog.findViewById(R.id.dialogmain);
        TextView lowerInfo = (TextView) dialog.findViewById(R.id.dialoghintsused);
        mainInfo.setText("your progress shows that you have memorized" + memorized + "%.");
        lowerInfo.setText("You have used " + hinstUsedCount + "hints ");

        Button resetBtn = (Button) dialog.findViewById(R.id.reset);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetView();
            }
        });

        Button backBtn = (Button) dialog.findViewById(R.id.back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });

        dialog.show();
    }

    private void ResetView() {

        grisViewCustomeAdapter.ResetImageData(gridView);
        grisViewCustomeAdapter.notifyDataSetChanged();
        gridView.setAdapter(grisViewCustomeAdapter);
        countHinted=0;
        SetCountText(0);
        SetHintCountText(0);

        TextView mainInfo = (TextView) dialog.findViewById(R.id.dialogmain);
        TextView lowerInfo = (TextView) dialog.findViewById(R.id.dialoghintsused);
        mainInfo.setText("your progress shows that you have memorized" + 0 + "%.");
        lowerInfo.setText("You have used " + 0 + "hints ");
    }

    private void SetCountText(int count) {
        TextView countText = (TextView) findViewById(R.id.foundCount);
        countText.setText(Integer.toString(count));
    }

    private void SetHintCountText(int hintsCount) {
        TextView hinstCountText = (TextView) findViewById(R.id.hintsCount);
        hinstCountText.setText(Integer.toString(hintsCount));
    }

    public void SubmitNameValue(View view) {
        EditText editText = (EditText) findViewById(R.id.enterText);
        String text = editText.getText().toString();
        boolean ArabicDetected = !text.matches("^[\u0000-\u0080]+$");
        String imageId = "";
        if (ArabicDetected) {
            imageId = GetSongIdByArabicText(text);
        } else {
            imageId = GetSongIdByLatinText(text);
        }

        Integer imageIdInteger = Integer.parseInt(imageId);
        int count = 0;
        if (imageIdInteger > 0 && imageIdInteger <= 99) {
            Map<Integer, Integer> result = grisViewCustomeAdapter.RefreshImage(imageId, gridView, Boolean.FALSE);
            for (Map.Entry<Integer, Integer> entry : result.entrySet()) {
                count = entry.getKey();
            }
            SetCountText(count);
            editText.setText("");
        } else {
            Toast.makeText(getApplicationContext(),
                    editText.getText().toString() + "Is not a correct name!!",
                    Toast.LENGTH_LONG).show();
        }
    }

    public int CompareStringSimiliraty(String sourceString, int sourceLength, String targetString, int targetLength) {
        int cost = 0;
        if (sourceLength == 0) return targetLength;
        if (targetLength == 0) return sourceLength;

        if (sourceString.charAt(sourceLength - 1) == targetString.charAt(targetLength - 1))
            cost = 0;
        else
            cost = 1;

        return Math.min(CompareStringSimiliraty(sourceString, sourceLength - 1, targetString, targetLength) + 1, Math.min(
                CompareStringSimiliraty(sourceString, sourceLength, targetString, targetLength - 1) + 1,
                CompareStringSimiliraty(sourceString, sourceLength - 1, targetString, targetLength - 1) + cost));
    }

    public String GetSongIdByArabicText(String nameValue) {
        byte[] enteredText = nameValue.trim().getBytes();
        for (Map.Entry<String, String> entry : grisViewCustomeAdapter._arabicList.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            byte[] valueBytes = value.trim().getBytes();
            if (Arrays.equals(enteredText, valueBytes)) {
                return key;
            }
        }
        return "-1";
    }

    public String GetSongIdByLatinText(String nameValue) {
        nameValue = nameValue.trim().toLowerCase();
        for (Map.Entry<String, String> entry : grisViewCustomeAdapter._latinList.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().toLowerCase();
            if (nameValue.equals(value)) {
                return key;
            }
        }
        return "-1";
    }
}
