public class Card {

    int number;                     //Card value (1-10, J, Q, K)
    String suit;                    //Card suit (Hearts, Diamonds, Spades, Clubs)
    boolean cardUsed = false;       //If the card is used or not
    String symbol;
    String name;
    int id;
    int value;

    public Card(int n, String s, int id) {         //Card Constructor
        this.number = n;
        this.suit = s;
        this.id = id;

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

        /**
        public void setUsed() {
            used = true;
            System.out.println("The card " + name + " of " + suit + " is now used.");
        }

        public void setNotUsed() {
            System.out.println("The card " + name + " of " + suit + " is not used.");
        }
         **/
        //System.out.println("Card " + name + " of " + suit + " was created. ID = " + this.id);
    }

}
