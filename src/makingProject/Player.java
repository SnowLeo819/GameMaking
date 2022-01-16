package makingProject;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Player {
	//팽귄그림.
	//점프모션?
	//좌표
	private Image image = new ImageIcon("images/penguin.png").getImage();
	private int x, y;
	private int width = image.getWidth(null);
	private int height = image.getHeight(null);
	private int speedX = 10;
	private int speedY = 10;
	private int jumpY = 0;
	private int jumpSet = 12;
	private int hp = 30;
	private int score = 0;
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getHp() {
		return hp;
	}
	
	public void setHp(int hp) {
		this.hp = hp;
	}

	// jumpY & jumpSet 활용
	// jump 시 최대치 고정..
	public int getJumpY() {
		return jumpY;
	}

	public int getJumpSet() {
		return jumpSet;
	}

	public Image getImage() {
		return image;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Player(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void moveLeft() {
		x -= speedX;
	}
	public void moveRight() {
		x += speedX;
	}	
	public void jumpUp() {
			y -= speedY;
			jumpY++;
	}	
	public void jumpDown() {
			y += speedY;
			jumpY--;
	}	

}
