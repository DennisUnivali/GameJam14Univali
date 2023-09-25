package engine.core.game;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.core.GamePanel;

public class ItemMapa extends Sprite {
	int tipoDeItem = 0; // 0 - xp; 1 - ganancia;
	int itemW = 10;
	int itemH = 10;
	
	public ItemMapa(int tipo,int x,int y) {
		switch (tipo) {
		case 0:
			tipoDeItem = 0;
			itemW = 10;
			itemH = 10;
			break;
		case 1:
			tipoDeItem = 1;
			itemW = 64;
			itemH = 64;
			break;
		case 2:
			tipoDeItem = 2;
			itemW = 10;
			itemH = 10;
			break;
		default:
			break;
		}
		
		this.X = x;
		this.Y = y;
	}

	@Override
	public void desenhaSe(Graphics2D dbg, int xTela, int yTela) {
		// TODO Auto-generated method stub
		//dbg.setColor(Color.DARK_GRAY);
		//dbg.fillRect((int)X-xTela,(int)Y-yTela, 16, 16);
		switch (tipoDeItem) {
		case 0:
			dbg.drawImage(Constantes.xpImage,(int)X-xTela,(int)Y-yTela,null);
			break;
		case 1:
			dbg.drawImage(Constantes.altarGanaciaImage,(int)X-xTela,(int)Y-yTela,null);
			break;
		case 2:
			dbg.drawImage(Constantes.itemLifeImage,(int)X-xTela,(int)Y-yTela,null);
			break;
		default:
			break;
		}
	}

	@Override
	public void atualizeSe(long DiffTime) {
		// TODO Auto-generated method stub

	}
	
	public boolean colideRet(Personagem p2) {
		if(!(p2.X > (X+itemW)|| (p2.X+p2.charW) < X)&&!(p2.Y > (Y+itemH)|| (p2.Y+p2.charH) < Y)) {
			return true;
		}
		
		return false;
	}
	
	public void heroiPegaItem(Personagem heroi) {
		switch (tipoDeItem) {
		case 0:
			heroi.xp++;
			if(heroi.xp>=heroi.xpLevelUp) {
				heroi.xpLevelUp+=heroi.level*5;
				heroi.level++;
				heroi.xp = 0;
				
				OverlayCanvas xpcanvas = new OverlayCanvas(Constantes.gameruncanvas);
				GamePanel.telaAtiva = xpcanvas;
			}
			break;
		case 1:
			OverlayCanvasGanancia gananciacanvas = new OverlayCanvasGanancia(Constantes.gameruncanvas);
			GamePanel.telaAtiva = gananciacanvas;
			
			break;
		case 2:
			heroi.vida+=200;
			if(heroi.vida>heroi.vidaMaxima) {
				heroi.vida = heroi.vidaMaxima;
			}
			break;			
		default:
			break;
		}
	}

}
