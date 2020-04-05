package com.example.scorekeeperapp.models;

import java.io.Serializable;

public class PlayerData implements Serializable {
    private String name;
    private int sets;
    private int games;
    private String score;
    private int scoreTiebreak;

    public PlayerData(String name, int sets, int games, String score, int scoreTiebreak) {
        this.name = name;
        this.sets = sets;
        this.games = games;
        this.score = score;
        this.scoreTiebreak = scoreTiebreak;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }


    int getScoreTiebreak() {
        return scoreTiebreak;
    }

    void setScoreTiebreak(int scoreTiebreak) {
        this.scoreTiebreak = scoreTiebreak;
    }

    public void resetFields() {
        this.games = 0;
        this.sets = 0;
        this.score = "0";
        scoreTiebreak = 0;
    }
}
