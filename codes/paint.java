package codes;

import codes.classes.*;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.ImageIcon;

public class paint {
    private int width,height;
    private int x,y;
    private ImageIcon fake = new ImageIcon("src/cards/fake.png");
    
    public paint(int width,int height){
        this.width=width;
        this.height=height;
    }
    
    public void paintHand(player currentPlayer,Component c,Graphics g){
        switch(currentPlayer.getName()){
            case "USER":
                y=height-135;
                x=((width/2)-62)-((currentPlayer.getCardCount())-1)*12;
                for(int i=0;i<currentPlayer.getCardCount();i++){
                    currentPlayer.getHand()[i].moveTo(x+(24*i), y);
                    currentPlayer.getHand()[i].paint(c, g);
                }
            break;
            case "WEST":
                y=((height/2)-62)-((currentPlayer.getCardCount())-1)*12;
                x=0;
                for(int i=0;i<currentPlayer.getCardCount();i++){
                currentPlayer.getHand()[i].moveTo(x, y+(24*i));
                currentPlayer.getHand()[i].paintBackRotate(c, g);
                }
            break;
            case "NORTH":
                y=0;
                x=((width/2)-62)-((currentPlayer.getCardCount())-1)*12;
                for(int i=0;i<currentPlayer.getCardCount();i++){
                currentPlayer.getHand()[i].moveTo(x+(24*i), y);
                currentPlayer.getHand()[i].paintBack(c, g);
                }
            break;
            case "EAST":
                y=((height/2)-62)-((currentPlayer.getCardCount())-1)*12;
                x=width-135;
                for(int i=0;i<currentPlayer.getCardCount();i++){
                currentPlayer.getHand()[i].moveTo(x, y+(24*i));
                currentPlayer.getHand()[i].paintBackRotate(c, g);
                } 
            break;
        }
    }
    
    public void paintDeck(deck d,Component c,Graphics g){
        x=((width/2)-63)-70;
        y=(height/2)-66;
        card deckcard=d.getTop();
        deckcard.moveTo(x, y);
        deckcard.paintBack(c, g);
    }
    
    public void paintDiscard(card[] discard,Component c,Graphics g){
        x=((width/2)-63)+70;
        y=(height/2)-66;
        for(int i=0;i<2;i++){
            discard[i].moveTo(x, y);
            discard[i].paint(c, g);
        }
        if(discard[1].getSuit().equals(""))
            fake.paintIcon(c,g,x,y);
    }
    
    public void paintOpenCards(player p,Component c,Graphics g){
        switch(p.getName()){
            case "USER":
                y=height-135-135-20;
                x=width/2-96-96-90;
                for(int i=0;i<4;i++){
                    p.getOpenCards()[i].moveTo(x+(24*i), y);
                    p.getOpenCards()[i].paint(c, g);
                }
                if(p.getOpenCards()[0].getSuit().equals(""))
                    fake.paintIcon(c,g,x,y);
                y=height-135-135-20;
                x=width/2-80;
                for(int i=0;i<4;i++){
                    p.getOpenCards()[i+4].moveTo(x+(24*i), y);
                    p.getOpenCards()[i+4].paint(c, g);
                }   
                if(p.getOpenCards()[4].getSuit().equals(""))
                    fake.paintIcon(c,g,x,y);
                y=height-135-135-20;
                x=width/2+96+96-70;
                for(int i=0;i<4;i++){
                    p.getOpenCards()[i+8].moveTo(x+(24*i), y);
                    p.getOpenCards()[i+8].paint(c, g);
                }
                if(p.getOpenCards()[8].getSuit().equals(""))
                    fake.paintIcon(c,g,x,y);
            break;
            
            case "NORTH":
                y=135+20;
                x=width/2-96-96-90;
                for(int i=0;i<4;i++){
                    p.getOpenCards()[i].moveTo(x+(24*i), y);
                    p.getOpenCards()[i].paint(c, g);
                }
                y=135+20;
                x=width/2-80;
                for(int i=0;i<4;i++){
                    p.getOpenCards()[i+4].moveTo(x+(24*i), y);
                    p.getOpenCards()[i+4].paint(c, g);
                }
                y=135+20;
                x=width/2+96+96-70;
                for(int i=0;i<4;i++){
                    p.getOpenCards()[i+8].moveTo(x+(24*i), y);
                    p.getOpenCards()[i+8].paint(c, g);
                }
            break;
            
            case "WEST":
                y=height/2-230;
                x=135+40;
                for(int i=0;i<4;i++){
                    p.getOpenCards()[i].moveTo(x+(24*i), y);
                    p.getOpenCards()[i].paint(c, g);
                }
                y=height/2-70;
                x=135+40;
                for(int i=0;i<4;i++){
                    p.getOpenCards()[i+4].moveTo(x+(24*i), y);
                    p.getOpenCards()[i+4].paint(c, g);
                }
                y=height/2+90;
                x=135+40;
                for(int i=0;i<4;i++){
                    p.getOpenCards()[i+8].moveTo(x+(24*i), y);
                    p.getOpenCards()[i+8].paint(c, g);
                }
            break;
            
            case "EAST":
                y=height/2-230;
                x=width-135-135-60;
                for(int i=0;i<4;i++){
                    p.getOpenCards()[i].moveTo(x+(24*i), y);
                    p.getOpenCards()[i].paint(c, g);
                }
                y=height/2-70;
                x=width-135-135-60;
                for(int i=0;i<4;i++){
                    p.getOpenCards()[i+4].moveTo(x+(24*i), y);
                    p.getOpenCards()[i+4].paint(c, g);
                }
                y=height/2+90;
                x=width-135-135-60;
                for(int i=0;i<4;i++){
                    p.getOpenCards()[i+8].moveTo(x+(24*i), y);
                    p.getOpenCards()[i+8].paint(c, g);
                }
            break;
        }
    }
}
