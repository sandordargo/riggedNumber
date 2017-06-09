package com.dargo.riggednumber;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class NumberDisplayer extends AppCompatActivity {

    Button greaterButton;
    Button smallerButton;
    Button newGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startNewGame();
    }

    private void startNewGame() {
        setContentView(R.layout.game_start);

        greaterButton = (Button) findViewById(R.id.greaterButton);
        greaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResult(Guess.GREATER);
            }
        });

        smallerButton = (Button) findViewById(R.id.smallerButton);
        smallerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResult(Guess.SMALLER);
            }
        });
    }

    private void showResult(Guess guess) {
        setContentView(R.layout.game_result);
        int randomNumber = generateRandomNumber();

        TextView randomNumberText = (TextView) findViewById(R.id.riggedNumber);
        randomNumberText.setText(String.valueOf(randomNumber));

        String notificationMessage = hasGuessWon(guess, randomNumber) ?
                getString(R.string.winner) : getString(R.string.loser);

        TextView notification = (TextView) findViewById(R.id.notification);
        notification.setText(notificationMessage);

        newGameButton = (Button) findViewById(R.id.newGameButton);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewGame();
            }
        });
    }

    private static int generateRandomNumber() {
        Random randomGenerator = new Random();
        int randomNumber = randomGenerator.nextInt(9) + 1;
        while (randomNumber == 5) {
            randomNumber = randomGenerator.nextInt(9) + 1;
        }
        return randomNumber;
    }

    private static boolean hasGuessWon(Guess guess, int randomNumber) {
        return (guess.equals(Guess.SMALLER) && randomNumber < 5) || (guess.equals(Guess.GREATER) && randomNumber > 5);
    }
}
