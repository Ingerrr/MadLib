package com.example.inger.madlib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/*
* Displays filled in story and offers to play once more
 */
public class ThirdActivity extends AppCompatActivity {

    // instantiate variables for storynumber
    int[] storyNumbers;
    int storyIndex;

    /*
    * Displays filled in story
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        // get all data from SecondActivity
        Bundle extras = getIntent().getExtras();
        storyNumbers = extras.getIntArray(getString(R.string.storyNumbers));
        storyIndex = extras.getInt(getString(R.string.index));

        // display story
        String completeStory = extras.getString(getString(R.string.completeStory));
        ((TextView)findViewById(R.id.output)).setText(completeStory);
    }

    /*
    * Shuffles array
    */
    private int[] shuffleArray(int[] array)
    {
        int index;
        int temp;

        // include random class
        Random random = new Random();

        // rearrange array
        for (int i = 4; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }

        // return
        return array;
    }

    /*
    * Restarts the SecondActivity with another storyNumber everytime the button anotherStory is pressed
     */
    public void restart(View view) {
        storyIndex++;

        if (storyIndex > 4) {
            // tell the user if all the stories are completed
            Toast.makeText(this, R.string.allComplete, Toast.LENGTH_LONG).show();

            // start again
            storyIndex = 0;
            storyNumbers = shuffleArray(storyNumbers);
        }
        else {
            // restart second activity with next story
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra(getString(R.string.storyNumbers), storyNumbers);
            intent.putExtra(getString(R.string.index), storyIndex);
            intent.putExtra(getString(R.string.zero), getString(R.string.madlib0));
            intent.putExtra(getString(R.string.one), getString(R.string.madlib1));
            intent.putExtra(getString(R.string.two), getString(R.string.madlib2));
            intent.putExtra(getString(R.string.three), getString(R.string.madlib3));
            intent.putExtra(getString(R.string.four), getString(R.string.madlib4));
            startActivity(intent);
            finish();
        }
    }
}
