package codes.classes;

public class deck {
    private String[] ranks={ "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
    private String[] suits={ "hearts", "spades", "diamonds", "clubs" }; 
    
    private card[] allcard=new card[106];
    private int count=0;
      
    public deck(){
        int rand;
        for(int k=0;k<2;k++){
            for(int i=0;i<4;i++)
                for(int j=0;j<13;j++){
                    allcard[count++]=new card(suits[i],ranks[j]);    
                }
            allcard[count++]=new card("joker","j");
        }
        count--;
         
        for(int i=count;0<=i;i--){
            rand=(int)(Math.random()*i);
            card temp = allcard[i];
            allcard[i]=allcard[rand];
            allcard[rand]=temp;
        }
            
    }
    
    public int getCardCount(){
        return count+1;
    }
    
    public card drawCard(){
        if(0<=count)
            return allcard[count--];
        else
            return null;
    }
    
     public card getTop(){
        if(0<=count)
            return allcard[count];
        else
            return allcard[0];
    }
    
    
}
