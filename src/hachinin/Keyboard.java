package hachinin;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// キー入力を管理するシングルトンクラス
public class Keyboard extends KeyAdapter{
	private static Keyboard instance = new Keyboard();

	public enum Key{
		KEY_A,
		KEY_W,
		KEY_S,
		KEY_D,
		KEY_F,
		KEY_J,
		KEY_K,
		KEY_L,
		KEY_UP,
		KEY_DOWN,
		KEY_LEFT,
		KEY_RIGHT,
		KEY_SEMICOLON,
		KEY_SPACE,
	};

	//キーの状態（離している=0、押し続けている=1、押した瞬間=2）
	int key_states[] = new int[256];

	private Keyboard(){
	}

	public static Keyboard getInstance(){
		return instance;
	}

	// 長押し判定
	public void update(){
		for(int i = 0; i < key_states.length; i++){
			if(key_states[i] == 2){
				key_states[i] = 1;
			}
		}
	}

	// 押されているか
	public boolean isOn(Key k){
		return key_states[k.ordinal()] == 1 || key_states[k.ordinal()] == 2;
	}
	// 押された瞬間
	public boolean isTriggered(Key k){
		return key_states[k.ordinal()] == 2;
	}

	// キーイベントを配列番号に変換
	private int convertKey(int k){
		int r = -1;

		switch(k){
		case KeyEvent.VK_A: r = Key.KEY_A.ordinal(); break;
		case KeyEvent.VK_W: r = Key.KEY_W.ordinal(); break;
		case KeyEvent.VK_S: r = Key.KEY_S.ordinal(); break;
		case KeyEvent.VK_D: r = Key.KEY_D.ordinal(); break;
		case KeyEvent.VK_F: r = Key.KEY_F.ordinal(); break;
		case KeyEvent.VK_J: r = Key.KEY_J.ordinal(); break;
		case KeyEvent.VK_K: r = Key.KEY_K.ordinal(); break;
		case KeyEvent.VK_L: r = Key.KEY_L.ordinal(); break;
		case KeyEvent.VK_SEMICOLON: r = Key.KEY_SEMICOLON.ordinal(); break;
		case KeyEvent.VK_UP: r = Key.KEY_UP.ordinal(); 	break;
		case KeyEvent.VK_DOWN: r = Key.KEY_DOWN.ordinal(); break;
		case KeyEvent.VK_LEFT: r = Key.KEY_LEFT.ordinal(); break;
		case KeyEvent.VK_RIGHT: r = Key.KEY_RIGHT.ordinal(); break;
		case KeyEvent.VK_SPACE: r = Key.KEY_SPACE.ordinal(); break;
		}

		return r;
	}

	// -----------------KeyAdapter----------------------

	public void keyPressed(KeyEvent e) {
		int key;
		key = convertKey(e.getKeyCode());
		if(key==-1){
			return;
		}

		if(key_states[key] == 0){
			key_states[key] = 2;
		}else{
			key_states[key] = 1;
		}
	}
	public void keyReleased(KeyEvent e) {
		int key;
		key = convertKey(e.getKeyCode());
		if(key==-1){
			return;
		}

		key_states[key] = 0;
	}
}
