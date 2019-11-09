package hachinin;

// エンティティを表す抽象クラス
public abstract class Entity {
	Game world;

	protected Vector2 pos = new Vector2(); // 座標
	protected Vector2 vel = new Vector2(); // 速度

	public Entity(Game world){
		this.world = world;
	}

	public Vector2 getPos(){
		return pos.clone();
	}
	public void setPos(Vector2 p){
		this.pos.set(p);
	}

	abstract public void update();

	abstract public void draw();
}
