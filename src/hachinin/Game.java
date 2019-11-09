package hachinin;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Game extends Window {
	BufferedImage  backImage;						// 背景画像
	ArrayList<Entity> entities = new ArrayList<>();	// エンティティ―のリスト
	ArrayList<Player> players = new ArrayList<>();	// プレイヤーのリスト
	Stage stage = new Stage(this);					// ステージ
	final int playerNum = 8;						// プレイヤーキャラクター数
	boolean gameover = false;						// ゲームオーバーフラグ
	int highScore = 0;								// ハイスコア
	int score = 0;									// スコア
	int count = 0;									// フレームカウント用変数
	State state = State.START;						// 状態変数
	String highScoreFilePath = ".\\score.txt"; 		// ハイスコアファイルパス

	enum State{
		START,
		PLAY,
		GAMEOVER,
	}

	public Game(){
		super();
		loadScore();
	}

	// 初期化
	public void init(){

		stage = null;
		entities.clear();

		stage = new Stage(this);

		// プレイヤーを追加
		for(int i = 0; i < playerNum; i++){
			addPlayer(i, "img.png", i * 50 + ((i>3)?50:0), 0);
		}

		score = 0;
		count = 0;
	}

	public Player addPlayer(int id, String filename, int x, int y){
		Player p = new Player(this, id, filename, x, y);
		players.add(p);
		entities.add(p);
		return p;
	}

	public Platform addPlatform(Vector2 begin, Vector2 end, double v){
		Platform p = new Platform(this, begin,end, v);
		entities.add(p);
		return p;
	}

	@Override
	public void update(){
		Keyboard kb = Keyboard.getInstance();
		GraphicsManager gm = GraphicsManager.getInstance();

		// 生きているプレイヤーキャラクターの数
		int aliveNum = 0;

		// --------------------更新処理----------------------
		switch(state){
		case START:
			break;
		case PLAY:
		case GAMEOVER:
			// 全エンティティを更新
			for(int i = 0; i < entities.size(); i++){
				Entity e = entities.get(i);
				e.update();
			}
			// ステージを更新
			stage.update();


			// 衝突判定(プレイヤーキャラクターのみ)
			for(int i = 0; i < players.size(); i++){
				Player p = players.get(i);

				Vector2 move = p.getMove();	 // 移動取得
				Vector2 pos = p.getFootPos(); // 足の位置
				stage.restrictMove(move, pos); // 移動を制限
				boolean grounded = stage.isHit(new Vector2(0,15),pos); // 足から20以内に床があれば接地
				//System.out.println(grounded);
				p.setMove(move);
				p.setGrounded(grounded);

				// まだ生きてる
				if(!p.isDead()){
					aliveNum++;

					if(count == 30){
						AdditionalScore a = new AdditionalScore(this, 10, (int)p.getPos().x, (int)p.getPos().y);
						entities.add(a);
					}
				}
			}
			// 30フレームごとにスコア計算
			if(count == 30){
				count = 0;
				score += 10 * aliveNum;

				if(score > highScore){
					highScore = score;
				}
			}
			break;
		}

		Iterator<Entity> it = entities.iterator();
		while(it.hasNext()){
			Entity e = it.next();
			if(e instanceof AdditionalScore){
				AdditionalScore score = (AdditionalScore)e;
				if(score.isFinished()){
					it.remove();
				}
			}
		}

		// -------------------描画--------------------------------
		// 全エンティティを描画
		for(int i = 0; i < entities.size(); i++){
			Entity e = entities.get(i);
			e.draw();
		}

		// スコア表示
		gm.drawString("SCORE " + score, width*2/6, 20);
		gm.drawString("HIGH SCORE " + highScore, width*4/6, 20);


		// --------------------状態遷移-------------------------------
		switch(state){
		case START:
			gm.drawString("PRESS START", width/2, height/2);
			if(kb.isTriggered(Keyboard.Key.KEY_SPACE)){
				state = State.PLAY;
				init();
			}
			break;
		case PLAY:
			// 全員死んだらゲームオーバー
			if(aliveNum == 0){
				state = State.GAMEOVER;

				saveScore();
			}
			break;
		case GAMEOVER:
			gm.drawString("GAME OVER", width/2, height/2);
			if(kb.isTriggered(Keyboard.Key.KEY_SPACE)){
				state = State.START;
			}
			break;
		}


		count++;
	}

	private void loadScore(){
		File scoreFile = new File(highScoreFilePath);

		try {
			if(scoreFile.createNewFile()){
				highScore = 0;
			}else{
				BufferedReader br = new BufferedReader( new FileReader(highScoreFilePath));

				String buf = br.readLine();
				if(buf != null){
					highScore = Integer.parseInt(buf);
				}else{
					highScore = 0;
				}
				br.close();
			}
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	private void saveScore(){
		try {
			FileWriter fw = new FileWriter(new File(highScoreFilePath));
			fw.write(Integer.toString(highScore));
	        fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
