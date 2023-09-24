package engine.core.game;

import java.awt.Graphics2D;

public abstract class Sprite {
	public float X;
	public float Y;
	
	public boolean vivo = true;
	
	public abstract void desenhaSe(Graphics2D dbg, int xTela,int yTela);
	public abstract void atualizeSe(long DiffTime);
}
