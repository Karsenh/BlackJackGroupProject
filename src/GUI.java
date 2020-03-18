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

    //Graphic User Interface
    public GUI() {
        //MAIN FRAME
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
        bHit.setBounds(200, 500, 125, 65); //Set 'Hit' button location & dimensions
        bHit.setBackground(colorButton);                        //Set 'Hit' button color
        bHit.setFont(fontButton);                               //Set 'Hit' button font
        bHit.setText("HIT");                                    //'Hit' text
        board.add(bHit);                                        //Add 'Hit' button to board

        //'STAY' Button
        ActStay aStay = new ActStay();                              //Action listener object for 'Stay'
        bStay.addActionListener(aStay);                             //Linking action (aStay) to 'Stay' button (bStay)
        bStay.setBounds(400, 500, 125, 65);    //Set 'Stay' button location & dimensions
        bStay.setBackground(colorButton);                           //Set 'Stay' button color
        bStay.setFont(fontButton);                                  //Set 'Stay' button font
        bStay.setText("STAY");                                      //'Stay' text
        board.add(bStay);                                           //Add 'Stay' button to board

        //'YES' Button
        ActYes aYes = new ActYes();                              //Action listener object for 'Yes'
        bYes.addActionListener(aYes);                             //Linking action to 'Yes' button
        bYes.setBounds(600, 500, 125, 65);    //Set 'Yes' button location & dimensions
        bYes.setBackground(colorButton);                           //Set 'Yes' button color
        bYes.setFont(fontButton);                                  //Set 'Yes' button font
        bYes.setText("Yes");                                      //'Yes' text
        board.add(bYes);                                           //Add 'Yes' button to board

        //'NO' Button
        ActNo aNo = new ActNo();                                  //Action listener object for 'No'
        bNo.addActionListener(aNo);                                 //Linking action to 'Stay' button
        bNo.setBounds(800, 500, 125, 65);     //Set 'Stay' button location & dimensions
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
        }
    }

    public class ActHit implements ActionListener {             //Action Listener

        @Override                                               //Auto-Overridden Method
        public void actionPerformed(ActionEvent e) {
            System.out.println("You just clicked the 'Hit' Button!");
        }
    }          //'HIT' Button Action Listener

    public class ActStay implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("You just clicked the 'Stay' Button!");
        }
    }         //'STAY' Button Action Listener

    public class ActYes implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("You just clicked the 'Yes' Button!");
        }
    }         //'YES' Button Action Listener

    public class ActNo implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("You just clicked the 'No' Button!");
        }
    }         //'NO' Button Action Listener
}
