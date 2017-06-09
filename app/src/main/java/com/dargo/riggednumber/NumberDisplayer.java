package com.dargo.riggednumber;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Random;

public class NumberDisplayer extends AppCompatActivity {

    Button greaterButton;
    Button smallerButton;
    Button newGameButton;
    boolean letTheUserWin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobileAds.initialize(this, "ca-app-pub-2833983884268240~3778531218");
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

        AdView adView = (AdView) findViewById(R.id.adViewGameStart);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(getString(R.string.banner_home_footer));
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adView.loadAd(adRequest);
    }

    private void showResult(Guess guess) {
        setContentView(R.layout.game_result);
        int randomNumber;
        String notificationMessage;

        if (letTheUserWin) {
            notificationMessage = getString(R.string.winner);
            randomNumber = generateWinningNumber(guess);
        } else {
            notificationMessage = getString(R.string.loser);
            randomNumber = generateLosingNumber(guess);
        }

        setNotificationFields(randomNumber, notificationMessage);

        newGameButton = (Button) findViewById(R.id.newGameButton);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewGame();
            }
        });

        letTheUserWin = !letTheUserWin;

        AdView adView = (AdView) findViewById(R.id.adViewGameResult);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(getString(R.string.banner_home_footer));
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adView.loadAd(adRequest);
    }

    private int generateWinningNumber(Guess guess) {
        return guess.equals(Guess.GREATER) ?
                generateRandomNumberBetween6And9() : generateRandomNumberBetween1And4();
    }

    private int generateLosingNumber(Guess guess) {
        return guess.equals(Guess.GREATER) ?
                generateRandomNumberBetween1And4() : generateRandomNumberBetween6And9();
    }

    private static int generateRandomNumberBetween1And4() {
        Random randomGenerator = new Random();
        return randomGenerator.nextInt(4) + 1;
    }

    private static int generateRandomNumberBetween6And9() {
        Random randomGenerator = new Random();
        return randomGenerator.nextInt(4) + 6;
    }

    private void setNotificationFields(int randomNumber, String notificationMessage) {
        TextView randomNumberText = (TextView) findViewById(R.id.riggedNumber);
        randomNumberText.setText(String.valueOf(randomNumber));

        TextView notification = (TextView) findViewById(R.id.notification);
        notification.setText(notificationMessage);
    }
}
