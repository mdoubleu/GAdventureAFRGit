package popups;

import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import com.wine.core.SceneManager;

import android.graphics.Typeface;
import android.util.Log;
import scenes.BaseScene;
import scenes.GameSceneAfrica;

public class GameSceneWinPopup extends BaseScene {

	public Sprite loadWinPopup(GameSceneAfrica gameScene){
		Sprite rectangleBig=new Sprite(0,0,rm.roundedMenuBox, vbom);
		rectangleBig.setColor(.325f, .494f, .5529f, 1f);
		
		Font fontDefault72Bold= FontFactory.create(engine.getFontManager(), 
				engine.getTextureManager(), 512, 512, 
				Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD), 72f, true, android.graphics.Color.WHITE);
		fontDefault72Bold.load();
		
		Text levelText=new Text(0,0, 
				fontDefault72Bold, 
				"Win!",new TextOptions(HorizontalAlign.CENTER),vbom);
		levelText.setColor(0,0,0,1f);
		levelText.setPosition(getCenterX(rectangleBig.getWidth(),levelText.getWidth()),
				5);
		
		Text levelTextWhite=new Text(0,-2, 
				fontDefault72Bold, 
				"Win!",new TextOptions(HorizontalAlign.CENTER),vbom);
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
				}
				return true;
			}
		};
		exitButton.setPosition(rectangleBig.getWidth()-exitButton.getWidth()-20, 
				6);
	
		rectangleBig.attachChild(exitButton);
		Sprite rectangleSmall=new Sprite(0,0,rectangleBig.getWidth()-10,
				rectangleBig.getHeight()-exitButton.getHeight()-20,rm.roundedMenuBox, vbom);
		rectangleSmall.setColor(.568f, .843f, .933f,1f);
		
		rectangleSmall.setPosition(5, exitButton.getHeight()+10);
		//.325f, .494f, .5529f, 1f DarkBlue
		//.568f, .843f, .933f,1f LightBlue
		
		Sprite happyGiraffe=new Sprite(0,0,rm.happyGiraffe,vbom);
		happyGiraffe.setPosition(5, 25);
		rectangleSmall.attachChild(happyGiraffe);
		
		float percentage=(float)gameScene.coinsCollected/(float)gameScene.totalCoinsInLevel;
		
		Color full=new Color(.925f, .905f, .047f);
		Color emptyGrey=new Color(.4f,.4f,.4f);
		
		Sprite star1=new Sprite(0,0,rm.star,vbom);
		star1.setColor(full);
		Sprite star2;
		Sprite star3;
		//level data: lvlNum|displayType|stars|priceToUnlock 1_1_0_0
		//PARSE LEVEL DATA AND SET NEW STARS RECORD I THINK THATS A MODULE
		
		if(percentage==1){
			//3stars
			star2=new Sprite(0,0,rm.star,vbom);
			star2.setColor(full);
			star3=new Sprite(0,0,rm.star,vbom);
			star3.setColor(full);
			gameScene.stars=3;
		}else if(percentage>=.5f){
			//2stars
			star2=new Sprite(0,0,rm.star,vbom);
			star2.setColor(full);
			star3=new Sprite(0,0,rm.star,vbom);
			star3.setColor(emptyGrey);
			gameScene.stars=2;
		}else{
			star2=new Sprite(0,0,rm.star,vbom);
			star2.setColor(emptyGrey);
			star3=new Sprite(0,0,rm.star,vbom);
			star3.setColor(emptyGrey);
			gameScene.stars=1;
		}
		int starYBase=40;
		star1.setPosition(happyGiraffe.getX()+happyGiraffe.getWidth()+50, starYBase);
		star2.setPosition(star1.getX()+star1.getWidth(), starYBase-30);
		star3.setPosition(star2.getX()+star2.getWidth(), starYBase);
		rectangleSmall.attachChild(star1);
		rectangleSmall.attachChild(star2);
		rectangleSmall.attachChild(star3);
		
		Sprite coinPile=new Sprite(0,0,rm.coinPileSingle,vbom);
		coinPile.setPosition(happyGiraffe.getX()+happyGiraffe.getWidth()+40,
				star1.getHeight()+star1.getY()+70);
		rectangleSmall.attachChild(coinPile);
		
		Sprite fillBar=new Sprite(0,0,rm.fillBar,vbom);
		fillBar.setPosition(coinPile.getWidth()+coinPile.getX()+7,
				coinPile.getY()+getCenterY(coinPile.getHeight(),fillBar.getHeight()));
		fillBar.setColor(.4f,.4f,.4f,1);
		float fillBarLength=fillBar.getWidth();
		
		float newWidth=percentage*fillBarLength;
		Log.d("new width", ""+newWidth);
		Sprite fillBarComplete=new Sprite(0,-1,newWidth,fillBar.getHeight()+2,rm.fillBar,vbom);
		Sprite fillBarGlare=new Sprite(3,1,newWidth-6,fillBarComplete.getHeight()/3f,rm.fillBar,vbom);
		
		fillBarGlare.setColor(.98f,.949f,.87f,1f);
		fillBarComplete.setColor(.98f,.949f,.568f,1);
		
		fillBarComplete.attachChild(fillBarGlare);
		
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
					GameSceneAfrica.getInstance().goToNextLevel();
				}
				return true;
			}
		};
		playButton.setPosition(65,rectangleSmall.getHeight()-playButton.getHeight()/2);
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
					this.setScale(1f);
				}
				return true;
			}
		};
		shareButton.setPosition(520,rectangleSmall.getHeight()-shareButton.getHeight()/2);
		rectangleSmall.attachChild(shareButton);
		
		rectangleBig.attachChild(rectangleSmall);
		
		rectangleBig.setPosition(getCenterX(rectangleBig.getWidth()), 
				getCenterY(rectangleBig.getHeight())-34);
		rectangleBig.setUserData(new Sprite[]{playButton,shareButton,exitButton});
		
		gameScene.registerTouchArea(playButton);
		gameScene.registerTouchArea(shareButton);
		gameScene.registerTouchArea(exitButton);
		gameScene.attachChild(rectangleBig);
		
		return rectangleBig;
	}
	
	@Override
	public void createScene() {
		rm.loadLevlWin();
		rm.loadFillBar();
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
