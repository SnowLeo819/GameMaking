package makingProject;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Heart {
	private Image image = new ImageIcon("images/heart.png").getImage();
	private int x;
	private int y;
	private int width = image.getWidth(null);
	private int height = image.getHeight(null);
	private int speedX = 10;
	private int hp = 15;

	public int getHp() {
		return hp;
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

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Heart(int x,int y) {
		this.x = x;
		this.y = y;
	}
	
	public void moveLeft() {
		x -= speedX;
	}

}
