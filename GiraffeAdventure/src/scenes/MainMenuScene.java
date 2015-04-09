package scenes;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import com.wine.core.SceneManager;
import com.wine.core.SoundManager;

import custom.ParallaxBackground2;
import custom.ParallaxBackground2.ParallaxEntity;
import functions.CommonFunctions;

public class MainMenuScene extends BaseScene {
	private ParallaxBackground2 pBG;
	public float parallaxPos=0;
	private Sprite lastGrass;
	public HUD mainMenuHud;

	@Override
	public void createScene() {
	 	 this.setTouchAreaBindingOnActionDownEnabled(true);
	 	 CommonFunctions.setMainHud(vbom);
	 	if(rm.SOUND){
	 		if(!SoundManager.getInstance().mainMusicLoop.isPlaying()){
	 			SoundManager.getInstance().mainMusicLoop.play();
	 		}
		}
	 	
		AnimatedSprite zooKeep=new AnimatedSprite(-20,380,rm.zooKeepWalk,vbom);
		zooKeep.animate(500);
		attachChild(zooKeep);
	
		createBackground();
		Sprite logo=new Sprite(0,0,rm.gameLogo,vbom);
		
		/****************** PLAY BUTTON LOGIC *********************/
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
					SceneManager.getInstance().createMapAfricaScene();
				}
				return true;
			}
		};
		
		logo.setPosition(getCenterX(logo.getWidth()), 3);
		
		playButton.setPosition(getCenterX(logo.getWidth(),playButton.getWidth()), logo.getHeight()-45);
		logo.attachChild(playButton);
		attachChild(logo);
		
		createGrassForeFront();

		Sprite storeButton=new Sprite(0,0,122,124,rm.storeButton,vbom){
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
					rm.camera.setHUD(null);
					SceneManager.getInstance().createStoreScene();
					
				}
				return true;
			}
		};
		
		storeButton.setPosition(1024-storeButton.getWidth()-10, 720-storeButton.getHeight()-10);
		attachChild(storeButton);
		
		registerTouchArea(playButton);
		registerTouchArea(storeButton);
		
		this.registerUpdateHandler(new IUpdateHandler() {
				public void onUpdate(float pSecondsElapsed) {
					pBG.setParallaxValue(parallaxPos);
					parallaxPos+=.02;
				}
				@Override
				public void reset(){}
			});
		
	}
	
	
	public void createGrassForeFront(){
		Sprite lowerGrass2=new Sprite(0, 650, rm.ABgrass5, vbom){
			@Override
	        protected void onManagedUpdate(float pSecondsElapsed)
	        {
				super.onManagedUpdate(pSecondsElapsed);
				if(this.getX()<-this.getWidth()+5){
					this.setPosition(lastGrass.getX()+lastGrass.getWidth()-1, this.getY());
					lastGrass=this;
				}else{
					this.setPosition(this.getX()-.7f,this.getY());
				}
	        }
		};
		Sprite lowerGrass3=new Sprite(lowerGrass2.getWidth()-1, 650, rm.ABgrass5, vbom){
			@Override
	        protected void onManagedUpdate(float pSecondsElapsed)
	        {
				super.onManagedUpdate(pSecondsElapsed);
				if(this.getX()<-this.getWidth()+5){
					this.setPosition(lastGrass.getX()+lastGrass.getWidth()-1, this.getY());
					lastGrass=this;
				}else{
					this.setPosition(this.getX()-.7f,this.getY());
				}
	        }
		};
		Sprite lowerGrass4=new Sprite(lowerGrass3.getX()+lowerGrass3.getWidth()-1, 650, rm.ABgrass5, vbom){
			@Override
	        protected void onManagedUpdate(float pSecondsElapsed)
	        {
				super.onManagedUpdate(pSecondsElapsed);
				if(this.getX()<-this.getWidth()+5){
					this.setPosition(lastGrass.getX()+lastGrass.getWidth()-1, this.getY());
					lastGrass=this;
				}else{
					this.setPosition(this.getX()-.7f,this.getY());
				}
	        }
		};
		lastGrass=lowerGrass4;
		attachChild(lowerGrass2);
		attachChild(lowerGrass3);
		attachChild(lowerGrass4);
	}
	
	public void createBackground(){
		Sprite sunBall=new Sprite(0,0,rm.ABSun,vbom);
		sunBall.setPosition(getCenterX(sunBall.getWidth()),450);
		
		Rectangle backgroundColor=new Rectangle(0,0,1024,800,vbom);
		backgroundColor.setColor(.984f,.839f,.611f,1f);
		
		pBG = new ParallaxBackground2(0, 0, 0);
		pBG.attachParallaxEntity(
				new ParallaxEntity(0.0f, -0.05f,true,false,backgroundColor));
		pBG.attachParallaxEntity(
				new ParallaxEntity(0f, 0f,false,false, sunBall));
		pBG.attachParallaxEntity(
				new ParallaxEntity(4,  0 ,false,false,
						new Sprite(1500, 490,rm.giraffeFamilyShadow, vbom),3));
		pBG.attachParallaxEntity(
				new ParallaxEntity(-10.0f,  -0.11f ,true,false,
						new Sprite(0, 620,rm.ABgrass1, vbom)));
		pBG.attachParallaxEntity(
				new ParallaxEntity(-15.0f,  -0.13f ,true,false,
						new Sprite(0, 640,rm.ABgrass2, vbom)));
		pBG.attachParallaxEntity(
				new ParallaxEntity(-20.0f,  -0.15f ,true,false,
						new Sprite(0, 660,rm.ABgrass3, vbom)));
		pBG.attachParallaxEntity(
				new ParallaxEntity(-25.0f, -0.17f,true,false,
						new Sprite(0, 690,rm.ABgrass4, vbom)));
		
		setBackground(pBG);
	}

	@Override
	public void disposeScene() {
		pBG=null;
		lastGrass=null;
		//UNREGISTER TOUCHES
		unregisterAllTouchAreas();
		detachChildren();
		rm.unloadMenuAssets();
	}

	@Override
	public void onBackKeyPressed() {
		//EXIT PROMPT. DO YOU WANT TO EXIT? 
		
	}

}
