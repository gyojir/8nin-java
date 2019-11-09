package hachinin;

import java.awt.Color;

public class Platform extends Entity {
	Line line;

	public Platform(Game world, Vector2 begin, Vector2 end, double v) {
		super(world);

		line = new Line(begin,end);
		this.vel.x = v;
	}

	@Override
	public void update() {
		Vector2 b = line.getBegin();
		Vector2 e = line.getEnd();
		line.setPosition(Vector2.add(b, vel), Vector2.add(e, vel));
	}

	@Override
	public void draw() {
		GraphicsManager gm = GraphicsManager.getInstance();

		gm.drawLine(line, Color.BLACK);
	}

	public void set(Vector2 begin, Vector2 end) {
		line.setPosition(begin, end);
	}

	public Line getLine() {
		return line;
	}

	public boolean isOutOfScreen(){
		return line.getEnd().x < -1;
	}
}
