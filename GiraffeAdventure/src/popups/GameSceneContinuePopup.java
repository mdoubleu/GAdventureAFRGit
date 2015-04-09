package popups;

import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.util.HorizontalAlign;

import com.wine.core.SceneManager;

import functions.GameSceneFunctions;
import android.graphics.Typeface;
import scenes.BaseScene;
import scenes.GameSceneAfrica;

public class GameSceneContinuePopup extends BaseScene{
	
	
	public Sprite loadContinuePopup(GameSceneAfrica gameScene){
		Sprite rectangleBig=new Sprite(0,0,639,513,rm.roundedMenuBox, vbom);
		rectangleBig.setColor(.396f, .007f, .007f, 1f);
		
		Font fontDefault72Bold= FontFactory.create(engine.getFontManager(), 
				engine.getTextureManager(), 512, 512, 
				Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD), 72f, true, android.graphics.Color.WHITE);
		fontDefault72Bold.load();

		Font fontDefaultSmall= FontFactory.create(engine.getFontManager(), 
				engine.getTextureManager(), 256, 256, 
				Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD), 25f, true, android.graphics.Color.BLACK);
		fontDefaultSmall.load();
		
		Text levelText=new Text(0,0, 
				fontDefault72Bold, 
				"Continue?",new TextOptions(HorizontalAlign.CENTER),vbom);
		levelText.setColor(0,0,0,1f);
		levelText.setPosition(getCenterX(rectangleBig.getWidth(),levelText.getWidth()),
				5);
		
		Text levelTextWhite=new Text(0,-2, 
				fontDefault72Bold, 
				"Continue?",new TextOptions(HorizontalAlign.CENTER),vbom);
		levelText.attachChild(levelTextWhite);
		rectangleBig.attachChild(levelText);
		
		Sprite exitButton=new Sprite(0,0,rm.exitButton,vbom){
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionMove()){
					 if(!checkClick((IAreaShape)this,pTouchAreaLocalX,pTouchAreaLocalY)){
						 this.setTag(0);}
				}else if(pSceneTouchEvent.isActionDown() && this.getScaleX()!=.8f){
					this.setTag(3);
					this.setScale(.8f);
				}
				if(pSceneTouchEvent.isActionUp() && this.getTag()==3){
					GameSceneAfrica.getInstance().loadLoseMenu(true);
				}
				return true;
			}
		};
		exitButton.setPosition(rectangleBig.getWidth()-exitButton.getWidth()-20, 
				6);
	
		rectangleBig.attachChild(exitButton);

		Sprite rectangleSmall=new Sprite(0,0,rectangleBig.getWidth()-10,
				rectangleBig.getHeight()-exitButton.getHeight()-20,rm.roundedMenuBox, vbom);
		rectangleSmall.setColor(.694f, .133f, .1529f,1f);
		rectangleSmall.setPosition(5, exitButton.getHeight()+10);

		Sprite zooKeepCatchGiraffe=new Sprite(0,0,rm.zooKeepCatchGiraffe,vbom);
		zooKeepCatchGiraffe.setPosition(5,rectangleSmall.getHeight()-zooKeepCatchGiraffe.getHeight());
		
		rectangleSmall.attachChild(zooKeepCatchGiraffe);
		/**********************PLAY BUTTON****************************/
		Sprite playButton=new Sprite(0,0,rm.playButton,vbom){
			@Override
	        protected void onManagedUpdate(float pSecondsElapsed)
	        {
				super.onManagedUpdate(pSecondsElapsed);
				if(this.getTag()!=3){
					if(this.getScaleX() < 1f && this.getTag()==0){
						this.setScale(this.getScaleX()+0.002f);
					}else if(this.getScaleX()>0.9 ){
						this.setScale(this.getScaleX()-0.002f);
						this.setTag(1);
					}else{
						this.setTag(0);
					}
				}
	        }
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, 
					final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionMove()){
					 if(!checkClick((IAreaShape)this,pTouchAreaLocalX,pTouchAreaLocalY)){
						 this.setTag(0);}
				}else if(pSceneTouchEvent.isActionDown() && this.getScaleX()!=.8f){
					this.setTag(3);
					this.setScale(.8f);
				}
				if(pSceneTouchEvent.isActionUp() && this.getTag()==3){
					///CONTINUE GAME...
					GameSceneAfrica.getInstance().continueGame();
					this.setTag(0);
				}
				return true;
			}
		};
		playButton.setPosition(getCenterX(rectangleSmall.getWidth(),playButton.getWidth()),
				-10);
		
		//TEXT and image cost 1 heart <3x1  
		Sprite livesImage=new Sprite(0,0,25,25,rm.heart,vbom);
	
		Text x1=new Text(0,0,fontDefaultSmall, 
				"x1",new TextOptions(HorizontalAlign.RIGHT),vbom);
		x1.setPosition(livesImage.getWidth()-1, livesImage.getHeight()-x1.getHeight()+6);
		livesImage.attachChild(x1);
		livesImage.setPosition(playButton.getWidth()/2+25, 
				playButton.getHeight()-68);
		playButton.attachChild(livesImage);
		
		rectangleSmall.attachChild(playButton);
		
		rectangleBig.attachChild(rectangleSmall);
		
		rectangleBig.setPosition(0, 720-rectangleBig.getHeight());
		gameScene.attachChild(rectangleBig);
		
		gameScene.registerTouchArea(exitButton);
		gameScene.registerTouchArea(playButton);
		
		rectangleBig.setUserData(new Sprite[]{exitButton,playButton});
		return rectangleBig;
	}

	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		rm.loadRoundedMenuBox790();

	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		
	}
}
