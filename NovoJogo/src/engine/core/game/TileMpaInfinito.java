package engine.core.game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class TileMpaInfinito extends TileMap{

	HashMap<String,int[][][]> mapBlock;
	int blockSize = 64;
	
	public TileMpaInfinito(BufferedImage tileset) {
		super(tileset);
		
		mapBlock = new HashMap<>();
		
	}
	
	
	
	public void desenhaSe(Graphics2D dbg, int xTela,int yTela,int blx,int bly) {
		
		int adjx = xTela%32;
		int adjy = yTela%32;
		
		int txbase = xTela/32;
		int tybase = yTela/32;
				
		for(int li = 0; li < nlayers;li++) {
			for(int yt = 0;yt < blx;yt++) {
				for(int xt = 0;xt < bly;xt++) {
					int txw = txbase+xt;
					int tyw = tybase+yt;
					
					int txBlocBase = txw/blockSize;
					int tyBlocBase = tyw/blockSize;
					
					int tilemap[][][] = mapBlock.get(""+txBlocBase+"_"+tyBlocBase);
					if(tilemap==null) {
						tilemap = new int[3][blockSize][blockSize];
						for(int iy = 0; iy < blockSize; iy++) {
							for(int ix = 0; ix < blockSize; ix++) {
								tilemap[0][iy][ix] = 3;
							}
						}
						mapBlock.put(""+txBlocBase+"_"+tyBlocBase, tilemap);
					}
					
					int tx = txw%blockSize;
					int ty = tyw%blockSize;
					int tile = tilemap[li][ty][tx];
					
					int tileX = (tile%14)*32;
					int tileY = ((int)(tile/14))*32;
					
					dbg.drawImage(tileset, (int)(xt*32)-xTela,(int)(yt*32)-yTela,(int)(xt*32)+32-xTela,(int)(yt*32)+32-yTela,tileX, tileY, tileX+32, tileY+32,null);
				}
			}
		}	
	}

	public void desenhaLayer(Graphics2D dbg, int xTela,int yTela,int layer,int blx,int bly) {
		
		int adjx = xTela%32;
		if(adjx<0) {
			adjx = 32+adjx;
		}
		int adjy = yTela%32;
		if(adjy<0) {
			adjy = 32+adjy;
		}
		
		int txbase = xTela/32;
		int tybase = yTela/32;
				
		for(int yt = 0;yt < blx;yt++) {
			for(int xt = 0;xt < bly;xt++) {
				int txw = txbase+xt;
				int tyw = tybase+yt;
				
				int txBlocBase = (txw/blockSize)+(txw<0?-1:0);
				int tyBlocBase = (tyw/blockSize)+(tyw<0?-1:0);
				
				int tilemap[][][] = mapBlock.get(""+txBlocBase+"_"+tyBlocBase);
				if(tilemap==null) {
					tilemap = new int[3][blockSize][blockSize];
					for(int iy = 0; iy < blockSize; iy++) {
						for(int ix = 0; ix < blockSize; ix++) {
							tilemap[0][iy][ix] = 3;
							System.out.println("Criou ");
						}
					}
					mapBlock.put(""+txBlocBase+"_"+tyBlocBase, tilemap);
				}
				
				System.out.println(""+txBlocBase+" "+tyBlocBase+" - "+txw+" "+tyw+" - "+xTela+" - "+yTela);
				
				int tx = txw%blockSize;
				if(tx < 0) {
					tx = blockSize+tx;
				}
				int ty = tyw%blockSize;
				if(ty < 0) {
					ty = blockSize+ty;
				}
				
				int tile = tilemap[layer][ty][tx];
				
				int tileX = (tile%14)*32;
				int tileY = ((int)(tile/14))*32;
				
				dbg.drawImage(tileset, (int)(xt*32)-adjx,(int)(yt*32)-adjy,(int)(xt*32)+32-adjx,(int)(yt*32)+32-adjy,tileX, tileY, tileX+32, tileY+32,null);
			}
		}
		
	}
	public boolean testColision(float xpos,float ypos) {
		int bx = (int)(xpos)/tilewidth; 
		int by = (int)(ypos)/tileheight;
		if(bx>0&&by>0) {
			if(bx < width && by < height && tilemap[1][by][bx]!=-1) {
				return true;
			}
		}
		return false;
	}

}
