public class Card {

    int number;                     //Card value (1-10, J, Q, K)
    String suit;                   //Card suit (Hearts, Diamonds, Spades, Clubs)
    boolean cardUsed = false;       //If the card is used or not
    String symbol;
    String name;
    int id;

    public Card(int n, String s, int id) {         //Card Constructor
        this.number = n;
        this.suit = s;
        this.id = id;

        if (number < 11) {
            symbol = Integer.toString(number);      //Converts integer value passed in to a String
            name = Integer.toString(number);
        } else if (number == 11) {
            symbol = "J";
            name = "Jack";
        } else if (number == 12) {
            symbol = "Q";
            name = "Queen";
        } else if (number == 13) {
            symbol = "K";
            name = "King";
        } else {
            symbol = "A";
            name = "Ace";
        }

        //System.out.println("Card " + name + " of " + suit + " was created. ID = " + this.id);
    }

}
