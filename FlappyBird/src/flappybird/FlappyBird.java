package flappybird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;
                            //implement all necessary Listeners
public class FlappyBird implements ActionListener, MouseListener, KeyListener {//the ActionListener's job is to do somthing when some action(ActionEvent) occurs while the program is runnin
    //initializing the variables
    
    public static FlappyBird flappy;
    public final int width=800,height=730;//fixing the window size so that the setSize funtione can use them
    public Renderer renderer;
    public Rectangle bird;
    public ArrayList<Rectangle> column;
    public Random rand;
    public int ticks,yMotion;
    public boolean gameover,started;
    public int score=0;
    
    public static void main(String[] args) {

        flappy=new FlappyBird();//every action starts from here...since we are creating a new object which is instance of the FlappyBird class, the constructor is going to be initialized
       
    }
    public FlappyBird(){
        renderer=new Renderer();//has to be in the first line i dnt know why
        
        Timer timer=new Timer(20,this);//passing the delay in microseconds...the higher the slower...and ofcourse the object, flappy, to which we are gonna add the timer
        timer.start();
        
        JFrame frame=new JFrame();
        frame.add(renderer);//add renderer to the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width,height);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setTitle("Flappy Bird");
        frame.addMouseListener(this);//adding a listener to the object that is created, flappy, in the main method
        frame.addKeyListener(this);//this refers to flappy indirectly
        
        rand=new Random();
        
        bird=new Rectangle(width/2-10, height/2-10,20,20);
        column=new ArrayList<>();//this instance of the ArrayList is going to store Rectangles
        
        addColumn(true);
        addColumn(true);
        addColumn(true);
        addColumn(true);
        //by the time that these 4 calls are done, we will have 8 Rectangles in the columns list
        
    }
    
    public void addColumn(boolean play){
        int gap=280;
        int wid=100;
        int high=50+rand.nextInt(300);
        if (play){//here 290 shows the separation between columns and the reason that the second one has -1 is logical because by the time the first line is executed, there will be 1 rectangle item in the ArrayList(column)
             column.add(new Rectangle(width+wid+column.size()*290,height-high-120,wid,high));//the lower column
             column.add(new Rectangle(width+wid+(column.size()-1)*290,0,wid,height-high-gap));
             
        }
        else{
             column.add(new Rectangle(column.get(column.size()-1).x+600,height-high-120,wid,high));//the numbers are just works of trail and error
             column.add(new Rectangle(column.get(column.size()-1).x,0,wid,height-high-gap));
        }
        
    }
    
    public void actionPerformed(ActionEvent e) {
        ticks++;//
        int speed=10;
        if(started){
            for(int i=0; i<column.size();i++){
                Rectangle colum=column.get(i);
                colum.x-=speed;
            }

            if(ticks%2==0 && yMotion<15){//DGI2
                yMotion+=2;
            } 
            for(int i=0; i<column.size();i++){
                Rectangle colum=column.get(i);//gets a specific column
                if(colum.x+colum.width<0){
                    column.remove(colum);//removes the column that gets passed the left corner of the window from the ArrayList
                    if(colum.y==0){
                        addColumn(false);//so every time one column passes the left corner, this call will be sent and two new Rectangles are gonna be added to the arraylist
                    }
                }
            }
            bird.y+=yMotion;
            //at any given time we have 8 rectangles in the column ArrayList
            for(Rectangle col: column){
                //i did col.y==0 because i just need one of the two rectangles that are happening at the same time to test if the bird passed a certain point
                //otherwise its gonna add 2 to the score instead of one because its a for-loop and all the rectangles are gonna be checked adding 2 for each obstacle insted of one
                if(col.y==0&& bird.x+bird.width/2>col.x+col.width/2-10 && bird.x+bird.width/2<col.x+col.width/2+ 10){
                    score++;//10 because the ball size is 20...and so  the half. And the three && statements make sure that the ball is indeed in the center such that the score could be incremented...easy to understand
                }
                
                if(col.intersects(bird)){
                    gameover=true;
                    started=false;
                    
                    if(bird.x<=col.x){
                        bird.x=col.x-bird.width;
                    }
                    else{
                        if(col.y!=0){
                            bird.y=col.y-bird.height;
                        }
                        else if(bird.y<col.height){
                            bird.y=col.height;
                        }
                   }
                 }
            } 
            if(bird.y>=height-120 || bird.y<=0){
                gameover=true;
                
            }
            if(bird.y+yMotion>=height-120){
                bird.y=height-120-bird.height;
                started=false;
                gameover=true;
            }
            if(bird.y+yMotion<=0){//the yMotion is the number of pixels the bird is gonna leap at a time...we have to take that into consideration when setting the y position
                bird.y=0;
                started=false;
                gameover=true;
            }
            
        }
        renderer.repaint();
    }
    
    public void paintColumn(Graphics g, Rectangle column){
        g.setColor(new Color(53,106,117));
        g.fillRect(column.x,column.y,column.width,column.height);
    }
    
    public void paint(Graphics g){
        g.setColor(new Color(120,206,248));
        g.fillRect(0,0,width,height);
        
        g.setColor(new Color(120,74,50));
        g.fillRect(0,height-120,width,120);
        
        g.setColor(Color.white);
        g.fillOval(40,20,25,15);
        g.fillOval(45,35,30,18);
        g.fillOval(150,30,30,20);
        g.fillOval(200,23,25,17);
        g.fillOval(400,30,19,15);
        g.fillOval(500,27,25,25);
        g.fillOval(40,14,30,15);
        g.fillOval(750,21,40,20);
        
        g.setColor(Color.green.brighter());
        g.fillRect(0, height-120, width, 20);
        
        g.setColor(Color.red);
        g.fillRect(bird.x, bird.y, bird.width, bird.height);
   
        for(Rectangle colu: column){
            paintColumn(g,colu);
        }
        //here we r setting the color for our text, so unless we set the color again, all the things we draw using g will have color red.
        g.setColor(Color.black.brighter());
        g.setFont(new Font("Airal",1,20));//the second argument is the thickness of the font
        g.drawString("Score "+score, 50, height/2-240);
        
        if(gameover){
            g.drawString("GameOver", 50, height/2-200);
            g.drawString("Score "+score, 50, height/2-240);
        }
        if(!started){
            g.drawString("Click to Start", 50, height/2-275);

        }
          
    }

    public void mouseClicked(MouseEvent e) {
        jump();
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
    
    public void jump(){
        if(gameover){
            bird=new Rectangle(width/2-10, height/2-10,20,20);
            column.clear();
            yMotion=0;//initially zero and when incremented that value is gonna be added to the bird.y and the result will be effected when the repaint method is called
            score=0;
            addColumn(true);
            addColumn(true);
            addColumn(true);
            addColumn(true);
            gameover=false;
           
        }
        if(!started){
            started=true;
        }
        if(!gameover){
            //DGI1
            if(yMotion>0){
                yMotion=0;
            } 
            yMotion-=10;//the number of pixels the bird is gonna jump/ pretty much the speed at which the bird rises up
        } 
        
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            jump();
        }
    }

    public void keyReleased(KeyEvent e) {
    }
    
    
}
