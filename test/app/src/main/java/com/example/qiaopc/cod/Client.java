package com.example.qiaopc.cod;

/**
 * Created by qiaopc on 2018/11/8.
 */

public class Client {
    public static void main(String[] args) {
        CallOfDuty game = new CallOfDuty();
        game.play();
        Caretaker caretaker = new Caretaker();
        caretaker.archive(game.createMemoto());
        game.quit();

        CallOfDuty newGame = new CallOfDuty();
        newGame.restore(caretaker.getMemoto());
    }
}
