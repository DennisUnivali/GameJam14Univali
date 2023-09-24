package engine.core.game.particulas;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;

import engine.core.game.Constantes;
import engine.core.game.TileMap;

public class ParticulaTexto extends Particula {
	String texto = "";
	
	public ParticulaTexto(TileMap mapa, float x, float y, float angulo, float velocidade, int tempoDeVida,String texto) {
		super(mapa, x, y, angulo, velocidade, tempoDeVida);
		this.texto = texto;
	}
	
	@Override
	public void desenhaSe(Graphics2D dbg, int xTela, int yTela) {

		Font ff = dbg.getFont();
		
		dbg.setFont(Constantes.font10);
		dbg.setColor(Color.black);
		dbg.drawString(texto, (int)X-xTela-size/2+1,(int)Y-yTela-size/2);
		dbg.drawString(texto, (int)X-xTela-size/2-1,(int)Y-yTela-size/2);
		dbg.drawString(texto, (int)X-xTela-size/2,(int)Y-yTela-size/2+1);
		dbg.drawString(texto, (int)X-xTela-size/2,(int)Y-yTela-size/2-1);
		dbg.setColor(Color.white);
		dbg.drawString(texto, (int)X-xTela-size/2,(int)Y-yTela-size/2);
		
		dbg.setFont(ff);
		
	}

}
