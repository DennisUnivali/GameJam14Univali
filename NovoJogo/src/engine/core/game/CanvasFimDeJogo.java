package engine.core.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;

import engine.core.CanvasMainMenu;
import engine.core.GamePanel;
import engine.core.MyCanvas;
import engine.core.game.GameRunCanvas;

public class CanvasFimDeJogo extends MyCanvas {

	int timer = 0;
	
	GameRunCanvas theGame = null;
	boolean firstdraw = true;

	@Override
	public void update(float diftime) {
		// TODO Auto-generated method stub
		timer += diftime;
		if(firstdraw==false && theGame==null) {
			theGame = new GameRunCanvas();
		}

		// if (timer > 8000) {
		// GamePanel.telaAtiva = new CanvasGame();
		// }
	}

	@Override
	public void render(Graphics2D dbg) {

		if(GameRunCanvas.sucubusPower==0) {
			dbg.drawImage(Constantes.fimJogoImages[0],0,0, null);
		}else if(GameRunCanvas.sucubusPower==1) {
			dbg.drawImage(Constantes.fimJogoImages[1],0,0, null);
		}else if(GameRunCanvas.sucubusPower==2) {
			dbg.drawImage(Constantes.fimJogoImages[2],0,0, null);
		}else if(GameRunCanvas.sucubusPower==3) {
			dbg.drawImage(Constantes.fimJogoImages[3],0,0, null);
		}
		
		
		if(theGame!=null) {
			dbg.setColor(Color.white);
			dbg.drawString("  Clique Na Tela Para Continuar ou pressione espaço ou enter ", 250, 700);
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();

		if(keyCode == KeyEvent.VK_SPACE || keyCode == KeyEvent.VK_ENTER){
			if(theGame!=null) {
				GamePanel.telaAtiva = new CanvasMainMenu();		
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		GamePanel.telaAtiva = new CanvasMainMenu();		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		// TODO Auto-generated method stub

	}

}
