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

    //COLORS
    Color colorBackground = new Color(39, 119, 20);   //Background Colors RGB values
    Color colorButton = new Color(204, 204, 0);

    //FONTS
    Font fontButton = new Font("Times New Roman", Font.PLAIN, 30); //Font name, type, size

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
    int cardSpacing = 10;
    int cardTW = gridw/6;                                       //Card Total Width = gridw(idth) / 6 card spots
    int cardTH = gridh/2;                                       //Card Total Height = gridh(eight) / 6 card spots
    int cardAW = cardTW - 2 * cardSpacing;
    int cardAH = cardTH - 2 * cardSpacing;

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

    //GUI Constructor
    public GUI() {
        //PRIMARY SPECIFICATIONS
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
        bHit.setBackground(colorButton);                        //Set 'Hit' button color
        bHit.setFont(fontButton);                               //Set 'Hit' button font
        bHit.setText("HIT");                                    //'Hit' text
        board.add(bHit);                                        //Add 'Hit' button to board

        //'STAY' Button
        ActStay aStay = new ActStay();                              //Action listener object for 'Stay'
        bStay.addActionListener(aStay);                             //Linking action (aStay) to 'Stay' button (bStay)
        bStay.setBounds(hsx + 55, hsy + 280, 120, 80);    //Set 'Stay' button location & dimensions
        bStay.setBackground(colorButton);                           //Set 'Stay' button color
        bStay.setFont(fontButton);                                  //Set 'Stay' button font
        bStay.setText("STAY");                                      //'Stay' text
        board.add(bStay);                                           //Add 'Stay' button to board

        //'YES' Button
        ActYes aYes = new ActYes();                              //Action listener object for 'Yes'
        bYes.addActionListener(aYes);                             //Linking action to 'Yes' button
        bYes.setBounds(pmx + 10, pmy + 110, 100, 80);    //Set 'Yes' button location & dimensions
        bYes.setBackground(colorButton);                           //Set 'Yes' button color
        bYes.setFont(fontButton);                                  //Set 'Yes' button font
        bYes.setText("Yes");                                      //'Yes' text
        board.add(bYes);                                           //Add 'Yes' button to board

        //'NO' Button
        ActNo aNo = new ActNo();                                  //Action listener object for 'No'
        bNo.addActionListener(aNo);                                 //Linking action to 'Stay' button
        bNo.setBounds(pmx + 120, pmy + 110, 100, 80);     //Set 'Stay' button location & dimensions
        bNo.setBackground(colorButton);                             //Set 'Stay' button color
        bNo.setFont(fontButton);                                    //Set 'Stay' button font
        bNo.setText("No");                                          //'Stay' text
        board.add(bNo);                                             //Add 'Stay' button to board
    }

    //GAME BOARD
    public class Board extends JPanel {
        public void paintComponent(Graphics g) {
            g.setColor(colorBackground);                            //Game board background
            g.fillRect(0, 0, aW, aH);                        //Game board (Background) dimensions

            //Draws Temporary Top Grid Paint
            g.setColor(Color.black);
            g.drawRect(gridx, gridy, gridw, gridh);

            //Draws Temporary Bottom Log Borders
            g.drawRect(gridx, gridy + gridh + 50, gridw, 500);

            //Draws Temporary Totals & Command Messages
            g.drawRect(hsx, hsy, hsw, hsh);

            //Draws Temporary "Play More?" Grid
            g.drawRect(pmx, pmy, pmw, pmh);

            for (int i = 0; i < 6; ++i) {
                g.drawRect(gridx + i * cardTW + cardSpacing, gridy + cardSpacing, cardAW, cardAH);
                g.drawRect(gridx + i * cardTW + cardSpacing, gridy + cardSpacing + cardTH, cardAW, cardAH);
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
            System.out.println("You just clicked the 'Hit' Button!");
        }
    }

    //'STAY' Button Action Listener
    public class ActStay implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("You just clicked the 'Stay' Button!");
        }
    }

    //'YES' Button Action Listener
    public class ActYes implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("You just clicked the 'Yes' Button!");
        }
    }

    //'NO' Button Action Listener
    public class ActNo implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("You just clicked the 'No' Button!");
        }
    }

}
