package makingProject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GamePanel extends JPanel{
	private Image bufferImage;
	private Graphics screenGraphics;
	private Game game;
	private String isState = "start";
	
	private Image startImage = new ImageIcon("images/winter.png").getImage();
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(1200,800));
		this.setOpaque(true);
		this.setBackground(Color.BLACK);
		gameInit();
	}
	
	private void gameInit() {
		game = new Game();
		this.setFocusable(true);
		this.requestFocus();
		this.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT : 
					game.setLeft(false);
					break;
				case KeyEvent.VK_RIGHT :
					game.setRight(false);
					break;
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
//				System.out.println(e.getKeyCode());
				switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT : 
					game.setLeft(true);
					break;
				case KeyEvent.VK_RIGHT :
					game.setRight(true);
					break;
				case KeyEvent.VK_SPACE :
					game.setJump(true);
					break;
				case KeyEvent.VK_ESCAPE :
					System.exit(0);
					break;
				case KeyEvent.VK_R :
					if(game.isOver()) isState = "start";
//					game = new Game();
//					game.setOver(false);
					break;
				case KeyEvent.VK_ENTER :
					if(isState.equals("start")) {
						startGame();
					}
					break;
				}
			}
		});
	}

	private void startGame() {
		isState="loading";Timer loadingTimer = new Timer();
		TimerTask loadingTimerTask = new TimerTask() {
			@Override
			public void run() {
				isState = "gaming";
				game = new Game();
			}
		};
		loadingTimer.schedule(loadingTimerTask, 1500);
	}
	
	public void paintComponent(Graphics g) {
		bufferImage = this.createImage(1800,900);
		screenGraphics = bufferImage.getGraphics();
		screenCapture(screenGraphics);
		g.drawImage(bufferImage, 0, 0, null); //graphics 가 한번에 모두 그리기
	}
	
	public void screenCapture(Graphics g) {
		if(isState.equals("start")) {
			g.drawImage(startImage, 0, 0, null);
			
			g.setColor(Color.DARK_GRAY);
			g.setFont(new Font("Arial",Font.BOLD,48));
			g.drawString("Penguin Jump!" ,410,400);
			g.setFont(new Font("맑은 고딕",Font.BOLD,36));
			g.drawString("시작하려면 'enter키'를 누르세요!" ,290,500);
			
		} else if(isState.equals("loading")) {
			g.drawImage(startImage, 0, 0, null);
			g.setFont(new Font("맑은 고딕",Font.BOLD,36));
			g.drawString("장애물들을 피해 최대한 멀리 가세요.." ,250,300);
			g.drawString("이동 : 방향키 ← / →",380,400);
			g.drawString("점프 : space_bar" ,400,500);
		}
		 else {
			 game.drawAll(g);
		 }
		repaint();
	}
}
