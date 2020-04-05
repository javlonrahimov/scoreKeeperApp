package com.example.scorekeeperapp.models;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.scorekeeperapp.R;

public class TiebreakActivity extends AppCompatActivity {
    public static final String PLAYER1 = "476";
    public static final String PLAYER2 = "289";
    public static final String WINNER = "852";

    TextView scorePlayer1, scorePlayer2;
    PlayerData player1, player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiebreak);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorStatusbar));
        }
        loadView();

    }

    public void onTiebreakButton1CLicked(View view) {
        performButtonClick(player2,player1,scorePlayer1, scorePlayer2);
    }

    public void onTiebreakButton2CLicked(View view) {
        performButtonClick(player1, player2, scorePlayer2, scorePlayer1);
    }

    private void loadView() {
        scorePlayer1 = findViewById(R.id.scoresPlayer1Tiebreak);
        scorePlayer2 = findViewById(R.id.scoresPlayer2Tiebreak);
        player1 = (PlayerData) getIntent().getSerializableExtra(PLAYER1);
        player2 = (PlayerData) getIntent().getSerializableExtra(PLAYER2);
    }

    private void performButtonClick(PlayerData player1, PlayerData player2, TextView scorePlayer1, TextView scorePlayer2) {
        if (player1.getScoreTiebreak() < 99) {
            player1.setScoreTiebreak(player1.getScoreTiebreak() + 1);
            scorePlayer1.setText(String.valueOf(player1.getScoreTiebreak()));
            if (player1.getScoreTiebreak() - player2.getScoreTiebreak() > 1 && player1.getScoreTiebreak() > 6) {
                Intent intent = new Intent();
                intent.putExtra(WINNER, player1);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        } else {
            if (String.valueOf(scorePlayer1.getText()).equals("AD")) {
                Intent intent = new Intent();
                intent.putExtra(WINNER, player1);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
            if (String.valueOf(scorePlayer2.getText()).equals("AD")) {
                scorePlayer2.setText(R.string._99);
            } else {
                scorePlayer1.setText(R.string.AD);
            }
        }
    }
}
