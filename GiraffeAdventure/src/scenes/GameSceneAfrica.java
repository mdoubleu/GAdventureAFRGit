package scenes;

import java.io.IOException;
import java.util.ArrayList;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.SAXUtils;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;
import org.andengine.util.level.IEntityLoader;
import org.andengine.util.level.LevelLoader;
import org.andengine.util.level.constants.LevelConstants;
import org.xml.sax.Attributes;

import popups.GameSceneBuyItem;
import popups.GameSceneContinuePopup;
import popups.GameSceneLosePopup;
import popups.GameSceneTutorial;
import popups.GameSceneWinPopup;
import android.content.Intent;
import android.hardware.SensorManager;
import android.opengl.GLES20;
import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.wine.core.MainGameActivity;
import com.wine.core.SceneManager;
import com.wine.core.SoundManager;

import custom.ParallaxBackground2;
import custom.ParallaxBackground2.ParallaxEntity;
import factory.GameSceneFactories;
import functions.CommonFunctions;
import functions.GameSceneFunctions;

public class GameSceneAfrica extends BaseScene implements IOnSceneTouchListener{
	
	public ParallaxBackground2 pBG;
	public float parallaxPos=0;
	private ArrayList<String[]> SeriesList;
	public int widthOfCreation;

	public PhysicsWorld physicsWorld;
	public IAreaShape player;
	public Body playerBody;

	public float platformPlacementX;
	public float platformPlacementY;

	private String[]currentLevelSeries;
	public int currentLevelSeriesIndex;
	public int lastSavedGapWidth;

	private float gravityFactor;
	public float jumpRate;
	private int jumpCount;
	public int touchState;
	
	public int gameCurrentLevel;
	public int totalObjectsInLevel;
	public int objectsRemovedCount;
	public int gameState;//0=playing 1=openingScene 2=Paused 3=Continue 4=Invincible Screen/Win Screen
	
	public int nextLevelSeries;
	public IAreaShape lastSavedLevelObject;
	
	public AnimatedSprite playerAnimation;
	
	public float floorY;
	public float floorYObstacles;
	
	public Sprite popupBox,popupBoxSaved;
	
	public Sprite lastGrass;
	public Sprite zooKeepPosImage;
	public int totalLevelWidthF;
	
	public float parallaxRate, parallaxRateSaved;
	public int gameSpeed, gameSpeedSaved,gameSpeedOriginal,gameSpeedSprint;
	public float grassMovementSpeed, grassMovementSpeedSaved;

	public float blinkTimeCount;
	public int zooKeepState;
	public int stars,starsSaved;
	
	public HUD gameHud;
	public int heartsUsed;
	
	public int totalCoinsInLevel,totalCoinsInLevelCounter;
	public int coinsCollected;
	public boolean ifFlagLoaded;
	public Sprite trackBall;
	public Text coinText;
	public int gAnimationSpeed;
	
	public float totalGameSpeed;
	public float totalZooKeepSpeed;
	public int zooKeepSpeed;
	
	public Body contactWhileRisingBody;
	public final boolean ifShowHitBoxes=false;
	
	private static  GameSceneAfrica INSTANCE;
	public static GameSceneAfrica getInstance(){return INSTANCE;}
	
	//=====================================================
	//LEVEL LOADING VARIABLES
	//=====================================================
	private static final String TAG_ENTITY = "entity";
	private static final String TAG_ENTITY_ATTRIBUTE_SERIES = "series";
	private static final String TAG_LEVEL_DATA = "levelData";
	
	//FIXTURE
	public FixtureDef Player_DEF;
	public FixtureDef Platform_DEF;
	public FixtureDef Platform_DEF_SENSOR; 
	@Override
	public void createScene() {
		setOnSceneTouchListener(this);
	 	setTouchAreaBindingOnActionDownEnabled(true);
		setVariables();
		createPhysics();
		GameSceneFunctions.getInstance().createBackground();
		createPlayer();
		loadLevel();
		this.currentLevelSeries=SeriesList.get(0);
		//loadNextLevelDataForTotalWidth();
		loadNextLevelData();
		
		if(rm.SOUND){
			if(SoundManager.getInstance().mainMusicLoop.isPlaying()){
				SoundManager.getInstance().mainMusicLoop.seekTo(0);
			}
			SoundManager.getInstance().mainMusicLoop.play();
		}
		
		this.registerUpdateHandler(new IUpdateHandler() {
			public void onUpdate(float pSecondsElapsed) {
				
				pBG.setParallaxValue(parallaxPos);
				parallaxPos+=parallaxRate;
			}
			@Override
			public void reset(){}
		});
		
	}
	public void setVariables(){
		INSTANCE=this;
		new GameSceneFunctions();
		
		platformPlacementY=590;
		gameCurrentLevel=rm.currentLevel;
		currentLevelSeriesIndex=0;
		touchState=0;
		
		parallaxRate=0f;
		grassMovementSpeed=0;
		
		widthOfCreation=rm.C_WIDTH*3;
		floorY=685;
		floorYObstacles=695;
		//0=playing 1=openingScene 2=Paused 3=Continue 4=Invinsible Screen/Win Screen
		gameState=1;
		gameHud=new HUD();
		rm.camera.setHUD(gameHud);
		stars=0;
		heartsUsed=0;
		ifFlagLoaded=false;
		new GameSceneFactories();
		GameSceneFunctions.getInstance().createProgressBar();
		GameSceneFunctions.getInstance().setCoins();

	}
	
	private void createPhysics(){
		physicsWorld= new FixedStepPhysicsWorld(60, new Vector2(0, SensorManager.GRAVITY_EARTH*gravityFactor),false);
		registerUpdateHandler(physicsWorld);
													//density, elacticy/bounciness, friction
		Player_DEF=PhysicsFactory.createFixtureDef(1f,0f,0f);
		Platform_DEF=PhysicsFactory.createFixtureDef(0f,0f,0f);
		Platform_DEF_SENSOR=PhysicsFactory.createFixtureDef(0f,0f,0f,true);

		physicsWorld.setContactListener(createContactListener());

	}

	private void createPlayer(){
		player=new Rectangle(700, floorYObstacles-rm.giraffe.getHeight(), 
				73, rm.giraffe.getHeight()-15, vbom){
			@Override
	        protected void onManagedUpdate(float pSecondsElapsed)
	        {
				super.onManagedUpdate(pSecondsElapsed);
				 Body body = physicsWorld.getPhysicsConnectorManager().findBodyByShape(this);
				 if(this.getTag()!=4 && playerAnimation.getCurrentTileIndex()!=4 
						 && body.getLinearVelocity().y>6){
					 this.setRotation(0);
					 setGiraffeAnimationFall();
					 this.setTag(0);
					 if(contactWhileRisingBody!=null){
						 playerContactWithObject(contactWhileRisingBody);
						 contactWhileRisingBody=null;
					 }
				 }else if(this.getTag()==10){
					 this.setRotation(this.getRotation()+10);
				 }
				 if(gameState==0 || gameState==3){
					totalGameSpeed+=-gameSpeed;
					GameSceneFunctions.getInstance().progressTrackBall();
					//totalZooKeepSpeed+=-zooKeepSpeed;
				 }
	        }
		};
			
		playerAnimation=new AnimatedSprite(0,0, rm.giraffe, vbom);
		playerAnimation.setPosition(-64, 0);
		player.attachChild(playerAnimation);
		if(ifShowHitBoxes)player.setAlpha(1);
		else player.setAlpha(0);
		playerAnimation.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		
		playerBody=PhysicsFactory.createBoxBody(physicsWorld,(IAreaShape)player, BodyType.KinematicBody,Player_DEF);
		playerBody.setLinearDamping(1f);		
		playerBody.setUserData("P");//P is giraffe 2 is bottomfloor
		
		float floorHeight=100;
		float yPos=floorHeight/2+floorY;
		IAreaShape bottomFloor= new Rectangle(0, floorY, 500,floorHeight,vbom);
		bottomFloor.setAlpha(0f);

		Body bottomFloorBody=PhysicsFactory.createBoxBody(physicsWorld,  
				0, yPos, 1024,floorHeight, BodyType.StaticBody,Player_DEF);
		bottomFloorBody.setUserData(1);//bottom floor is 1
		physicsWorld.registerPhysicsConnector(new PhysicsConnector
				((IAreaShape)bottomFloor,bottomFloorBody,true,false));
		
		attachChild(bottomFloor);
		attachChild(player);
		player.setZIndex(3);
												// last true->rotation.
		physicsWorld.registerPhysicsConnector(new PhysicsConnector((IAreaShape)player,playerBody,true,false));
	
	}
	
	public void setLastPlatformX(){
		if(lastSavedLevelObject.getUserData()!=null &&lastSavedLevelObject.getUserData().equals("s")){
			Sprite temp=(Sprite)lastSavedLevelObject.getChildByIndex(0);
			platformPlacementX=lastSavedLevelObject.getX()+temp.getX()+temp.getWidth();
			Log.d("Stump Data", "D"+temp.getX()+ " wid"+temp.getWidth());
		}else{
			platformPlacementX=lastSavedLevelObject.getX()+lastSavedLevelObject.getWidth();
		}
		platformPlacementX+=lastSavedGapWidth;
	}

	public boolean increaseLevelSeriesIndex(){
		if(currentLevelSeriesIndex<currentLevelSeries.length-1){
			currentLevelSeriesIndex++;
			return true;
        }else{
        	currentLevelSeriesIndex=0;
        	return setNextLevelSeries();
        }
	}
	public boolean setNextLevelSeries(){
		int tempIndex=nextLevelSeries+1;
		if(tempIndex<SeriesList.size()){
			nextLevelSeries++;
		}else{
			return false;
		}
		this.currentLevelSeries=SeriesList.get(nextLevelSeries);
		return true;
	}
	public void loadNextLevelData(){
		//Log.e("E", "curr series"+currentLevelSeriesIndex);
		String tempData=currentLevelSeries[currentLevelSeriesIndex];
		String[] levelDataArr=tempData.split("_");
		String descriptor=levelDataArr[0];
		if(descriptor.equals("r")){//ROCK
			GameSceneFactories.getInstance().rockFactory(levelDataArr);
		}else if(descriptor.equals("f")){//FROG
			GameSceneFactories.getInstance().frogFactory(levelDataArr);
		}else if(descriptor.equals("sn")){//SNAKE
			GameSceneFactories.getInstance().snakeFactory(levelDataArr);
		}else if(descriptor.equals("s")){//STUMP
			GameSceneFactories.getInstance().stumpFactory(levelDataArr);
		}else if(descriptor.equals("v")){//VENUS TRAP
			GameSceneFactories.getInstance().venusFactory(levelDataArr);
		}else if(descriptor.equals("t")){//TREE
			GameSceneFactories.getInstance().treeFactory(levelDataArr);
		}else if(descriptor.equals("tu")){//TUTORIAL
			GameSceneFactories.getInstance().tutorialFactory(levelDataArr);
		}else if(descriptor.equals("gs")){//GAMESPEED
			GameSceneFactories.getInstance().speedChangeFactory(levelDataArr);
		}else if(descriptor.equals("fl")){//FLAG
			GameSceneFactories.getInstance().flagFactory(levelDataArr);
		}
	}
	public void landedGiraffe(){
		if(touchState==3){
			if(gameSpeed!=0){
				GameSceneFunctions.getInstance().updateGameSpeedAndVars(gameSpeedOriginal);
			}
			touchState=0;
		}
		playerBody.setLinearDamping(100f);
		setGiraffeAnimationRun();
		jumpCount=0;
		player.setTag(4);
		setGiraffeSprint();
	}
	public void setGiraffeSprint(){
		if(touchState==1){
			if(gameSpeed!=0){
				GameSceneFunctions.getInstance().updateGameSpeedAndVars(gameSpeedSprint);
			}
			touchState=2;
		}
	}
	
	public void slowToStopGameSpeed(){
		parallaxRateSaved=parallaxRate;
		gameSpeedSaved=gameSpeed;
		grassMovementSpeedSaved=grassMovementSpeed;
		
		parallaxRate=0;
		gameSpeed=0;
		grassMovementSpeed=0;
		//gameState 0=playing 1=openingScene 2=Paused 3=Continue 4=Invinsible 5=Screen/Win Screen 6=Tutorial
	}
	public void showContinueScreen(){
			engine.registerUpdateHandler(new TimerHandler(.7f, false, new ITimerCallback(){
				@Override
				public void onTimePassed(TimerHandler pTimerHandler) {
					if(rm.heartsCapacity!=heartsUsed){
						GameSceneContinuePopup gscp=new GameSceneContinuePopup();
						popupBox=gscp.loadContinuePopup(GameSceneAfrica.getInstance());
						zooKeepState=2;
						engine.unregisterUpdateHandler(pTimerHandler);
					}else{
						loadLoseMenu(false);
					}
				}
			}));
	}
	public void stumbleGiraffe(){
		gameState=3;
		touchState=0;
		//float t=(totalGameSpeed-totalZooKeepSpeed)/2;
		//this.totalZooKeepSpeed+=t;
		zooKeepSpeed-=18;
		
		GameSceneFunctions.getInstance().blinkPlayer();
	}
	public void setGiraffeAnimationFall(){
		playerAnimation.stopAnimation();
		playerAnimation.setCurrentTileIndex(4);
	}
	public void setGiraffeAnimationRun(){
	    playerAnimation.animate(new long[]{gAnimationSpeed,gAnimationSpeed,gAnimationSpeed}, 0, 2, true);
	}
	public void setGiraffeAnimationSingleJump(){
		playerAnimation.stopAnimation();
		playerAnimation.setCurrentTileIndex(3);
	}
	
	public void removePopup(int price){
		Sprite[] touchAreas=(Sprite[])popupBox.getUserData();
		for(int i=0; i<touchAreas.length; i++){
			unregisterTouchArea(touchAreas[i]);
		}
		popupBox.detachChildren();
		popupBox.detachSelf();
		popupBox.dispose();
		popupBox=null;
		if(price>0){
			rm.updateCoins(-price);
		}
	}
	public void removeTutorialBox(){
		unregisterTouchArea(popupBox);
		popupBox.detachSelf();
		popupBox.dispose();
		popupBox=null;
		GameSceneTutorial.getInstance().disposeScene();
		physicsWorld.setContactListener(createContactListener());
		setSavedSpeeds();
		zooKeepSpeed=gameSpeed;
		jumpGiraffe();
	}
	
	public void loadLoseMenu(boolean ifRemovePopup){
		if(rm.SOUND){
			SoundManager.getInstance().mainMusicLoop.seekTo(0);
			SoundManager.getInstance().mainMusicLoop.pause();
		}
		if(ifRemovePopup){
			removePopup(0);
		}
		rm.updateCoins(coinsCollected);
		CommonFunctions.setMainHud(vbom);
		GameSceneLosePopup g=new GameSceneLosePopup();
		popupBox=g.loadLosePopup(GameSceneAfrica.getInstance()); 
	}
	public void winScreen(){
   		 Log.d("Total Level Width and Coins:", "totalWidth: "
   				 +totalGameSpeed+" totalCoins: "+totalCoinsInLevelCounter);
		
		if(rm.SOUND){
			SoundManager.getInstance().mainMusicLoop.seekTo(0);
			SoundManager.getInstance().mainMusicLoop.pause();
		}
		rm.updateCoins(coinsCollected);
		CommonFunctions.setMainHud(vbom);
		gameState=5;
		playerAnimation.setAlpha(0);
		slowToStopGameSpeed();
		GameSceneWinPopup g=new GameSceneWinPopup();
		popupBox=g.loadWinPopup(GameSceneAfrica.getInstance()); 
		
		if(rm.currentLevel==rm.furthestLevel){
			GameSceneFunctions.getInstance().updateLevel(rm.furthestLevel+1, 0, stars);
			rm.updateCurrentLevel(rm.currentLevel+1);
			rm.updateFurthestLevel(rm.furthestLevel+1);
		}else if(stars>starsSaved){
			GameSceneFunctions.getInstance().updateStars(rm.currentLevel, stars);
		}
		
	}
	public void restartLevel(){
		rm.unloadLevelLose();
		SceneManager.getInstance().restartGameScene();
	}
	public void goToNextLevel(){
		rm.unloadLevelWin();
		
		SceneManager.getInstance().createMapAfricaScene();
	}
	public void updateHearts(int incremenet){
		rm.updateHearts(incremenet);
		GameSceneFunctions.getInstance().removeHearts();
		GameSceneFunctions.getInstance().setHearts(heartsUsed);
	}
	public void setSavedSpeeds(){
		parallaxRate=parallaxRateSaved;
		gameSpeed=gameSpeedOriginal;
		grassMovementSpeed=grassMovementSpeedSaved;
	}
	public void continueGame(){
		if(rm.totalHearts>0){
			if(rm.SOUND){
				SoundManager.getInstance().mainMusicLoop.play();
			}
			zooKeepState=4;
			this.totalZooKeepSpeed=this.totalGameSpeed*.8f;
			GameSceneFunctions.getInstance().progressTrackBall();
			heartsUsed++;
			updateHearts(-1);
			removePopup(0);
			playerAnimation.setAlpha(1);
			setSavedSpeeds();
			playerAnimation.setColor(Color.GREEN);
			gameState=4;
			engine.registerUpdateHandler(new TimerHandler(1.5f, false, new ITimerCallback(){
				@Override
				public void onTimePassed(TimerHandler pTimerHandler) {
					playerAnimation.setColor(Color.WHITE);
					gameState=0;
					zooKeepState=0;
					GameSceneFunctions.getInstance().createZooKeeperChase();
					engine.unregisterUpdateHandler(pTimerHandler);
				}
			}));
		}else{
			GameSceneBuyItem gsbi=new GameSceneBuyItem();
			Integer price=rm.heartPrice;
			Sprite image=new Sprite(0,0,68,66,rm.heart,vbom);
			popupBoxSaved=popupBox;
			popupBox=gsbi.addBuyPopup(GameSceneAfrica.getInstance(),
					"1 heart for "+price+ " coins?",price,image);
		}
	}
	
	public void playerContactWithObject(Body object){		
		IAreaShape objectTemp=(IAreaShape) object.getUserData();
		int tempTag=objectTemp.getTag();
		
		if(tempTag==1){//ROCK AND STUMP
			if(!checkIgnoreUpwardContact(object)){
				float playerY=player.getY()+player.getHeight();
				if(playerY-10<objectTemp.getY()){//GIRAFFE IS ABOVE LAND
					landedGiraffe();
				}else if(gameState!=4){//GIRAFFE IS BELOW MAKE FALL
					stumbleGiraffe();
				}
			}
		}else if(tempTag==-1){//FLAG
			if(rm.SOUND){
				SoundManager.getInstance().woohooFX.play();
			}
			winScreen();
			//LOAD LEVEL WIN SCREEN
		}else if(tempTag==3){//COIN
			objectTemp.setTag(-100);
			if(rm.SOUND){
				SoundManager.getInstance().coinFX.play();
			}
			coinsCollected++;
			GameSceneFunctions.getInstance().updateCoinsText(coinsCollected);
		}else if(tempTag==2){//TUTORIAL
			slowToStopGameSpeed();
			zooKeepSpeed=0;
			objectTemp.setTag(-100);
			playerAnimation.stopAnimation();
			engine.registerUpdateHandler(new IUpdateHandler() {
				@Override public void onUpdate(float pSecondsElapsed) {
					new GameSceneTutorial();
					engine.unregisterUpdateHandler(this);
				}
				@Override public void reset() {}});
			
		}else if(tempTag==4){//GAMESPEED
			final int newGSpeed=(Integer)objectTemp.getUserData();
				engine.registerUpdateHandler(new IUpdateHandler() {
					@Override public void onUpdate(float pSecondsElapsed) {
						if(newGSpeed<gameSpeed){
							GameSceneFunctions.getInstance().createZooKeeperSpeedUp(newGSpeed);	
						}else{
							GameSceneFunctions.getInstance().updateGameSpeedAndVars(newGSpeed);
						}
						engine.unregisterUpdateHandler(this);
					}@Override public void reset() {}});
			
			//GameSceneFunctions.getInstance().updateGameSpeedAndVars((Integer)objectTemp.getUserData());
			objectTemp.setTag(-100);
		}else if(tempTag==5){//VENUS TRAP
			stumbleGiraffe();
		}else if(tempTag==7 || tempTag==8){//FROG AND SNAKE
			//if(!checkIgnoreUpwardContact(object)){
				float playerY=player.getY()+player.getHeight();
				objectTemp.setTag(-100);
				if(playerY-10<objectTemp.getY()){//GIRAFFE IS ABOVE LAND
					flipGiraffe();
				}else if(gameState!=4){//GIRAFFE IS BELOW MAKE FALL
					stumbleGiraffe();
				}
			//}
		}else if(tempTag==6){//TREE
			if(!checkIgnoreUpwardContact(object)){
				float playerY=player.getY()+player.getHeight();
				if(playerY-40<objectTemp.getY()){//GIRAFFE IS ABOVE LAND
					landedGiraffe();
				}else{//GIRAFFE IS BELOW MAKE FALL
					stumbleGiraffe();
				}
			}
		}
	}
	//CHECK IF GIRAFFE IS MOVING UP, IF SO, IGNORES CONTACT UNTL FALL
	public boolean checkIgnoreUpwardContact(Body bod){
		if(jumpCount>0 && playerAnimation.getCurrentTileIndex()!=4){//giraffe rising
			contactWhileRisingBody=bod;
			return true;
		}else{
			return false;
		}
		
	}
	public void flipGiraffe(){
		if(rm.SOUND){
			SoundManager.getInstance().jumpFX.play();
		}
		player.setTag(10);
		playerAnimation.setCurrentTileIndex(5);
		
		playerBody.setLinearDamping(1f);
		Vector2 velocity=Vector2Pool.obtain(playerBody.getLinearVelocity().x, 
	    		physicsWorld.getGravity().y * jumpRate);
	    		playerBody.setLinearVelocity(velocity);
	    
	    Vector2Pool.recycle(velocity);
	    jumpCount++;
	  
	}
	private ContactListener createContactListener(){
		ContactListener cL= new ContactListener(){
			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {}
			@Override
			public void beginContact(Contact contact) {
				final Body x1=contact.getFixtureA().getBody();
				final Body x2=contact.getFixtureB().getBody();
				
				if((x1.getUserData() instanceof String  && //GIRAFFE AND MOVING OBJECT
						x2.getUserData() instanceof IAreaShape ) || 
						(x2.getUserData() instanceof String  && 
						x1.getUserData() instanceof IAreaShape)){
					
					Body movingObject=(x1.getUserData() instanceof IAreaShape)?x1:x2;
					playerContactWithObject(movingObject);
					
				}else if((x1.getUserData() instanceof String  && //GIRAFFE AND FLOOR
						x2.getUserData() instanceof Integer ) || 
						(x2.getUserData() instanceof String  && 
						x1.getUserData() instanceof Integer)){
					Integer floor=(x1.getUserData() instanceof Integer)?(Integer)x1.getUserData()
								:(Integer)x2.getUserData();
					if(floor==1){//GROUND FLOOR STOP Y MOVEMENT ON GIRAFFE
						landedGiraffe();
					}
				}
				
			}
			@Override
			public void endContact(Contact contact) {
				final Body x1=contact.getFixtureA().getBody();
				final Body x2=contact.getFixtureB().getBody();
				
				if((x1.getUserData() instanceof String  && //GIRAFFE AND MOVING OBJECT
						x2.getUserData() instanceof IAreaShape ) || 
						(x2.getUserData() instanceof String  && 
						x1.getUserData() instanceof IAreaShape)){
					Body movingObject=(x1.getUserData() instanceof IAreaShape)?x1:x2;
					IAreaShape tempShape=(IAreaShape) movingObject.getUserData();
					int tempTag=tempShape.getTag();
					if(tempTag==1 || tempTag==6){
						playerBody.setLinearDamping(1f);
						player.setTag(0);
					}
				}
				if(contactWhileRisingBody!=null && (contactWhileRisingBody.equals(x1)|| 
						contactWhileRisingBody.equals(x2))){
					contactWhileRisingBody=null;
				}
			}
			
			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
			
			}
		};
		return cL;
	}
	
	public void jumpGiraffe(){
		if(rm.SOUND){
			SoundManager.getInstance().jumpFX.play();
		}
		player.setTag(0);
		playerBody.setLinearDamping(1f);
		Vector2 velocity=Vector2Pool.obtain(playerBody.getLinearVelocity().x, 
	    		physicsWorld.getGravity().y * jumpRate);
	    		playerBody.setLinearVelocity(velocity);
	    
	    Vector2Pool.recycle(velocity);
	    jumpCount++;
	    touchState=1;
	    setGiraffeAnimationSingleJump();
	}
	public float touchCount=0;
	//TOUCH EVENT TAP EVENT
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		//gameState 0=playing 1=openingScene 2=Paused 3=Continue 4=Invincible Screen/Win Screen
		if(gameState==0 || gameState==4 ){
			if(pSceneTouchEvent.isActionUp() ){
				
				if(jumpCount==0 && touchState==2){// GIRAFFE IS ON THE GROUND AND RUNNING && touchState==2 
					touchState=5;
					engine.registerUpdateHandler(new TimerHandler(.05f, true, new ITimerCallback(){
						@Override
						public void onTimePassed(TimerHandler pTimerHandler) {
							touchCount+=.05;
							if(jumpCount==1){//GIRAFFE IS IN AIR, KEEP SPEED THE SAME
								touchCount=0;
								engine.unregisterUpdateHandler(pTimerHandler);
							}
							if(touchCount>=.15f){//IF .15mls PASSES, TURN SPPED BACK TO NORMAL.
								touchCount=0;
								if(gameSpeed!=0){
									GameSceneFunctions.getInstance().updateGameSpeedAndVars(gameSpeedOriginal);
								}
								touchState=0;
								engine.unregisterUpdateHandler(pTimerHandler);
							}
						}
					}));
					
				}else if(touchState==6 || touchState==3){
					touchCount=0;
					if(jumpCount==0){
						gameSpeed=-5;
						touchState=0;
					}else{touchState=3;}
				}else{touchState=0;}
				
			}else if(touchState==0 && jumpCount<1){
				jumpGiraffe();
				touchState=1;
			}else if(touchState==0){
				touchState=1;
			}else if(touchState==5 && jumpCount<1){
				jumpGiraffe();
			    touchState=6;
			}else if(touchState==3){
				 touchState=6;
			}
		}
		return false;
	}
	  private void loadLevel(){
	    	
			final LevelLoader levelLoader = new LevelLoader();
			SeriesList=new ArrayList<String[]>();
			
			levelLoader.setAssetBasePath("level/");
			levelLoader.registerEntityLoader(LevelConstants.TAG_LEVEL, new IEntityLoader() {
				@Override
				public IEntity onLoadEntity(String pEntityName, Attributes pAttributes) {
					final String LevelData = SAXUtils.getAttributeOrThrow(
							pAttributes, TAG_LEVEL_DATA);
					String[] splitArray = LevelData.split(":");
					totalCoinsInLevel=Integer.parseInt(splitArray[0]);
					gameSpeed=Integer.parseInt(splitArray[1]);
					gameSpeedSprint=gameSpeed-5;
					zooKeepSpeed=gameSpeed;//-1
					gameSpeedOriginal=gameSpeed;
					gravityFactor=Integer.parseInt(splitArray[2]);
					jumpRate=Float.parseFloat(splitArray[3]);
					platformPlacementX=Integer.parseInt(splitArray[4]);
					totalLevelWidthF=Integer.parseInt(splitArray[5]);
					physicsWorld.setGravity(new Vector2(0, SensorManager.GRAVITY_EARTH*gravityFactor));
					widthOfCreation+=platformPlacementX;
					
					return null;}
			});
			levelLoader.registerEntityLoader(TAG_ENTITY, new IEntityLoader() {
					@Override
					public IEntity onLoadEntity(String pEntityName, Attributes pAttributes) {
						final String Series = SAXUtils.getAttributeOrThrow(pAttributes, 
								TAG_ENTITY_ATTRIBUTE_SERIES);
						String[] splitArray = Series.split(":");
						totalObjectsInLevel+=splitArray.length;
						SeriesList.add(splitArray);
						return null;
					}
			 });
			 
			 try {
				 String level="level"+gameCurrentLevel+".lvl";
					levelLoader.loadLevelFromAsset(activity.getAssets(),  level);
				} catch (IOException e) {
					Debug.e(e);
				}
	    } 
	  
	  public void shareGame(int score){
			String message = "This Giraffe Adventure is amazing!"
			+"http://goo.gl/gYV8lR";
			/*if(!MainGameActivity.getInstance().ifDEBUG){
			   FlurryAgent.logEvent("ShareEvent"); 
		   }*/
				Intent share = new Intent(Intent.ACTION_SEND);
				share.setType("text/plain");
				share.putExtra(Intent.EXTRA_TEXT, message);
				MainGameActivity.getInstance().startActivity(Intent.createChooser(share, 
						"Share Giraffe Adventure!"));
		}
	 
	@Override
	public void disposeScene() {
		//UNREGISTER TOUCHES
		unloadGameScene();
		rm.unloadRoundedMenuBox790();
		rm.unloadAfricaGameScene();
		SoundManager.getInstance().unloadGameEffects();
		// TODO Auto-generated method stub
	}
	public void unloadGameScene(){
		unregisterAllTouchAreas();
		GameSceneFunctions.getInstance().clearPhysicsWorld(physicsWorld);
		gameHud.detachChildren();
		gameHud.dispose();
		gameHud=null;
		rm.camera.setHUD(null);
		detachChildren();
		dispose();
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		
	}

}
