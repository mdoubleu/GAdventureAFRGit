package functions;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import com.wine.core.ResourceManager;

public class CommonFunctions {
	
	public static void createHeartsCoinsHudDisplays2(VertexBufferObjectManager vbom, HUD hud){
		ResourceManager rm=ResourceManager.getInstance();
		rm.loadFillBar();
		
		Sprite coinImage=new Sprite(0,0,48,48,rm.coinPileSingle,vbom);
		coinImage.setPosition(5, 
				5);
		
		Sprite heartImage=new Sprite(0,0,rm.heart,vbom);
		heartImage.setPosition(5, 
				coinImage.getHeight()+coinImage.getX()+10);
		
		Text coinText=new Text(-1,-1,rm.fontBold36, 
				":"+rm.totalCoins,new TextOptions(HorizontalAlign.CENTER),vbom);
		Text coinTextBlack=new Text(0,0,rm.fontBold36, 
				""+rm.totalCoins,new TextOptions(HorizontalAlign.CENTER),vbom);
		coinTextBlack.setColor(.2f,.2f,.2f);
		//coinTextBlack.setColor(Color.BLACK);
		//coinTextBlack.attachChild(coinText);
		
		coinTextBlack.setPosition(coinImage.getWidth()+4,
				getCenterY(coinImage.getHeight(),coinText.getHeight())+5);//coinImage.getHeight()-35
		
		Text heartsText=new Text(-1,-1,rm.fontBold36, 
				":"+rm.totalHearts,new TextOptions(HorizontalAlign.CENTER),vbom);
		Text heartsTextBlack=new Text(0,0,rm.fontBold36, 
				""+rm.totalHearts,new TextOptions(HorizontalAlign.CENTER),vbom);
		heartsTextBlack.setColor(.2f,.2f,.2f);
		//heartsTextBlack.setColor(Color.BLACK);
		//heartsTextBlack.attachChild(heartsText);
		
		heartsTextBlack.setPosition(heartImage.getWidth()+4,
				getCenterY(heartImage.getHeight(),heartsText.getHeight())+5);//heartImage.getHeight()-35
		
		
		coinImage.attachChild(coinTextBlack);
		heartImage.attachChild(heartsTextBlack);
		
		
		hud.attachChild(coinImage);
		hud.attachChild(heartImage);
	}
	public static void setMainHud(VertexBufferObjectManager vbom){
		ResourceManager rm=ResourceManager.getInstance();
		
 		 HUD mainHud=new HUD();
 		 CommonFunctions.createHeartsCoinsHudDisplays(vbom, mainHud);
	 	 rm.camera.setHUD(mainHud);
	 	
	}
	public static void createHeartsCoinsHudDisplays(VertexBufferObjectManager vbom, HUD hud){
		ResourceManager rm=ResourceManager.getInstance();
		rm.loadFillBar();
		Color outerColor=new Color(.1f,.1f,.1f,.2f); 
		Color innerColor=new Color(.8f,.8f,.8f,.2f);

		Sprite coinBar=new Sprite(0,0,103,36,rm.resourceBar,vbom);
		coinBar.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		coinBar.setColor(innerColor);
		
		Sprite coinOuterBar=new Sprite(0,0,105,40,rm.resourceBar,vbom);
		coinOuterBar.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		coinOuterBar.setColor(outerColor);
		coinBar.setPosition(getCenterX(coinOuterBar.getWidth(),coinBar.getWidth()),
				getCenterY(coinOuterBar.getHeight(),coinBar.getHeight()));
		
		Sprite heartBar=new Sprite(0,0,103,36,rm.resourceBar,vbom);
		heartBar.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		heartBar.setColor(innerColor);
		Sprite heartOuterBar=new Sprite(0,0,105,40,rm.resourceBar,vbom);
		heartOuterBar.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		heartOuterBar.setColor(outerColor);
		heartBar.setPosition(getCenterX(heartOuterBar.getWidth(),heartBar.getWidth()),
				getCenterY(heartOuterBar.getHeight(),heartBar.getHeight()));
		
		Sprite heartImage=new Sprite(0,0,rm.heart,vbom);
		heartImage.setPosition(-heartImage.getWidth()/2, 
				getCenterY(heartBar.getHeight(),heartImage.getHeight()));
		
		Sprite coinImage=new Sprite(0,0,rm.coin,vbom);
		coinImage.setPosition(-coinImage.getWidth()/2, 
				getCenterY(coinBar.getHeight(),coinImage.getHeight()));
		
		Text coinText=new Text(0,0,rm.fontBold36, 
				""+rm.totalCoins,new TextOptions(HorizontalAlign.CENTER),vbom);
		coinText.setPosition(getCenterX(coinBar.getWidth(),coinText.getWidth()),
				getCenterY(coinBar.getHeight(),coinText.getHeight()));
		
		Text heartsText=new Text(0,0,rm.fontBold36, 
				""+rm.totalHearts,new TextOptions(HorizontalAlign.CENTER),vbom);
		heartsText.setPosition(getCenterX(heartBar.getWidth(),heartsText.getWidth()),
				getCenterY(heartBar.getHeight(),heartsText.getHeight()));
		
		heartBar.attachChild(heartImage);
		heartBar.attachChild(heartsText);
		
		coinBar.attachChild(coinImage);
		coinBar.attachChild(coinText);
		
		coinOuterBar.attachChild(coinBar);
		heartOuterBar.attachChild(heartBar);
		
		coinOuterBar.setPosition(coinImage.getWidth()/2, 15);
		heartOuterBar.setPosition(coinImage.getWidth()/2,coinOuterBar.getY()+coinOuterBar.getHeight()+15);
		
		hud.attachChild(coinOuterBar);
		hud.attachChild(heartOuterBar);
		
	}
	public static float getCenterX(float parentObjectWidth, float objectWidth){
		return parentObjectWidth/2-objectWidth/2;
	}
	public static float getCenterY(float parentObjectHeight, float objectHeight){
		return parentObjectHeight/2-objectHeight/2;
	}
}
