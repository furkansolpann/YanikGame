package codes.classes;

import codes.controls;

public class player {
    private String playerName;
    private card[] playerHand;
    private int cardCount;
    private card[] openCards=new card[12];
    
    public player(String playerName){
        this.playerName = playerName;
        playerHand = new card[13];
        cardCount=0;
        for(int i=0;i<12;i++){
            openCards[i] = new card("","");
        }
    }
    
    public card[] getHand(){
        return playerHand;
    }
    
    public int getCardCount(){
        return cardCount;
    }
    
    public String getName(){
        return playerName;
    }
    
    public void removeCard(int i){
        if(i>-1){
            for(;i<cardCount;i++)
                playerHand[i]=playerHand[i+1];
            cardCount--;
        }
    }
    
    public void removeCard(card c){
        for(int i=0;i<cardCount;i++){
            if(playerHand[i]==c){
                removeCard(i);
                break;
            }
        }
    }
    
    public void addCard(card newcard){
        playerHand[cardCount]=newcard;
        cardCount++;
    }
    
    public void addCard(card newcard,int i){
        for(int k=cardCount;k>i;k--)
            playerHand[k]=playerHand[k-1];
        playerHand[i]=newcard;
        cardCount++;
    }
    
    public void drawCard(deck d){
        playerHand[cardCount] = d.drawCard();
        cardCount++;
    }
    
    public boolean isHandContains(int x,int y){
        for(int i=cardCount-1;i>=0;i--){
            if(playerHand[i].contains(x, y)){
                return true;
            }
        }
        return false;
    }
    
    public int getHandContains(int x,int y){
        for(int i=cardCount-1;i>=0;i--)
            if(playerHand[i].contains(x,y))
                return i;
        return -1;
    }
    
    public card[] getOpenCards(){
        return openCards;
    }
    
    public void setOpenCards(card[] cards){
        openCards=cards;
    }
    
    public boolean isOpenContains(int x,int y){
        for(int i=11;i>=0;i--){
            if(openCards[i].contains(x, y)){
                return true;
            }
        }
        return false;
    }
    
    public int getOpenContains(int x,int y){
        for(int i=11;i>=0;i--){
            if(openCards[i].contains(x, y)){
                return i;
            }
        }
        return -1;
    }
    
    public int drawOpenContains(int x,int y){
        for(int i=11;i>=0;i--){
            if(openCards[i].contains(x, y)&&!openCards[i].getSuit().equals("")){
                return i;
            }
        }
        return -1;
    }
    
    public void removeOpenCard(int i){
        if(i<4){
            for(;i<4;i++){
                if(i!=3)
                    openCards[i]=openCards[i+1];
                else
                    openCards[3]= new card("","");
            }
        }
        else if(i<8){
            for(;i<8;i++){
                if(i!=7)
                    openCards[i]=openCards[i+1];
                else
                    openCards[7]= new card("","");
            }    
        }
        else if(i<12){
            for(;i<12;i++){
                if(i!=11)
                    openCards[i]=openCards[i+1];
                else
                    openCards[11]= new card("","");
            }
        }
    }
    
    public boolean addOpenCard(int i,card c){
        controls _controls = new controls();
        card[] cards=new card[4];
        if(i<4)
            i=0;
        else if(i<8)
            i=4;
        else if(i<12)
            i=8;
        else
            return false;
        for(int k=0;k<4;k++)
            cards[k]=openCards[i+k];
        if(_controls.ctrlAddOpenCard(cards, c)){
            if (openCards[i].getSuit().equals("")) {
                openCards[i] = c;
                return true;
            }
            else if (openCards[i + 1].getSuit().equals("")) {
                if(c.getSuit().equals("joker"))
                    openCards[i + 1] = c;
                else if(cards[0].getRank()==c.getRank()+1){
                    openCards[i+1]=openCards[i];
                    openCards[i]=c;
                }
                else
                    openCards[i + 1] = c;
                return true;
            }
            else if (openCards[i + 2].getSuit().equals("")) {
                if(openCards[i+1].getSuit().equals("joker"))
                    if(c.getRank()==(openCards[i].getRank()-2)){
                        openCards[i+2]=openCards[i];
                        openCards[i]=c;
                        return true;
                    }
                if(c.getSuit().equals("joker"))
                    openCards[i+2] = c;
                else if(cards[0].getRank()==c.getRank()+1){
                    openCards[i+2]=openCards[i+1];
                    openCards[i+1]=openCards[i];
                    openCards[i]=c;
                }
                else
                    openCards[i + 2] = c;
                return true;
            }
            else if (openCards[i + 3].getSuit().equals("")) {
                if(openCards[i+2].getSuit().equals("joker"))
                    if(c.getRank()==(openCards[i].getRank()-2)){
                        openCards[i+3]=openCards[i+1];
                        openCards[i+1]=openCards[i+2];
                        openCards[i+2]=openCards[i];
                        openCards[i]=c;
                        return true;
                    }
                if(c.getSuit().equals("joker"))
                    openCards[i+3] = c;
                else if(cards[0].getRank()==c.getRank()+1){
                    openCards[i+3]=openCards[i+2];
                    openCards[i+2]=openCards[i+1];
                    openCards[i+1]=openCards[i];
                    openCards[i]=c;
                }
                else
                    openCards[i + 3] = c;
                return true;
            }
        }
        return false;
    }
}
