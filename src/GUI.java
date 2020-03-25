/*
Title:              CPSC224 Group Project - 'PlaguedJack' GUI
Author(s):          Karsen Hansen
Creation Date:      3.17.2020
*/

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;                           //Implemented separately from above import


//GRAPHIC USER INTERFACE
public class GUI extends JFrame {

    //RESOLUTIONS
    int aW = 1280;                                              //Actual screen resolution Width
    int aH = 800;                                               //Actual screen resolution Height

    //GAME PHASE BOOLEANS
    boolean bool_hit_stay = true;                               //
    boolean bool_dealer_turn = false;                           //
    boolean bool_play_more = false;                             //

    //COLORS
    Color colorBackground = new Color(39, 119, 20);   //Background Colors RGB values
    Color colorButton = new Color(204, 189, 0);         //Yes & No Button Colors
    Color colorHitButton = new Color(204, 183, 4);      //Hit Button Color
    Color colorStayButton = new Color(203, 204, 201);   //Stay Button Color

    //FONTS
    Font fontButton = new Font("Times New Roman", Font.PLAIN, 30); //Font name, type, size
    Font fontCard = new Font("Times New Roman", Font.BOLD, 40); //Card Font
    Font fontQuestion = new Font("Times New Roman", Font.BOLD, 40);

    //QUESTIONS
    String play_more_q = "Play More?";

    //BUTTONS
    JButton bHit = new JButton();                                 //'Hit' jbutton for new card
    JButton bStay = new JButton();                                //'Stay' jbutton for turn pass
    JButton bYes = new JButton();                                 //'Yes' jbutton
    JButton bNo = new JButton();                                  //'No' jbutton

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

    //ARRAY LIST CONTAINING ALL CARDS
    ArrayList<Card> allCards = new ArrayList<Card>();
    ArrayList<Card> playerCards = new ArrayList<Card>();
    ArrayList<Card> dealerCards = new ArrayList<Card>();

    //PLAYER - DEALER TOTALS
    int total_player_max;
    int total_player_min;
    int total_dealer_max;
    int total_dealer_min;


    int rand = new Random().nextInt(52);                //Generates random integer from 1-51 for random dealing

    //GUI Constructor
    public GUI() {
        //PRIMARY ATTRIBUTES
        this.setSize(aW + 6, aH + 6);             //Set Application Size
        this.setTitle("PlaguedJack");                           //Set Application Title
        this.setVisible(true);                                  //Enable visibility
        this.setResizable(false);                               //Can't resize
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //Close frame & terminate app on 'x'

        //BOARD
        Board board = new Board();
        this.setContentPane(board);                             //Set Content Pane to board
        this.setLayout(null);                                   //Set Layout to 'null' to update dimensions

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
        bStay.setBounds(hsx + 55, hsy + 280, 120, 80);    //Set 'Stay' button location & dimensions
        bStay.setBackground(colorStayButton);                           //Set 'Stay' button color
        bStay.setFont(fontButton);                                  //Set 'Stay' button font
        bStay.setText("STAY");                                      //'Stay' text
        board.add(bStay);                                           //Add 'Stay' button to board

        //'YES' Button
        ActYes aYes = new ActYes();                              //Action listener object for 'Yes'
        bYes.addActionListener(aYes);                             //Linking action to 'Yes' button
        bYes.setBounds(pmx + 10, pmy + 110, 100, 80);    //Set 'Yes' button location & dimensions
        bYes.setBackground(colorButton);                           //Set 'Yes' button color
        bYes.setFont(fontButton);                                  //Set 'Yes' button font
        bYes.setText("Yes");                                       //'Yes' text
        board.add(bYes);                                           //Add 'Yes' button to board

        //'NO' Button
        ActNo aNo = new ActNo();                                    //Action listener object for 'No'
        bNo.addActionListener(aNo);                                 //Linking action to 'Stay' button
        bNo.setBounds(pmx + 120, pmy + 110, 100, 80);     //Set 'Stay' button location & dimensions
        bNo.setBackground(colorButton);                             //Set 'Stay' button color
        bNo.setFont(fontButton);                                    //Set 'Stay' button font
        bNo.setText("No");                                          //'Stay' text
        board.add(bNo);                                             //Add 'Stay' button to board

        String suitS1 = null;                                       //Assign Suit
        int id_setter = 0;                                          //Assign ID for every card

        for (int s = 0; s < 4; ++s) {
            if(s == 0) {
                suitS1 = "Spades";
            } else if (s == 1) {
                suitS1 = "Hearts";
            } else if (s == 2) {
                suitS1 = "Diamonds";
            } else suitS1 = "Clubs";
            for (int i = 2; i < 15; ++i) {
                allCards.add(new Card(i, suitS1, id_setter));
                id_setter++;                                         //New ID for every card created
            }
        }

        //CARD ASSIGNMENT ATTRIBUTES
        rand = new Random().nextInt(52);              //Generate random number 1-51 to draw from allCards
        playerCards.add(allCards.get(rand));                 //Randomly add new card to playerCards from allCards
        allCards.get(rand).cardUsed = true;

        //DEAL CARDS
        //Dealer First Card Assignment:
        rand = new Random().nextInt(52);
        while(true) {                                       //Check if same number
            if (allCards.get(rand).cardUsed == false) {     // If it hasn't been used...
                dealerCards.add(allCards.get(rand));        // Assign to dealers hand
                allCards.get(rand).cardUsed = true;         // Then set that card to used
                break;
            } else {
                rand = new Random().nextInt(52);     //Otherwise, if it has already been used create a new random
            }
        }
        //Player First Card Assignment:
        rand = new Random().nextInt(52);
        while(true) {                                       //Check if same number
            if (allCards.get(rand).cardUsed == false) {     // If it hasn't been used...
                playerCards.add(allCards.get(rand));        // Assign to dealers hand
                allCards.get(rand).cardUsed = true;         // Then set that card to used
                break;
            } else {
                rand = new Random().nextInt(52);     //Otherwise, if it has already been used create a new random
            }
        }
        //Dealer Second Card Assignment:
        rand = new Random().nextInt(52);
        while(true) {                                       //Check if same number
            if (allCards.get(rand).cardUsed == false) {     // If it hasn't been used...
                dealerCards.add(allCards.get(rand));        // Assign to dealers hand
                allCards.get(rand).cardUsed = true;         // Then set that card to used
                break;
            } else {
                rand = new Random().nextInt(52);     //Otherwise, if it has already been used create a new random
            }
        }
        //Print Cards Dealt
        for (Card c : playerCards) {
            System.out.println("Player has the card " + c.name + " of " + c.suit);
        }
        for (Card c : dealerCards) {
            System.out.println("Dealer has the card " + c.name + " of " + c.suit);
        }
    }

    //Check whether Player turn, Dealer turn, or end of either - Choice to continue
    public void refresher() {
        //BUTTON VISIBILITY
        if (bool_hit_stay == true) {
            bHit.setVisible(true);
            bStay.setVisible(true);
            bYes.setVisible(false);
            bNo.setVisible(false);
        } else if (bool_dealer_turn == true) {
            bHit.setVisible(false);
            bStay.setVisible(false);
            bYes.setVisible(false);
            bNo.setVisible(false);
        } else if (bool_play_more == true) {
            bHit.setVisible(false);
            bStay.setVisible(false);
            bYes.setVisible(true);
            bNo.setVisible(true);
        }

        //TOTALS CHECKER
        //PLAYER total
        int sum = 0;
        boolean hasAces = false;
        for (Card c : playerCards) {
            if (c.symbol.equalsIgnoreCase("A")) {
                hasAces = true;
            }
            sum += c.value;
        }
        total_player_min = sum;
        total_player_max = sum;

        if (hasAces == true) {
            total_player_max += 1;     //Only count a single ace as 11
        }

        //DEALER Total
        sum = 0;
        hasAces = false;
        for (Card c : dealerCards) {
            if (c.symbol.equalsIgnoreCase("A")) {
                hasAces = true;
            }
            sum += c.value;
        }
        total_dealer_min = sum;
        total_dealer_max = sum;

        if (hasAces == true) {
            total_dealer_max += 1;     //Only count a single ace as 11
        }

    }

    public void dealerHitStay() {

    }

    //GAME BOARD
    public class Board extends JPanel {
        //PAINTING ENTIRE BOARD
        public void paintComponent(Graphics g) {
            g.setColor(colorBackground);                            //Game board background
            g.fillRect(0, 0, aW, aH);                        //Game board (Background) dimensions

            //Paints Temporary Top Grid Paint
            g.setColor(Color.black);
            g.drawRect(gridx, gridy, gridw, gridh);

            //Paints Temporary Bottom Log Borders
            g.drawRect(gridx, gridy + gridh + 50, gridw, 500);

            //Paints Temporary Totals & Command Messages
            g.drawRect(hsx, hsy, hsw, hsh);

            //Paints Temporary "Play More?" Grid & Player Totals
            g.drawRect(pmx, pmy, pmw, pmh);
            if (bool_play_more == true) {
                g.setFont(fontQuestion);
                g.drawString(play_more_q, pmx + 26, pmy + 60);
            } else if (bool_hit_stay == true) {
                g.setFont(fontQuestion);
                g.drawString(Integer.toString(total_player_min) + "/" + Integer.toString(total_player_max), hsx + 62, hsy + 220);
            } else if (bool_dealer_turn) {
                g.setFont(fontQuestion);
                g.drawString(Integer.toString(total_player_min) + "/" + Integer.toString(total_player_max), hsx + 65, hsy + 120);
                g.drawString(Integer.toString(total_dealer_min) + "/" + Integer.toString(total_dealer_max), hsx + 65, hsy + 320);

            }

            //Paints Card Grid Rectangles
            for (int i = 0; i < 6; ++i) {
                g.drawRect(gridx + i * cardTW + cardSpacing, gridy + cardSpacing, cardAW, cardAH);
                g.drawRect(gridx + i * cardTW + cardSpacing, gridy + cardSpacing + cardTH, cardAW, cardAH);
            }

            //PLAYERS CARDS PAINT
            int index = 0;                          //Creates new card
            for (Card c : playerCards) {
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
                g.drawString(c.symbol, gridx + index * cardTW + cardSpacing*2, gridy + cardAH);

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
                for (Card c : dealerCards) {
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
                    g.drawString(c.symbol, gridx + index * cardTW + cardSpacing*2, gridy + cardTH + cardAH);

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

    /*
    * ACTION LISTENER METHOD OVERRIDES
    */
    //'HIT' Button Action Listener
    public class ActHit implements ActionListener {             //Action Listener

        @Override                                               //Auto-Overridden Method
        public void actionPerformed(ActionEvent e) {
            System.out.println("You just clicked the 'HIT' Button!");
            rand = new Random().nextInt(52);
            while(true) {                                       //Check if same number
                if (allCards.get(rand).cardUsed == false) {     // If it hasn't been used...
                    playerCards.add(allCards.get(rand));        // Assign to dealers hand
                    allCards.get(rand).cardUsed = true;         // Then set that card to used
                    break;
                } else {
                    rand = new Random().nextInt(52);     //Otherwise, if it has already been used create a new random
                }
            }
        }
    }

    //'STAY' Button Action Listener
    public class ActStay implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("You just clicked the 'STAY' Button!");
            bool_hit_stay = false;
            bool_dealer_turn = true;
            dealerHitStay();
        }
    }

    //'YES' Button Action Listener
    public class ActYes implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("You just clicked the 'YES' Button!");
        }
    }

    //'NO' Button Action Listener
    public class ActNo implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("You just clicked the 'NO' Button!");
        }
    }

}
