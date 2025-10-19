package ru.nsu.tokarev4;

import java.util.Scanner;

import static ru.nsu.tokarev4.Hands.*;
import static ru.nsu.tokarev4.Score.*;

/**
 * Класс Round управляет логикой одного раунда игры в блэкджек.
 * Содержит методы для проведения раунда, раздачи карт и определения победителя.
 */
public class Round {
    /**
     * Проводит один полный раунд игры в блэкджек.
     * Включает раздачу начальных карт, ходы игрока и дилера,
     * проверку условий победы и обновление счета.
     */
    public static void rnd(){
        count_dealers_cards = 0;
        count_player_cards = 0;
        player_score = 0;
        dealer_score = 0;
        dealerhand = new Card[22];
        playerhand = new Card[22];
        int losewin = 0;
        cartAdd(1, 2);
        cartAdd(2, 2);
        int res = winnerSearchAfterStart();
        if (res == 1){
            printhands(2);
            System.out.println("Еще карту? 1 - взять / 0 - не брать?");
            Scanner in = new Scanner(System.in);
            int ans = in.nextInt();
            while (ans == 1) {
                cartAdd(1, 1);
                res = checkLose(player_score);
                if (res == 2) {
                    printhands(3);
                    System.out.println("Выиграл игрок");
                    losewin = 1;
                    global_score[0]++;
                    break;
                } else if (res == 1) {
                    printhands(2);
                } else {
                    boolean check = acefind(1);
                    if (check) {
                        printhands(2);
                    } else {
                        printhands(3);
                        System.out.println("Выиграл дилер");
                        losewin = 1;
                        global_score[1]++;
                        break;
                    }
                }
                System.out.println("Возьмешь еще? 1 - взять / 0 - не брать?");
                ans = in.nextInt();
            }
            int flagforprint = 0;
            if (losewin == 0){
                while(dealer_score < 17) {
                    flagforprint = 1;
                    cartAdd(2, 1);
                    res = checkLose(dealer_score);
                    if (res == 2){
                        losewin = 1;
                        printhands(2);
                        System.out.println("Выиграл дилер");
                        global_score[1]++;
                        break;
                    } else if (res == 0) {
                        boolean check = acefind(2);
                        if (!check) {
                            losewin = 1;
                            printhands(2);
                            System.out.println("Выиграл игрок");
                            global_score[0]++;
                        } else {
                            printhands(2);
                        }
                    }
                }
            }
            if (losewin == 0){
                if (flagforprint == 0){
                    printhands(3);
                }
                checkwinner();
            }
        }
    }
}