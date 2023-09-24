package engine.core.game;

import java.awt.image.BufferedImage;

public class Inimigo extends Personagem {

	int iaTimer = 0;
	
	public Inimigo(int x, int y, TileMap mapa, BufferedImage charset) {
		super(x, y, mapa, charset);
	}
	
	@Override
	public void atualizeSe(long DiffTime) {
		iaTimer += DiffTime;
		
		if(iaTimer>250) {
			Personagem p2 = colidePersonagemRP(25);
			//Personagem p2 = null;
			
			if(p2!=null&&p2!=Constantes.heroi) {
				int xdf = (int)(X-p2.X);
				int ydf = (int)(Y-p2.Y);
				
				double ang = Math.atan2(ydf, xdf);
				
				velX = (float)(vel*Math.cos(ang));
				velY = (float)(vel*Math.sin(ang));
			}else {
				int xdf = (int)(Constantes.heroi.X-X);
				int ydf = (int)(Constantes.heroi.Y-Y);
				
				double ang = Math.atan2(ydf, xdf);
				
				velX = (float)(vel*Math.cos(ang));
				velY = (float)(vel*Math.sin(ang));
			}
		}
		

		
		super.atualizeSe(DiffTime);
	}

}
