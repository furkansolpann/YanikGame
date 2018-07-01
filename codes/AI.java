package codes;

import codes.classes.card;
import codes.classes.deck;
import codes.classes.player;

public class AI {
    private card[] notused=new card[12];
    private int cnotused=0;
    
    public void AIDrawCard(player p,deck d,card[] discard){
        int counter=p.getCardCount();
        int drawen=0;
        for(int i=0;i<counter;i++){
            if(discard[1].getRank()==p.getHand()[i].getRank()){
                for(int j=0;j<counter;j++){
                    if(!discard[1].getSuit().equals(p.getHand()[j].getSuit())){
                        p.addCard(discard[1]);
                        discard[1]=discard[0];
                        drawen=1;
                        break;
                    }
                }
            }
            else if(discard[1].getSuit().equals(p.getHand()[i].getSuit())){
                for(int j=0;j<counter;j++){
                    if(discard[1].getRank()-1==p.getHand()[j].getRank()||discard[1].getRank()+1==p.getHand()[j].getRank()){
                       p.addCard(discard[1]);
                       discard[1]=discard[0];
                       drawen=1;
                       break;
                    }
                }
            }
            if(drawen==1)
                break;
        }
        if(drawen==0)
            p.drawCard(d);
    }
    
    public void AIThrowCard(player p,card[] discard){
        discard[0]=discard[1];
        if(cnotused<0){
            int max=0;
            int maxi=0;
            for(int i=0;i<p.getCardCount();i++)
                if(max<p.getHand()[i].getRank()){
                    max=p.getHand()[i].getRank();
                    maxi=i;
                }
            discard[1]=p.getHand()[maxi];
            p.removeCard(maxi);
        }
        else{
            int max=0;
            int maxi=0;
            for(int i=0;i<cnotused+1;i++)
                if(max<notused[i].getRank()){
                    max=notused[i].getRank();
                    maxi=i;
                }
            discard[1]=notused[maxi];
            p.removeCard(notused[maxi]);
        }
    }
    
    public void AIOpenTable(player p){
        int counter=0,copenable=0,needcardscount=0;
        card[] openableCards=new card[12];
        card[] needcards=new card[12];
        card[] value;
        for(int i=0;i<3;i++){
            value=AICanOpen(p);
            for(int c=0;c<4;c++){
                openableCards[copenable++]=value[c];
            }
            if(!value[0].getSuit().equals("")){
                if(!value[3].getSuit().equals("")){//4
                    counter++;
                    for(int k=0;k<4;k++){
                        for(int m=0;m<p.getCardCount();m++){
                            if(value[k]==p.getHand()[m]){
                                p.removeCard(m);
                                break;
                            }
                        }
                    }
                }
                else{//3
                    counter++;
                    for(int k=0;k<3;k++){
                        for(int m=0;m<p.getCardCount();m++){
                            if(value[k]==p.getHand()[m]){
                                p.removeCard(m);
                                break;
                            }
                        }
                    }
                }
            }
        }
        
        for(int i=0;i<p.getCardCount();i++){
            for(int j=0;j<p.getCardCount();j++){
                if(p.getHand()[i].getRank()==p.getHand()[j].getRank()){
                    if(!p.getHand()[i].getSuit().equals(p.getHand()[j].getSuit())){
                        needcards[needcardscount++]=p.getHand()[i];
                        break;
                    }
                }
                else if(p.getHand()[i].getSuit().equals(p.getHand()[i].getSuit())){
                    if(p.getHand()[i].getRank()-1==p.getHand()[j].getRank()||p.getHand()[i].getRank()+1==p.getHand()[j].getRank()){
                        needcards[needcardscount++]=p.getHand()[i];
                        break;
                    }
                }
                if(p.getHand()[i].getSuit().equals("joker")){
                    needcards[needcardscount++]=p.getHand()[i];
                    break;
                }
            }
        }
        for(int i=0;i<p.getCardCount();i++){
            int found=0;
            for(int j=0;j<needcardscount;j++){
                if(p.getHand()[i]==needcards[j]){
                    found=1;
                }
            }
            if(found==0)
                notused[cnotused++]=p.getHand()[i];
        }
            
        cnotused--;
        
        if (p.getCardCount()>=4) {
            if (p.getCardCount() < 6) {
                if (counter > 0) {
                    if (p.getOpenCards()[0].getSuit().equals("")) {
                        p.setOpenCards(openableCards);
                    } else if (p.getOpenCards()[4].getSuit().equals("")) {
                        for (int i = 4; i < 12; i++) {
                            p.addOpenCard(i, openableCards[i - 4]);
                        }
                    } else if (p.getOpenCards()[8].getSuit().equals("")) {
                        for (int i = 8; i < 12; i++) {
                            p.addOpenCard(i, openableCards[i - 8]);
                        }
                    } else {
                        for (int i = 0; i < copenable; i++) {
                            if (!openableCards[i].getSuit().equals("")) {
                                p.addCard(openableCards[i]);
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < copenable; i++) {
                        if (!openableCards[i].getSuit().equals("")) {
                            p.addCard(openableCards[i]);
                        }
                    }
                }
            } else if (counter > 1) {
                if (p.getOpenCards()[0].getSuit().equals("")) {
                    p.setOpenCards(openableCards);
                } else if (p.getOpenCards()[4].getSuit().equals("")) {
                    for (int i = 4; i < 12; i++) {
                        p.addOpenCard(i, openableCards[i - 4]);
                    }
                } else if (p.getOpenCards()[8].getSuit().equals("")) {
                    for (int i = 8; i < 12; i++) {
                        p.addOpenCard(i, openableCards[i - 8]);
                    }
                } else {
                    for (int i = 0; i < copenable; i++) {
                        if (!openableCards[i].getSuit().equals("")) {
                            p.addCard(openableCards[i]);
                        }
                    }
                }
            } else {
                for (int i = 0; i < copenable; i++) {
                    if (!openableCards[i].getSuit().equals("")) {
                        p.addCard(openableCards[i]);
                    }
                }
            }
        } else {
            for (int i = 0; i < copenable; i++) {
                    if (!openableCards[i].getSuit().equals("")) {
                        p.addCard(openableCards[i]);
                    }
                }
        }
    }  
    public boolean processCard(player p,player playerWest,player playerNorth,player playerEast,player playerUser){
        boolean find=false;
        if(p.getCardCount()<6){
            for(int i=0;i<p.getCardCount();i++){
                if(p.getCardCount()==1){
                    break;
                }
                for(int k=0;k<9;k+=4){
                    if (!playerWest.getOpenCards()[k].getSuit().equals("")){
                        if(playerWest.addOpenCard(k,p.getHand()[i]))
                        {
                            int j=0,a=0;
                            for(;j<cnotused+1;j++)
                                if(notused[j]==p.getHand()[i]){
                                    a++;
                                    break;
                                }
                            for(;j<cnotused+1;j++)
                                notused[j]=notused[j+1];
                            if(a>0)
                                cnotused--;
                            p.removeCard(i);
                            i--;
                            find=true;
                            break;
                            
                        }
                    }
                    if (!playerNorth.getOpenCards()[k].getSuit().equals("")){
                        if(playerNorth.addOpenCard(k,p.getHand()[i]))
                        {
                            int j=0,a=0;
                            for(;j<cnotused+1;j++)
                                if(notused[j]==p.getHand()[i]){
                                    a++;
                                    break;
                                }
                            for(;j<cnotused+1;j++)
                                notused[j]=notused[j+1];
                            if(a>0)
                                cnotused--;
                            p.removeCard(i);
                            i--;
                            find=true;
                            break;
                        }
                    }
                    if (!playerEast.getOpenCards()[k].getSuit().equals("")){
                        if(playerEast.addOpenCard(k,p.getHand()[i]))
                        {
                            int j=0,a=0;
                            for(;j<cnotused+1;j++)
                                if(notused[j]==p.getHand()[i]){
                                    a++;
                                    break;
                                }
                            for(;j<cnotused+1;j++)
                                notused[j]=notused[j+1];
                            if(a>0)
                                cnotused--;
                            p.removeCard(i);
                            i--;
                            find=true;
                            break;
                        }
                    }
                    if (!playerUser.getOpenCards()[k].getSuit().equals("")){
                        if(playerUser.addOpenCard(k,p.getHand()[i]))
                        {
                            int j=0,a=0;
                            for(;j<cnotused+1;j++)
                                if(notused[j]==p.getHand()[i]){
                                    a++;
                                    break;
                                }
                            for(;j<cnotused+1;j++)
                                notused[j]=notused[j+1];
                            if(a>0)
                                cnotused--;
                            p.removeCard(i);
                            i--;
                            find=true;
                            break;
                        }
                    }
                }
            }
        }
        if(find){
            return find;
        }
        else
        {
            return false;
        }
    }
    

    private card[] AICanOpen(player phand){
        card[] hand=phand.getHand();
        card[] value=new card[4];
        //suit
        for(int i=0;i<phand.getCardCount();i++){
            for(int j=0;j<phand.getCardCount();j++){
                if(hand[i].getSuit().equals(hand[j].getSuit()) && hand[i].getRank()+1==hand[j].getRank()){//2
                    for(int k=0;k<phand.getCardCount();k++){
                        if(hand[j].getSuit().equals(hand[k].getSuit()) && hand[j].getRank()+1==hand[k].getRank()){//3
                            for(int m=0;m<phand.getCardCount();m++){
                                if(hand[k].getSuit().equals(hand[m].getSuit()) && hand[k].getRank()+1==hand[m].getRank()){
                                    //open 4
                                    value[0]=hand[i];value[1]=hand[j];value[2]=hand[k];value[3]=hand[m];
                                    return value;
                                }
                                else{
                                    //open 3
                                    value[0]=hand[i];value[1]=hand[j];value[2]=hand[k];value[3]=new card("","");
                                    return value;
                                }
                                    
                            }
                        }
                    }
                }
            }
        }
        //rank
        for(int i=0;i<phand.getCardCount();i++){
            for(int j=i;j<phand.getCardCount();j++){
                if(hand[j].getRank()==hand[i].getRank() && !hand[i].getSuit().equals(hand[j].getSuit())){//2
                    for(int k=j;k<phand.getCardCount();k++){
                        if(hand[k].getRank()==hand[i].getRank() && !hand[j].getSuit().equals(hand[k].getSuit()) &&!hand[i].getSuit().equals(hand[k].getSuit()) ){//3
                            for(int m=k;m<phand.getCardCount();m++){
                                if(hand[m].getRank()==hand[i].getRank() && !hand[i].getSuit().equals(hand[m].getSuit()) &&!hand[j].getSuit().equals(hand[m].getSuit())&&!hand[k].getSuit().equals(hand[m].getSuit()) ){//4
                                    value[0]=hand[i];value[1]=hand[j];value[2]=hand[k];value[3]=hand[m];
                                    return value;
                                }
                                else{
                                    value[0]=hand[i];value[1]=hand[j];value[2]=hand[k];value[3]=new card("","");
                                    return value;
                                }
                            }
                        }
                    }
                }
            }
        }       
        //nomatch
        value[0]=new card("","");value[1]=new card("","");value[2]=new card("","");value[3]=new card("","");
        return value;
    }
}
