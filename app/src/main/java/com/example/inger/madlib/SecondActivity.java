package com.example.inger.madlib;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import java.io.*;
import java.util.*;

/*
* Asks user to fill in all the placeholders one by one based on the given description
 */
public class SecondActivity extends AppCompatActivity {

    // create variables
    int count;
    Story story;
    String placeHolderText;
    String completeStory;
    int[] storyNumbers;
    int index;

    /*
    * Reads the text file and converts into a Story class
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // get current storyNumber
        Bundle extras = getIntent().getExtras();
        storyNumbers = extras.getIntArray(getString(R.string.storyNumbers));
        index = extras.getInt(getString(R.string.index));
        String textNumber = extras.getString(String.valueOf(storyNumbers[index]));

        // read text file and throw error when this fails
        InputStream textFile = null;
        try {
            textFile = this.getAssets().open(textNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // convert textfile into Scanner
        Scanner scanner = new Scanner(textFile);

        // create story of class Story
        story = new Story(scanner);

        // get remaining number of placeholders to be filled in and display this
        count = story.getPlaceholderRemainingCount();
        ((TextView) findViewById(R.id.wordsLeft)).setText(count + getString(R.string.space) + getString(R.string.wordsLeft));

        // get description of next placeholder and display
        placeHolderText = story.getNextPlaceholder();
        ((TextView) findViewById(R.id.placeHolderText)).setText(getString(R.string.pleaseType) + placeHolderText);
        ((EditText) findViewById(R.id.input)).setHint(placeHolderText);
    }

    /*
    * Gets inputted word from EditText and places this in the first empty placeholder of story.
     */
    public void inputWord(View view) {

        // get filled in word from EditText and store in story
        String input = ((EditText) findViewById(R.id.input)).getText().toString();

        if (input.matches("")) {
            Toast.makeText(this, R.string.errorBlanc, Toast.LENGTH_SHORT).show();
            return;
        } else {
            story.fillInPlaceholder(input);
        }

        // check whether the last word is now filled in
        if (story.isFilledIn() == true) {

            // save finished story
            completeStory = story.toString();
            story.clear();

            // start new activity
            Intent intent = new Intent(this, ThirdActivity.class);
            intent.putExtra(getString(R.string.completeStory), completeStory);
            intent.putExtra(getString(R.string.storyNumbers), storyNumbers);
            intent.putExtra(getString(R.string.index), index);
            startActivity(intent);
            finish();
        } else {
            // clear EditText
            ((EditText) findViewById(R.id.input)).setText("");

            // update info textfields
            count--;
            ((TextView) findViewById(R.id.wordsLeft)).setText(count + getString(R.string.space) + getString(R.string.wordsLeft));
            placeHolderText = story.getNextPlaceholder();
            ((TextView) findViewById(R.id.placeHolderText)).setText(getString(R.string.pleaseType) + placeHolderText);
            ((EditText) findViewById(R.id.input)).setHint(placeHolderText);
        }
    }

    /*
    * Save current story, count and placeHolderText in case orientation is changed
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        // pass story as serializable
        outState.putSerializable(getString(R.string.story), story);

        // pass count
        outState.putInt("count", count);

        // pass placeHolderText
        outState.putString(getString(R.string.placeHolderText), placeHolderText);
    }

    /*
    * Restores text in EditText after orientation has been changed
    */
    @Override
    public void onRestoreInstanceState(Bundle inState) {

        // restore story
        story = (Story) inState.getSerializable(getString(R.string.story));

        // restore count
        count = inState.getInt(getString(R.string.count));
        ((TextView)findViewById(R.id.wordsLeft)).setText(count + getString(R.string.space) + getString(R.string.wordsLeft));

        // restore placeHolderText
        placeHolderText = inState.getString(getString(R.string.placeHolderText));
        ((TextView)findViewById(R.id.placeHolderText)).setText(getString(R.string.pleaseType) + placeHolderText);
        ((EditText)findViewById(R.id.input)).setHint(placeHolderText);

    }
}

