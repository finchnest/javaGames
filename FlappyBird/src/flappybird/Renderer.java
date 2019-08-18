package flappybird;

import java.awt.Graphics;
import javax.swing.JPanel;

public class Renderer extends JPanel{
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);//calls the paintComponent method in JPanel, the super/parent class with graphics object g
        FlappyBird.flappy.paint(g);
    }
}
