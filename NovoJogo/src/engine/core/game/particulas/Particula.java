package engine.core.game.particulas;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;

import engine.core.game.Sprite;
import engine.core.game.TileMap;

public class Particula extends Sprite {
	float velX = 0;
	float velY = 0;
	
	float angulo = 0;
	float velocidae = 100;
	
	TileMap mapa;
	
	int tempoDeVida;
	int tempoDeVidaInicial;
	
	int size = 10;
	Color cor = Color.red;
	
	public Particula(TileMap mapa,float x,float y,float angulo,float velocidade,int tempoDeVida) {
		this.X = x;
		this.Y = y;
		this.angulo = angulo;
		this.velocidae = velocidade;

		this.mapa = mapa;
		this.tempoDeVida = tempoDeVida;
		this.tempoDeVidaInicial = tempoDeVida;
		
		velX = (float)(velocidade*Math.cos(angulo));
		velY = (float)(velocidade*Math.sin(angulo));
	}
	
	@Override
	public void desenhaSe(Graphics2D dbg, int xTela, int yTela) {
		Composite comp = dbg.getComposite();
		
		float prop = tempoDeVida/(float)tempoDeVidaInicial;
		if(prop<=0) {
			prop = 0;
		}
		
		AlphaComposite acomp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.05f+0.5f*prop);
		
		dbg.setComposite(acomp);
		dbg.setColor(cor);
		dbg.fillRect((int)X-xTela-size/2,(int)Y-yTela-size/2, size, size);
		
		dbg.setComposite(comp);
	}

	@Override
	public void atualizeSe(long DiffTime) {
		// TODO Auto-generated method stub
		X = X + velX*DiffTime/1000.0f;
		Y = Y + velY*DiffTime/1000.0f;
		
		tempoDeVida -=DiffTime;
		
		if(tempoDeVida<=0) {
			vivo = false;
		}
		
	}
	
}
