package hachinin;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

// 描画を管理するシングルトンのクラス
public class GraphicsManager {
	private static GraphicsManager instance;

	JFrame frame;
	BufferStrategy bstrategy;
	Graphics g;

	private GraphicsManager(JFrame frame){
		this.frame = frame;
		bstrategy = frame.getBufferStrategy();
	}

	public static GraphicsManager getInstance(){
		return instance;
	}

	public static void create(JFrame frame){
		assert instance == null: "GraphicsManagerはシングルトンです";
		instance = new GraphicsManager(frame);
	}

	// 描画開始時に呼ぶ
	public Graphics beginDraw(){
		g = bstrategy.getDrawGraphics();
		clear();
		if (bstrategy.contentsLost() == false) {
			Insets insets = frame.getInsets();
			g.translate(insets.left, insets.top);
		}
		return g;
	}

	// 描画終了時に呼ぶ
	public void endDraw(){
		bstrategy.show();
		g.dispose();
	}

	// 消去
	public void clear(){
		g.clearRect(0,0,frame.getWidth(),frame.getHeight());
	}

	public void drawString(String str, int x, int y){
		g.setFont(new Font("SansSerif",Font.BOLD,20));

		int strW = g.getFontMetrics().stringWidth(str);

		g.drawString(str, x - strW/2, y );
	}

	// 画像描画
	public void drawImage(Image img, int x, int y){
		g.drawImage(img, x, y, frame);
	}

	// 線分描画
	public void drawLine(Line line, Color c){
		Vector2 a = line.getBegin();
		Vector2 b = line.getEnd();
		g.setColor(c);
		g.drawLine((int)a.x, (int)a.y, (int)b.x, (int)b.y);
	}
}
