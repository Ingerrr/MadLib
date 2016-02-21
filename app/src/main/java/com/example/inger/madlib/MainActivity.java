package com.example.inger.madlib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/*
* The user is welcomed, told about the rules of the game and than moved on to the SecondActivity
 */
public class MainActivity extends AppCompatActivity {

    // set current storyNumber to zero
    int storyIndex = 0;
    int[] storyNumbers = {0,1,2,3,4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
    * Shuffles array to determine random order of stories
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
    * Activates the SecondActivity
     */
    public void getStarted(View view) {
        Intent intent = new Intent(this, SecondActivity.class);

        // shuffle array of story numbers to get them in random order
        storyNumbers = shuffleArray(storyNumbers);

        // attaches the current storyNumber and textnames related to different storyNumber-options
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
