package engine.core.game.projetil;

import java.awt.Graphics2D;

import engine.core.game.Constantes;
import engine.core.game.GameRunCanvas;
import engine.core.game.Personagem;
import engine.core.game.TileMap;
import engine.core.game.particulas.Particula;

public class SucubusOrb extends Projetil {
	int timerang = 0;
	int raioorb = 75;
	int divrot = 200;
	
	public SucubusOrb(TileMap mapa, Personagem pai, int raioorb, int divrot) {
		super(mapa, 0, 0, 0, 0, pai, 0);
		this.raioorb = raioorb;
		this.divrot = divrot;
	}
	@Override
	public void desenhaSe(Graphics2D dbg, int xTela, int yTela) {
		dbg.drawImage(Constantes.sucubusOrbImage,(int)X-xTela-8,(int)Y-yTela-8,null);
	}
	@Override
	public void atualizeSe(long DiffTime) {
		timerang += DiffTime;
		double ang = timerang/(float)divrot;
		
		X = Constantes.heroi.X+12+(float)(Math.cos(ang)*raioorb);
		Y = Constantes.heroi.Y+16+(float)(Math.sin(ang)*raioorb);
		
		for(int i = 0; i < GameRunCanvas.listaDePersonagem.size();i++) {
			Personagem p2 = GameRunCanvas.listaDePersonagem.get(i);
				if(colide(p2)&&p2!=pai) {
					//vivo = false;
					p2.levaDano(Constantes.heroi.damage+Constantes.rnd.nextInt((int)(Constantes.heroi.damage/2)));
					
					break;
				}
		}
	}

}
