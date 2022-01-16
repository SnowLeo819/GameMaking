package makingProject;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Block {
	
	private Image image;
	private int x;
	private int y;
	private int width;
	private int height;
	private int speedX = 30;
	private int damage = 10;

	// 생성 주기는 어떻게..?  , 고정? 랜덤?
	
	public int getDamage() {
		return damage;
	}

	public Block(int x, int y, String blockImage) {
		this.x = x;
		this.y = y;
		this.image = new ImageIcon("images/"+blockImage+".png").getImage();
		this.width = image.getWidth(null);
		this.height = image.getHeight(null);
		this.speedX = (int)(Math.random()*15+10);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
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

	public void moveLeft() {
		x -= speedX;
	}
}
