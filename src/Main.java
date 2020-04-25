/*
Title:              CPSC224 Group Project - 'PlaguedJack' MAIN
Author(s):          Karsen Hansen
Creation Date:      3.17.2020
*/

//Main
public class Main implements Runnable {
    GUI gui = new GUI();
    long xTime = System.nanoTime();
    public static int pWins = 0;
    public static int dWins = 0;
    public int Hz = 100;


    public static void main(String[] args) {
        new Thread(new Main()).start();
    }

    //This override run method only runs a single time to refresh the JPanel window
    @Override
    public void run() {
        //Infinite while loop to continuously refresh the GUI class 'Board' JPanel paintComponent as cards are updated
        while (true) {
            if (System.nanoTime() - this.xTime >= (long)(1000000000 / this.Hz)) {
                gui.refresher();
                gui.repaint();
                this.xTime = System.nanoTime();
            }

        }

    }
}
