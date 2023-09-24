package engine.core.game;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import engine.core.GamePanel;

public class Constantes {
	
	public static int PWIDTH = 1020;
	public static int PHEIGHT = 720;
	public static double PWIDTHZ = 1020;
	public static double PHEIGHTZ = 720;
	
	public static int FPS,SFPS;
	public static int fpscount;
	
	public static GameRunCanvas gameruncanvas = null;
	
	public static Personagem heroi;
	public static Font font10 = new Font("",Font.BOLD,10);
	
	public static BufferedImage slashImages[] = new BufferedImage[3];
	public static BufferedImage dashImage;
	
	static {
		slashImages[0] = carregaImagem("slash00.png");
		slashImages[1] = carregaImagem("slash01.png");
		slashImages[2] = carregaImagem("slash02.png");
		
		dashImage = carregaImagem("dash00.png");
	}
	
	public static BufferedImage carregaImagem(String imageName) {
		
		try {
			BufferedImage imageTmp = ImageIO.read( GamePanel.class.getResource(imageName) );
			BufferedImage imageFinal = new BufferedImage(imageTmp.getWidth(), imageTmp.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
			imageFinal.getGraphics().drawImage(imageTmp, 0, 0, null);
			
			return imageFinal;
		}
		catch(IOException e) {
			System.out.println("Load Image error:");
		}
		return null;
	}
}
