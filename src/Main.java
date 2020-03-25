/*
Title:              CPSC224 Group Project - 'PlaguedJack' MAIN
Author(s):          Karsen Hansen
Creation Date:      3.17.2020
*/

//Main
public class Main implements Runnable {
    GUI gui = new GUI();

    public static void main(String[] args) {
        new Thread(new Main()).start();
    }

    //This override run method only runs a single time to refresh the JPanel window
    @Override
    public void run() {
        //Infinite while loop to continuously refresh the GUI class 'Board' JPanel paintComponent as cards are updated
        while (true) {
            gui.repaint();
        }

    }
}
