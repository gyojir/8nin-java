package hachinin;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Entity{
	BufferedImage image;

	int id = 0;									// プレイヤーID 0～3
	double jumpPower = 0.0;						// ジャンプの加速度
	boolean isGrounded = false;				// 接地フラグ
	boolean dead = false;						// 死亡フラグ
	protected Vector2 move = new Vector2();	// 移動ベクトル
	Vector2 foot;								// 足の相対位置
	Vector2 center;								// 中心の相対位置
	Keyboard.Key jumpKey = Keyboard.Key.KEY_A; // ジャンプ時に用いるキー

	public Player(Game world,int id, String filename, int x, int y){
		super(world);

		// 画像読み込み
		try {
			image = ImageIO.read(getClass().getResource(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// idに応じてキーを割り当てる
		switch(id){
		case 0: jumpKey = Keyboard.Key.KEY_A; break;
		case 1: jumpKey = Keyboard.Key.KEY_S; break;
		case 2: jumpKey = Keyboard.Key.KEY_D; break;
		case 3: jumpKey = Keyboard.Key.KEY_F; break;
		case 4: jumpKey = Keyboard.Key.KEY_J; break;
		case 5: jumpKey = Keyboard.Key.KEY_K; break;
		case 6: jumpKey = Keyboard.Key.KEY_L; break;
		case 7: jumpKey = Keyboard.Key.KEY_SEMICOLON; break;
		}

		this.id = id;
		this.pos.set(x,y);
		this.foot = new Vector2(image.getWidth()/2, image.getHeight());
		this.center = new Vector2(image.getWidth()/2, image.getHeight()/2);
	}

	// 更新
	public void update(){
		Keyboard kb = Keyboard.getInstance();

		move.set(0, 0);

		if(dead){
			return;
		}

		// 接地していてジャンプをしようとしていなければjumpPowerを0に
		if (isGrounded && jumpPower < 0) {
			jumpPower = 0.0;
		}
		// 接地中にジャンプボタンが押されたらジャンプ
		if(kb.isTriggered(jumpKey) && isGrounded){
			jumpPower = 15.0;
		}

		// 画面外に出たら死亡
		if(pos.y > world.height){
			dead = true;
		}

		// 重力
		move.y -= jumpPower;
		jumpPower -= 1.5;

	}

	// 描画
	public void draw(){
		GraphicsManager gm = GraphicsManager.getInstance();

		if(dead){
			return;
		}

		// 位置を更新
		pos.setAdd(move);

		// 描画
		gm.drawImage(image, (int)pos.x, (int)pos.y);
	}

	public Vector2 getMove(){
		return move.clone();
	}

	public void setMove(Vector2 v){
		this.move = v.clone();
	}

	public Vector2 getFootPos(){
		return Vector2.add(pos, foot);
	}

	public void setGrounded(boolean b){
		this.isGrounded = b;
	}

	public boolean isDead(){
		return dead;
	}
}
