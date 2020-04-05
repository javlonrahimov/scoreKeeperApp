package com.example.scorekeeperapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.scorekeeperapp.models.PlayerData;
import com.example.scorekeeperapp.models.TiebreakActivity;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 238;

    PlayerData player1 = new PlayerData("Roger Federer", 0, 0, "0", 0);
    PlayerData player2 = new PlayerData("Dominic Thiem", 0, 0, "0", 0);

    TextView setsPlayer1, setsPlayer2, gamesPlayer1, gamesPlayer2, scoresPlayer1, scoresPlayer2;
    LinearLayout challengesPlayer1, challengesPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorStatusbar));
        }

        loadView();

    }

    public void onPlayer1AddButtonClicked(View view) {
        performPlayerAddButtonClick(player1, player2, scoresPlayer1, scoresPlayer2);
    }

    public void onPlayer2AddButtonClicked(View view) {
        performPlayerAddButtonClick(player2, player1, scoresPlayer2, scoresPlayer1);
    }

    public void onResetButtonClicked(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Reset entry")
                .setMessage("Are you sure to reset the scores?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        resetScores();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    private void resetScores() {
        player1.resetFields();
        player2.resetFields();
        // Reset player one fields;
        scoresPlayer1.setText(String.valueOf(player1.getScore()));
        gamesPlayer1.setText(String.valueOf(player1.getGames()));
        setsPlayer1.setText(String.valueOf(player1.getSets()));
        for (int i = 0; i < challengesPlayer1.getChildCount(); i++) {
            challengesPlayer1.getChildAt(i).setVisibility(View.VISIBLE);
        }
        //Reset player two fields;
        scoresPlayer2.setText(String.valueOf(player2.getScore()));
        gamesPlayer2.setText(String.valueOf(player2.getGames()));
        setsPlayer2.setText(String.valueOf(player2.getSets()));
        for (int i = 0; i < challengesPlayer2.getChildCount(); i++) {
            challengesPlayer2.getChildAt(i).setVisibility(View.VISIBLE);
        }
    }

    public void onChallengeClicked(View view) {
        view.setVisibility(View.INVISIBLE);
    }

    private void loadView() {
        setsPlayer1 = findViewById(R.id.setsPlayer1);
        setsPlayer2 = findViewById(R.id.setsPlayer2);
        gamesPlayer1 = findViewById(R.id.gamesPlayer1);
        gamesPlayer2 = findViewById(R.id.gamesPlayer2);
        scoresPlayer1 = findViewById(R.id.scoresPlayer1);
        scoresPlayer2 = findViewById(R.id.scoresPlayer2);
        challengesPlayer1 = findViewById(R.id.challengesPlayer1);
        challengesPlayer2 = findViewById(R.id.challengesPlayer2);
    }

    private void performPlayerAddButtonClick(PlayerData player1, PlayerData player2, TextView scoresPlayer1, TextView scoresPlayer2) {
        switch (player1.getScore()) {
            case "0": {
                player1.setScore("15");
                scoresPlayer1.setText(player1.getScore());
                break;
            }
            case "15": {
                player1.setScore("30");
                scoresPlayer1.setText(player1.getScore());
                break;
            }
            case "30": {
                player1.setScore("40");
                scoresPlayer1.setText(player1.getScore());
                break;
            }
            case "40": {
                if (player2.getScore().equals("40")) {
                    player1.setScore("AD");
                    scoresPlayer1.setText(player1.getScore());
                } else if (player2.getScore().equals("AD")) {
                    player2.setScore("40");
                    scoresPlayer2.setText(player2.getScore());
                } else {
                    TextView p1, p2;
                    player1.setScore("0");
                    scoresPlayer1.setText(player1.getScore());
                    player2.setScore("0");
                    scoresPlayer2.setText(player2.getScore());
                    if (player1 == this.player1) {
                        p1 = gamesPlayer1;
                        p2 = gamesPlayer2;
                    } else {
                        p1 = gamesPlayer2;
                        p2 = gamesPlayer1;
                    }
                    performPlayerGamesChange(player1, player2, p1, p2);
                }
                break;
            }
            case "AD": {
                TextView p1, p2;
                player1.setScore("0");
                scoresPlayer1.setText(player1.getScore());
                player2.setScore("0");
                scoresPlayer2.setText(player2.getScore());
                if (player1 == this.player1) {
                    p1 = gamesPlayer1;
                    p2 = gamesPlayer2;
                } else {
                    p1 = gamesPlayer2;
                    p2 = gamesPlayer1;
                }
                performPlayerGamesChange(player1, player2, p1, p2);
            }
        }
    }

    private void performPlayerGamesChange(PlayerData player1, PlayerData player2, TextView gamesPlayer1, TextView gamesPlayer2) {
        if (player1.getGames() < 5) {
            player1.setGames(player1.getGames() + 1);
            gamesPlayer1.setText(String.valueOf(player1.getGames()));
        } else {
            if (player1.getGames() - player2.getGames() > 1) {
                player1.setGames(0);
                gamesPlayer1.setText(String.valueOf(player1.getGames()));
                player2.setGames(0);
                gamesPlayer2.setText(String.valueOf(player2.getGames()));
                performPlayerSetsChange(player1);
            } else {
                player1.setGames(player1.getGames() + 1);
                gamesPlayer1.setText(String.valueOf(player1.getGames()));
                if (player1.getGames() - player2.getGames() > 1) {
                    player1.setGames(0);
                    gamesPlayer1.setText(String.valueOf(player1.getGames()));
                    player2.setGames(0);
                    gamesPlayer2.setText(String.valueOf(player2.getGames()));
                    performPlayerSetsChange(player1);
                }
            }
        }
        if (player1.getGames() == 6 && 6 == player2.getGames()) {
            Intent intent = new Intent(this, TiebreakActivity.class);
            intent.putExtra(TiebreakActivity.PLAYER1, player1);
            intent.putExtra(TiebreakActivity.PLAYER2, player2);
            startActivityForResult(intent, REQUEST_CODE);
            Toast.makeText(this, "Tiebreak", Toast.LENGTH_SHORT).show();
        }
    }

    private void performPlayerSetsChange(PlayerData player) {
        player.setSets(player.getSets() + 1);
        if (player == player1) {
            setsPlayer1.setText(String.valueOf(player.getSets()));
            gamesPlayer1.setText(String.valueOf(player.getGames()));
        } else {
            setsPlayer2.setText(String.valueOf(player.getSets()));
            gamesPlayer2.setText(String.valueOf(player.getGames()));
        }
        for (int i = 0; i < challengesPlayer1.getChildCount(); i++) {
            challengesPlayer1.getChildAt(i).setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < challengesPlayer2.getChildCount(); i++) {
            challengesPlayer2.getChildAt(i).setVisibility(View.VISIBLE);
        }
        if (player.getSets() == 3) {
            new AlertDialog.Builder(this)
                    .setCancelable(true)
                    .setTitle("Game is over!!!")
                    .setMessage(player.getName() + " has won the match!!!")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            resetScores();
                        }
                    })
                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            resetScores();
                        }
                    })
                    .setCancelable(false)
                    .show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE == requestCode && resultCode == Activity.RESULT_OK && data != null) {
            PlayerData winner = (PlayerData) data.getSerializableExtra(TiebreakActivity.WINNER);
            if (winner != null) {
                if (winner.getName().equals(player1.getName())) {
                    player1.setGames(0);
                    gamesPlayer1.setText(String.valueOf(player1.getGames()));
                    player2.setGames(0);
                    gamesPlayer2.setText(String.valueOf(player2.getGames()));
                    performPlayerSetsChange(player1);
                } else {
                    player1.setGames(0);
                    gamesPlayer1.setText(String.valueOf(player1.getGames()));
                    player2.setGames(0);
                    gamesPlayer2.setText(String.valueOf(player2.getGames()));
                    performPlayerSetsChange(player2);
                }
            }
        }
    }
}
