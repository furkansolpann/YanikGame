package frames;

import codes.game;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class tableJComponent extends JComponent implements MouseListener,MouseMotionListener{
    private game _game;
    private int width,height;
    
    public tableJComponent(game _game){
        this._game=_game;
        MYinitComponents();
    }
    
    private void MYinitComponents(){
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        width = getWidth();
        height = getHeight();
        g.setColor(Color.GREEN.darker().darker());
        g.fillRect(0,0,width,height);
        _game.paintGame(g);
    }
    
    @Override
    public void mousePressed(MouseEvent e){
        _game.mousePressed(e);
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        _game.mouseDragged(e);
    }
   
    @Override
    public void mouseReleased(MouseEvent e) {
        _game.mouseReleased(e);
        
    }
    
    @Override
    public void mouseClicked(MouseEvent e) { }
 
    @Override
    public void mouseExited(MouseEvent e) {}
    
    @Override
    public void mouseEntered(MouseEvent e){}
    
    @Override
    public void mouseMoved(MouseEvent e){}
}
