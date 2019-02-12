package com.rear_admirals.york_pirates;

import java.util.Random;

public class Minigame {
    private int gold;
    private String turn;
    private int[] playerdice;
    private int[] enemydice;
    private boolean lost;
    private boolean won;
    private String gameState;


    //Run game

    //Instatiates the main game and sets all the necessary variables
    public Minigame(){
        gold = 1000;
        turn = "player";
        lost = false;
        won = false;
        playerdice = new int[3];
        enemydice = new int[3];
        gameState = "playing";
    }


    //Simulates a roll of the dice vs. your oppopnent, and returns whether you won, lost or whether you're still playing (draw_
    public String playGame(){
        String[] Scores = new String[2];



        playerdice = randomdice();
        enemydice = randomdice();
        Scores = comparison(playerdice,enemydice);

        if (Scores[2] == "Win"){
           gameState = "Won";
        } else if (Scores[2] == "Loss") {
            gameState = "Lost";
        } else {
            gameState = "playing";
        }

        return gameState;


    }


//Gets random values to simulate the dice rolls
    public int[] randomdice(){
        Random rand = new Random();
        int[] dice = new int[3];
        for (int i = 0; i < 3; i++){
            dice[i] = rand.nextInt(6) + 1;
            rand = new Random();
        }

        return dice;
    }

    //Compares two sets of dice rolls and returns who won, and what the respective types of dice rolls they got
    public String[] comparison(int[] playerdice, int[] enemydice){
        String[] comp = new String[3];
        int[] score = new int[2];
        int[] dice = new int[3];
        for (int i = 0; i < 2; i++){
            if(i == 0){
                dice = playerdice;
            } else {
                dice = enemydice;
            }

            if (dice[0] == 4 && dice[1] == 5 && dice[2] == 6){
                //456
                comp[i] = "456";
                score[i] = 15;
            } else if (dice[0] == dice[1] && dice[1] == dice[2]){
                //Trip
                comp[i] = "Trip";
                score[i] = 8 + dice[0];
            } else if (dice[0] == dice[1] ){
                //Point
                comp[i] = "Point";
                score[i] = 2 + dice[2];
            } else if (dice[1] == dice[2] ){
                //Point
                comp[i] = "Point";
                score[i] = 2 + dice[0];
            } else if (dice[0] == dice[2] ){
                //Point
                comp[i] = "Point";
                score[i] = 2 + dice[1];
            } else if (dice[0] == 1 && dice[1] == 2 && dice[2] ==3) {
                //123
                comp[i] = "123";
                score[i] = 1;
            } else {
                //Dud
                comp[i] = "Dud";
                score[i] = 2;
            }
        }

        //Works who had the highest score, and decides whether it was a win/loss/draw
        if (score[0] > score[1]){
            comp[2] = "Win";
        } else if (score[0] < score[1]){
            comp[2] = "Loss";
        } else {
            comp[2] = "Draw";
        }


        //For debugging purposes
        System.out.println("Your score is " + score[0]);
        System.out.println("Their score is " + score[1]);
        System.out.println("");

        return comp;
    }

    //Returns the player's dice scores
    public int[] getPlayerdice(){
        return playerdice;
    }

    //Returns the opponent's dice scores
    public int[] getEnemydice(){
        return enemydice;
    }




}