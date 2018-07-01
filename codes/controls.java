package codes;

import codes.classes.*;

public class controls {
    public boolean ctrlAddOpenCard(card[] cards,card c){
        int counter=0;
        for(int i=0;i<4;i++)
            if(!cards[i].getSuit().equals(""))
                    counter++;
        if(counter==1){
            if(c.getSuit().equals("joker"))
                return true;
            else if(c.getSuit().equals(cards[0].getSuit())){
                if(cards[0].getRank()==c.getRank()+1)
                    return true;
                else if(cards[0].getRank()==c.getRank()-1)
                    return true;
                else
                    return false;
            }
            else if(c.getRank() == cards[0].getRank())
                return true;
            else
                return false;
        }
        else if(counter==0){
            if(c.getSuit().equals("joker"))
                return false;
            return true;
        }
        else if(c.getSuit().equals("joker"))
            return true;
        String suit;
        if(cards[0].getSuit().equals("joker"))
            suit = cards[1].getSuit();
        else
            suit = cards[0].getSuit();
        int x;
        if(cards[0].getSuit().equals("joker"))
            x=cards[1].getRank();
        else
            x=cards[0].getRank();
        if(c.getSuit().equals(suit)){
            if(cards[1].getSuit().equals("joker")||suit.equals(cards[1].getSuit())){
                int firstcard,lastcard;
                if(cards[0].getSuit().equals("joker"))
                    firstcard = cards[1].getRank()-1;
                else
                    firstcard = cards[0].getRank();
                if(cards[counter-1].getSuit().equals("joker"))
                    lastcard = cards[counter-2].getRank()+1;
                else
                    lastcard = cards[counter-1].getRank();
            
                if(cards[counter-1].getSuit().equals("joker"))
                    if(c.getRank()==(firstcard-2))
                        return true;

                if(c.getRank()==(firstcard-1))
                    return true;
                else if(c.getRank()==(lastcard+1))
                    return true;
            
                else
                    return false;
            }
            else
                return false;
        }
        else if(x==cards[1].getRank()||(cards[1].getSuit().equals("joker")&&cards[2].getSuit().equals(""))){
            if(c.getRank()==x){
                int k=0;
                for (int i = 0; i < counter; i++) {
                    if(cards[i].getSuit().equals("joker"))
                        k++;
                    else if (!cards[i].getSuit().equals(c.getSuit())) {
                        k++; 
                    }
                }
                return (k==counter);
            }
            return false;
        }
        else
            return false;
    }
    
    public void ctrlOpenCards(card[] cards){
        if(!cards[3].getSuit().equals("")){
            for(int i=0;i<4;i++){
                cards[i]=cards[i+4];
                cards[i+4]=cards[i+8];
                cards[i+8]= new card("","");
            }
            ctrlOpenCards(cards);
        }
        else if(!cards[7].getSuit().equals("")){
            for(int i=0;i<4;i++){
                cards[i+4]=cards[i+8];
                cards[i+8]= new card("","");
            }
            ctrlOpenCards(cards);
        }
        else if(!cards[11].getSuit().equals("")){
            for(int i=0;i<4;i++){
                cards[i+8]= new card("","");
            }
        }
    }
    
    public boolean isRoundDone(player p,deck d){
        if (p.getCardCount()==0)
            return true;
        if (d.getCardCount()==0)
            return true;
        return false;
    }
    
    public boolean canOpen(player p){
        int k=0;
        if(!p.getOpenCards()[2].getSuit().equals(""))
            k++;
        else
            if(!p.getOpenCards()[1].getSuit().equals(""))
                k=-5;
            else if(!p.getOpenCards()[0].getSuit().equals(""))
                k=-5;
        if(!p.getOpenCards()[6].getSuit().equals(""))
            k++;
        else
            if(!p.getOpenCards()[5].getSuit().equals(""))
                k=-5;
            else if(!p.getOpenCards()[4].getSuit().equals(""))
                k=-5;
        if(!p.getOpenCards()[10].getSuit().equals(""))
            k++;
        else
            if(!p.getOpenCards()[9].getSuit().equals(""))
                k=-5;
            else if(!p.getOpenCards()[8].getSuit().equals(""))
                k=-5;
        if(k>1)
            return true;
        else
            return false;
    }
    
    public boolean canOpenS(player p){
        int k=0;
        if(!p.getOpenCards()[2].getSuit().equals(""))
            k++;
        else
            if(!p.getOpenCards()[1].getSuit().equals(""))
                k=-5;
            else if(!p.getOpenCards()[0].getSuit().equals(""))
                k=-5;
        if(!p.getOpenCards()[6].getSuit().equals(""))
            k++;
        else
            if(!p.getOpenCards()[5].getSuit().equals(""))
                k=-5;
            else if(!p.getOpenCards()[4].getSuit().equals(""))
                k=-5;
        if(!p.getOpenCards()[10].getSuit().equals(""))
            k++;
        else
            if(!p.getOpenCards()[9].getSuit().equals(""))
                k=-5;
            else if(!p.getOpenCards()[8].getSuit().equals(""))
                k=-5;
        if(k>0)
            return true;
        else
            return false;
    }
}
