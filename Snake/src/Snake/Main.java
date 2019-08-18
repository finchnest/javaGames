package Snake;

import java.awt.Color;
import javax.swing.JFrame;

public class Main{
    public static void main(String[] args) {
          JFrame frame=new JFrame();
          Snake sna=new Snake();
          frame.setBounds(10, 10, 905, 700);
          frame.setVisible(true);
          frame.setResizable(false);
          frame.setTitle("Snake");
          frame.setBackground(Color.DARK_GRAY);
          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          frame.add(sna);
    
  
    }
}
