package piano;

import javax.swing.JFrame;

public class Piano {

    public static void main(String[] args) {
        PianoFrame frame= new PianoFrame();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false); 
    }
    
}
