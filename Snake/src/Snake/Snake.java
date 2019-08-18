package Snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Snake extends JPanel implements ActionListener, KeyListener {//the ActionListener's job is to do somthing when some action(ActionEvent) occurs while the program is runnin

    private int[] snakexlength=new int[750];
    private int[] snakeylength=new int[750];
    
    private boolean left=false;
    private boolean right=false;
    private boolean up=false;
    private boolean down=false;
    
    private ImageIcon rightmouth;
    private ImageIcon leftmouth;
    private ImageIcon upmouth;
    private ImageIcon downmouth;
    
    private int lengthofsnake=3;
    
    private Timer timer;
    private int delay=100;
    
    private ImageIcon snake;
  
    private int moves=0;
    public Snake(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer= new Timer(delay,this);
        timer.start();
    }
    public void paint(Graphics g){
        if(moves==0){
            snakexlength[2]=50;
            snakexlength[1]=75;
            snakexlength[0]=100;
            
            snakeylength[2]=100;
            snakeylength[1]=100;
            snakeylength[0]=100;
            
        }
        
        //title frame
        g.setColor(Color.pink);
        g.fillRect(25,11,851,55);
        //game play area
        g.setColor(Color.black);
        g.fillRect(25,75,851,575);
    
        rightmouth=new ImageIcon("right.jpg");
        rightmouth.paintIcon(this,g,snakexlength[0],snakeylength[0]); 
        
        for (int a = 0; a < lengthofsnake; a++) {
            if(a==0 && right){
                rightmouth=new ImageIcon("right.jpg");
                rightmouth.paintIcon(this,g,snakexlength[a],snakeylength[a]); 
            }  
            if(a==0 && left){
                leftmouth=new ImageIcon("left.jpg");
                leftmouth.paintIcon(this,g,snakexlength[a],snakeylength[a]); 
            }
            if(a==0 && up){
                upmouth=new ImageIcon("up.jpg");
                upmouth.paintIcon(this,g,snakexlength[a],snakeylength[a]); 
            }
            if(a==0 && down){
                downmouth=new ImageIcon("down.jpg");
                downmouth.paintIcon(this,g,snakexlength[a],snakeylength[a]); 
            }
            if(a!=0){
                 snake=new ImageIcon("snake.jpg");
                 snake.paintIcon(this,g,snakexlength[a],snakeylength[a]); 
            }
        }
        
        g.dispose();
        
    }
    
    
    
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(right){
            for(int r=lengthofsnake-1;r>=0;r--){
                snakeylength[r+1]=snakeylength[r];
            }
            for (int r=lengthofsnake;r>=0;r--) {
                if(r==0){
                    snakexlength[r]=snakexlength[r]+25;
                }else{
                    snakexlength[r]=snakexlength[r-1];
                }
                if(snakexlength[r]>850){
                    snakexlength[r]=25;
                }
                
            } 
            repaint();
        }
        if(left){
            
        }
        if(up){
            
        }
        if(down){
            
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            moves++;
            right=true;
            if(!left){
                right=true;
            }else{
                left=true;
                right=false;
            }
            up=false;
            down=false;
        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT){
            moves++;
            left=true;
            if(!right){
                left=true;
            }else{
                right=true;
                left=false;
            }
            up=false;
            down=false;
        }
        if(e.getKeyCode()==KeyEvent.VK_DOWN){
            moves++;
            down=true;
            if(!up){
                down=true;
            }else{
                up=true;
                down=false;
            }
            left=false;
            right=false;
        }
        if(e.getKeyCode()==KeyEvent.VK_UP){
            moves++;
            up=true;
            if(!down){
                up=true;
            }else{
                up=false;
                down=true;
            }
            left=false;
            right=false;
        }
    }

    public void keyReleased(KeyEvent e) {
    }
   
    
}    