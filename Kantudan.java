import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Kantudan implements ActionListener
{
    private Timer timer;
    private int TimerTime;
    private boolean nowTransfixion;

    public Kantudan()
    {
	    this.timer = new Timer(3000, this);
    }
    
    public void actionPerformed(ActionEvent e)
    {
        this.nowTransfixion = false;
        this.timer.stop();
    }

    public void setNowTransfixion(boolean p){
        this.nowTransfixion = p;
    }

    public boolean getNowTransfixion(){
        return this.nowTransfixion;
    }

    public void StartTimer(){
        this.timer.start();
    }
}