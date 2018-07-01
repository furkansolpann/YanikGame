package codes;

import frames.*;
import codes.classes.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class game {
    private int roundCount;
    private int startGameWith; //0:USER/1:WEST/2:NORTH/3:EAST
    
    private int[] p;
    private int[] w;
    private int[] e;
    private int[] n;
    private String[] names;
    
    private JFrame mainframe;
    private JComponent comp;
    private controls _controls;
    private JLabel deckCount,userName,westName,northName,eastName,userScore,westScore,northScore,eastScore;
    private deck d;
    private player playerWest,playerNorth,playerEast,playerUser;

    private int dragfromx=0,dragfromy=0;
    private int cardNumber;
    private card currentCard =null;
    
    private boolean canDraw,canThrow,opened;
    
    private card[] discard=new card[2];

    public game(String[] names,JFrame mainframe,int startGameWith){
        this.startGameWith = startGameWith;
        this.d = new deck();
        this.mainframe=mainframe;
        this.names=names;
        p= new int[10];
        w= new int[10];
        e= new int[10];
        n= new int[10];
        for(int i=0;i<10;i++){
            p[i]=-1;w[i]=-1;e[i]=-1;n[i]=-1;
        }
        roundCount=1;
        createPlayers();
        comp =new tableJComponent(this);
        _controls = new controls();
        
        deckCount = new JLabel();
        this.mainframe.add(deckCount);
        
        userName = new JLabel();
        this.mainframe.add(userName);
        
        westName = new JLabel();
        this.mainframe.add(westName);
        
        northName = new JLabel();
        this.mainframe.add(northName);
        
        eastName = new JLabel();
        this.mainframe.add(eastName);
        
        userScore = new JLabel();
        this.mainframe.add(userScore);
        
        westScore = new JLabel();
        this.mainframe.add(westScore);
        
        northScore = new JLabel();
        this.mainframe.add(northScore);
        
        eastScore = new JLabel();
        this.mainframe.add(eastScore);
        
        this.mainframe.setContentPane(comp);
        this.mainframe.setVisible(true);
        setHands();
        startGame();
    }
    
    private void startGame(){
         switch(startGameWith){
            case 0:
                reset();
                break;
            case 1:
                paintDelay(200);
                playAI(playerWest);
                paintDelay(200);
                playAI(playerNorth);
                paintDelay(200);
                playAI(playerEast);
                paintDelay(200);
                reset();
                break;
            case 2:
                paintDelay(200);
                playAI(playerNorth);
                paintDelay(200);
                playAI(playerEast);
                paintDelay(200);
                reset();
                break;
            case 3:
                paintDelay(200);
                playAI(playerEast);
                paintDelay(200);
                reset();
                break;
        }
    }
    
    private void createPlayers(){
        this.playerWest = new player("WEST");
        this.playerNorth = new player("NORTH");
        this.playerEast = new player("EAST");
        this.playerUser = new player("USER");
        discard[0]=new card("","");
        discard[1]=new card("","");
        opened=false;
        if(roundCount==1) {
            p[roundCount-1]=0;
            w[roundCount-1]=0;
            e[roundCount-1]=0;
            n[roundCount-1]=0;
        }
        else{
            p[roundCount-1]=p[roundCount-2];
            w[roundCount-1]=w[roundCount-2];
            e[roundCount-1]=e[roundCount-2];
            n[roundCount-1]=n[roundCount-2];
        }
    }
     
    private void setHands(){
        switch(startGameWith){
            case 0:
                for(int i=0;i<10;i++){
                    playerUser.drawCard(d);
                    paintDelay(100);
                    playerWest.drawCard(d);
                    paintDelay(100);
                    playerNorth.drawCard(d);
                    paintDelay(100);
                    playerEast.drawCard(d);
                    paintDelay(100);
                }
            break;
            case 1:
                for(int i=0;i<10;i++){
                    playerWest.drawCard(d);
                    paintDelay(100);
                    playerNorth.drawCard(d);
                    paintDelay(100);
                    playerEast.drawCard(d);
                    paintDelay(100);
                    playerUser.drawCard(d);
                    paintDelay(100);
                }
            break;
            case 2:
                for(int i=0;i<10;i++){
                    playerNorth.drawCard(d);
                    paintDelay(100);
                    playerEast.drawCard(d);
                    paintDelay(100);
                    playerUser.drawCard(d);
                    paintDelay(100);
                    playerWest.drawCard(d);
                    paintDelay(100);
                }
            break;
            case 3:
                for(int i=0;i<10;i++){  
                    playerEast.drawCard(d);
                    paintDelay(100);
                    playerUser.drawCard(d);
                    paintDelay(100);
                    playerWest.drawCard(d);
                    paintDelay(100);
                    playerNorth.drawCard(d);
                    paintDelay(100);
                }
            break;
        }
    }
     
    public void paintGame(Graphics g){
        paint _paint = new paint(comp.getWidth(),comp.getHeight());
        _paint.paintHand(playerUser, comp, g);
        _paint.paintHand(playerWest, comp, g);
        _paint.paintHand(playerNorth, comp, g);
        _paint.paintHand(playerEast, comp, g);
        _paint.paintDeck(d, comp, g);
        _paint.paintDiscard(discard,comp,g);
        _paint.paintOpenCards(playerUser, comp, g);
        _paint.paintOpenCards(playerNorth, comp, g);
        _paint.paintOpenCards(playerWest, comp, g);
        _paint.paintOpenCards(playerEast, comp, g);
        addComponents();
        if(currentCard!=null)
            currentCard.paint(comp, g);
    }
    
    private void addComponents(){
        mainframe.remove(deckCount);
        deckCount.setLocation(d.getTop().getX()+d.getTop().getWidth()-35, d.getTop().getY()+d.getTop().getHeight()-45);
        deckCount.setSize(70, 60);
        deckCount.setForeground(Color.BLUE);
        deckCount.setFont(new Font("Verdana", Font.BOLD, 24));
        deckCount.setText(String.valueOf(d.getCardCount()));
        if(d.getCardCount()<67){
            if(currentCard!=null){
                if(!currentCard.contains(deckCount.getX()+25, deckCount.getY()+30))
                    deckCount.setVisible(true);
                else
                    deckCount.setVisible(false);
            }
            else
                deckCount.setVisible(true);
        }
        else
            deckCount.setVisible(false);
        mainframe.add(deckCount);
        int i=p.length-1;
        for(;0<=i;i--){
            if(p[i]!=-1)
                break;
        }
        //NAMES
        mainframe.remove(userName);
        userName.setLocation(3*comp.getWidth()/4-60,comp.getHeight()-100);
        userName.setSize(100, 20);
        userName.setForeground(Color.BLACK);
        userName.setFont(new Font("Verdana", Font.BOLD, 20));
        userName.setText(String.valueOf(names[0]));
        if(currentCard!=null){
            if(!currentCard.contains(3*comp.getWidth()/4-60,comp.getHeight()-100))
                userName.setVisible(true);
            else
                userName.setVisible(false);
        }
        else
            userName.setVisible(true);
        mainframe.add(userName);
        
        mainframe.remove(westName);
        westName.setLocation(40,3*comp.getHeight()/4);
        westName.setSize(100, 20);
        westName.setForeground(Color.BLACK);
        westName.setFont(new Font("Verdana", Font.BOLD, 20));
        westName.setText(String.valueOf(names[1]));
        if(currentCard!=null){
            if(!currentCard.contains(40,3*comp.getHeight()/4))
                westName.setVisible(true);
            else
                westName.setVisible(false);
        }
        else
            westName.setVisible(true);
        mainframe.add(westName);
      
        mainframe.remove(northName);
        northName.setLocation(comp.getWidth()/4,40);
        northName.setSize(100, 20);
        northName.setForeground(Color.BLACK);
        northName.setFont(new Font("Verdana", Font.BOLD, 20));
        northName.setText(String.valueOf(names[2]));
        if(currentCard!=null){
            if(!currentCard.contains(comp.getWidth()/4,40))
                northName.setVisible(true);
            else
                northName.setVisible(false);
        }
        else
            northName.setVisible(true);
        mainframe.add(northName);
          
        mainframe.remove(eastName);
        eastName.setLocation(comp.getWidth()-120,comp.getHeight()/4-60);
        eastName.setSize(100, 20);
        eastName.setForeground(Color.BLACK);
        eastName.setFont(new Font("Verdana", Font.BOLD, 20));
        eastName.setText(String.valueOf(names[3]));
        if(currentCard!=null){
            if(!currentCard.contains(comp.getWidth()-120,comp.getHeight()/4-60))
                eastName.setVisible(true);
            else
                eastName.setVisible(false);
        }
        else
            eastName.setVisible(true);
        mainframe.add(eastName);
    //SCORES
        mainframe.remove(userScore);
        userScore.setLocation(3*comp.getWidth()/4-60,comp.getHeight()-80);
        userScore.setSize(100, 20);
        userScore.setForeground(Color.RED);
        userScore.setFont(new Font("Verdana", Font.BOLD, 20));
        userScore.setText(String.valueOf(p[i])+"/100");
        if(currentCard!=null){
            if(!currentCard.contains(3*comp.getWidth()/4-60,comp.getHeight()-80))
                userScore.setVisible(true);
            else
                userScore.setVisible(false);
        }
        else
            userScore.setVisible(true);
        mainframe.add(userScore);
      
        mainframe.remove(westScore);
        westScore.setLocation(40,3*comp.getHeight()/4+20);
        westScore.setSize(100, 20);
        westScore.setForeground(Color.RED);
        westScore.setFont(new Font("Verdana", Font.BOLD, 20));
        westScore.setText(String.valueOf(w[i])+"/100");
        if(currentCard!=null){
            if(!currentCard.contains(40,3*comp.getHeight()/4+20))
                westScore.setVisible(true);
            else
                westScore.setVisible(false);
        }
        else
            westScore.setVisible(true);
        mainframe.add(westScore);
         
        mainframe.remove(northScore);
        northScore.setLocation(comp.getWidth()/4,60);
        northScore.setSize(100, 20);
        northScore.setForeground(Color.RED);
        northScore.setFont(new Font("Verdana", Font.BOLD, 20));
        northScore.setText(String.valueOf(n[i])+"/100");
        if(currentCard!=null){
            if(!currentCard.contains(comp.getWidth()/4,60))
                northScore.setVisible(true);
            else
                northScore.setVisible(false);
        }
        else
            northScore.setVisible(true);
        mainframe.add(northScore);
       
        mainframe.remove(eastScore);
        eastScore.setLocation(comp.getWidth()-120,comp.getHeight()/4-40);
        eastScore.setSize(100, 20);
        eastScore.setForeground(Color.RED);
        eastScore.setFont(new Font("Verdana", Font.BOLD, 20));
        eastScore.setText(String.valueOf(e[i])+"/100");
        if(currentCard!=null){
            if(!currentCard.contains(comp.getWidth()-120,comp.getHeight()/4-40))
                eastScore.setVisible(true);
            else
                eastScore.setVisible(false);
        }
        else
            eastScore.setVisible(true);
        mainframe.add(eastScore);
    }
    
    private void playAI(player p){
        AI ai = new AI();
        ai.AIDrawCard(p,d,discard);
        paintDelay(200);
        ai.AIOpenTable(p);
        paintDelay(200);
        if(ai.processCard(p,playerWest,playerNorth,playerEast,playerUser))
        {
            paintDelay(300);
        }
        ai.AIThrowCard(p, discard);
    }

    private void newRound(){
        startGameWith=(startGameWith+1)%4;
        d = new deck();
        roundCount++;
        createPlayers();
        setHands();
        startGame();
    }
    
    private void roundFinished(){
        boolean[] red = new boolean[4];
        for(int i=0;i<4;i++)
            red[i]=false;
        if(discard[1].getSuit().equals("joker")){
            for(int i=0;i<playerUser.getCardCount();i++)
                p[roundCount-1]+=playerUser.getHand()[i].getPoint()*2;
            for(int i=0;i<playerWest.getCardCount();i++)
                w[roundCount-1]+=playerWest.getHand()[i].getPoint()*2;
            for(int i=0;i<playerEast.getCardCount();i++)
                e[roundCount-1]+=playerEast.getHand()[i].getPoint()*2;
            for(int i=0;i<playerNorth.getCardCount();i++)
                n[roundCount-1]+=playerNorth.getHand()[i].getPoint()*2;
        }
        else{
            for(int i=0;i<playerUser.getCardCount();i++)
                p[roundCount-1]+=playerUser.getHand()[i].getPoint();
            for(int i=0;i<playerWest.getCardCount();i++)
                w[roundCount-1]+=playerWest.getHand()[i].getPoint();
            for(int i=0;i<playerEast.getCardCount();i++)
                e[roundCount-1]+=playerEast.getHand()[i].getPoint();
            for(int i=0;i<playerNorth.getCardCount();i++)
                n[roundCount-1]+=playerNorth.getHand()[i].getPoint();
        }
        
        int max=0;
        if(p[roundCount-1]>max && p[roundCount-1]<100)
            max=p[roundCount-1];
        if(w[roundCount-1]>max && w[roundCount-1]<100)
            max=w[roundCount-1];
        if(e[roundCount-1]>max && e[roundCount-1]<100)
            max=e[roundCount-1];
        if(n[roundCount-1]>max && n[roundCount-1]<100)
            max=n[roundCount-1];
        
        if(p[roundCount-1]>=100){
            p[roundCount-1]=max;
            red[0]=true;
        }
        if(w[roundCount-1]>=100){
            w[roundCount-1]=max;
            red[1]=true;
        }
        if(e[roundCount-1]>=100){
            e[roundCount-1]=max;
            red[3]=true;
        }
        if(n[roundCount-1]>=100){
            n[roundCount-1]=max;
            red[2]=true;
        }
        scoreFrame frame = new scoreFrame(this,p,w,e,n,names,red);
        frame.setLocation(new java.awt.Point(0, 0));
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        mainframe.hide();
    }
    
    public void nextState(){
        if(roundCount==10){
            mainJFrame frame = new mainJFrame();
            frame.setVisible(true);
            mainframe.dispose();
        }
        else{
            mainframe.show();
            newRound();
        }
    }
    
    public void quit(){
        mainframe.dispose();
    }
    
    public void mousePressed(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        currentCard = null;
        if(playerUser.isHandContains(x, y)){
            int i = playerUser.getHandContains(x,y);
            currentCard=playerUser.getHand()[i];
            dragfromx=x-currentCard.getX();
            dragfromy=y-currentCard.getY();
            cardNumber=i;
            playerUser.removeCard(cardNumber);
            comp.repaint();
        }
        if(playerUser.isOpenContains(x, y)){
            int i = playerUser.drawOpenContains(x, y);
            if(opened){
                int k = getK(i);
                if (playerUser.getOpenCards()[k+2].getSuit().equals("")) {
                    if(i!=-1){
                        playerUser.addCard(playerUser.getOpenCards()[i]);
                        playerUser.removeOpenCard(i);
                        comp.repaint();
                    };
                }
            }
            else{
                if(i!=-1){
                    playerUser.addCard(playerUser.getOpenCards()[i]);
                    playerUser.removeOpenCard(i);
                    comp.repaint();
                }
            }
        }
        if(canDraw){
            if(d.getTop().contains(x, y)){
                playerUser.drawCard(d);
                canDraw=false;
                canThrow=true;
                comp.repaint();
            }
            if(discard[1].contains(x, y) && !discard[1].getSuit().equals("")){
                playerUser.addCard(discard[1]);
                discard[1]=discard[0];
                canDraw=false;
                canThrow=true;
                comp.repaint();
            }
        }
    }
    
    public void mouseDragged(MouseEvent e) {
        if(currentCard != null){
            int newX = e.getX()-dragfromx;
            int newY = e.getY()-dragfromy;
            newX=Math.max(newX,0);
            newX=Math.min(newX,comp.getWidth()-currentCard.getWidth());
            newY=Math.max(newY, 0);
            newY=Math.min(newY, comp.getHeight()-currentCard.getHeight());
            currentCard.moveTo(newX, newY);
            comp.repaint();
        }
    }
    
    public void mouseReleased(MouseEvent e) {
        if (e.getClickCount()==1) {
            int x = e.getX();
            int y = e.getY();
            if (currentCard != null) {
                if (discard[1].contains(x, y)) {
                    if (canThrow) {
                        if(opened&&(!playerUser.getOpenCards()[0].getSuit().equals("")||!playerUser.getOpenCards()[4].getSuit().equals("")||!playerUser.getOpenCards()[8].getSuit().equals("")))
                            if(_controls.canOpenS(playerUser)){
                                userDiscard();
                            }
                            else{
                                playerUser.addCard(currentCard, cardNumber);
                                currentCard = null;
                                comp.repaint();
                            }
                        else if(!opened&&(!playerUser.getOpenCards()[0].getSuit().equals("")||!playerUser.getOpenCards()[4].getSuit().equals("")||!playerUser.getOpenCards()[8].getSuit().equals("")))
                            if(_controls.canOpen(playerUser)){
                                opened=true;
                                userDiscard();
                            }
                            else{
                                playerUser.addCard(currentCard, cardNumber);
                                currentCard = null;
                                comp.repaint(); 
                            }
                        else{
                            userDiscard();
                        }
                    } else {
                        playerUser.addCard(currentCard, cardNumber);
                        currentCard = null;
                        comp.repaint();
                    }
                } else if (playerUser.isHandContains(x, y)) {
                    int i = playerUser.getHandContains(x, y);
                    playerUser.addCard(currentCard, i + 1);
                    currentCard = null;
                    comp.repaint();
                } else if (playerUser.getCardCount() != 0 && playerUser.getHand()[0].contains(x + 36, y)) {
                    playerUser.addCard(currentCard, 0);
                    currentCard = null;
                    comp.repaint();
                } else if (playerUser.isOpenContains(x, y)) {
                    int i = playerUser.getOpenContains(x, y);
                    if (!playerUser.addOpenCard(i, currentCard)) {
                        playerUser.addCard(currentCard, cardNumber);
                    }
                    currentCard = null;
                    paintDelay(200);
                    comp.repaint();
                } else if (playerWest.isOpenContains(x, y)) {
                    if (opened) {
                        int i = playerWest.getOpenContains(x, y);
                        int k = getK(i);
                        if (!playerWest.getOpenCards()[k].getSuit().equals("")) {
                            if (!playerWest.addOpenCard(i, currentCard)) {
                                playerUser.addCard(currentCard, cardNumber);
                            }
                        } else {
                            playerUser.addCard(currentCard, cardNumber);
                        }
                        currentCard = null;
                        paintDelay(200);
                        _controls.ctrlOpenCards(playerWest.getOpenCards());
                        comp.repaint();
                    }  
                    else{
                        if(!opened&&(!playerUser.getOpenCards()[0].getSuit().equals("")||!playerUser.getOpenCards()[4].getSuit().equals("")||!playerUser.getOpenCards()[8].getSuit().equals("")))
                            if(_controls.canOpen(playerUser)){
                                opened=true;
                                int i = playerWest.getOpenContains(x, y);
                                int k = getK(i);
                                if (!playerWest.getOpenCards()[k].getSuit().equals("")) {
                                    if (!playerWest.addOpenCard(i, currentCard)) {
                                        playerUser.addCard(currentCard, cardNumber);
                                    }
                                } else {
                                    playerUser.addCard(currentCard, cardNumber);
                                }
                                currentCard = null;
                                paintDelay(200);
                                _controls.ctrlOpenCards(playerWest.getOpenCards());
                                comp.repaint();
                            }
                            else{
                                playerUser.addCard(currentCard, cardNumber);
                                currentCard = null;
                                comp.repaint(); 
                            }
                        else{
                            playerUser.addCard(currentCard, cardNumber);
                            currentCard = null;
                            comp.repaint();
                        }
                    }
                } else if (playerEast.isOpenContains(x, y)) {
                    if (opened) {
                        int i = playerEast.getOpenContains(x, y);
                        int k = getK(i);
                        if (!playerEast.getOpenCards()[k].getSuit().equals("")) {
                            if (!playerEast.addOpenCard(i, currentCard)) {
                                playerUser.addCard(currentCard, cardNumber);
                            }
                        } else {
                            playerUser.addCard(currentCard, cardNumber);
                        }
                        currentCard = null;
                        paintDelay(200);
                        _controls.ctrlOpenCards(playerEast.getOpenCards());
                        comp.repaint();
                    }
                    else{
                        if(!opened&&(!playerUser.getOpenCards()[0].getSuit().equals("")||!playerUser.getOpenCards()[4].getSuit().equals("")||!playerUser.getOpenCards()[8].getSuit().equals("")))
                            if(_controls.canOpen(playerUser)){
                                opened=true;
                                int i = playerEast.getOpenContains(x, y);
                                int k = getK(i);
                                if (!playerEast.getOpenCards()[k].getSuit().equals("")) {
                                    if (!playerEast.addOpenCard(i, currentCard)) {
                                        playerUser.addCard(currentCard, cardNumber);
                                    }
                                } else {
                                    playerUser.addCard(currentCard, cardNumber);
                                }
                                currentCard = null;
                                paintDelay(200);
                                _controls.ctrlOpenCards(playerEast.getOpenCards());
                                comp.repaint();
                            }
                            else{
                                playerUser.addCard(currentCard, cardNumber);
                                currentCard = null;
                                comp.repaint(); 
                            }
                        else{
                            playerUser.addCard(currentCard, cardNumber);
                            currentCard = null;
                            comp.repaint();
                        }
                    }
                } else if (playerNorth.isOpenContains(x, y)) {
                    if (opened) {
                        int i = playerNorth.getOpenContains(x, y);
                        int k = getK(i);
                        if (!playerNorth.getOpenCards()[k].getSuit().equals("")) {
                            if (!playerNorth.addOpenCard(i, currentCard)) {
                                playerUser.addCard(currentCard, cardNumber);
                            }
                        } else {
                            playerUser.addCard(currentCard, cardNumber);
                        }
                        currentCard = null;
                        paintDelay(200);
                        _controls.ctrlOpenCards(playerNorth.getOpenCards());
                        comp.repaint();
                    }
                    else{
                        if(!opened&&(!playerUser.getOpenCards()[0].getSuit().equals("")||!playerUser.getOpenCards()[4].getSuit().equals("")||!playerUser.getOpenCards()[8].getSuit().equals("")))
                            if(_controls.canOpen(playerUser)){
                                opened=true;
                                int i = playerNorth.getOpenContains(x, y);
                                int k = getK(i);
                                if (!playerNorth.getOpenCards()[k].getSuit().equals("")) {
                                    if (!playerNorth.addOpenCard(i, currentCard)) {
                                        playerUser.addCard(currentCard, cardNumber);
                                    }
                                } else {
                                    playerUser.addCard(currentCard, cardNumber);
                                }
                                currentCard = null;
                                paintDelay(200);
                                _controls.ctrlOpenCards(playerNorth.getOpenCards());
                                comp.repaint();
                            }
                            else{
                                playerUser.addCard(currentCard, cardNumber);
                                currentCard = null;
                                comp.repaint(); 
                            }
                        else{
                            playerUser.addCard(currentCard, cardNumber);
                            currentCard = null;
                            comp.repaint();
                        }
                    }
                } else {
                    playerUser.addCard(currentCard, cardNumber);
                    currentCard = null;
                    comp.repaint();
                }
            }
        }
        else
            if(currentCard != null){
                playerUser.addCard(currentCard, cardNumber);
                currentCard = null;
                comp.repaint();
            }
    }
    
    private void userDiscard(){
        canThrow = false;
        card temp = discard[1];
        discard[1] = currentCard;
        currentCard = null;
        discard[0] = temp;
        paintDelay(200);
        ctrlall();
        paintDelay(200);
        if (_controls.isRoundDone(playerUser, d)) {
            roundFinished();
        } else {
            playAI(playerWest);
            paintDelay(200);
            ctrlall();
            paintDelay(200);
            if (_controls.isRoundDone(playerWest, d)) {
                roundFinished();
            } else {
                playAI(playerNorth);
                paintDelay(200);
                ctrlall();
                paintDelay(200);
                if (_controls.isRoundDone(playerNorth, d)) {
                    roundFinished();
                } else {
                    playAI(playerEast);
                    paintDelay(200);
                    ctrlall();
                    paintDelay(200);
                    if (_controls.isRoundDone(playerEast, d)) {
                        roundFinished();
                    } else {
                        reset();
                    }
                }
            }
        }
    }
    
    private void ctrlall(){
        _controls.ctrlOpenCards(playerUser.getOpenCards());
        _controls.ctrlOpenCards(playerWest.getOpenCards());
        _controls.ctrlOpenCards(playerNorth.getOpenCards());
        _controls.ctrlOpenCards(playerEast.getOpenCards());
    }
    
    private int getK(int i){
        int k=0;
        if(i<4)
            k=0;
        else if(i<8)
            k=4;
        else if(i<12)
            k=8;
        return k;
    }
    
    private void paintDelay(int x){
        comp.paintImmediately(0, 0, comp.getWidth(),comp.getHeight() );
        try{Thread.sleep(x);}catch(InterruptedException ex){System.out.println(ex);}
    }
    
    private void reset(){
        canThrow=false;
        canDraw=true;
        cardNumber=-1;
    }
}