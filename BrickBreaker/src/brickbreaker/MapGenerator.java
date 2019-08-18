package brickbreaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {
   //instantiating variables
    public int map[][];//2D array
    public int brickwidth;
    public int brickheight;
   //f1:constructor
    public MapGenerator(int row,int col){//this constructor runs only once
        map=new int[row][col];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = 1;
                
            }
        }
        brickwidth=380/col;//the reason we assigned 540 and 150 here is to have a fixed boundary to the map no matter what rows and columns we may have
        brickheight=150/row;
        
    }
    //f2:drawer
    public void draw(Graphics2D g){//g is of datatype Graphics2D because we are drawing a 2D graphics 
        for (int i = 0; i < map.length; i++) {//the no of rows we passed
            for (int j = 0; j < map[0].length; j++) {//the no of columns we passed
                if(map[i][j] >0){//map[0][0] means the first row the first column element
                    g.setColor(new Color(200,40,70));
                    g.fillRect(j*brickwidth+150, i*brickheight+50, brickwidth, brickheight);//this is logical because x varies wrt j and y varies wrt i
                    //here 80 and 50 are distances from the topleft of the map from the container panel...we used them as the origin of our map
                    g.setStroke(new BasicStroke(2));//setting the thickness of the stroke
                    g.setColor(Color.white);
                    g.drawRect(j*brickwidth+150, i*brickheight+50, brickwidth, brickheight);//this line should be similar to the abv for obvious reasons
                       //here we draw a rectangle but above we filled a rectangle
                    //each line of code here gets executed again for every index in the 2D array
                }
                
            }
        }
    }
    //f3:value setter
    public void setBrickValue(int value,int row,int col){
        map[row][col]=value;
    }
}