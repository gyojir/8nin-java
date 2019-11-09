package hachinin;

import java.util.ArrayList;

// ステージを管理する
public class Stage {
	Game world;

	ArrayList<Platform> platforms = new ArrayList<>();
	double lastEnd = 0.0; // 最後尾のプラットフォームの位置

	public Stage(Game world){
		this.world = world;

		// 一番最初の足場は固定
		double height = 200;
		double width = 2000;
		Platform p = world.addPlatform(new Vector2(0,height),new Vector2(width,height), -10);
		lastEnd = width;
		platforms.add(p);

		// ランダムで3つの足場を作成
		for(int i = 0; i < 3; i++){
			addPlatform();
		}

	}

	public void addPlatform(){
		double span = 80 + 50*(Math.random() * 2.0 - 1.0);
		double height = 200 + 40*(Math.random() * 2.0 - 1.0);
		double width = 600 + 300*(Math.random() * 2.0 - 1.0);
		Platform p = world.addPlatform(new Vector2(lastEnd + span, height),new Vector2(lastEnd + span + width,height), -10);
		lastEnd = lastEnd + span + width;
		platforms.add(p);
	}

	public void update(){
		// 最後尾の位置を更新
		lastEnd = 0.0;
		for( int i = 0; i < platforms.size(); ++i ){
			Platform p = platforms.get(i);
			double end = p.getLine().getEnd().x;
			if(lastEnd < end){
				lastEnd = end;
			}
		}

		// 画面からはみ出たら最後尾に移動
		for( int i = 0; i < platforms.size(); ++i ){
			Platform p = platforms.get(i);
			if(p.isOutOfScreen()){
				double span = 80 + 50*(Math.random() * 2.0 - 1.0);
				double height = 200 + 40*(Math.random() * 2.0 - 1.0);
				double width = 600 + 300*(Math.random() * 2.0 - 1.0);
				p.set(new Vector2(lastEnd + span, height),new Vector2(lastEnd + span + width,height));
				lastEnd = lastEnd + span + width;
			}
		}
	}

	// 壁にぶつかるベクトルを壁に沿わせる
	boolean restrictMove( Vector2 v,  Vector2 p ){
		Vector2 vCopy = v.clone();
		boolean hit = false;
		//第一ループ
		double t = Double.MAX_VALUE;
		int minI = -1;
		for( int i = 0; i < platforms.size(); ++i ){
			double tt = platforms.get(i).getLine().getIntersectionTime( vCopy, p );
			if( tt < t ){
				t = tt;
				minI = i;
			}
		}
		if( t >= 0.f && t <= 1.f ){//当たった
			platforms.get(minI).getLine().restrictMove( vCopy, p );
			hit = true;
		}

		v.set(vCopy);
		return hit;
	}

	boolean isHit(  Vector2 d,  Vector2 p ) {
		for( int i = 0; i < platforms.size(); ++i ){
			if( platforms.get(i).getLine().isIntersect( d, p ) ){
				return true;
			}
		}
		return false;
	}

}
