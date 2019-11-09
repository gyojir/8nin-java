package hachinin;

public class AdditionalScore extends Entity {
	private int score;
	private int lifeTime = 10;
	private int time = 0;

	public AdditionalScore(Game world, int score, int x, int y) {
		super(world);
		this.score = score;
		pos.set(x + 10, y);
	}

	@Override
	public void update() {
		pos.y -= 1;
		time += 1;

	}

	@Override
	public void draw() {
		GraphicsManager gm = GraphicsManager.getInstance();
		gm.drawString("+"+Integer.toString(score), (int)pos.x, (int)pos.y);

	}

	public boolean isFinished(){
		return time > lifeTime;
	}

}
