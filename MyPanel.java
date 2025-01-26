import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.ArrayList;

public class MyPanel extends JPanel implements KeyListener, MouseListener
{
	private Wave1 wave1;
	private Kantudan kantudan;

	//キー入力用変数
    private boolean keyLeft, keyRight, keySpace, keyQ;
	//バーの座標、移動速度
    private double px, py, pvx, pvy;
	//ボールの座標、移動速度
	private double boalx, boaly, boalvx, boalvy;
	//Enemy1たちの座標、移動速度
	private ArrayList<Integer> e1x_right;
	private ArrayList<Integer> e1x_left;
	private ArrayList<Integer> e1y_right;
	private ArrayList<Integer> e1y_left;
	private int e1vx;
	//Enemy1が飛ばす玉
	private ArrayList<Integer> e1x_ballet_right;
	private ArrayList<Integer> e1x_ballet_left;
	private ArrayList<Integer> e1y_ballet_right;
	private ArrayList<Integer> e1y_ballet_left;
	//敵の画像
	private Image enemy1, enemy2;
	//体力
	private int lineHP = 3;
	private int playerHP = 10;
	//スコア
	private int score = 0;
	private Font font;

	private boolean nowSpace;
    
    public MyPanel()
    {
		this.wave1 = new Wave1();
		this.kantudan = new Kantudan();

		this.setPreferredSize(new Dimension(640, 480));
		this.setBackground(Color.black);
	
		this.addKeyListener(this);
		this.addMouseListener(this);
	
		//バー
		this.px = 530 / 2;
		this.py = 480 / 1.5;
		this.pvx = 0;
		this.pvy = 0;

		//ボール
		this.boalx = 600 / 2;
		this.boaly = 480 / 2;
		this.boalvx = 4;
		this.boalvy = -4;

		//Enemy1
		this.e1x_left = new ArrayList<>();
		this.e1x_right = new ArrayList<>();
		this.e1y_right = new ArrayList<>();
		this.e1y_left = new ArrayList<>();
		this.e1vx = 2;

		//Enemy1の弾
		this.e1x_ballet_left = new ArrayList<>();
		this.e1x_ballet_right = new ArrayList<>();
		this.e1y_ballet_right = new ArrayList<>();
		this.e1y_ballet_left = new ArrayList<>();
	
		this.requestFocusInWindow();
    }
    
    public void paintComponent(Graphics g)
    {
		super.paintComponent(g);

		if (keySpace == true) {
			this.nowSpace = true;
		}
		else{
			this.nowSpace = false;
		}
	
		if (keyLeft == true) {
	    	this.px -= 4;
		}
		if (keyRight == true) {
	    	this.px += 4;
		}
		if (keySpace == true) {
	    	this.pvx = 0;
	    	this.pvy = 0;
		}
	
		//バーの移動
		this.px = this.px + this.pvx;
		if (this.px < 0) {
	    	this.px = 0;
		} else if (this.px > 640 - 100) {
	    	this.px = 640 - 100;
		}

		//ボールの移動
		if(this.kantudan.getNowTransfixion()){
			this.boalx += this.boalvx * 1.3;
			this.boaly += this.boalvy * 1.3;
		}
		else{
			this.boalx += this.boalvx;
			this.boaly += this.boalvy;
		}

		//壁への当たり判定
		if(this.boalx < 0) {
			this.boalx = 0;
			this.boalvx = -this.boalvx;
		}else if(this.boalx > 640 - 30){
			this.boalx = 640 - 30;
			this.boalvx = -this.boalvx;
		}

		//ラインについての当たり判定と、LineHPの管理
		if(this.boaly >= this.py + 50 && this.lineHP >= 1){
			this.lineHP -= 1;
			this.boalvy = -this.boalvy;
		}
		else if(this.boaly < 0) {
			this.boaly = 0;
			this.boalvy = -this.boalvy;
		}else if(this.boaly > 480 - 30){
			this.boaly = 480 - 30;
			//GameOver処理
			g.setColor(new Color(64, 128, 64));
			this.font = new Font("Arial", Font.BOLD, 30);
			g.setFont(font);
			g.drawString("GameOver", 230 , 200);
		}

		//バーについての当たり判定
		if(this.boaly >= this.py - 30 && this.boaly <= this.py - 27 && this.px - 30.0 <= this.boalx && this.boalx <= this.px + 100.0
			|| this.boaly <= this.py + 30 && this.boaly >= this.py + 27 && this.px - 30.0 <= this.boalx && this.boalx <= this.px + 100.0){
			this.boalvy = -this.boalvy;

			if(this.nowSpace){
				this.kantudan.StartTimer();
				this.kantudan.setNowTransfixion(true);
			}
		}
		else if(this.boaly <= this.py + 30 && this.boaly >= this.py - 27 && this.px + 97.0 <= this.boalx && this.boalx <= this.px + 100.0
			|| this.boaly <= this.py + 30 && this.boaly >= this.py - 27 && this.px - 35.0 >= this.boalx && this.boalx >= this.px - 40.0){
			this.boalvx = -this.boalvx;

			if(this.nowSpace){
				this.kantudan.StartTimer();
				this.kantudan.setNowTransfixion(true);
			}
		}

		//図形描画
		//HP表示とかの文字
		g.setColor(new Color(64, 128, 64));
		Font font2 = new Font("Arial", Font.PLAIN, 18);
		g.setFont(font2);
		g.drawString("LINE HP: " + this.lineHP, 30, 20);
		g.drawString("Player HP: " + this.playerHP, 30, 40);
		Font font3 = new Font("Arial", Font.PLAIN, 24);
		g.setFont(font3);
		g.drawString("Score: " + this.score, 500, 40);

		//バー
		if(!this.nowSpace){
			g.setColor(new Color(64, 128, 64));
			g.fillRect((int)this.px, (int)this.py, 100, 20);
			g.setColor(new Color(64, 255, 64));
			g.drawRect((int)this.px, (int)this.py, 100, 20);
		}
		else{
			g.setColor(new Color(64, 64, 128));
			g.fillRect((int)this.px, (int)this.py, 100, 20);
			g.setColor(new Color(64, 64, 255));
			g.drawRect((int)this.px, (int)this.py, 100, 20);
		}

		//ボール
		if(!this.kantudan.getNowTransfixion()){
			g.setColor(new Color(64, 128, 64));
			g.fillOval((int)this.boalx, (int)this.boaly, 30, 30);
			g.setColor(new Color(64, 255, 64));
			g.drawOval((int)this.boalx, (int)this.boaly, 30, 30);
		}
		else{
			g.setColor(new Color(64, 64, 128));
			g.fillOval((int)this.boalx, (int)this.boaly, 30, 30);
			g.setColor(new Color(64, 64, 255));
			g.drawOval((int)this.boalx, (int)this.boaly, 30, 30);
		}

		//ライン
		if(this.lineHP > 1){
			g.setColor(new Color(64, 128, 64));
			g.drawLine(0, (int)this.py + 80, 640, (int)this.py + 80);
		}
		else if(this.lineHP == 1){
			int startX = 0;
			int endX = startX + 5;
			while(endX < 640) {
				g.setColor(new Color(64, 128, 64));
				g.drawLine(startX, (int)this.py + 80, endX, (int)this.py + 80);
				startX = endX + 5;
				endX = startX + 5;
			}
		}

		//エネミー1
		if(this.wave1.getWave1EnemyPop()){

			//Enemy1の座標にWave1.javaで作ったランダムの値を代入
			this.e1x_right = this.wave1.getE1X_RIGHT();
			this.e1x_left = this.wave1.getE1X_LEFT();
			this.e1y_right = this.wave1.getE1Y_RIGHT();
			this.e1y_left = this.wave1.getE1Y_LEFT();

			for(int i = 0; i <= this.e1y_right.size()-1; i++){
				this.e1x_ballet_right.add(this.e1x_right.get(i));
				this.e1y_ballet_right.add(this.e1y_right.get(i));
			}

			for(int i = 0; i <= this.e1y_left.size()-1; i++){
				this.e1x_ballet_left.add(this.e1x_left.get(i));
				this.e1y_ballet_left.add(this.e1y_left.get(i));
			}

			for(int i = 0; i <= this.e1y_right.size()-1; i++){
				//Enemy1の右側のやつを描写
				g.drawImage(this.enemy1, this.e1x_right.get(i), this.e1y_right.get(i), this);
				this.e1x_right.set(i, this.e1x_right.get(i) - this.e1vx);
				//それの弾を描写
				g.setColor(new Color(64, 255, 64));
				g.drawRect(this.e1x_ballet_right.get(i) + 23, this.e1y_ballet_right.get(i) + 30, 5, 5);
				this.e1y_ballet_right.set(i, this.e1y_ballet_right.get(i) + 1);

				//当たり判定
				if(this.boalx + 30 >= this.e1x_right.get(i) && this.boalx <= this.e1x_right.get(i) + 50 && this.boaly <= this.e1y_right.get(i) + 30 && this.boaly >= this.e1y_right.get(i) + 27
				|| this.boalx + 30 >= this.e1x_right.get(i) && this.boalx <= this.e1x_right.get(i) + 50 && this.boaly + 27 <= this.e1y_right.get(i) && this.boaly + 30 >= this.e1y_right.get(i)){
					this.e1x_right.remove(i);
					this.e1y_right.remove(i);
					i -= 1;
					this.score += 20;
					if(!this.kantudan.getNowTransfixion()){
						this.boalvy = -this.boalvy;
					}
					else{

					}
				}

				if(this.boalx >= this.e1x_right.get(i) + 50 && this.boalx <= this.e1x_right.get(i) + 53 && this.boaly + 30 >= this.e1y_right.get(i) && this.boaly <= this.e1y_right.get(i) + 30
				|| this.boalx + 33 >= this.e1x_right.get(i) && this.boalx + 30 <= this.e1x_right.get(i) && this.boaly + 30 >= this.e1y_right.get(i) && this.boaly <= this.e1y_right.get(i) + 30){
					this.e1x_right.remove(i);
					this.e1y_right.remove(i);
					i -= 1;
					this.score += 20;
					if(!this.kantudan.getNowTransfixion()){
						this.boalvx = -this.boalvx;
					}
					else{

					}
				}

				if(this.e1x_ballet_right.get(i) >= (int)this.px - 23 && this.e1x_ballet_right.get(i) <= (int)this.px + 77 && this.e1y_ballet_right.get(i) >= (int)this.py - 30 && this.e1y_ballet_right.get(i) <= (int)this.py - 10){
					this.playerHP -= 1;
					this.e1x_ballet_right.remove(i);
					this.e1y_ballet_right.remove(i);
				}
			}

			for(int i = 0; i <= this.e1y_left.size()-1; i++){
				//Enemy1の左側のやつを描写
				g.drawImage(this.enemy1, this.e1x_left.get(i), this.e1y_left.get(i), this);
				this.e1x_left.set(i, this.e1x_left.get(i) + this.e1vx);
				//それの弾を描写
				g.setColor(new Color(64, 255, 64));
				g.drawRect(this.e1x_ballet_left.get(i) + 23, this.e1y_ballet_left.get(i) + 30, 5, 5);
				this.e1y_ballet_left.set(i, this.e1y_ballet_left.get(i) + 1);

				//当たり判定
				if(this.boalx + 30 >= this.e1x_left.get(i) && this.boalx <= this.e1x_left.get(i) + 50 && this.boaly <= this.e1y_left.get(i) + 30 && this.boaly >= this.e1y_left.get(i) + 27
				|| this.boalx + 30 >= this.e1x_left.get(i) && this.boalx <= this.e1x_left.get(i) + 50 && this.boaly + 27 <= this.e1y_left.get(i) && this.boaly + 30 >= this.e1y_left.get(i)){
					this.e1x_left.remove(i);
					this.e1y_left.remove(i);
					i -= 1;
					this.score += 20;
					if(!this.kantudan.getNowTransfixion()){
						this.boalvy = -this.boalvy;
					}
					else{

					}
				}

				if(this.boalx >= this.e1x_left.get(i) + 50 && this.boalx <= this.e1x_left.get(i) + 53 && this.boaly + 30 >= this.e1y_left.get(i) && this.boaly <= this.e1y_left.get(i) + 30
				|| this.boalx + 33 >= this.e1x_left.get(i) && this.boalx + 30 <= this.e1x_left.get(i) && this.boaly + 30 >= this.e1y_left.get(i) && this.boaly <= this.e1y_left.get(i) + 30){
					this.e1x_left.remove(i);
					this.e1y_left.remove(i);
					i -= 1;
					this.boalvx = -this.boalvx;
					this.score += 20;
					if(!this.kantudan.getNowTransfixion()){
						this.boalvx = -this.boalvx;
					}
					else{

					}
				}

				if(this.e1x_ballet_left.get(i) >= (int)this.px - 23 && this.e1x_ballet_left.get(i) <= (int)this.px + 77 && this.e1y_ballet_left.get(i) >= (int)this.py - 30 && this.e1y_ballet_left.get(i) <= (int)this.py - 10){
					this.playerHP -= 1;
					this.e1x_ballet_left.remove(i);
					this.e1y_ballet_left.remove(i);
				}
			}
			
			if(this.playerHP == 0){
				g.setColor(new Color(64, 128, 64));
				g.setFont(this.font);
				g.drawString("GameOver", 230 , 200);
			}
			this.e1x_ballet_right.clear();
			this.e1x_ballet_left.clear();
		}
    }

	//Enemy1の画像を持ってくる
	public void setEnemy1(Image enemy1){
		this.enemy1 = enemy1;
	}

	//Enemy2の画像を持ってくる
	public void setEnemy2(Image enemy2){
		this.enemy2 = enemy2;
	}
    
	//キーを押したとき
    public void keyPressed(KeyEvent e)
    {
		int code = e.getKeyCode();
		switch (code) {
			case KeyEvent.VK_LEFT:  this.keyLeft  = true; break;
			case KeyEvent.VK_RIGHT: this.keyRight = true; break;
			case KeyEvent.VK_SPACE: this.keySpace = true; break;
			case KeyEvent.VK_Q:     System.exit(0);  break;
			default:                                 break;
		}
	}
    
	//キーを離したとき
    public void keyReleased(KeyEvent e)
    {
		int code = e.getKeyCode();
		switch (code) {
			case KeyEvent.VK_LEFT:  this.keyLeft  = false; break;
			case KeyEvent.VK_RIGHT: this.keyRight = false; break;
			case KeyEvent.VK_SPACE: this.keySpace = false; break;
			default:                                  break;
		}
	}
    
    public void keyTyped(KeyEvent e) { }
    public void mousePressed(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { requestFocusInWindow(); }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
}