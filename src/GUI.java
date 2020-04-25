/*
Title:              CPSC224 Group Project - 'PlagueJack' GUI
Author(s):          Karsen Hansen
Creation Date:      3.17.2020
*/

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;                           //Implemented separately from above import

/**
 * Graphic User Interface (GUI) Class for the PlagueJack Game
 *
 * @author Karsen Hansen
 * @version 1.0
 */
public class GUI extends JFrame {

    /**
     * 1. Resolution variables
     * 2. Game phase booleans
     * 3. Color objects
     * 4. Font objects
     * 5. Question strings
     * 6. JButton objects
     * 7. Health variables
     * 8. JProgressBar objects
     * 9. Card Location grid variables
     * 10. Card dimensions and spacing
     * 11. Detail Log dimensions
     * 12. Deck and Hand objects
     * 13. JLabels and Hand Status booleans
     */
    //RESOLUTIONS
    int aW = 1280;                                              //Actual screen resolution Width
    int aH = 800;                                               //Actual screen resolution Height

    //GAME PHASE BOOLEANS
    boolean bool_hit_stay = true;
    boolean bool_dealer_turn = false;
    boolean bool_play_more = false;

    //COLORS
    Color colorBackground = new Color(39, 119, 20);     //Background Colors RGB values
    Color barColor = new Color(80, 200, 80);            //Background Colors RGB values
    Color colorButton = new Color(204, 189, 0);         //Yes & No Button Colors
    Color colorHitButton = new Color(204, 183, 4);      //Hit Button Color
    Color colorStayButton = new Color(203, 204, 201);   //Stay Button Color

    //FONTS
    Font fontButton = new Font("Times New Roman", Font.PLAIN, 30); //Font name, type, size
    Font fontCard = new Font("Times New Roman", Font.BOLD, 40); //Card Font
    Font fontQuestion = new Font("Times New Roman", Font.BOLD, 30);
    Font fontScore = new Font("Times New Roman", Font.BOLD, 20);

    //QUESTIONS
    String play_more_q = "Play More?";

    //BUTTONS
    JButton bHit = new JButton();                                 //'Hit' jbutton for new card
    JButton bStay = new JButton();                                //'Stay' jbutton for turn pass
    JButton btnGame = new JButton();

    private int playerHealth = 100;
    private int dealerHealth = 100;
    JProgressBar playerBar = new JProgressBar(0, playerHealth);
    JProgressBar dealerBar = new JProgressBar(0, dealerHealth);

    //CARD SPOT GRID POSITIONING & DIMENSIONS
    int gridx = 50;
    int gridy = 50;
    int gridw = 900;
    int gridh = 400;

    //CARD DIMENSIONS & SPACING
    int cardSpacing = 10;                                       //Spacing Between Cards
    int cardEdgeSoftening = 6;                                  //Card Corner Edge Softener
    int cardTW = gridw / 6;                                       //Card Total Width = gridw(idth) / 6 card spots
    int cardTH = gridh / 2;                                       //Card Total Height = gridh(eight) / 6 card spots
    int cardAW = cardTW - 2 * cardSpacing;                      //Card Total Width
    int cardAH = cardTH - 2 * cardSpacing;                      //Card Total Height

    //TOTALS & COMMAND LOG GRID POSITIONING & DIMENSIONS
    int hsx = gridx + gridw + 50;
    int hsy = gridy;
    int hsw = 230;
    int hsh = 400;

    //"PLAY MORE?" QUESTION GRID POSITION
    int pmx = hsx;
    int pmy = hsy + hsh + 50;
    int pmw = hsw;
    int pmh = 200;

    // Deck and Hands
    private Deck deck = new Deck();
    private Hand player = new Hand();
    private Hand dealer = new Hand();

    //JLABELS AND HAND STATUS BOOLEANS
    private JLabel lblPlayerScore;
    private JLabel lblDealScore;
    private JLabel lblMessage;
    private boolean isPlayerBusted;
    private boolean isPlayerBlackJack;
    private boolean isDealerBusted;
    private boolean isDealerBlackJack;
    private boolean loser;

    /**
     * GUI Constructor
     */
    public GUI() {
        //PRIMARY ATTRIBUTES
        this.setSize(aW + 6, aH + 6);             //Set Application Size
        this.setTitle("PlaguedJack");                           //Set Application Title
        this.setVisible(true);                                  //Enable visibility
        this.setResizable(false);                               //Can't resize
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //Close frame & terminate app on 'x'

        playerBar.setValue(playerHealth);
        dealerBar.setValue(dealerHealth);

        //BOARD
        Board board = new Board();
        this.setContentPane(board);                             //Set Content Pane to board
        this.setLayout(null);                                   //Set Layout to 'null' to update dimensions

        lblMessage = new JLabel("Player's Turn", JLabel.CENTER);
        lblMessage.setBounds(20, 540, 700, 50);
        lblMessage.setFont(fontQuestion);
        lblMessage.setForeground(Color.WHITE);
        board.add(lblMessage);

        playerBar.setBounds(100, 600, 700, 30);
        playerBar.setForeground(barColor);
        playerBar.setBackground(Color.black);
        playerBar.setString("Player: 100%");
        playerBar.setStringPainted(true);

        board.add(playerBar);

        dealerBar.setBounds(100, 650, 700, 30);
        dealerBar.setForeground(barColor);
        dealerBar.setBackground(Color.black);
        dealerBar.setString("Dealer: 100%");
        dealerBar.setStringPainted(true);
        board.add(dealerBar);

        lblPlayerScore = new JLabel("Player Score: ", JLabel.CENTER);
        lblPlayerScore.setBounds(900, 540, 400, 50);
        lblPlayerScore.setFont(fontScore);
        lblPlayerScore.setForeground(Color.WHITE);
        board.add(lblPlayerScore);

        lblDealScore = new JLabel("Player Score: ", JLabel.CENTER);
        lblDealScore.setBounds(900, 600, 400, 50);
        lblDealScore.setFont(fontScore);
        lblDealScore.setForeground(Color.WHITE);
        board.add(lblDealScore);

        //'HIT' Button
        ActHit aHit = new ActHit();                             //Action listener object for 'Hit'
        bHit.addActionListener(aHit);                           //Linking action (aHit) to 'Hit' button (bHit)
        bHit.setBounds(hsx + 55, hsy + 40, 120, 80); //Set 'Hit' button location & dimensions
        bHit.setBackground(colorHitButton);                        //Set 'Hit' button color
        bHit.setFont(fontButton);                               //Set 'Hit' button font
        bHit.setText("HIT");                                    //'Hit' text
        board.add(bHit);                                        //Add 'Hit' button to board

        //'STAY' Button
        ActStay aStay = new ActStay();                              //Action listener object for 'Stay'
        bStay.addActionListener(aStay);                             //Linking action (aStay) to 'Stay' button (bStay)
        bStay.setBounds(hsx + 55, hsy + 140, 120, 80);    //Set 'Stay' button location & dimensions
        bStay.setBackground(colorStayButton);                           //Set 'Stay' button color
        bStay.setFont(fontButton);                                  //Set 'Stay' button font
        bStay.setText("STAY");                                      //'Stay' text
        board.add(bStay);                                           //Add 'Stay' button to board

        //'STAY' Button
        ActGame aGame = new ActGame();                              //Action listener object for 'Stay'
        btnGame.addActionListener(aGame);                             //Linking action (aStay) to 'Stay' button (bStay)
        btnGame.setBounds(hsx + 55, hsy + 240, 120, 80);    //Set 'Stay' button location & dimensions
        btnGame.setBackground(Color.cyan);                           //Set 'Stay' button color
        btnGame.setFont(fontButton);
        btnGame.setText("New");                                      //'Stay' text
        board.add(btnGame);                                           //Add 'Stay' button to board

        // Shuffle Deck
        deck.shuffle();

        // Deal Cards
        // Player
        player.addCard(deck.deal());
        player.addCard(deck.deal());

        // Dealer Hand
        dealer.addCard(deck.deal());
        dealer.addCard(deck.deal());

        // display to console
        System.out.println("Player Hand: \n" + player);
        System.out.println("Dealer Hand: \n" + dealer);
        evalPlayerHand();
    }

    /**
     * Player Hand Evaluator
     *
     * @author Karsen Hansen
     * @version 1.0
     */
    private void evalPlayerHand() {
        int score = player.calcValue();
        this.lblPlayerScore.setText("Player Score: " + score);
        if(player.isBusted()) {
            bool_hit_stay = false;
            bool_dealer_turn = true;
            bHit.setEnabled(false);
            bStay.setEnabled(false);
            isPlayerBusted = true;
            loser = true;
            lblMessage.setText("Player has busted - Dealer Wins!");
            this.lblDealScore.setText("Dealer Score: " + dealer.calcValue());
            this.lblPlayerScore.setText("Player Score: " + score);
            doUpdateBars();
        } else if(player.isBlackJack()) {
            bool_hit_stay = false;
            bool_dealer_turn = true;
            bHit.setEnabled(false);
            bStay.setEnabled(false);
            isPlayerBlackJack = true;
            this.lblDealScore.setText("Dealer Score: " + dealer.calcValue());
            doUpdateBars();
            if(dealer.isBlackJack()) {
                lblMessage.setText("Dealer & Player Hit BlackJack - Its a Draw!");
            } else {
                lblMessage.setText("Player Hit BlackJack - Dealer Loses!");
            }
            this.lblPlayerScore.setText("Player Score: " + score);
        }
    }

    /**
     * Dealer Hand Evaluator
     *
     * @author Karsen Hansen
     * @version 1.0
     */
    private void evalDealerHand() {
        int score = dealer.calcValue();
        this.lblDealScore.setText("Dealer Score: " + score);
        if(dealer.isBusted()) {
            isDealerBusted = true;
            lblMessage.setText("Dealer has busted - Player Wins!");
            doUpdateBars();
       } else if(player.isBlackJack()) {
            isDealerBlackJack = true;
            if(player.isBlackJack()) {
                lblMessage.setText("Dealer & Player Hit BlackJack - Its a Draw!");
            } else {
                loser = true;
                lblMessage.setText("Dealer Hit BlackJack - Player Loses!");
            }
            doUpdateBars();
        }
    }

    /**
     * Check Winner
     *
     * @author Karsen Hansen
     * @version 1.0
     */
    private void checkWinner() {
        if(!isPlayerBusted && !isDealerBusted && !isDealerBlackJack && !isPlayerBlackJack)
        {
            int pScore = player.calcValue();
            int dScore = dealer.calcValue();
            this.lblDealScore.setText("Dealer Score: " + dScore);
            this.lblPlayerScore.setText("Player Score: " + pScore);

            if(pScore > dScore) {
                lblMessage.setText("Player Won the Hand!");
            } else if(pScore < dScore) {
                loser = true;
                lblMessage.setText("Player Lost the Hand!");
            } else {
                lblMessage.setText("Its a Draw!");
            }
        }
        doUpdateBars();
    }

    /**
     * Method to refresh game states and  button visibility.
     */
    public void refresher() {
        //BUTTON VISIBILITY
        if (bool_hit_stay == true) {
            bHit.setVisible(true);
            bStay.setVisible(true);
            btnGame.setVisible((false));
        } else if (bool_dealer_turn == true) {
            bHit.setVisible(false);
            bStay.setVisible(false);
            btnGame.setVisible((true));
        } else if (bool_play_more == true) {
            bHit.setVisible(false);
            bStay.setVisible(false);
            btnGame.setVisible((true));
        }
    }

    /**
     * Method to control dealer hit/stay.
     */
    public void dealerHitStay() {
        while(dealer.canHit()) {
            dealer.addCard(deck.deal());
            evalDealerHand();
        }

        // Check winner if not previously determined
        checkWinner();
    }

    /**
     * Game Board Class
     *
     * @author Karsen Hansen
     * @version 1.0
     */
    public class Board extends JPanel {

        /**
         * Method to paint entities on the game board.
         *
         * @param g object used to create board entities (background, cards, etc.)
         */
        public void paintComponent(Graphics g) {
            g.setColor(colorBackground);                            //Game board background
            g.fillRect(0, 0, aW, aH);                        //Game board (Background) dimensions

            //Paints Temporary Top Grid Paint
            g.setColor(Color.black);
            g.drawRect(gridx, gridy, gridw, gridh);

            //Paints Temporary Bottom Log Borders
            g.drawRect(gridx, gridy + gridh + 50, gridw, 200);

            //Paints Temporary Totals & Command Messages
            g.drawRect(hsx, hsy, hsw, hsh);

            //Paints Temporary "Play More?" Grid & Player Totals
            g.drawRect(pmx, pmy, pmw, pmh);

            //Paints Card Grid Rectangles
            for (int i = 0; i < 6; ++i) {
                g.drawRect(gridx + i * cardTW + cardSpacing, gridy + cardSpacing, cardAW, cardAH);
                g.drawRect(gridx + i * cardTW + cardSpacing, gridy + cardSpacing + cardTH, cardAW, cardAH);
            }

            //PLAYERS CARDS PAINT
            int index = 0;                          //Creates new card
            for (int i = 0; i < player.getCount(); i++) {
                Card c = player.getCard(i);
                g.setColor(Color.white);            //Create card background color

                //First RECTANGLE drawn for card edge softening (Height)
                g.fillRect(gridx + index * cardTW + cardSpacing, gridy + cardSpacing + cardEdgeSoftening, cardAW, cardAH - 2 * cardEdgeSoftening);
                //Second RECTANGLE drawn for card edge softening (Width)
                g.fillRect(gridx + index * cardTW + cardSpacing + cardEdgeSoftening, gridy + cardSpacing, cardAW - 2 * cardEdgeSoftening, cardAH);
                //First CIRCLE drawn for curved corner - TOP LEFT
                g.fillOval(gridx + index * cardTW + cardSpacing, gridy + cardSpacing, 2 * cardEdgeSoftening, 2 * cardEdgeSoftening);
                //Second CIRCLE drawn for curved corner - Top RIGHT
                g.fillOval(gridx + index * cardTW + cardSpacing + cardAW - 2 * cardEdgeSoftening, gridy + cardSpacing, 2 * cardEdgeSoftening, 2 * cardEdgeSoftening);
                //Third CIRCLE drawn for curved corner - BOTTOM LEFT
                g.fillOval(gridx + index * cardTW + cardSpacing, gridy + cardSpacing + cardAH - 2 * cardEdgeSoftening, 2 * cardEdgeSoftening, 2 * cardEdgeSoftening);
                //Fourth CIRCLE drawn for curved corner - Bottom RIGHT
                g.fillOval(gridx + index * cardTW + cardSpacing + cardAW - 2 * cardEdgeSoftening, gridy + cardSpacing + cardAH - 2 * cardEdgeSoftening, 2 * cardEdgeSoftening, 2 * cardEdgeSoftening);

                //Default Suit Color of Cards
                g.setColor(Color.black);

                //If the suit is a Diamond or Heart - Make cards red
                if (c.suit.equalsIgnoreCase("Hearts") || c.suit.equalsIgnoreCase("Diamonds")) {
                    g.setColor(Color.red);
                }

                g.setFont(fontCard);
                g.drawString(c.symbol, gridx + index * cardTW + cardSpacing * 2, gridy + cardAH);

                //Painting SUIT SYMBOL on cards
                //SPADES paint
                if (c.suit.equalsIgnoreCase("Spades")) {
                    g.setColor(Color.black);
                    g.fillOval(gridx + index * cardTW + 40, gridy + 85, 40, 40);
                    g.fillOval(gridx + index * cardTW + 70, gridy + 85, 40, 40);
                    g.fillArc(gridx + index * cardTW + 30, gridy + 28, 90, 70, 230, 80);
                    g.fillRect(gridx + index * cardTW + 70, gridy + 90, 10, 50);
                    //HEARTS paint
                } else if (c.suit.equalsIgnoreCase("Hearts")) {
                    g.setColor(Color.red);
                    g.fillOval(gridx + index * cardTW + 40, gridy + 70, 40, 40);
                    g.fillOval(gridx + index * cardTW + 70, gridy + 70, 40, 40);
                    g.fillArc(gridx + index * cardTW + 30, gridy + 97, 90, 70, 50, 80);
                    //DIAMONDS paint
                } else if (c.suit.equalsIgnoreCase("Diamonds")) {
                    g.setColor(Color.red);
                    //Diamond suit symbol tip coordinates
                    int x1, x2, x3, x4, y1, y2, y3, y4;
                    //TOP Tip
                    x1 = 75 + gridx + index * cardTW;           //75 pixels in width (half a card @ 150)
                    y1 = 60 + gridy;                            //70 pixels from the top & bottom = 140 spacing = 60 pixel high image
                    //LEFT Tip
                    x2 = 50 + gridx + index * cardTW;
                    y2 = 100 + gridy;
                    //BOTTOM Tip
                    x3 = 75 + gridx + index * cardTW;
                    y3 = 140 + gridy;
                    //RIGHT Tip
                    x4 = 100 + gridx + index * cardTW;
                    y4 = 100 + gridy;
                    int[] xPoly = {x1, x2, x3, x4};
                    int[] yPoly = {y1, y2, y3, y4};
                    //Fill dual int poly array with x & y points = 4 points each
                    g.fillPolygon(xPoly, yPoly, 4);
                    //CLUBS paint
                } else if (c.suit.equalsIgnoreCase("Clubs")) {
                    g.setColor(Color.black);
                    g.fillOval(gridx + index * cardTW + 35, gridy + 85, 40, 40);
                    g.fillOval(gridx + index * cardTW + 75, gridy + 85, 40, 40);
                    g.fillOval(gridx + index * cardTW + 55, gridy + 55, 40, 40);
                    g.fillRect(gridx + index * cardTW + 70, gridy + 90, 11, 50);
                }
                //Move to next card index to paint
                index++;
            }

            //DEALER CARD PAINT
            if (bool_dealer_turn == true) {     //Toggle dealer turn to hide/show cards
                index = 0;                          //Creates new card
                for (int i = 0; i < dealer.getCount(); i++) {
                    Card c = dealer.getCard(i);
                    g.setColor(Color.white);            //Create card background color

                    //First RECTANGLE drawn for card edge softening (Height)
                    g.fillRect(gridx + index * cardTW + cardSpacing, gridy + cardTH + cardSpacing + cardEdgeSoftening, cardAW, cardAH - 2 * cardEdgeSoftening);
                    //Second RECTANGLE drawn for card edge softening (Width)
                    g.fillRect(gridx + index * cardTW + cardSpacing + cardEdgeSoftening, gridy + cardTH + cardSpacing, cardAW - 2 * cardEdgeSoftening, cardAH);
                    //First CIRCLE drawn for curved corner - TOP LEFT
                    g.fillOval(gridx + index * cardTW + cardSpacing, gridy + cardTH + cardSpacing, 2 * cardEdgeSoftening, 2 * cardEdgeSoftening);
                    //Second CIRCLE drawn for curved corner - Top RIGHT
                    g.fillOval(gridx + index * cardTW + cardSpacing + cardAW - 2 * cardEdgeSoftening, gridy + cardTH + cardSpacing, 2 * cardEdgeSoftening, 2 * cardEdgeSoftening);
                    //Third CIRCLE drawn for curved corner - BOTTOM LEFT
                    g.fillOval(gridx + index * cardTW + cardSpacing, gridy + cardTH + cardSpacing + cardAH - 2 * cardEdgeSoftening, 2 * cardEdgeSoftening, 2 * cardEdgeSoftening);
                    //Fourth CIRCLE drawn for curved corner - Bottom RIGHT
                    g.fillOval(gridx + index * cardTW + cardSpacing + cardAW - 2 * cardEdgeSoftening, gridy + cardTH + cardSpacing + cardAH - 2 * cardEdgeSoftening, 2 * cardEdgeSoftening, 2 * cardEdgeSoftening);

                    //Default Suit Color of Cards
                    g.setColor(Color.black);

                    //If the suit is a Diamond or Heart - Make cards red
                    if (c.suit.equalsIgnoreCase("Hearts") || c.suit.equalsIgnoreCase("Diamonds")) {
                        g.setColor(Color.red);
                    }

                    g.setFont(fontCard);
                    g.drawString(c.symbol, gridx + index * cardTW + cardSpacing * 2, gridy + cardTH + cardAH);

                    //Painting SUIT SYMBOL on cards
                    //SPADES paint
                    if (c.suit.equalsIgnoreCase("Spades")) {
                        g.setColor(Color.black);
                        g.fillOval(gridx + index * cardTW + 40, gridy + cardTH + 85, 40, 40);
                        g.fillOval(gridx + index * cardTW + 70, gridy + cardTH + 85, 40, 40);
                        g.fillArc(gridx + index * cardTW + 30, gridy + cardTH + 28, 90, 70, 230, 80);
                        g.fillRect(gridx + index * cardTW + 70, gridy + cardTH + 90, 10, 50);
                        //HEARTS paint
                    } else if (c.suit.equalsIgnoreCase("Hearts")) {
                        g.setColor(Color.red);
                        g.fillOval(gridx + index * cardTW + 40, gridy + cardTH + 70, 40, 40);
                        g.fillOval(gridx + index * cardTW + 70, gridy + cardTH + 70, 40, 40);
                        g.fillArc(gridx + index * cardTW + 30, gridy + cardTH + 97, 90, 70, 50, 80);
                        //DIAMONDS paint
                    } else if (c.suit.equalsIgnoreCase("Diamonds")) {
                        g.setColor(Color.red);
                        //Diamond suit symbol tip coordinates
                        int x1, x2, x3, x4, y1, y2, y3, y4;
                        //TOP Tip
                        x1 = 75 + gridx + index * cardTW;           //75 pixels in width (half a card @ 150)
                        y1 = 60 + gridy + cardTH;                            //70 pixels from the top & bottom = 140 spacing = 60 pixel high image
                        //LEFT Tip
                        x2 = 50 + gridx + index * cardTW;
                        y2 = 100 + gridy + cardTH;
                        //BOTTOM Tip
                        x3 = 75 + gridx + index * cardTW;
                        y3 = 140 + gridy + cardTH;
                        //RIGHT Tip
                        x4 = 100 + gridx + index * cardTW;
                        y4 = 100 + gridy + cardTH;
                        int[] xPoly = {x1, x2, x3, x4};
                        int[] yPoly = {y1, y2, y3, y4};
                        //Fill dual int poly array with x & y points = 4 points each
                        g.fillPolygon(xPoly, yPoly, 4);
                        //CLUBS paint
                    } else if (c.suit.equalsIgnoreCase("Clubs")) {
                        g.setColor(Color.black);
                        g.fillOval(gridx + index * cardTW + 35, gridy + cardTH + 85, 40, 40);
                        g.fillOval(gridx + index * cardTW + 75, gridy + cardTH + 85, 40, 40);
                        g.fillOval(gridx + index * cardTW + 55, gridy + cardTH + 55, 40, 40);
                        g.fillRect(gridx + index * cardTW + 70, gridy + cardTH + 90, 11, 50);
                    }
                    //Move to next card index to paint
                    index++;
                }
            }
        }
    }

    /**
     * ActHit Class Implementation of ActionListener for overridden actionPerformed Method on 'Hit'.
     *
     * @see ActHit
     * @author Karsen Hansen
     * @version 1.0
     */
    //'HIT' Button Action Listener
    public class ActHit implements ActionListener {             //Action Listener
        @Override                                               //Auto-Overridden Method
        /**
         * Overridden method for 'STAY' button action
         *
         * @param e used to take an actionPerformed on 'HIT' button.
         */
        public void actionPerformed(ActionEvent e) {
            System.out.println("You just clicked the 'HIT' Button!");
            player.addCard(deck.deal());
            evalPlayerHand();
        }
    }

    /**
     * ActStay Class Implementation of ActionListener for overridden actionPerformed Method on 'Stay'.
     *
     * @see ActHit
     */
    public class ActStay implements ActionListener {

        @Override
        /**
         * Overridden method for 'STAY' button action
         *
         * @param e used to take an actionPerformed on 'STAY' button.
         */
        public void actionPerformed(ActionEvent e) {
            System.out.println("You just clicked the 'STAY' Button!");
            bool_hit_stay = false;
            bool_dealer_turn = true;
            dealerHitStay();
        }
    }

    /**
     * Method to update player and dealer health bars.
     */
    private void doUpdateBars() {
        if(loser) {
            if(isPlayerBusted) {
                playerHealth -= (player.calcValue() - 21);
            } else {
                playerHealth -= (21 - player.calcValue());
            }
            playerBar.setValue(playerHealth);
            if(playerHealth < 0) playerHealth = 0;
            playerBar.setString(String.format("Player: %.2f%%", ((double)playerHealth)));
        } else {
            if(isDealerBusted) {
                dealerHealth -= (dealer.calcValue() - 21);
            } else {
                dealerHealth -= (21 - dealer.calcValue());
            }
            dealerBar.setValue(dealerHealth);

            if(dealerHealth < 0) dealerHealth = 0;
            dealerBar.setString(String.format("Dealer: %.2f%%", ((double)dealerHealth)));
        }

        if(dealerHealth <= 0 || playerHealth <= 0) {
            btnGame.setVisible(false);
            System.exit(0);
        }
    }

    /**
     * Class ActGame which implements ActionListener for game events.
     *
     * @author Karsen Hansen
     * @version 1.0
     */
    // Handle the new Hand playing
    public class ActGame implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("You just clicked the 'New' Button!");
            bool_hit_stay = true;
            bool_dealer_turn = false;
            player.reset();
            dealer.reset();
            deck.reset();
            deck.shuffle();
            loser = false;
            player.addCard(deck.deal());
            player.addCard(deck.deal());
            dealer.addCard(deck.deal());
            dealer.addCard(deck.deal());
            isPlayerBlackJack = false;
            isDealerBlackJack = false;
            isDealerBusted = false;
            isPlayerBusted = false;
            bHit.setEnabled(true);
            bStay.setEnabled(true);
            evalPlayerHand();
            lblDealScore.setText("Dealer Score: ");
        }
    }
}
