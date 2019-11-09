package hachinin;

// 二次元ベクトルを表すクラス
public class Vector2 implements Cloneable{
	public double x;
	public double y;

	public Vector2(){
		this.x = 0.0;
		this.y = 0.0;
	}

	public Vector2(double a){
		this.x = a;
		this.y = a;
	}

	public Vector2(double x, double y){
		this.x = x;
		this.y = y;
	}

	public Vector2(Vector2 a){
		this.x = a.x;
		this.y = a.y;
	}

	public void set(Vector2 a){
		this.x = a.x;
		this.y = a.y;
	}

	public void set(double x, double y){
		this.x = x;
		this.y = y;
	}

	// ---------------和・差・積---------------------

	public void setAdd(Vector2 a){
		this.x += a.x;
		this.y += a.y;
	}

	public void setSub(Vector2 a){
		this.x -= a.x;
		this.y -= a.y;
	}

	public void setMul(Vector2 a){
		this.x *= a.x;
		this.y *= a.y;
	}

	public void setAdd(double a){
		this.x += a;
		this.y += a;
	}

	public void setSub(double a){
		this.x -= a;
		this.y -= a;
	}

	public void setMul(double a){
		this.x *= a;
		this.y *= a;
	}

	public static Vector2 add(Vector2 a, Vector2 b){
		return new Vector2(a.x + b.x, a.y + b.y);
	}

	public static Vector2 sub(Vector2 a, Vector2 b){
		return new Vector2(a.x - b.x, a.y - b.y);
	}

	public static Vector2 mul(Vector2 a, Vector2 b){
		return new Vector2(a.x * b.x, a.y * b.y);
	}


	public static Vector2 add(Vector2 a, double b){
		return new Vector2(a.x + b, a.y + b);
	}

	public static Vector2 sub(Vector2 a, double b){
		return new Vector2(a.x - b, a.y - b);
	}

	public static Vector2 mul(Vector2 a, double b){
		return new Vector2(a.x * b, a.y * b);
	}
	// ----------------------------------------------

	// 正規化
	public void normalize(){
		double t = x*x + y*y;
		assert t > 0.f;
		t = 1.f / Math.sqrt(t);
		x *= t;
		y *= t;
	}

	// 長さ
	public double length(){
		return Math.sqrt( x*x + y*y );
	}

	// 長さの二乗
	public double squareLength(){
		return x*x + y*y;
	}

	// 内積
	public double dot(Vector2 a){
		return (x*a.x)+(y*a.y);
	}

	// 内積
	public static double dot(Vector2 a, Vector2 b){
		return (a.x*b.x)+(a.y*b.y);
	}

	// 外積(z=0の3次元ベクトル同士の外積と考える)
	public static double cross2d(Vector2 a, Vector2 b){
		return (a.x*b.y)-(a.y*b.x);
	}

	// 法線ベクトル
	public Vector2 getNormal(){
		return new Vector2(-y, x);
	}

	// 回転
	public Vector2 rotate(double deg){
		double rad = Math.toRadians(deg);
		double tx = Math.cos(rad)*x - Math.sin(rad)*y;
		double ty = Math.sin(rad)*x + Math.cos(rad)*y;
		return new Vector2(tx, ty);
	}

	// コピー用
    @Override
    public Vector2 clone() {
        try {
            return (Vector2)super.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
    }
}
