 package brickbreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;//
import java.awt.event.ActionListener;
//An event which indicates that a keystroke occurred in a component(such as a text field) when a key is pressed or released.
//The event is then passed to every KeyListener or KeyAdapter object which registered to receive such events using the component's addKeyListener method. 
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;//the class that is interested in processing a keyboard event
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.Timer;//Fires one or more ActionEvents at specified intervals
import javax.swing.JButton;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public  class GamePlay extends javax.swing.JPanel implements KeyListener, ActionListener{//we cant implement JFrame since its not interface and obviously we cant extend it since we already extended JPanel
   //initializations and declarations
    private boolean play=false;
    private int score=0;
    private JButton exit;
    private JButton again;
    private int Life=3;
    private int totalBricks=12;
    private Timer timer;
    private int delay=6;//Once the timer has been started, it waits for the initial delay before firing its first ActionEvent to registered listeners. pretty much 
    private int playerX=310;//the distance of the paddle from the left side of the container in pixels
    private int ballposX=355;//the distance of the ball from the left side of the container
    private int ballposY=540;//the distance of the ball from the bottom of the container
    private int ballxdir=1;//setting up the initial dxn in vector form
    private int ballydir=-2;//these 2 are simple math concepts of vectors...in computers our origin starts at the left most corner and Y counts downward
                            //as X counts to the right...so positive Y dxn is to downward
    
    private MapGenerator bricks;
    //f1:constructor////1
    public GamePlay(){//things that are supposed to start instantly should be instantiated in the constructor
        //the word "this" in the 2 cases below refers to the KeyListener object which is hoped to be created later on...so this acts as a place holder
        bricks=new MapGenerator(3,4);//in this constructor the constructor of MapGenerator is called and this bricks objects will contain the generated matrix
        
        addKeyListener(this);//adds a KeyListener to the OBJECT that is created when the constructor of this class is called. 
        setFocusable(true);//the currently active elements/componenets in UI should be visible
        timer=new Timer(delay,this);//for the timer constructor we pass the delay/the speed at which actions occur while the program is running and the object
        timer.start();
       
    }
    //f2:built-in painter
    //all the codes in this function will get executed or checked everytime this method is called...the process is so fast that's why its hard to notice
   //this method is called in every single movement of the ball
    @Override
    public void paint(Graphics g){//g is of data type Graphics
        //background: prints out a black background with the given dimensions everytime the this paint() method is called
        g.setColor(new Color(231,215,137));//
        g.fillRect(0,0,693,590);//the first two are the x and y coordinates of the starting point of filling as measured from the topleft corner if the container panel
        
        //drawwing map: prints out the most up-to-date map using the draw method of the MapGenerator
        //the map value updated by the setBrickValue() method which itself is influenced whenever intersection of the ball and the rectangle occurs
        bricks.draw((Graphics2D)g);//syntax is just like this
        
        //scores: prints the updated score everytime method is called
        g.setColor(Color.white);
        g.setFont(new Font("serif",Font.BOLD,15));
        g.drawString("Score: "+score, 590, 20);
        
        //lives
        g.setColor(Color.white);
        g.setFont(new Font("serif",Font.BOLD,15));
        g.drawString("Life: "+Life, 590, 40);
        
        //paddle: everytime as usual
        g.setColor(new Color(24,56,103));
        g.fillRect(playerX,560,100,10);
        
        //ball: the ball is drawn with the given dimensions everytime throughout the game but the process is so fast for us to notice
        g.setColor(new Color(188, 40, 3));//thats why it doesnt seem like its being drawn
        g.fillOval(ballposX,ballposY,20,20);
        
        
        //gameover

       if(ballposY>570){
            play=false;
            g.setColor(new Color(44,138,135));
            g.setFont(new Font("Serif", Font.BOLD,30));
            g.drawString("Game Over Score:  "+score, 190, 300);

            g.setColor(new Color(44,138,135));
            g.setFont(new Font("Serif", Font.BOLD,20));
            g.drawString("Press Space To Restart and Enter to shoot", 190, 350);
            ballposX=355;
            playerX=310;
       }
    
        //win
        if(totalBricks==0){
            play=false;
           
            g.setColor(new Color(44,138,135));
            g.setFont(new Font("Serif", Font.BOLD,30));
            g.drawString("  You Won \nScore  "+score, 190, 300);
            
            g.setColor(new Color(44,138,135));
            g.setFont(new Font("Serif", Font.BOLD,20));
            g.drawString("Press Space To Restart and Enter to shoot", 190, 350);
            ballposX=355;
            playerX=310;
            
        
        }
        
        g.dispose();//garbage collection, not that much necessary tho, for saftey reasons
   
    }
    
 
    //these 2 functions are here just for the purpose of implementation 
    public void keyTyped(KeyEvent e) {
    }
    
    //i dnt have anything to do with them now
    public void keyReleased(KeyEvent e) {
    }
    
   //f3:handles all the actions performed while the program is running
    //all the actions that occur why the program is running are taken care of by this method
    //"e" is just a place holder for the ActionEvent that is hoped to happen...like int x
    //then since "e" is datatyped as ActionEvent, it needs some kind of built-in method to handle the ActionEvent, which is actionPerformed()
    @Override
    //all the actions that are presumed to happend are taken care of by this method
    //this method gets into action whenever a key is pressed
    public void actionPerformed(ActionEvent e) {
    //so it basically stays sleep unless some kind of keystroke occurs    
        if(play){//all the things that happen when play id true are handled in this if Statement
            //this if statement checks the intersection of the paddle and the ball
            if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,560,100,10))){
               ballydir=-ballydir;//x dxn is maintained for example (3,4) will be changed to (3,-4)
            }
            
            //we can name our blocks...it makes it easy to know which loop we are supposed to break out of
            //this for loop checks the intersection of the ball and rectangles
            A: for (int i = 0; i < bricks.map.length; i++) {//since i am using instance of another class, i gotta access it via the object i created for that class
                for (int j = 0; j < bricks.map[0].length; j++) {
                    if(bricks.map[i][j]>0){
                         int brickxS=j*bricks.brickwidth+150;
                         int brickyS=i*bricks.brickheight+50;
                         int brickwidth=bricks.brickwidth;
                         int brickheight=bricks.brickheight;
                         
                         Rectangle rect=new Rectangle(brickxS, brickyS,brickwidth,brickheight) ;
                         
                         //firstly i directly used the object I created without assigning it to a variable, secondly i used the variable
                       
                         if (new Rectangle(ballposX,ballposY,20,20).intersects(rect)){//here im having 2 different ways of using objects
                            bricks.setBrickValue(0,i,j);//updates the map when intersections occurs
                            totalBricks--;
                            score+=5;
                            crashSound();
                            if(totalBricks==0){
                                 tada();
                             }
                            if(ballposX+19<=rect.x || ballposX+1>=rect.x+rect.width){ 
                                 ballxdir=-ballxdir;
                            }
                            //I1
                            if(ballposY+3>=rect.y+ rect.height || ballposY<=rect.y){
                                ballydir=-ballydir;
                            }
                            break A; //the reason we break out of the loop after finding just one intersection is that, mathematically the ball can touch only
                            //one rectangle at a time, so once we find an intersection the program is gonna check for other things to handle in the bigger if(play) statement

                        }
                          
                    }
                    
                }
                
            }
            
            ballposX+=ballxdir;//since the ball is moving at some angle throughout its journey
            ballposY+=ballydir;//both its X and Y postions change at given time
            if(ballposX<0){
                ballxdir=-ballxdir;
            }
            if(ballposY<0){
                ballydir=-ballydir;
            }
            if(ballposX>670){
                ballxdir=-ballxdir;
            }  
            //only one if statement remains here: the case when ballposY>560. That one leads to gameover and i wanted to print out 
            //some message so i handled that one in the paint method, but i could have done it hare
        }
        
        //once all the things in the if Statement are done, its time for calling paint method again
        
        repaint();//built-in method in JPanel class
        //the repaint method is the most active of all
        //the 2 repaint() methods are active 24x7. Both keyPressed() and  actionPerformed() update the variables in our initialization
        //so whenever the repaint() method is called later on, its gonna print out the most recent data it we have.
    
}
    //I4
    public void crashSound(){
          try{
            InputStream in = new FileInputStream(new File("sound.wav"));
            AudioStream audio = new AudioStream(in);
            AudioPlayer.player.start(audio);
         }
          catch(IOException e){
            System.out.println("Audio file not Found");
         }
    
    }
        public void tada(){
          try{
            InputStream in = new FileInputStream(new File("tada.wav"));
            AudioStream audio = new AudioStream(in);
            AudioPlayer.player.start(audio);
         }
          catch(IOException e){
            System.out.println("Audio file not Found");
         }
    
    }
    ////2
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            if(playerX>=598){
                playerX=598;
            }else{
                moveRight();
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT){
           if(playerX<=2){
                playerX=2;
            }else{
                moveLeft();
            } 
        }
        
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(!play){//this means "when the game is not in play"...pressing enter while gameing wont work
                play=true;
            }
        }
        //I3 using 2 keys to operate
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            if(!play){//this means "when the game is not in play"...pressing enter while gameing wont work
                ballposY=540;
                ballxdir=1;
                ballydir=-2;
                score=0;
                totalBricks=12;
                bricks=new MapGenerator(3,4);//creating object the MapGenetator class basically restarts the whole process again
               
            }
        }
        
       
        
        //the repaint method is the most active of all
        //the 2 repaint() methods are active 24x7. Both keyPressed() and  actionPerformed() update the variables in our initialization
        //so whenever the repaint() method is called later on, its gonna print out the most recent data it we have.
    }

    public void moveRight(){
        if(play){
            playerX+=20;
        }//I2
        else{
            ballposX+=20;
            playerX+=20;
        }  
    }
    public void moveLeft(){
        if(play){
            playerX-=20;
        }
        else{
            ballposX-=20;
            playerX-=20;
        }
        
    }

}