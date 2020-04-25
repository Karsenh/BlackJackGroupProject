/**
 * Card Class
 *
 * @author Karsen Hansen
 * @version 1.0
 */
public class Card {

    int number;                     //Card value (1-10, J, Q, K)
    String suit;                    //Card suit (Hearts, Diamonds, Spades, Clubs)
    String symbol;
    String name;
    int value;

    /**
     * Constructor to setup the Card
     *
     * @param n to set card number value.
     * @param s to set card suit.
     */
    public Card(int n, String s) {         //Card Constructor
        this.number = n;
        this.suit = s;

        if (number < 11) {
            symbol = Integer.toString(number);      //Converts integer value passed in to a String
            name = Integer.toString(number);
            value = number;
        } else if (number == 11) {
            symbol = "J";
            name = "Jack";
            value = 10;
        } else if (number == 12) {
            symbol = "Q";
            name = "Queen";
            value = 10;
        } else if (number == 13) {
            symbol = "K";
            name = "King";
            value = 10;
        } else {
            symbol = "A";
            name = "Ace";
            value = 11;
        }
    }
}
