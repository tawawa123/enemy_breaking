import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.Random;
import java.util.ArrayList;

public class Wave1 implements ActionListener
{
    private Timer timer;
    private int rX, rY;
    //エネミー１の座標
    private ArrayList<Integer> e1x_right;
	private ArrayList<Integer> e1x_left;
	private ArrayList<Integer> e1y_right;
	private ArrayList<Integer> e1y_left;
    private boolean ballet;
    private boolean wave1_EnemyPop;
    
    public Wave1()
    {  
        this.e1x_right = new ArrayList<>();
        this.e1x_left = new ArrayList<>();
        this.e1y_right = new ArrayList<>();
        this.e1y_left = new ArrayList<>();

	    this.timer = new Timer(1500, this);
	    this.timer.start();
    }
    
    public void actionPerformed(ActionEvent e)
    {
        Random r = new Random();

        this.rX = r.nextInt(2) * 650 - 50;
        this.rY = r.nextInt(200) + 50;

        if(this.rX <= 0){
            this.e1x_left.add(this.rX);
            this.e1y_left.add(this.rY);
        } else{
            this.e1x_right.add(this.rX);
            this.e1y_right.add(this.rY);
        }

        this.wave1_EnemyPop = true;
        this.ballet = true;
    }

    public ArrayList getE1X_RIGHT(){
		return this.e1x_right;
	}

    public ArrayList getE1X_LEFT(){
		return this.e1x_left;
	}

	public ArrayList getE1Y_RIGHT(){
		return this.e1y_right;
	}

    public ArrayList getE1Y_LEFT(){
		return this.e1y_left;
	}

	public boolean getWave1EnemyPop(){
		return this.wave1_EnemyPop;
	}

    public void setWave1EnemyPop(boolean p){
        this.wave1_EnemyPop = p;
    }

    public boolean getBallet(){
		return this.wave1_EnemyPop;
	}

    public void setBallet(boolean p){
        this.wave1_EnemyPop = p;
    }
}