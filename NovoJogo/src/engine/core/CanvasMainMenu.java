package engine.core;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;

import old.CanvasMAIN;
import old.Constantes;

public class CanvasMainMenu extends MyCanvas {

	int timer = 0;

	@Override
	public void update(float diftime) {
		// TODO Auto-generated method stub
		timer += diftime;

		//if (timer > 8000) {
		//	GamePanel.telaAtiva = new CanvasGame();
		//}
	}
	String arrayOpcoes[] = {"  Novo Jogo ","  Teclas ","  Sobre "};
	int selected = 0;

	@Override
	public void render(Graphics2D dbg) {

		//dbg.setColor(Color.black);
		//dbg.fillRect(0, 0, Constantes.telaW, Constantes.telaH);

		dbg.drawImage(engine.core.game.Constantes.aberturaImage,0,0, null);
		
		dbg.setFont(Constantes.font.deriveFont(Font.PLAIN, 50));
		
		for(int i = 0; i < arrayOpcoes.length;i++) {
			if(selected==i) {
				dbg.setColor(Color.WHITE);
			}else {
				dbg.setColor(Color.LIGHT_GRAY);
			}
			dbg.drawString(arrayOpcoes[i], Constantes.telaW / 2 - 421, Constantes.telaH / 2 + 50+50*i);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W){
			selected--;
		}
		if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S){
			selected++;
		}
		if (selected>=arrayOpcoes.length) {
			selected=0;
		}
		if(selected<0) {
			selected=arrayOpcoes.length-1;
		}
		
		if(keyCode == KeyEvent.VK_SPACE || keyCode == KeyEvent.VK_ENTER){
			if(selected==0) {
				GamePanel.telaAtiva = new  CanvasHistorinha();
			}
			if(selected==1) {
				GamePanel.telaAtiva = new  CanvasTeclas();
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
		// TODO Auto-generated method stub
		if(selected==0) {
			GamePanel.telaAtiva = new  CanvasHistorinha();
		}
		if(selected==1) {
			GamePanel.telaAtiva = new  CanvasTeclas();
		}
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
