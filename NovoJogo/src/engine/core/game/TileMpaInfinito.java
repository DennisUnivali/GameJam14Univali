package engine.core.game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.org.json.JSONArray;
import com.org.json.JSONException;
import com.org.json.JSONObject;

public class TileMpaInfinito extends TileMap{

	Random rnd = new Random();
	HashMap<String,int[][][]> mapBlock;
	int blockSize = 64;
	
	ArrayList<int[][][]> templateList = new ArrayList<>();
	
	public TileMpaInfinito(BufferedImage tileset) {
		super(tileset);
		
		mapBlock = new HashMap<>();
		
		int[][][] t1 = carregaTemplateJson("./res/engine/core/template01.json");
		int[][][] t2 = carregaTemplateJson("./res/engine/core/template02.json");
		templateList.add(t1);
		templateList.add(t2);
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
					tilemap = createMapBlock();
					mapBlock.put(""+txBlocBase+"_"+tyBlocBase, tilemap);
				}
				
				
				int tx = txw%blockSize;
				if(tx < 0) {
					tx = blockSize+tx;
				}
				int ty = tyw%blockSize;
				if(ty < 0) {
					ty = blockSize+ty;
				}
				
				int tile = tilemap[layer][ty][tx];
				
				if(tile==-1) {
					continue;
				}
				
				int tileX = (tile%14)*32;
				int tileY = ((int)(tile/14))*32;
				
				dbg.drawImage(tileset, (int)(xt*32)-adjx,(int)(yt*32)-adjy,(int)(xt*32)+32-adjx,(int)(yt*32)+32-adjy,tileX, tileY, tileX+32, tileY+32,null);
			}
		}
		System.out.println(""+xTela+" - "+yTela);
		
	}



	private int[][][] createMapBlock() {
		int[][][] tilemap;
		tilemap = new int[3][blockSize][blockSize];
		for(int iy = 0; iy < blockSize; iy++) {
			for(int ix = 0; ix < blockSize; ix++) {
				tilemap[0][iy][ix] = 3;
				tilemap[1][iy][ix] = -1;
				tilemap[2][iy][ix] = -1;
				System.out.println("Criou ");
			}
		}
		
		int templates = 20;//rnd.nextInt(4);
		for(int i = 0; i < templates;i++) {
			int tpid = rnd.nextInt(templateList.size());
			copyTemplateToMamp(templateList.get(tpid), tilemap, 4+rnd.nextInt(55), 4+rnd.nextInt(55));
		}
		
		return tilemap;
	}
	public boolean testColision(float xpos,float ypos) {
		int bx = (int)(xpos)/tilewidth; 
		int by = (int)(ypos)/tileheight;
		
		int txBlocBase = (bx/blockSize)+(bx<0?-1:0);
		int tyBlocBase = (by/blockSize)+(by<0?-1:0);
		
		int tilemap[][][] = mapBlock.get(""+txBlocBase+"_"+tyBlocBase);
		if(tilemap==null) {
			tilemap = createMapBlock();
			mapBlock.put(""+txBlocBase+"_"+tyBlocBase, tilemap);
		}
		
		
		int tx = bx%blockSize;
		if(tx < 0) {
			tx = blockSize+tx;
		}
		int ty = by%blockSize;
		if(ty < 0) {
			ty = blockSize+ty;
		}
		
		
		
		int tile = tilemap[1][ty][tx];
		
		//System.out.println(" tx "+tx+" ty "+ty+" "+tile);
		
		if(tile>0) {
			return true;
		}
		return false;
	}
	
	public int[][][] carregaTemplateJson(String nomemapa) {
		try {
			FileReader fr = new FileReader(nomemapa,Charset.forName("UTF-8"));

			BufferedReader bfr = new BufferedReader(fr);
			String line = "";
			String json = "";
			while((line = bfr.readLine())!=null) {
				json+=line+"\n";
			}
		
			//System.out.println(""+json);
			
			JSONObject jso = new JSONObject(json);
			
			height = jso.getInt("height");
			width = jso.getInt("width");
			tileheight = jso.getInt("tileheight");
			tilewidth = jso.getInt("tilewidth");
			
			worldW = width*tilewidth;
			worldH = height*tileheight;
			
			JSONArray jarr = jso.getJSONArray("layers");
			nlayers = jarr.length();
			
			int[][][] templatemap = new int[nlayers][height][width];
			
			for(int i = 0; i < nlayers;i++) {
				JSONObject layrobject = jarr.getJSONObject(i);
				JSONArray jsondata = layrobject.getJSONArray("data");
				int index = 0;
				for(int yi = 0; yi < height;yi++) {
					for(int xi = 0; xi < width;xi++) {
						templatemap[i][yi][xi] = jsondata.getInt(index)-1;
						index++;
					}
				}
			}
			
			return templatemap;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void copyTemplateToMamp(int[][][] template, int[][][] destino, int bx,int by) {
		for(int li = 1; li < template.length;li++) {
			for(int yi = 0; yi < template[li].length;yi++) {
				for(int xi = 0; xi < template[li][yi].length;xi++) {
					if(template[li][yi][xi]>0) {
						destino[li][yi+by][xi+bx] = template[li][yi][xi];
					}
				}	
			}
		}
	}

}
