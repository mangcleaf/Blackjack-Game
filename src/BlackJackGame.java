import java.util.Scanner;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class BlackJackMain {
    private static void enterToContinue(Scanner scan){
        System.out.print("\nEnter to Continue.");
        scan.nextLine();
    }

    private static void clearScreen(){
        System.out.println("\u001Bc");
    }

    public static void spadeSymbol(){
        System.out.print("\u2660");
    }

    public static void diamondSymbol(){
        System.out.print("\u2666");
    }

    public static void clubSymbol(){
        System.out.print("\u2663");
    }

    public static void heartSymbol(){
        System.out.print("\u2665");
    }

    public static int wager(int money, Scanner scan){
        clearScreen();

        System.out.println("Bank: $" + money);
        
        boolean validResponse = false;
        int wager = 0;

        while(!validResponse){
            System.out.print("\nHow much would you like to wager? ");
            String response = scan.nextLine();
            
            if(!response.matches("\\d+")){
                System.out.println("Error!!! Please enter the wager as an integer!\n");
            } else{
                wager = Integer.parseInt(response);
                if(wager > money){
                    System.out.println("Sorry! You don't have enough money to wager that much!");
                } else if (wager == 0){
                    System.out.println("Please enter a valid!");
                } else {
                    validResponse = true;
                }
            }
        }
        return wager;
    }

    public static String endQuestion(Scanner scan){
        boolean validResponse = false;
        String response = null;

        while (!validResponse){
            System.out.print("\nWould you like to play again? ");
            response = scan.nextLine();

            if (response.equalsIgnoreCase("Yes")){
                validResponse = true;
                return response;
            } else if (response.equalsIgnoreCase("No")){
                validResponse = true;
                return response;
            } else {
                System.out.println("Please enter a valid response (Yes/No). ");
            }
        }
        
        return response;
    }

    public static void printBank(int money, int wager){
        clearScreen();
        System.out.println("Bank: " + money);
        System.out.println("Wager: " + wager);
    }
    
    public static String[] randomCard(String[] symbols, String[] rank, Random rand){
        String[] card = new String[2];

        int randomSymbol = rand.nextInt(4);
        int randomValue = rand.nextInt(13);

        card[0] = symbols[randomSymbol];
        card[1] = rank[randomValue];
        return card;
    }

    public static void printCard(String[] card){
        if(card[0].equals("Spade")){
            spadeSymbol();
        } else if(card[0].equals("Diamond")){
            diamondSymbol();
        } else if(card[0].equals("Club")){
            clubSymbol();
        } else if (card[0].equals("Heart")){
            heartSymbol();
        }
        System.out.println(" " + card[1]);
    }
   
    public static void scoreBoard(int Blackjack, int roundsWon, int highestBank){
        clearScreen();
        System.out.println("!SCOREBOARD!");
        System.out.println("-----------------------");
        System.out.println("Highest Bank: " + highestBank);
        System.out.println("Number of Rounds Won: " + roundsWon);
        System.out.println("Number of Blackjacks: " + Blackjack);
    }

    public static void dealerUnknown(List <String[]> dealerHand){
        System.out.println("\nDealer's Hand: ");
        printCard(dealerHand.get(0));
        System.out.println("???");
    }

    public static void printingHand(List <String[]> hand){
        for(int i = 0; i < hand.size(); i++){
            printCard(hand.get(i));
        }
    }

    public static void roundOver(List <String[]> playerHand, List <String[]> dealerHand){
        playerHand.clear();
        dealerHand.clear();
    }

    public static int handValue(List <String[]> hand){
        int value = 0;
        int numAs = 0;

        for (int i = 0; i < hand.size(); i++){
            String[] card = hand.get(i);

            card = hand.get(i);

            String rank = card[1];

            if(rank.equals("2")) {
                value = value + 2;
            } else if (rank.equals("3")) {
                value = value + 3;
            } else if (rank.equals("4")) {
                value = value + 4;
            } else if (rank.equals("5")) {
                value = value + 5;
            } else if (rank.equals("6")) {
                value = value + 6;
            } else if (rank.equals("7")) {
                value = value + 7;
            } else if (rank.equals("8")) {
                value = value + 8;
            } else if (rank.equals("9")) {
                value = value + 9;
            } else if (rank.equals("10") || rank.equals("K") || rank.equals("Q") || rank.equals("J")) {
                value = value + 10;
            } else if (rank.equals("A")) {
                if (numAs <= 1){
                    value = value + 11;
                } else {
                    value = value +1;
                }
                numAs++;
            }
        }

        return value;
    }

    public static void hit(List <String[]> hand, String[] symbols, String[] rank, Random rand){
        hand.add(randomCard(symbols, rank, rand));
    }

    public static String choice(List <String[]> playerHand, Scanner scan){
        boolean validResponse = false;
        String response = null;

        while(!validResponse){
            if(playerHand.get(0)[1].equals(playerHand.get(1)[1])){
                System.out.print("\nWould you like to \"Hit\" or \"Split\" or \"Stand\"? ");
            } else {
                System.out.print("\nWould you like to \"Hit\" or \"Stand\"? ");
            }
            response = scan.nextLine();

            if(response.equalsIgnoreCase("Hit")){
                validResponse = true;
            } else if(response.equalsIgnoreCase("Split")){
                validResponse = true;
            } else if (response.equalsIgnoreCase("Stand")){
                validResponse = true;
            } else {
                System.out.println("Error!!! Please enter a valid response!!!");
            }

        }

        return response;
    }

    public static void dealerTurn(List <String[]> dealerHand, String[] symbols, String[] rank, Random rand, Scanner scan, int money, int wager, List <String[]> playerHand){
        clearScreen();
        printBank(money, wager);
        System.out.println("\nYour Hand: ");
        printingHand(playerHand);
        System.out.println("\nDealer's Hand");
        printingHand(dealerHand);
        System.out.println("\nDealer is drawing a card....");
        enterToContinue(scan);

        while (handValue(dealerHand) < 17) { 
            clearScreen();
            
            
            dealerHand.add(randomCard(symbols, rank, rand));
            
            printBank(money, wager);
            System.out.println("\nYour Hand: ");
            printingHand(playerHand);
            System.out.println("\nDealer's Hand");
            printingHand(dealerHand);
        
            if (handValue(dealerHand) < 17) {
                System.out.println("\nDealer is drawing a card...");
            }

            enterToContinue(scan);
        }
    
    }

    public static int insurance(int money, int wager, Scanner scan, List<String[]> playerHand, List <String[]> dealerHand){
        boolean validResponse = false;
        String response = "";

        while(!validResponse) {
            System.out.print("\nWould you like to buy Insurance? ");
            response = scan.nextLine();

            if(response.equalsIgnoreCase("Yes")){
                validResponse = true;
            } else if(response.equalsIgnoreCase("No")){
                validResponse = true;
            } else {
                System.out.println("Please enter a valid response!!!");
            }
        }

        if (response.equalsIgnoreCase("Yes")){
            int insuranceBet = wager /2;
            System.out.println("You placed an Insurance Bet of $" + insuranceBet + ".");

            if(handValue(dealerHand) == 21){
                System.out.println("\nThe dealer has a Blackjack! You win the insurance Bet.");
                money += insuranceBet * 2;
            } else {
                System.out.println("\nThe dealer does not have a Blackjack. You lost the Insurance Bet.");
                money -= insuranceBet;
            }
        }

        return money;
    }

    public static int playSplit(int money, int wager, List <String[]> playerHand, List <String[]> dealerHand, Scanner scan, String[] symbol, String[] rank, Random rand, int round){
        String str = "";

        if (round == 1){
            str = "1st";
        } else if (round == 2){
            str = "2nd";
        }
        
        if(handValue(playerHand) == 21) {
            if(handValue(playerHand) == 21 && handValue(dealerHand) == 21){
                printBank(money, wager);
                System.out.println("\nYour " + str + " Hand: ");
                printingHand(playerHand);
                System.out.println("\nDealer's Hand: ");
                printingHand(dealerHand);
    
                System.out.println("\nPUSH!!!");
            } else if (handValue(playerHand) == 21){
                printBank(money, wager);
                System.out.println("\nYour " + str + " Hand: ");
                printingHand(playerHand);
                System.out.println("BLACKJACK!!!");
                System.out.println("\nDealer's Hand: ");
                printingHand(dealerHand);
                System.out.println("Dealer Loses.");
                
                money = money + wager + (wager/2);
            }

            return money;
        } 

        if (dealerHand.get(0)[1].equals("A")) {
            money = insurance(money, wager, scan, playerHand, dealerHand);

            if(handValue(dealerHand) == 21){
                money -= wager;
                return money;
            }
        } 

        String response = choice(playerHand, scan);

        while (!response.equalsIgnoreCase("Stand") && handValue(playerHand) <= 21){
            if (response.equalsIgnoreCase("hit")){
                hit(playerHand, symbol, rank, rand);

                clearScreen();
                printBank(money, wager);
                System.out.println("\nYour " + str + " Hand: ");
                printingHand(playerHand);
                dealerUnknown(dealerHand);

                if (handValue(playerHand) > 21) {
                    break; 
                }
            } else if (response.equalsIgnoreCase("Split")){
                money = split(playerHand, symbol, rank, rand, scan, wager, money, dealerHand);
                return money;
            }
            response = choice(playerHand, scan);
        }

        if (handValue(playerHand) > 21){
            if (handValue(dealerHand) == 21){
                clearScreen();
                printBank(money, wager);
                System.out.println("\nYour " + str + " Hand: ");
                printingHand(playerHand);
                System.out.println("BUST!!! You Lose!");
                System.out.println("\nDealer's Hand: ");
                printingHand(dealerHand);
                System.out.println("BLACKJACK!!! Dealer Wins!");

                money = money - wager - (wager/2);
                return money;
            } else {
                clearScreen();
                printBank(money, wager);
                System.out.println("\nYour " + str + " Hand: ");
                printingHand(playerHand);
                System.out.println("BUST!!! You Lose!");
                System.out.println("\nDealer's Hand: ");
                printingHand(dealerHand);
                System.out.println("Dealer Wins!");

                money = money - wager;
                return money;
            }
        }

        else if (handValue(playerHand) == 21){
            if (handValue(dealerHand) == 21){
                clearScreen();
                printBank(money, wager);
                System.out.println("\nYour " + str + " Hand: ");
                printingHand(playerHand);
                System.out.println("You Lose!");
                System.out.println("\nDealer's Hand: ");
                printingHand(dealerHand);
                System.out.println("BLACKJACK!!! Dealer Wins!");

                money = money - wager - (wager/2);
                return money;
            } else {
                clearScreen();
                printBank(money, wager);
                System.out.println("\nYour " + str + " Hand: ");
                printingHand(playerHand);
                System.out.println("\nDealer's Hand: ");
                printingHand(dealerHand);
                dealerTurn(dealerHand, symbol, rank, rand, scan, money, wager, playerHand);
                clearScreen();

                if (handValue(dealerHand) == 21) {
                    printBank(money, wager);
                    System.out.println("\nYour " + str + " Hand: ");
                    printingHand(playerHand);
                    System.out.println("\nDealer's Hand: ");
                    printingHand(dealerHand);

                    System.out.println("PUSH!!!");

                    return money;
                } else if (handValue(dealerHand) > 21) {
                    printBank(money, wager);
                    System.out.println("\nYour " + str + " Hand: ");
                    printingHand(playerHand);
                    System.out.println("You Win!!!");
                    System.out.println("\nDealer's Hand: ");
                    printingHand(dealerHand);
                    System.out.println("BUSTED!!! Dealer Loses.");

                    money = money + wager;
                    return money;
                } else {
                    printBank(money, wager);
                    System.out.println("\nYour " + str + " Hand: ");
                    printingHand(playerHand);
                    System.out.println("You Win!!!");
                    System.out.println("\nDealer's Hand: ");
                    printingHand(dealerHand);
                    System.out.println("Dealer Loses.");

                    money = money + wager;
                    return money;
                }
                
            }
            
        }

        else {
            clearScreen();
            printBank(money, wager);
            System.out.println("\nYour " + str + " Hand: ");
            printingHand(playerHand);
            System.out.println("\nDealer's Hand: ");
            printingHand(dealerHand);
            dealerTurn(dealerHand, symbol, rank, rand, scan, money, wager, playerHand);

            if (handValue(dealerHand) > 21){
                clearScreen();
                printBank(money, wager);
                System.out.println("\nYour " + str + " Hand: ");
                printingHand(playerHand);
                System.out.println("You Win!!!");
                System.out.println("\nDealer's Hand: ");
                printingHand(dealerHand);
                System.out.println("BUST!!! Dealer Loses!!!");

                money = money + wager;
                return money; 
            } else if (handValue(dealerHand) == 21){
                clearScreen();
                printBank(money, wager);
                System.out.println("\nYour " + str + " Hand: ");
                printingHand(playerHand);
                System.out.println("You Lose!!!");
                System.out.println("\nDealer's Hand: ");
                printingHand(dealerHand);
                System.out.println("Dealer Wins!!!");

                money = money - wager;
                return money; 
            } else if (handValue(dealerHand) == handValue(playerHand)){
                clearScreen();
                printBank(money, wager);
                System.out.println("\nYour " + str + " Hand: ");
                printingHand(playerHand);
                System.out.println("\nDealer's Hand: ");
                printingHand(dealerHand);
                System.out.println("\nPUSH!!!");

                return money; 

            } else if (handValue(playerHand) > handValue(dealerHand)){
                clearScreen();
                printBank(money, wager);
                System.out.println("\nYour " + str + " Hand: ");
                printingHand(playerHand);
                System.out.println("You Win!!!");
                System.out.println("\nDealer's Hand: ");
                printingHand(dealerHand);
                System.out.println("Dealer Loses!!!");

                money = money + wager;
                return money; 
            } else if (handValue(playerHand) < handValue(dealerHand)) {
                clearScreen();
                printBank(money, wager);
                System.out.println("\nYour " + str + " Hand: ");
                printingHand(playerHand);
                System.out.println("You Lose!!!");
                System.out.println("\nDealer's Hand: ");
                printingHand(dealerHand);
                System.out.println("Dealer Wins!!!");

                money = money - wager;
                return money; 
            }
        }

        return money;
    }

    public static int split(List <String[]> hand, String[] symbols, String[] rank, Random rand, Scanner scan, int wager, int money, List <String[]> dealerHand){
        List<String[]> hand1 = new ArrayList<>();
        List<String[]> hand2 = new ArrayList<>();

        hand1.add(hand.get(0));
        hand2.add(hand.get(1));

        hand1.add(randomCard(symbols, rank, rand));
        hand2.add(randomCard(symbols, rank, rand));

        int round = 1;

        clearScreen();

        //match 1
        printBank(money, wager);
        System.out.println("\nYour 1st Hand: ");
        printingHand(hand1);
        dealerUnknown(dealerHand);

        money = playSplit(money, wager, hand1, dealerHand, scan, symbols, rank, rand, round);

        enterToContinue(scan);

        round ++;

        //match 2
        clearScreen();
        printBank(money, wager);
        System.out.println("\nYour 2nd Hand: ");
        printingHand(hand2);
        System.out.println("\nDealer's Hand");
        printingHand(dealerHand);

        money = playSplit(money, wager, hand2, dealerHand, scan, symbols, rank, rand, round);

        return money;
    }

    public static int play(int money, int wager, List <String[]> playerHand, List <String[]> dealerHand, Scanner scan, String[] symbol, String[] rank, Random rand){

        if(handValue(playerHand) == 21) {
            if(handValue(playerHand) == 21 && handValue(dealerHand) == 21){
                printBank(money, wager);
                System.out.println("\nYour Hand: ");
                printingHand(playerHand);
                System.out.println("\nDealer's Hand: ");
                printingHand(dealerHand);
    
                System.out.println("\nPUSH!!!");
            } else if (handValue(playerHand) == 21){
                printBank(money, wager);
                System.out.println("\nYour Hand: ");
                printingHand(playerHand);
                System.out.println("BLACKJACK!!!");
                System.out.println("\nDealer's Hand: ");
                printingHand(dealerHand);
                System.out.println("Dealer Loses.");
                
                money = money + wager + (wager/2);
            }

            return money;
        } 

        if (dealerHand.get(0)[1].equals("A")) {
            money = insurance(money, wager, scan, playerHand, dealerHand);

            if(handValue(dealerHand) == 21){
                money -= wager;
                return money;
            }
        } 

        String response = choice(playerHand, scan);

        while (!response.equalsIgnoreCase("Stand") && handValue(playerHand) <= 21){
            if (response.equalsIgnoreCase("hit")){
                hit(playerHand, symbol, rank, rand);

                clearScreen();
                printBank(money, wager);
                System.out.println("\nYour Hand: ");
                printingHand(playerHand);
                dealerUnknown(dealerHand);

                if (handValue(playerHand) > 21) {
                    break; 
                }
            } else if (response.equalsIgnoreCase("Split")){
                money = split(playerHand, symbol, rank, rand, scan, wager, money, dealerHand);
                return money;
            }
            response = choice(playerHand, scan);
        }

        if (handValue(playerHand) > 21){
            if (handValue(dealerHand) == 21){
                clearScreen();
                printBank(money, wager);
                System.out.println("\nYour Hand: ");
                printingHand(playerHand);
                System.out.println("BUST!!! You Lose!");
                System.out.println("\nDealer's Hand: ");
                printingHand(dealerHand);
                System.out.println("BLACKJACK!!! Dealer Wins!");

                money = money - wager - (wager/2);
                return money;
            } else {
                clearScreen();
                printBank(money, wager);
                System.out.println("\nYour Hand: ");
                printingHand(playerHand);
                System.out.println("BUST!!! You Lose!");
                System.out.println("\nDealer's Hand: ");
                printingHand(dealerHand);
                System.out.println("Dealer Wins!");

                money = money - wager;
                return money;
            }
        }

        else if (handValue(playerHand) == 21){
            if (handValue(dealerHand) == 21){
                clearScreen();
                printBank(money, wager);
                System.out.println("\nYour Hand: ");
                printingHand(playerHand);
                System.out.println("You Lose!");
                System.out.println("\nDealer's Hand: ");
                printingHand(dealerHand);
                System.out.println("BLACKJACK!!! Dealer Wins!");

                money = money - wager - (wager/2);
                return money;
            } else {
                clearScreen();
                printBank(money, wager);
                System.out.println("Your Hand: ");
                printingHand(playerHand);
                System.out.println("\nDealer's Hand: ");
                printingHand(dealerHand);
                dealerTurn(dealerHand, symbol, rank, rand, scan, money, wager, playerHand);
                clearScreen();

                if (handValue(dealerHand) == 21) {
                    printBank(money, wager);
                    System.out.println("\nYour Hand: ");
                    printingHand(playerHand);
                    System.out.println("\nDealer's Hand: ");
                    printingHand(dealerHand);

                    System.out.println("PUSH!!!");

                    return money;
                } else if (handValue(dealerHand) > 21) {
                    printBank(money, wager);
                    System.out.println("\nYour Hand: ");
                    printingHand(playerHand);
                    System.out.println("You Win!!!");
                    System.out.println("\nDealer's Hand: ");
                    printingHand(dealerHand);
                    System.out.println("BUSTED!!! Dealer Loses.");

                    money = money + wager;
                    return money;
                } else {
                    printBank(money, wager);
                    System.out.println("\nYour Hand: ");
                    printingHand(playerHand);
                    System.out.println("You Win!!!");
                    System.out.println("\nDealer's Hand: ");
                    printingHand(dealerHand);
                    System.out.println("Dealer Loses.");

                    money = money + wager;
                    return money;
                }
                
            }
            
        }

        else {
            clearScreen();
            printBank(money, wager);
            System.out.println("\nYour Hand: ");
            printingHand(playerHand);
            System.out.println("\nDealer's Hand: ");
            printingHand(dealerHand);
            dealerTurn(dealerHand, symbol, rank, rand, scan, money, wager, playerHand);

            if (handValue(dealerHand) > 21){
                clearScreen();
                printBank(money, wager);
                System.out.println("\nYour Hand: ");
                printingHand(playerHand);
                System.out.println("You Win!!!");
                System.out.println("\nDealer's Hand: ");
                printingHand(dealerHand);
                System.out.println("BUST!!! Dealer Loses!!!");

                money = money + wager;
                return money; 
            } else if (handValue(dealerHand) == 21){
                clearScreen();
                printBank(money, wager);
                System.out.println("\nYour Hand: ");
                printingHand(playerHand);
                System.out.println("You Lose!!!");
                System.out.println("\nDealer's Hand: ");
                printingHand(dealerHand);
                System.out.println("Dealer Wins!!!");

                money = money - wager;
                return money; 
            } else if (handValue(dealerHand) == handValue(playerHand)){
                clearScreen();
                printBank(money, wager);
                System.out.println("\nYour Hand: ");
                printingHand(playerHand);
                System.out.println("\nDealer's Hand: ");
                printingHand(dealerHand);
                System.out.println("\nPUSH!!!");

                return money; 

            } else if (handValue(playerHand) > handValue(dealerHand)){
                clearScreen();
                printBank(money, wager);
                System.out.println("\nYour Hand: ");
                printingHand(playerHand);
                System.out.println("You Win!!!");
                System.out.println("\nDealer's Hand: ");
                printingHand(dealerHand);
                System.out.println("Dealer Loses!!!");

                money = money + wager;
                return money; 
            } else if (handValue(playerHand) < handValue(dealerHand)){
                clearScreen();
                printBank(money, wager);
                System.out.println("\nYour Hand: ");
                printingHand(playerHand);
                System.out.println("You Lose!!!");
                System.out.println("\nDealer's Hand: ");
                printingHand(dealerHand);
                System.out.println("Dealer Wins!!!");

                money = money - wager;
                return money; 
            }
        }

        return money;
    }
    public static void main(String[] args) {

        String[] symbols = {"Spade", "Diamond", "Club", "Heart"};
        String[] rank = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        
        List<String[]> playerHand = new ArrayList<>();
        List<String[]> dealerHand = new ArrayList<>();

        Scanner scan = new Scanner(System.in);
        Random rand = new Random();

        boolean running = true;
        int money = 500;
        int previous = 500;
        int roundsWon = 0;
        int highestBank = money;
        int blackjack = 0;

        clearScreen();
        System.out.println("Lets Play BlackJack!!!");
        System.out.println("\nRules: ");
        System.out.println("Players will receive all their cards face up and the dealer's first card is face up and second is face down. \nThe objective of the game is to get closer to 21 than the dealer without going over 21!\n");
        enterToContinue(scan);
        
        clearScreen();
        
        while(money > 0 && running){
            int wager = wager(money, scan);
            
            //Game Start!!!
            //randomly assign player and dealer 2 cards!!!
            playerHand.add(randomCard(symbols, rank, rand));
            playerHand.add(randomCard(symbols, rank, rand));
            dealerHand.add(randomCard(symbols, rank, rand));
            dealerHand.add(randomCard(symbols, rank, rand));

            enterToContinue(scan);

            printBank(money, wager);

            System.out.println("\nYour Hand: ");
            printingHand(playerHand);
            dealerUnknown(dealerHand);

            money = play(money, wager, playerHand, dealerHand, scan, symbols, rank, rand);

            if (money > highestBank){
                highestBank = money;
            }

            if (handValue(playerHand) == 21 && playerHand.size() == 2) {
                blackjack++;
            }

            if (previous < money){
                roundsWon++;
                previous = money;
            }


            //END, ask player if they wanna play again//
            if(money == 0){
                enterToContinue(scan);
                running = false;
            } else {
                String playAgain = endQuestion(scan);
                if(playAgain.equalsIgnoreCase("No")){
                    running = false; 
                }
            }

            roundOver(playerHand, dealerHand);
        }


        scoreBoard(blackjack, roundsWon, highestBank);
        System.out.println("\nThank you for playing!!!\nGOODBYE!!!");

        scan.close();
    }
}
