import java.util.ArrayList;
import java.util.Random;


/**
 * Deck Class to handle all 52 cards
 *
 * @author Karsen Hansen
 * @version 1.0
 */
public class Deck {
    private ArrayList<Card> cards;

    /**
     * Initialize a Deck
     */
    public Deck() {
        cards = new ArrayList<Card>();
        reset();
    }

    /**
     * Reset the Deck to the intitial state.
     */
    public void reset() {
        cards.clear();
        // Initialize Deck
        String suits [] = {"Spades", "Hearts", "Diamonds", "Clubs"};

        for(int i=0; i<suits.length; i++) {
            for(int j=2; j<=14; j++) {
                Card card = new Card(j, suits[i]);
                cards.add(card);
            }
        }
    }

    /**
     * Shuffle the Deck
     */
    public void shuffle() {

        Random random = new Random();

        for(int i=0; i<1000; i++) {
            int x = random.nextInt(cards.size());
            int y = random.nextInt(cards.size());
            Card tmp = cards.get(x);
            cards.set(x, cards.get(y));
            cards.set(y, tmp);
        }
    }

    /**
     * Deal a card and remove from the Deck
     *
     * @return Card
     */
    public Card deal() {
        return cards.remove(0);
    }
}
