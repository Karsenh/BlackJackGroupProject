import java.util.ArrayList;

/**
 * Hand Class
 *
 * @author Karsen Hansen
 * @version 1.0
 */
public class Hand {

    private ArrayList<Card> cards;
    private int reduceAces;

    /**
     * Initialize Empty Card
     */
    public Hand() {
        this.cards = new ArrayList<Card>();
    }

    /**
     * Add Card into Handl
     *
     * @param card adds cards to hand.
     */
    public void addCard(Card card) {
        this.cards.add(card);
    }

    /**
     * Marginal algorithm to stand when the player hand reaches 18 score.
     * this is safe bet. Useful for automation of dealer player.
     *
     * @return true if player can hit
     */
    public boolean canHit() {
        return calcValue() <= 18;
    }

    /**
     * Calculate the Hand Value
     *
     * @return value
     */
    public int calcValue() {
        int value = 0;

        for(Card card: cards) {
            value += card.value;
        }

        // if there are more aces, reduce scores
        if(hasMoreAces()) {
            value -= reduceAces;
        }
        return value;
    }

    /**
     * Check if the Hand is busted
     *
     * @return busted
     */
    public boolean isBusted() {
        return this.calcValue() > 21;
    }

    /**
     * Function to check if the Player scored a Black Jack.
     * @return true if value is equal to 21 (BlackJack)
     */
    public boolean isBlackJack() {
        return this.calcValue() == 21;
    }

    /**
     * Helper function to reduce the aces value of 11 and count only 1 for
     * additional aces
     *
     * @return true if there are more than 1 aces
     */
    private boolean hasMoreAces() {
        int count = 0;
        for(Card card: cards) {
            if(card.value == 11) {
                count++;
            }
        }

        reduceAces = (count - 1) * 10;
        return count > 1;
    }

    /**
     * Get the String representation of the Hand
     *
     * @return string representation
     */
    public String toString() {
        String output = "";
        for (Card c : cards) {
            output += "Player has the card " + c.name + " of " + c.suit + "\n";
        }
        return output;
    }

    /**
     * Method to get card count.
     * @return card size amount.
     */
    public int getCount() {
        return this.cards.size();
    }

    /**
     * Method to get card.
     * @param index is the card index location.
     * @return card index location.
     */
    public Card getCard(int index) {
        return this.cards.get(index);
    }

    /**
     * Method to clear card.
     */
    public void reset() {
        this.cards.clear();
    }
}
