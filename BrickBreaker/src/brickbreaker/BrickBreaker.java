package brickbreaker;

import javax.swing.JFrame;

public class BrickBreaker {

    public static void main(String[] args) {
        JFrame obj=new JFrame();
        GamePlay gamePlay=new GamePlay();
        obj.setBounds(30, 30, 700, 600);//to the window, not the panel
        obj.setLocationRelativeTo(null);
        obj.setTitle("Breaker");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gamePlay);//all the process is incorporated in the object of JFrame...so everything will happen in the frame
        
     
    
    }
    
}
