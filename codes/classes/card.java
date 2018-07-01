package codes.classes;

import java.awt.*;
import javax.swing.*;

public class card {
    private String rank,suit;
    private ImageIcon image,back,backrotate;
    private int x,y;
    
    public card(String suit,String rank){
        this.suit=suit;
        this.rank=rank;
        image = new ImageIcon("src/cards/"+suit+rank+".png");
        back = new ImageIcon("src/cards/back.png");
        backrotate =new ImageIcon("src/cards/backrotate.png");
    }
    
    public void moveTo(int x,int y){
        this.x = x;
        this.y = y;
    }
    
    public boolean contains(int x, int y) {
        return (x > this.x && x < this.x + getWidth() && 
                y > this.y && y < this.y + getHeight());
    }
    
    public int getWidth(){
        return back.getIconWidth();
    }
    
    public int getHeight() {
        return back.getIconHeight();
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public int getRank(){
        if(rank.equals("Ace"))
            return 1;
        else if(rank.equals("Jack"))
            return 11;
        else if(rank.equals("Queen"))
            return 12;
        else if(rank.equals("King"))
            return 13;
        else if(rank.equals("j"))
            return -1;
        else if(rank.equals(""))
            return 15;
        else    
            return Integer.parseInt(rank);
    }
    
    public int getPoint(){
        if(rank.equals("Ace")||rank.equals("Jack")||rank.equals("Queen")||rank.equals("King"))
            return 10;
        else if(rank.equals("j"))
            return 20;
        else
            return Integer.parseInt(rank);
    }
    
    public String getSuit(){
        return suit;
    }
    
    public void paint(Component c,Graphics g){
        image.paintIcon(c,g,x,y);
    }
    
    public void paintBack(Component c,Graphics g){
        back.paintIcon(c,g,x,y);
    }
    
    public void paintBackRotate(Component c,Graphics g){
        backrotate.paintIcon(c,g,x,y);
    }
}
        
    
    
   
