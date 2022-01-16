package makingProject;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Game extends Thread{
	private int ThreadDelay = 20;  // 전체 화면 렌더링 속도.. 0.02
	
	// 바닥위치 고정..통일하기
	private int floorY = 600;
	
	private Image backImage = new ImageIcon("images/winter.png").getImage();
	private Player player = new Player(200,600);
	
	// 이동. 점프
	private boolean isLeft = false;
	private boolean isRight = false;
	private boolean JumpUp = false;
	private boolean JumpDown = false;
	
	//종료
	private boolean isOver = false;
	
	// 입력 부분.. 키보드
	public void setLeft(boolean isLeft) {this.isLeft = isLeft;}
	public void setRight(boolean isRight) {this.isRight = isRight;}
	public void setJump(boolean JumpUp) {this.JumpUp = JumpUp;}
	
	// 블록 리스트..
	private int blockDelay = 30;
	private Block block;
	private String blockImageList[] = {"block01","block02","block03"};
	private ArrayList<Block> blockList = new ArrayList<>();
	private int blockCount = 0;
	
	private Heart heart;	
	private ArrayList<Heart> heartList = new ArrayList<>();
	
	private int scoreDelay = 50;
	private int scoreCount = 0;
	
	private Image endingImage = new ImageIcon("images/ending.jpg").getImage();
	
	public boolean isOver() {
		return isOver;
	}
	public void setOver(boolean isOver) {
		this.isOver = isOver;
	}
	// thread..
	public Game() {
		isOver = false;
		blockList.clear();
		blockCount = 0;
		scoreCount = 0;
		start();
	}
	
	public void run() {
		while(true) {
			try {
				Thread.sleep(ThreadDelay);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
			scoreCount++;
			blockCount++;
			keyProcess();
			blockProcess();
			makeBlock();
			makeHeart();
			heartProcess();
			scoreProcess();
		}
	}
	
	// 리스너 반응
	private void keyProcess() {
		if(isLeft) player.moveLeft();
		if(isRight) player.moveRight();
		if(JumpUp && player.getJumpY() == player.getJumpSet()) {
			JumpUp = false;
			JumpDown = true;
		} else if(JumpUp && player.getJumpY() < player.getJumpSet()) player.jumpUp();
		 else if(JumpDown && player.getJumpY() >= 0) player.jumpDown();
		 else if(JumpDown && player.getJumpY() == 0) JumpDown = false;
	}

	// 점수반영..
	private void scoreProcess() {
		if(scoreCount == scoreDelay) {
			player.setScore(player.getScore() + 50);
			scoreCount = 0;
		}
	}
	
	private void makeHeart() {
		if (Math.random() < 0.005) {
			heart = new Heart(1200, floorY);
			heartList.add(heart);
		}
	}
	
	public void heartProcess() {
		for (int i = 0; i < heartList.size(); i++) {
			heart = heartList.get(i);
			heart.moveLeft();
			if (hitBlock(new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getHeight()),
					new Rectangle(heart.getX(), heart.getY(), heart.getWidth(), heart.getHeight()))) {
				heartList.remove(heart);
				player.setHp(player.getHp() + heart.getHp());
				if(player.getHp() > 30) {
					player.setHp(30); // 30 체력최대치;;
					player.setScore(player.getScore() + 100); // 풀피에 하트먹으면 점수+.
				}
			}
			if (heart.getX()< 0 - 64) {
				heartList.remove(heart);
			}
		}
	}
	
	// 블록생성
	private void makeBlock() {
		if(blockCount % blockDelay == 0) {
			String blockImage = blockImageList[(int)(Math.random()*3)];
			block = new Block(1200,floorY,blockImage);
			blockList.add(block);
			block.moveLeft();	
		}
	}
	
	// 충돌ㅇ
	private boolean hitBlock(Rectangle rect01, Rectangle rect02) {
		return rect01.intersects(rect02);
	}
	
	// 블록 반응..충돌..
	private void blockProcess() {
//		System.out.println(blockList.size());
		for(int i=0;i<blockList.size();i++) {
			block = blockList.get(i);
			block.moveLeft();
			
			// 히트박스 너무큼.. 줄임..(양쪽 -5)
			if(hitBlock(
					new Rectangle(player.getX()+5,player.getY()+5,player.getWidth()-10,player.getHeight()-10), 
					new Rectangle(block.getX()+5,block.getY()+5,block.getWidth()-10,block.getHeight()-10))
					) {
				blockList.remove(block);
				player.setHp(player.getHp() - block.getDamage());
				if(player.getHp() <= 0) {
					isOver = true;
					interrupt();   // Thread종료
				}
			}
			
			if(block.getX() <= 0 - 64) {
				blockList.remove(block);
				if(player.getHp() <= 0) {
					isOver = true;
					interrupt();   // Thread종료
				}
			}
		}
	}
	
	public void drawAll(Graphics g){
		if(isOver) drawGameOver(g);
		else {
			drawBackground(g);
			drawPlayer(g);
			drawBlock(g);
			drawInfo(g);
			drawHeart(g);
		}
	}
	
	private void drawBackground(Graphics g){
		g.drawImage(backImage, 0,0, null);
	}	
	
	private void drawInfo(Graphics g){
		//hp label
		g.setColor(Color.DARK_GRAY);
		g.setFont(new Font("Arial",Font.BOLD,24));
		g.drawString("HP:" ,30,30);
		
		// 생명바.
		g.setColor(Color.GRAY);
		g.fillRect(80,15,300,15);
		g.setColor(Color.RED);
		g.fillRect(80,15,300*player.getHp()/30,15);
		
		// 스코어
		g.setColor(Color.DARK_GRAY);
		g.setFont(new Font("Arial",Font.BOLD,24));
		g.drawString("score:"+ player.getScore(),30,60);
		
	}
	
	private void drawPlayer(Graphics g){
		g.drawImage(player.getImage(),player.getX(),player.getY(), null);
	}	
	
	private void drawHeart(Graphics g){
		for(int i=0;i < heartList.size();i++) {
			heart = heartList.get(i);
			g.drawImage(heart.getImage(),heart.getX(),heart.getY(), null);
		}
	}
	
	private void drawBlock(Graphics g){
		for(int i=0;i<blockList.size();i++) {
			block = blockList.get(i);
			g.drawImage(block.getImage(),block.getX(),block.getY(), null);
		}
	}	
	
	private void drawGameOver(Graphics g){
		g.drawImage(endingImage,0,0,null);
		g.setColor(Color.white);
		g.setFont(new Font("Arial",Font.BOLD,52));
		g.drawString("GAME OVER.." ,410,300);
		g.setFont(new Font("Arial",Font.BOLD,42));
		g.drawString("Score : "+player.getScore() ,450,400);
		
	}	
}
