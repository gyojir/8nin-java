package hachinin;


// 線分クラス 線分同士の交差判定を行う
public class Line {
	private Vector2 begin;
	private Vector2 end;

	public Line(){
		begin = new Vector2( 0.f, 0.f );
		end = new Vector2( 0.f, 0.f );
	}

	public Line( Vector2 begin, Vector2 end ) {
		this.begin =begin;
		this.end = end;
	}

	public void setPosition( Vector2 begin, Vector2 end ){
		this.begin =begin;
		this.end = end;
	}

	public double getLength(){
		return Math.sqrt( ( end.x - begin.x )*( end.x - begin.x ) + ( end.y - begin.y )*( end.y - begin.y ) );
	}

	public Vector2 getBegin(){
		return begin.clone();
	}

	public Vector2 getEnd(){
		return end.clone();
	}


	public Vector2 getNormal(){
		Vector2 v = Vector2.sub( end, begin );

		return v.getNormal();
	}

	boolean isIntersect(Vector2 A, Vector2 B, Vector2 C, Vector2 D){

	   Vector2 AC = Vector2.sub( C, A );
	   Vector2 AB = Vector2.sub(B,A);
	   Vector2 CD = Vector2.sub(D,C);
	   double crossAB_CD = Vector2.cross2d( AB, CD );
	   if ( crossAB_CD == 0.0f ) {
	      // 平行状態
	      return false;
	   }

	   double crossAC_AB = Vector2.cross2d( AC, AB );
	   double crossAC_CD = Vector2.cross2d( AC, CD);

	   double t1 = crossAC_CD / crossAB_CD;
	   double t2 = crossAC_AB / crossAB_CD;


	   float eps = 0.00001f;
	   if ( t1 + eps < 0 || t1 - eps > 1 || t2 + eps < 0 || t2 - eps > 1 ) {
	      // 交差していない
	      return false;
	   }

//	   Vector2 outPos = Vector2.mul( B, t1);

	   return true;
	}

	boolean isIntersect( Vector2 v, Vector2 p ){
		Vector2 A = Vector2.mul(begin, 1000);
		Vector2 B = Vector2.mul(end, 1000);
		Vector2 C = Vector2.mul(p, 1000);
		Vector2 D = Vector2.mul(Vector2.add(v, p), 1000);

		return isIntersect(A,B,C,D);
	}

	boolean restrictMove( Vector2 v, Vector2 p ){
		Vector2 move = v.clone();
		Vector2 pos = p.clone();
		double t = getIntersectionTime(move,pos);

		if( t < 0.0 && 1.0 < t ){
			return false;
		}

		// 交点から終点までのベクトル
		Vector2 penetration = Vector2.mul(move, 1-t);

		Vector2 n;
		n = getNormal();
		// 法線の長さをtoleranceとのcosにする
		// n*(|d||n|cosθ/n^2) = n*|d|cosθ 垂直方向ベクトル * 大きさ
		n.setMul(n.dot(penetration)/n.squareLength());
		n.setMul(1.3); //多めに戻す
		move.setSub(n);
		v.set( move );
		return true;
	}

	double getIntersectionTime( Vector2 moveVector, Vector2 position ){
		double inf = Double.MAX_VALUE;
		Vector2 A = Vector2.mul(begin,1000);
		Vector2 B = Vector2.mul(end,1000);
		Vector2 C = Vector2.mul( position, 1000);
		Vector2 D = Vector2.mul(Vector2.add( position, moveVector), 1000);


	   Vector2 AC = Vector2.sub(C,A);
	   Vector2 AB = Vector2.sub(B,A);
	   Vector2 CD = Vector2.sub(D,C);
	   double crossAB_CD = Vector2.cross2d( AB, CD );
	   if ( crossAB_CD == 0.0f ) {
	      // 平行状態
	      return inf;
	   }

	   double crossAC_AB = Vector2.cross2d( AC, AB );
	   double crossAC_CD = Vector2.cross2d( AC, CD);

	   double t1 = crossAC_CD / crossAB_CD;
	   double t2 = crossAC_AB / crossAB_CD;

	   double eps = 0.00001;
	   if ( t1 + eps < 0 || t1 - eps > 1 || t2 + eps < 0 || t2 - eps > 1 ) {
	      // 交差していない
	      return inf;
	   }

//	   Vector2 outPos = Vector2.mul( B, t1);

	   return t2;

	}

}