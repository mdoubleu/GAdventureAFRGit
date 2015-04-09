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

import android.graphics.Typeface;
import android.util.Log;
import scenes.BaseScene;
import scenes.GameSceneAfrica;

public class GameSceneLosePopup extends BaseScene {
	
	public Sprite loadLosePopup(GameSceneAfrica gameScene){
		Sprite rectangleBig=new Sprite(0,0,rm.roundedMenuBox, vbom);
		rectangleBig.setColor(.396f, .007f, .007f, 1f);
		
		Font fontDefault72Bold= FontFactory.create(engine.getFontManager(), 
				engine.getTextureManager(), 512, 512, 
				Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD), 72f, true, android.graphics.Color.WHITE);
		fontDefault72Bold.load();
		
		Text levelText=new Text(0,0, 
				fontDefault72Bold, 
				"Lose",new TextOptions(HorizontalAlign.CENTER),vbom);
		levelText.setColor(0,0,0,1f);
		levelText.setPosition(getCenterX(rectangleBig.getWidth(),levelText.getWidth()),
				5);
		
		Text levelTextWhite=new Text(0,-2, 
				fontDefault72Bold, 
				"Lose",new TextOptions(HorizontalAlign.CENTER),vbom);
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
					SceneManager.getInstance().createMapAfricaScene();
					this.setScale(1f);
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
		
		Sprite zooKeepFace=new Sprite(0,0,rm.zooKeepFace,vbom);
		zooKeepFace.setPosition(getCenterX(rectangleSmall.getWidth(),zooKeepFace.getWidth()), 
				rectangleSmall.getHeight()-zooKeepFace.getHeight());
		rectangleSmall.attachChild(zooKeepFace);
		float percentage=(float)gameScene.coinsCollected/(float)gameScene.totalCoinsInLevel;
		
		Sprite coinPile=new Sprite(0,0,rm.coinPileSingle,vbom);
		
		Sprite fillBar=new Sprite(0,0,rm.fillBar,vbom);
		fillBar.setPosition(getCenterX(rectangleSmall.getWidth(),fillBar.getWidth()),
				zooKeepFace.getY()-100);
		coinPile.setPosition(fillBar.getX()-coinPile.getWidth()-7, 
				fillBar.getY()+getCenterY(fillBar.getHeight(),coinPile.getHeight()));
		
		fillBar.setColor(.4f,.4f,.4f,1);
		float fillBarLength=fillBar.getWidth();
		
		float newWidth=percentage*fillBarLength;
		Log.d("new width", ""+newWidth);
		Sprite fillBarComplete=new Sprite(0,-1,newWidth,fillBar.getHeight()+2,rm.fillBar,vbom);
		Sprite fillBarGlare=new Sprite(3,1,newWidth-6,fillBarComplete.getHeight()/3f,rm.fillBar,vbom);
		
		fillBarGlare.setColor(.98f,.949f,.87f,1f);
		fillBarComplete.setColor(.98f,.949f,.568f,1);
		
		fillBarComplete.attachChild(fillBarGlare);
		
		rectangleSmall.attachChild(coinPile);

		fillBar.attachChild(fillBarComplete);
		
		Text currCoinsText=new Text(0,0, 
				fontDefault72Bold, 
				":"+(int)gameScene.coinsCollected,new TextOptions(HorizontalAlign.CENTER),vbom);
		currCoinsText.setPosition(fillBar.getX()+fillBar.getWidth()+7,
				coinPile.getY()+getCenterY(coinPile.getHeight(),currCoinsText.getHeight())-4);
		rectangleSmall.attachChild(currCoinsText);
		
		rectangleSmall.attachChild(fillBar);
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
					///RESTART LEVEL
					GameSceneAfrica.getInstance().restartLevel();
				}
				return true;
			}
		};
		playButton.setPosition(10,rectangleSmall.getHeight()-playButton.getHeight()/1.5f);
		rectangleSmall.attachChild(playButton);
		
		Sprite shareButton=new Sprite(0,0,rm.shareButton,vbom){
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
					///SHARE
					GameSceneAfrica.getInstance().shareGame(0);
				}
				return true;
			}
		
		};
		shareButton.setPosition(580,rectangleSmall.getHeight()-shareButton.getHeight()/1.5f);
		rectangleSmall.attachChild(shareButton);
		
		rectangleBig.attachChild(rectangleSmall);
		
		rectangleBig.setPosition(getCenterX(rectangleBig.getWidth()), 
				getCenterY(rectangleBig.getHeight())-20);
		rectangleBig.setUserData(new Sprite[]{playButton,shareButton,exitButton});

		gameScene.registerTouchArea(playButton);
		gameScene.registerTouchArea(shareButton);
		gameScene.registerTouchArea(exitButton);
		
		gameScene.attachChild(rectangleBig);
		return rectangleBig;
	}
	
	@Override
	public void createScene() {
		rm.loadLevelLose();
		rm.loadFillBar();
		rm.loadRoundedMenuBox790();
	}

	@Override
	public void disposeScene() {
		detachChildren();
		dispose();
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		
	}

}
