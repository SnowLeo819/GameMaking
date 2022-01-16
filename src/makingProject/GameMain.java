package makingProject;

import javax.swing.JFrame;

public class GameMain extends JFrame{
	
	public GameMain() {
		this.setTitle("Jump Penguin");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		GamePanel gamePanel = new GamePanel();
		this.setContentPane(gamePanel);
		
		this.setVisible(true);
		this.pack();
	}
	
	public static void main(String[] args) {
		new GameMain();
	}
}
