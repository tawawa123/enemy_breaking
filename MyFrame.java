import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class MyFrame extends JFrame implements ActionListener
{
    private MyPanel mp;
    private Wave1 wave1;
    private Timer timer;
    
    public MyFrame()
    {
	    super("KeyTest1, 2021 Ver.");
        
        Toolkit tk = Toolkit.getDefaultToolkit();
        Image enemy1 = tk.getImage("Enemy1.png");
        Image enemy2 = tk.getImage("Enemy2.png");
        
	    this.mp = new MyPanel();
        this.mp.setEnemy1(enemy1);
        this.mp.setEnemy2(enemy2);

        this.wave1 = new Wave1();

	    getContentPane().add(this.mp);
	
	    this.timer = new Timer(15, this);
	    this.timer.start();
    }
    
    public void actionPerformed(ActionEvent e)
    {
	    this.mp.repaint();
	    Toolkit.getDefaultToolkit().sync();
    }
    
    public static void main(String[] args)
    {
	    MyFrame frame = new MyFrame();
	    frame.setSize(640, 480);
	    frame.setLocation(200, 200);
	    frame.setVisible(true);
    }
}