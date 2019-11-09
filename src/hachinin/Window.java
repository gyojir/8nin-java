package hachinin;

import java.awt.Color;
import java.awt.Insets;
import java.awt.image.BufferStrategy;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

public class Window {
	JFrame frame;
	BufferStrategy bstrategy;

	public final int width=840;
	public final int height=360;

	Window () {
		frame = new JFrame("8nin");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(new Color(225,255,255));
		frame.setResizable(false);
		frame.setVisible(true);
		Insets insets = frame.getInsets();
		frame.setSize(width+insets.left+insets.right,
				height+insets.top+insets.bottom);
		frame.setLocationRelativeTo(null);
		frame.setIgnoreRepaint(true);
		frame.createBufferStrategy(2);
		bstrategy = frame.getBufferStrategy();

		// シングルトン生成
		GraphicsManager.create(frame);
		frame.addKeyListener(Keyboard.getInstance());
	}

	// 派生クラスのコンストラクタ後に呼び出すため分離
	public void run(){
		Timer t = new Timer();
		t.schedule(new MyTimerTask(), 10, 30);
	}

	// 更新 これをオーバーライドしてメインループの処理を書く
	public void update(){
	}

	class MyTimerTask extends TimerTask {
		public void run() {
			Keyboard kb = Keyboard.getInstance();
			GraphicsManager gm = GraphicsManager.getInstance();

			// 描画開始
			gm.beginDraw();

			// 更新
			update();

			// 描画終了
			gm.endDraw();

			kb.update();
		}
	}
}
