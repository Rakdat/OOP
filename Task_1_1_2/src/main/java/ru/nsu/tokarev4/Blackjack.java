package ru.nsu.tokarev4;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Blackjack {
    private static int count_of_deck = 0; // количество используемых колод
    private static Card[] deck; // все карты который у нас есть
    private static int card_index; // карта, которая будет использована при следующей выдаче
    private static Card[] dealerhand; // рука дилера
    private static Card[] playerhand; // рука игрока
    private static final int[] global_score = {0, 0}; // 0 элемент счет игрока, 1 элемент счет дилера

    static int value_of_card(String rank) { // определение стоимости карт
        switch (rank) {
            case "Валет":
            case "Дама":
            case "Король":
                return 10;
            case "Туз":
                return 11;
            default:
                return Integer.parseInt(rank);
        }
    }
    private static void shuffle() { //перетасовка карт
        Random random = new Random();
        for (int i = deck.length - 1; i>0; i--) { //идем с конца в начало
            int j = random.nextInt(i); //выбираем карту с которой будем меняться местами
            Card temp = deck[i];
            deck[i] = deck[j];
            deck[j] = temp;
        }
        card_index = 0;
    }
    private static void initdeck() { //создание колоды
        String[] suits = {"Черви","Буби","Пики","Крести"}; //масти карт
        String[] ranks = {"2", "3", "4" ,"5","6", "7", "8", "9", "10", "Валет", "Дама", "Король", "Туз"};// имена карт
        int index = 0;
        deck = new Card[52*count_of_deck]; // выделение мест код n-ое количество колод
        for (int DeckNum = 0; DeckNum<count_of_deck; DeckNum++){ //создание карты
            for (String rank : ranks) { // выбираем имя карты
                for (String suit : suits) { //добавляем масть
                    deck[index] = new Card(rank, suit, value_of_card(rank)); //создаем карту
                    index++;
                }
            }
        }
        card_index = 0;
    }
    private static void printHand(int dp, int countofcards) {// вывод руки
        int sumofcards = 0; // сумма карт
        if (dp == 1) { // вывод руки игрока
            System.out.print("Ваша рука");
            for (int i = 0; i < countofcards; i++) {
                System.out.print(playerhand[i]+" ");
                sumofcards += playerhand[i].value;
            }
            System.out.println("\\ Cумма вашей руки: " + sumofcards);
        }
        else{
            System.out.print("Рука дилера");
            if (dp == 3) { //вывод руки дилера в случае если игрок набрал больше 21, а дилер так ине взял карту
                for (int i = 0; i < countofcards; i++) {
                    System.out.print(dealerhand[i] + " ");
                    sumofcards += dealerhand[i].value;
                }
                System.out.println("\\ Cумма руки дилера: " + sumofcards + "\n");
            }
            else if (countofcards == 2) { // вывод руки дилера пока он не начал брать карты
                System.out.println(dealerhand[0] + " <закрытая карта>" + "\n");
            }
            else { // вывод руки дилера когда он начал брать карты
                for (int i = 0; i < countofcards; i++) {
                    System.out.print(dealerhand[i] + " ");
                    sumofcards += dealerhand[i].value;
                }
                System.out.println("\\ Cумма руки дилера: " + sumofcards + "\n");
            }
        }
    }
    public static void game() { // сама игра
        System.out.println("Сколько колод будем использовать?");
        while (count_of_deck < 1 ) {// выбор количества колод
            Scanner in = new Scanner(System.in);
            count_of_deck = in.nextInt();
            if (count_of_deck < 1) { // проверка на коректное значение количества колод
                System.out.println("Ну как же мы будем так играть?) Давай другое количество колод");
            }
        }
        System.out.println("Диллер берет карты и перетасовывает их");
        initdeck(); // создание колоды
        shuffle(); // перетасовка колоды
        System.out.println("Дилер начал раздачу карт..");
        String gamestatus = "y";
        while (Objects.equals(gamestatus, "y")) { //будут раунды пока пользователь не введет n после окончания раунда
            if (52 * count_of_deck - card_index < 10 * count_of_deck) { // Проверка на то нужно ли перетасовывать колоду, если карты заканчиваются, то есть если осталось ~20% колоды
                System.out.println("Ох, колода подходит к концу, время перемешать");
                shuffle();
            }
            int dealer_score = 0; // счет дилера
            int player_score = 0; // счет игрока
            int count_dealers_cards = 0; //количество карт дилера
            int count_player_cards = 0; // количество карт игрока
            dealerhand = new Card[22]; // создание руки, 22 сделано чтобы не увеличивать руку если ее станет не хватать, выбран самый плохой вариант
            // когда используется >=6 колод и играющий берет 22 туза подряд
            playerhand = new Card[22];
            // выдача начальных карт дилеру и игроку, а так увеличение их очков
            playerhand[count_player_cards++] = new Card(deck[card_index].name, deck[card_index].suit, deck[card_index].value);
            player_score += deck[card_index++].value;
            playerhand[count_player_cards++] = new Card(deck[card_index].name, deck[card_index].suit, deck[card_index].value);
            player_score += deck[card_index++].value;
            dealerhand[count_dealers_cards++] = new Card(deck[card_index].name, deck[card_index].suit, deck[card_index].value);
            dealer_score += deck[card_index++].value;
            dealerhand[count_dealers_cards++] = new Card(deck[card_index].name, deck[card_index].suit, deck[card_index].value);
            dealer_score += deck[card_index++].value;
            printHand(1, count_player_cards);// вывод руки пользователя
            if (player_score == 21) { //если игроку выпало сразу 21 очко
                // вывод руки дилера
                printHand(3, count_dealers_cards);
                if(player_score > dealer_score) { // если у игрока больше очков чем у дилера, то он выиграл
                    System.out.println("Прям так сразу меня обыграл?");
                    global_score[0] += 1;
                    System.out.println("Счет: " + global_score[0] + ":" + global_score[1]);
                    System.out.println("Ты должен дать мне еще шанс! y/n");
                    Scanner in = new Scanner(System.in);
                    in.nextLine();
                    gamestatus = in.nextLine();
                } else if (player_score == dealer_score) { // случай если дилеру тоже выпало 21 и у них ничья
                    System.out.println("Самое начало, а уже ничьяБ невероятно..");
                    System.out.println("Нам все таки надо решить кто из нас везучее. Согласен? y/n");
                    Scanner in = new Scanner(System.in);
                    in.nextLine();
                    gamestatus = in.nextLine();
                }
            }
            else {
                printHand(2, count_dealers_cards);
                System.out.println("И как, рискнешь взять еще карту? 1 - взять / 0 - не брать");
                Scanner in = new Scanner(System.in);
                int ans = in.nextInt();
                while (ans == 1) {
                    playerhand[count_player_cards++] = new Card(deck[card_index].name, deck[card_index].suit, deck[card_index].value); //добавление взятой карты в руку
                    System.out.println("Вы получили карту: " + deck[card_index]);
                    player_score += deck[card_index++].value;
                    if (player_score > 21) { // если игрок перебрал с очками, запускается проверка на туза, чтобы изменить его значение с 11 до 1
                        for (int i = 0; i < count_player_cards; i++) {
                            if (Objects.equals(playerhand[i].name, "Туз")) {
                                if (playerhand[i].value == 11) {// это сделано если у игрока будет несколько тузов, и некоторые будут уже иметь значение 1, тот же случай когда 22 туза подряд
                                    playerhand[i].value = 1;
                                    player_score -= 10;
                                    break;
                                }
                            }
                        }
                        if (player_score > 21) { // если тузов не было найдено, или их оказалось 22, игрок проигрывает
                            printHand(1, count_player_cards);
                            printHand(3, count_dealers_cards);
                            System.out.println("Ну что, доигрался?");
                            global_score[1] += 1; // добавление очка противнику
                            break;
                        }
                    }
                    if (player_score == 21) { // если игрок после взятия некоторого количества карт набрал 21, то он сразу выигрывает
                        printHand(1, count_player_cards);
                        printHand(3, count_dealers_cards);
                        System.out.println("Эх, проиграл я, ты выиграл!");
                        global_score[0] += 1; // добавление очка игроку
                        break;
                    }
                    printHand(1, count_player_cards);
                    printHand(2, count_dealers_cards);
                    System.out.println("Возьмешь еще? 1 - взять / 0 - не брать?");
                    ans = in.nextInt();
                }
                if (player_score < 21) { // если игрок не набрал нужное количество очков для победы или проигрыша, ход переходит дилеру
                    if (dealer_score > 16 && dealer_score < 21) { // так как дилер берет карты, пока их сумма меньше 17, мы делаем на это проверку, чтобы вывести их руки
                        printHand(1, count_player_cards);
                        printHand(3, count_dealers_cards);
                    } else if (dealer_score == 21) { // если дилер сразу получил 21 очко ему причисляется победа
                        global_score[1] += 1;
                        printHand(1, count_player_cards);
                        printHand(3, count_dealers_cards);
                        System.out.println("Моя безоговрочная победа");
                    }
                    while (dealer_score < 17) { // если сумма карт дилера меньше 17, он берет еще
                        dealerhand[count_dealers_cards++] = new Card(deck[card_index].name, deck[card_index].suit, deck[card_index].value); //  добавление карты
                        System.out.println("Дилер получил карту: " + deck[card_index]);
                        dealer_score += deck[card_index++].value;
                        if (dealer_score > 21) { // если дилер набрал больше 21 очка, то идет проверка на туза, все так же как и у игрока
                            for (int i = 0; i < count_dealers_cards; i++) {
                                if (Objects.equals(dealerhand[i].name, "Туз")) {
                                    if (dealerhand[i].value == 11) {
                                        dealerhand[i].value = 1;
                                        dealer_score -= 10;
                                        break;
                                    }
                                }
                            }
                            if (dealer_score > 21) {
                                printHand(1, count_player_cards);
                                printHand(2, count_dealers_cards);
                                System.out.println("Плохо тасовал карты.. Ты выиграл!");
                                global_score[0] += 1;
                                break;
                            }
                        }
                        if (dealer_score == 21) {
                            printHand(1, count_player_cards);
                            printHand(2, count_dealers_cards);
                            System.out.println("Сегодня я в ударе");
                            global_score[1] += 1;
                            break;
                        }
                        printHand(1, count_player_cards);
                        printHand(2, count_dealers_cards);
                    }

                }
                if (dealer_score < 21 && player_score < 21) { // если и дилер и игрок набрали меньше 21, надо сравнить очки для выявления победителя или ничьей
                    if (player_score > dealer_score) { // выигрыш игрока
                        System.out.println("Неожиданно, но я проиграл");
                        global_score[0] += 1;
                    } else if (dealer_score > player_score) { // выигрыш дилера
                        System.out.println("Не пошла у тебя карта, в следующий раз повезет");
                        global_score[1] += 1;
                    } else { // ничья
                        System.out.println("Ух ты, у нас ничья, хорошая игра!");
                    }

                }
                System.out.println("Счет: " + global_score[0] + ":" + global_score[1]); // вывод счета
                System.out.println("Раскинем картишки еще раз? y/n"); // вопрос об продолжении игры и считывания ответа
                in.nextLine();
                gamestatus = in.nextLine();
            }
        }
        System.out.println("Тогда увидимся в следующий раз, до встречи");
    }
    public static void main(String[] args) {
        System.out.print("Ну что, раскинем картишки? y/n\n");// вопрос об начале игры и считывания ответа
        Scanner in = new Scanner(System.in);
        String answer = in.nextLine();
        if (Objects.equals(answer, "y")) {
            System.out.println("Начнем тогда игру!");
            game(); // старт игры
        } else {
            System.out.print("В этот раз останешься тогда с деньгами)");
        }
    }
}
