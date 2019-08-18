package pkg2048;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class pro2 {
    //variable initialization
    private int mMatrix[][];
    //method initialization
    public pro2(int size){
        ArrayList<Integer> nums=new ArrayList<>();
        nums.add(2);  nums.add(2);  nums.add(2);  nums.add(2); nums.add(4); 
        mMatrix=new int[size][size];
        for(int i = 0; i < mMatrix.length; i++) {
            for (int j = 0; j < mMatrix[0].length; j++) {
                mMatrix[i][j] = 0;
            }
        }
        //directly using random object rather than declaring and assigning it
        mMatrix[new Random().nextInt(size)][new Random().nextInt(size)]=nums.get(new Random().nextInt(nums.size()));
        mMatrix[new Random().nextInt(size)][new Random().nextInt(size)]=nums.get(new Random().nextInt(nums.size()));

    }
    public int[][] swapper(int[][] mat){
        for(int i=0;i<mat.length;i++){
            for(int j=0;j<mat[0].length/2;j++){
                 int a=mat[i][j];
                 mat[i][j]=mat[i][mat[0].length-1-j];
                 mat[i][mat[0].length-1-j]=a;
            }
        }
        return mat;
    }
    public int[][] transpose(int[][] mato){
        int[][] matt=new int[mato.length][mato[0].length];
        for (int i = 0; i < mato.length; i++) {
            for (int j = 0; j < mato[0].length; j++) {
                matt[i][j] = 0;
            }
        }
        for (int x = 0; x < mato.length; x++) {
            for (int y = 0; y < mato[0].length; y++) {
                if(x==y)
                    matt[x][y] = mato[x][y];
                else
                    matt[x][y] = mato[y][x];
            }
        }
        return matt;
    }
    public int[][] leftAdder(int[][] arr){

        ArrayList<ArrayList<Integer>> mata=new ArrayList();
        
        for(int row=0; row<arr.length;row++){ 
            ArrayList<Integer> prep=new ArrayList();
            for(int column=0;column<arr[row].length;column++){
                prep.add(arr[row][column]);
            }
            mata.add(prep);
        }
        
        int[][] end=new int [mata.size()][mata.size()];
        for(int row=0;row<mata.size();row++){
            ArrayList<Integer> merged=new ArrayList();//merged is of data type Integer[]
            int size=(mata.get(row)).size();
            for(int colVal=0;colVal<size;colVal++){
                if((mata.get(row)).get(colVal)!=0)
                    merged.add((mata.get(row)).get(colVal));
            }
            for(int u=0;u<merged.size()-1;u++){
                if(merged.get(u)==merged.get(u+1)){
                    merged.set(u, (merged.get(u))*2);
                    merged.remove(u+1);
                }
            }
            while(merged.size()<size){
                merged.add(0);
            }
            int[] mapper=merged.stream().mapToInt(i->i).toArray();//the CENTER of this method: mapping Integer[] to int[]
            end[row]=mapper;//mapper is of data type int[]...means its an array of a primitive data type int
        }
        return end;
    }
    //the concept is that if we implement an interface, then we need to override all the methods that are available in that interface
    //luckly the only method to override  in the ActionListener Interface is actionPerformed()
    int [][] mm;
    public int[][] checker(int[][] matrio, String dxn){
        switch (dxn) {
            case "a":
                mm=leftAdder(matrio);
                break;
            case "s":
                mm=transpose(swapper(leftAdder(swapper(transpose(matrio)))));
                break;
            case "w":
                mm=transpose(leftAdder(transpose(matrio)));
                break;
            case "d":
                mm=swapper(leftAdder(swapper(matrio)));
                break;
        }
        return mm;
    }
    public void until(){
        A: while(true){
            for(int row = 0; row < mMatrix.length; row++) {
                for (int column = 0; column < mMatrix[row].length; column++) {
                    System.out.print(mMatrix[row][column]+"\t");
                }
                System.out.println();
            }
           
           String dn;
        B: while(true){
           System.out.print("Enter your Move: ");
           Scanner in=new Scanner(System.in);
           dn=in.nextLine().toLowerCase();
           if("a".equals(dn) || "s".equals(dn) || "w".equals(dn) || "d".equals(dn)){
               break B;
           }
           System.out.println("Invalid Movement");//no need for else
       }
        
       mMatrix=checker(mMatrix,dn);
       
       ArrayList<Integer> row_indexes_with_zero=new ArrayList<>();
       ArrayList<Integer> column_indexes_with_zero=new ArrayList<>();
       int ctr=0;
       
       for (int row = 0; row < mMatrix.length; row++) {
                for (int column = 0; column < mMatrix[row].length; column++) {
                    if(mMatrix[row][column]==0){
                        ctr++;
                        row_indexes_with_zero.add(row);
                        column_indexes_with_zero.add(column);
                    }
                    if(mMatrix[row][column]==2048){
                        System.out.println("You Win");
                        break A;
                                
                    }
                } 
       }
       ArrayList<Integer> nums=new ArrayList<>();
       nums.add(2);  nums.add(2);  nums.add(2);  nums.add(2); nums.add(4); 
       int randi=nums.get(new Random().nextInt(nums.size()));
     
       if(ctr>1){
           int randomIndex=row_indexes_with_zero.indexOf(row_indexes_with_zero.get(new Random().nextInt(row_indexes_with_zero.size())));
           mMatrix[row_indexes_with_zero.get(randomIndex)][column_indexes_with_zero.get(randomIndex)]=randi;
       } 
       else if(ctr==1){
           mMatrix[row_indexes_with_zero.get(0)][column_indexes_with_zero.get(0)]=randi;
       }
       else{
           System.out.println("Game over");
           break A;
       }

       }
        
    }   
}