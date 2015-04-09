package com.wine.core;

import org.andengine.engine.FixedStepEngine;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;


public class ResourceManager {
	/****************************  SETUP VARIABLES ****************************/
	private final static ResourceManager INSTANCE = new ResourceManager();
	private final TextureOptions mNormalTextureOptions = TextureOptions.BILINEAR_PREMULTIPLYALPHA;//best looking graphics.
	public FixedStepEngine engine;
	public MainGameActivity activity;
	public SmoothCamera camera;
	public Context context;
	public VertexBufferObjectManager vbom;
	public int C_WIDTH;
	public int C_HEIGHT;
	
	private String mPreviousAssetBasePath = "";
	
	/**************************** FONTS ****************************/
	public Font fontBold36;
	public Font fontBold45;
	public Font fontBold72;
	public Font fontBold60;
	
	/**************************** SHARED PREFERENCES ****************************/
	public SharedPreferences prefs;
	public int currentLevel;
	public int furthestLevel;
	public int totalCoins;
	public String levelsData;
	public boolean SOUND;
	public int totalHearts;
	public int heartsCapacity,heartPrice;
	
	
	/**************************** OPENING SCENE ****************************/
	//CUT SCENE OPENING
	public BitmapTextureAtlas africaCutSceneAtlas;
	public TextureRegion ACSZooKeepLarge;
	public TextureRegion ACSMap;
	
	/**************************** AFRICA BACKGROUND ****************************/
	public BitmapTextureAtlas africaBackgroundAtlas;
	public TextureRegion ABgrass1;
	public TextureRegion ABgrass2;
	public TextureRegion ABgrass3;
	public TextureRegion ABgrass4;
	public TextureRegion ABgrass5;
	public TextureRegion ABTree1;
	public TextureRegion ABTree2;
	public TextureRegion ABSun;

	
	/**************************** MAIN MENU ****************************/
	public BitmapTextureAtlas africaMenuAtlas;
	public TextureRegion gameLogo;
	public TextureRegion giraffeFamilyShadow;
	
	/**************************** COMMON ITEMS ****************************/
	public BitmapTextureAtlas commonItemsAtlas;
	public TextureRegion playButton;
	public TextureRegion exitButton;
	public TextureRegion coinPileSingle;
	public TextureRegion rectangleButton;
	public TextureRegion rectangleButtonGlare;
	public TextureRegion heart;
	public TextureRegion heartEmpty;
	public TextureRegion plusButton;
	public TextureRegion coin;//56x55
	public TextureRegion shareButton;
	public TextureRegion storeButton;
	public TextureRegion star;
	public TextureRegion smallCircle;
	
	/**************************** ZOO KEEP WALK ****************************/
	public BitmapTextureAtlas zooKeepWalkAtlas;
	public TiledTextureRegion zooKeepWalk;

	/**************************** TITLE BAR MENU ****************************/
	public BitmapTextureAtlas titleBarMenuAtlas;
	public TextureRegion titleBar;
	public TextureRegion titleBarGlare;	

	/**************************** AFRICA MAP SCENE ****************************/
	public BitmapTextureAtlas africaMapAtlas;
	public TextureRegion AMMap;
	public TextureRegion AMGiraffeFace;
	public TextureRegion lock;
	
	/**************************** ROUNDED MENU ****************************/
	public BitmapTextureAtlas roundMenuBox790Atlas;
	public TextureRegion roundedMenuBox;

	/**************************** GIRAFFE ****************************/
	public BitmapTextureAtlas giraffeAtlas;
	public TiledTextureRegion giraffe;
	
	/**************************** ROCKS/STUMPS ****************************/
	public BitmapTextureAtlas rocksAtlas;
	public TextureRegion rock1;
	public TextureRegion rock2;
	public TextureRegion stump1;
	public TextureRegion stump2;
	
	/**************************** SNAKE/FROG ****************************/
	public BitmapTextureAtlas snakeFrogAtlas;
	public TiledTextureRegion snake;
	public TiledTextureRegion frog;
	public TiledTextureRegion venusTrap;
	
	/**************************** FLAG ****************************/
	public BitmapTextureAtlas flagAtlas;
	public TextureRegion flag;
	public TiledTextureRegion coinSpin;
	public TextureRegion flagSmall;
	public TextureRegion trackBallGPrint;
	public TextureRegion trackNetSmall;
	
	/**************************** FILL BAR LOAD UNLOAD ****************************/
	public BitmapTextureAtlas fillBarAtlas;
	public TextureRegion fillBar;
	public TextureRegion resourceBar;

	/**************************** LEVEL WIN POPUP ****************************/
	public BitmapTextureAtlas levelWinMenuAtlas;
	public TextureRegion happyGiraffe;
	
	/**************************** LEVEL LOSE POPUP ****************************/
	public BitmapTextureAtlas levelLoseMenuAtlas;
	public TextureRegion zooKeepFace;
	
	/**************************** ZOO KEEP CATCH GIRAFFE ****************************/
	public BitmapTextureAtlas zooKeepCatchAtlas;
	public TextureRegion zooKeepCatchGiraffe;
	public TextureRegion zooKeepNetSwoosh;
	
	/**************************** STORE ****************************/
	public BitmapTextureAtlas storeAtlas;
	public TextureRegion storeItemBox;
	public TextureRegion storeItemBottomShade;
	
	/**************************** TUTORIAL ****************************/
	public BitmapTextureAtlas tutorialAtlas;
	public TextureRegion tapFinger;
	
	/**************************** CONSTRUCTORS ****************************/
	public static ResourceManager getInstance(){
		return INSTANCE;
	}
	
	public void setup(FixedStepEngine pEngine, MainGameActivity pActivity, SmoothCamera pCamera, 
			VertexBufferObjectManager pVbom, Context pContext, int pWidth, int pHeight){
		getInstance().engine=pEngine;
		getInstance().activity=pActivity;
		getInstance().camera=pCamera;
		getInstance().context=pContext;
		getInstance().vbom=pVbom;
		getInstance().C_WIDTH=pWidth;
		getInstance().C_HEIGHT=pHeight;
		prefs= activity.getSharedPreferences("prefs", Activity.MODE_PRIVATE);
		
		SOUND = prefs.getBoolean("SOUND", true);
		
		String levelParseString="1_1_0_0_-50_-180,2_1_0_300_120_0";//,3_1_0_500_-120_0
		//UNCOMMENT TO RESET LEVELS DATA
		/*prefs.edit().putString("levelsData", levelParseString).commit();
		prefs.edit().putInt("currentLevel", 1).commit();
		prefs.edit().putInt("furthestLevel",1).commit();
		prefs.edit().putInt("totalCoins",50).commit();//*/
		
		totalCoins=prefs.getInt("totalCoins", 50);
		totalHearts=prefs.getInt("totalHearts", 2);
		heartsCapacity=prefs.getInt("heartsCapacity", 2);
		heartPrice=50;
		//levelNum_displayType_Stars_PriceToUnlock_WidthToNext_HeightToNext
		
		levelsData=prefs.getString("levelsData",levelParseString);
		currentLevel=prefs.getInt("currentLevel",1);
		furthestLevel=prefs.getInt("furthestLevel",1);
		
		//LOAD COMMON ASSETS
		loadToAfricaCutScene();
		loadCommonItems();
		loadMenuAssets();
		loadAfricaBackground();
		loadZooKeepWalk();
		loadFonts();
	}
	/**************************** GETTERS SETTERS ****************************/

	public void updateLevelsData(String data){
		levelsData=data;
		prefs.edit().putString("levelsData", levelsData).commit();
	}
	public void updateHearts(int livesIncDec){
		totalHearts=totalHearts+livesIncDec;
		prefs.edit().putInt("totalHearts", totalHearts).commit();
	}
	public void updateHeartsCapacity(int newCapacity){
		heartsCapacity=newCapacity;
		prefs.edit().putInt("heartsCapacity", heartsCapacity).commit();
	}
	public void updateCoins(int coinsIncDec){
		totalCoins=totalCoins+coinsIncDec;
		prefs.edit().putInt("totalCoins", totalCoins).commit();
	}
	public void setSound(boolean ifSound){
		prefs.edit().putBoolean("SOUND", ifSound).commit();
		SOUND=ifSound;
	}
	public void updateCurrentLevel(int level){
		currentLevel=level;
		prefs.edit().putInt("currentLevel", level).commit();
	}
	public void updateFurthestLevel(int level){
		furthestLevel=level;
		prefs.edit().putInt("furthestLevel", level).commit();
	}
	/**************************** LOAD AFRICA GAME SCENE ****************************/
	public void unloadAfricaGameScene(){
		unloadFlag();
		unloadRocks();
		unloadGiraffe();
		unloadZooKeepCapture();
		//unloadZooKeepWalk();
	}
	public void loadAfricaGameScene(){
		loadFlag();
		loadRocks();
		loadGiraffe();
		//loadZooKeepWalk();
		loadZooKeepCaptureGiraffe();
		loadSnakeFrog();
	}
	
	
	/**************************** SNAKE AND FROG LOAD UNLOAD ****************************/
	public void unloadSnakeFrog(){
		snakeFrogAtlas.unload();
		snake=null;
		frog=null;
		venusTrap=null;
		snakeFrogAtlas=null;
	}
	@SuppressWarnings("static-access")
	public void loadSnakeFrog(){
		mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("enemies/");
		
		snakeFrogAtlas= new BitmapTextureAtlas(activity.getTextureManager(),591,759, mNormalTextureOptions);
		
		frog= new BitmapTextureAtlasTextureRegionFactory()
		.createTiledFromAsset(snakeFrogAtlas, activity, "frogSprite.png", 0, 0, 3, 3);//591x339
		snake=new BitmapTextureAtlasTextureRegionFactory()
		.createTiledFromAsset(snakeFrogAtlas, activity, "snakeSprite.png", 0, 340, 2, 2);//450x213
		venusTrap=new BitmapTextureAtlasTextureRegionFactory()
		.createTiledFromAsset(snakeFrogAtlas, activity, "venusTrapSprite.png", 0, 654, 2, 1);//136x105	
		snakeFrogAtlas.load();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);
	}
	/**************************** TUTORIAL LOAD UNLOAD ****************************/
	public void unloadTutorial(){
		tutorialAtlas.unload();
		tapFinger=null;
		tutorialAtlas=null;
	}
	@SuppressWarnings("static-access")
	public void loadTutorial(){
		mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("tutorial/");
		
		tutorialAtlas= new BitmapTextureAtlas(activity.getTextureManager(),130,230, mNormalTextureOptions);
		
		tapFinger = new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(tutorialAtlas, activity, "tapFinger.png",0,0);//130x230
				
		tutorialAtlas.load();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);
	}
	
	/**************************** STORE LOAD UNLOAD ****************************/
	public void unloadStore(){
		unloadFillBar();
		storeAtlas.unload();
		storeItemBox=null;
		storeItemBottomShade=null;
		storeAtlas=null;
	}
	@SuppressWarnings("static-access")
	public void loadStore(){
		loadFillBar();
		
		mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("store/");
		
		storeAtlas= new BitmapTextureAtlas(activity.getTextureManager(),251,264, mNormalTextureOptions);
	
		storeItemBox=new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(storeAtlas, activity, "storeItemBox.png",0,0);//251x201
		
		storeItemBottomShade=new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(storeAtlas, activity, "storeItemBottomShade.png",0,202);//243x61
		
		storeAtlas.load();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);
		
	}
	
	/**************************** ZOO KEEP CATCH GIRAFFE LOAD UNLOAD ****************************/
	public void unloadZooKeepCapture(){
		zooKeepCatchAtlas.unload();
		zooKeepCatchGiraffe=null;
		zooKeepNetSwoosh=null;
		zooKeepCatchAtlas=null;	
	}
	@SuppressWarnings("static-access") 
	public void loadZooKeepCaptureGiraffe(){
		mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("enemies/");
		
		zooKeepCatchAtlas= new BitmapTextureAtlas(activity.getTextureManager(),532,589, mNormalTextureOptions);
		zooKeepCatchGiraffe=new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(zooKeepCatchAtlas, activity, "zooKeepCaptureGiraffe.png",0,0);//532x214
		zooKeepNetSwoosh=new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(zooKeepCatchAtlas, activity, "zooKeepNetSwoosh.png",0,215);//311x372
		
		zooKeepCatchAtlas.load();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);
	}
	
	/**************************** LEVEL LOSE POPUP LOAD UNLOAD ****************************/
	public void unloadLevelLose(){
		levelLoseMenuAtlas.unload();
		zooKeepFace=null;
		levelLoseMenuAtlas=null;	
	}
	@SuppressWarnings("static-access")
	public void loadLevelLose(){
		mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("enemies/");
		
		levelLoseMenuAtlas= new BitmapTextureAtlas(activity.getTextureManager(),402,302, mNormalTextureOptions);
		zooKeepFace=new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(levelLoseMenuAtlas, activity, "zooKeepLevelLose.png",0,0);//402x302
		
		levelLoseMenuAtlas.load();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);
	}
	/**************************** FILL BAR LOAD UNLOAD ****************************/
	public void unloadFillBar(){
		fillBarAtlas.unload();
		fillBar=null;
		resourceBar=null;
		fillBarAtlas=null;
	}
	@SuppressWarnings("static-access")
	public void loadFillBar(){
		mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("menu/");
		
		fillBarAtlas= new BitmapTextureAtlas(activity.getTextureManager(),250,73, mNormalTextureOptions);
	
		fillBar=new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(fillBarAtlas, activity, "fillBar.png",0,0);//250x34
		resourceBar=new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(fillBarAtlas, activity, "resourceBar.png",0,35);//199x37
		
		fillBarAtlas.load();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);
	}
	/**************************** LEVEL WIN POPUP LOAD UNLOAD ****************************/
	public void unloadLevelWin(){
		levelWinMenuAtlas.unload();
		happyGiraffe=null;
		levelWinMenuAtlas=null;
	}
	@SuppressWarnings("static-access")
	public void loadLevlWin(){
		mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("menu/");
		
		levelWinMenuAtlas= new BitmapTextureAtlas(activity.getTextureManager(),266,438, mNormalTextureOptions);
	
		happyGiraffe=new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(levelWinMenuAtlas, activity, "happyGiraffe.png",0,0);//266x438
		
		levelWinMenuAtlas.load();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);

	}
	/**************************** FLAG LOAD UNLOAD ****************************/

	public void unloadFlag(){
		flagAtlas.unload();
		flag=null;
		flagSmall=null;
		coinSpin=null;
		trackBallGPrint=null;
		trackNetSmall=null;
		flagAtlas=null;
	}
	@SuppressWarnings("static-access")
	public void loadFlag(){
		mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("game/");
		
		flagAtlas= new BitmapTextureAtlas(activity.getTextureManager(),314,464, mNormalTextureOptions);
		
		flag = new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(flagAtlas, activity, "flag.png",0,0);//192x407
		flagSmall=new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(flagAtlas, activity, "flagSmall.png",193,0);//53x41
		trackBallGPrint=new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(flagAtlas, activity, "trackBallGiraffePrint.png",247,0);//26x26
		trackNetSmall=new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(flagAtlas, activity, "trackZooNetSmall.png",273,0);//28x31
		coinSpin=new BitmapTextureAtlasTextureRegionFactory()
				.createTiledFromAsset(flagAtlas, activity, "coinSpin.png",0,408,6,1);//314x55
		
		flagAtlas.load();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);
	}
	/**************************** ROCKS LOAD UNLOAD ****************************/

	public void unloadRocks(){
		rocksAtlas.unload();
		rock1=null;
		rock2=null;
		stump1=null;
		stump2=null;
		rocksAtlas=null;
	}
	@SuppressWarnings("static-access")
	public void loadRocks(){
		mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("enemies/");
		
		rocksAtlas= new BitmapTextureAtlas(activity.getTextureManager(),345,683, mNormalTextureOptions);
		
		rock1 = new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(rocksAtlas, activity, "rock1.png",0,0);//155x77
		rock2 = new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(rocksAtlas, activity, "rock2.png",0,78);//237x120
		
		stump1 = new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(rocksAtlas, activity, "treeStump1.png",0,200);//345x162
		stump2 = new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(rocksAtlas, activity, "treeStump2.png",0,363);//345x320
				
		rocksAtlas.load();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);
	}
	/**************************** GIRAFFE LOAD UNLOAD****************************/

	public void unloadGiraffe(){
		giraffeAtlas.unload();
		giraffe=null;
		giraffeAtlas=null;
	}
	@SuppressWarnings("static-access")
	public void loadGiraffe(){
		mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("giraffe/");
		
		giraffeAtlas= new BitmapTextureAtlas(activity.getTextureManager(),750,485, mNormalTextureOptions);
		
		giraffe = new BitmapTextureAtlasTextureRegionFactory()
		.createTiledFromAsset(giraffeAtlas, activity, "giraffeLarge.png", 0, 0, 3, 2);//750x485
				
		giraffeAtlas.load();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);
	}
	/**************************** AFRICA MAP LOAD UNLOAD ****************************/

	public void unloadAfricaMap(){
		africaMapAtlas.unload();
		AMMap=null;
		AMGiraffeFace=null;
		africaMapAtlas=null;
	}
	@SuppressWarnings("static-access")
	public void loadAfricaMap(){
		mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("maps/");
		
		africaMapAtlas= new BitmapTextureAtlas(activity.getTextureManager(),846,711, mNormalTextureOptions);
		
		AMMap=new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(africaMapAtlas, activity, "africaMap.png",0,0);//846x587
		//AMLevelIcon=new BitmapTextureAtlasTextureRegionFactory()
	//	.createFromAsset(africaMapAtlas, activity, "circleLevel.png",0,588);//61x61
		AMGiraffeFace=new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(africaMapAtlas, activity, "giraffeFaceLevel.png",62,588);//63x71		
		lock=new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(africaMapAtlas, activity, "lockIcon.png",126,588);//54x71	
		
		africaMapAtlas.load();
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);
	}
	
	public void loadFonts(){
		fontBold36= FontFactory.create(engine.getFontManager(), 
				engine.getTextureManager(), 256, 256, 
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 36f, true, Color.WHITE);
		fontBold36.load();
		
		fontBold45= FontFactory.create(engine.getFontManager(), 
				engine.getTextureManager(), 256, 256, 
				Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD), 45f, true, Color.WHITE);
		fontBold45.load();
		
		fontBold72= FontFactory.create(engine.getFontManager(), 
				engine.getTextureManager(), 512, 512, 
				Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD), 72f, true, Color.WHITE);
		fontBold72.load();
		
		fontBold60=FontFactory.create(engine.getFontManager(), 
				engine.getTextureManager(), 256, 256, 
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 60f, true, Color.WHITE);
		fontBold60.load();
	}
	
	/**************************** TITLE BAR MENU LOAD UNLOAD ****************************/

	public void unloadTitleBarMenu(){
		titleBarMenuAtlas.unload();
		titleBar=null;
		titleBarGlare=null;
		titleBarMenuAtlas=null;
	}
	@SuppressWarnings("static-access")
	public void loadTitleBarMenu(){
		mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("menu/");
		
		titleBarMenuAtlas= new BitmapTextureAtlas(activity.getTextureManager(),1021,180, mNormalTextureOptions);
	
		titleBar=new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(titleBarMenuAtlas, activity, "titleBarBody.png",0,0);//1021x120
		
		titleBarGlare=new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(titleBarMenuAtlas, activity, "titleBarGlare.png",0,121);//1014x56
		
		titleBarMenuAtlas.load();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);

	}
	/**************************** ROUNDED MENU BOX LOAD UNLOAD ****************************/
	public void unloadRoundedMenuBox790(){
		roundMenuBox790Atlas.unload();
		roundedMenuBox=null;
		roundMenuBox790Atlas=null;
	}
	@SuppressWarnings("static-access")
	public void loadRoundedMenuBox790(){
		mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("menu/");
		
		roundMenuBox790Atlas= new BitmapTextureAtlas(activity.getTextureManager(),790,1093, mNormalTextureOptions);
		
		roundedMenuBox=new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(roundMenuBox790Atlas, activity, "roundedMenuBig.png",0,0);//790x618
		
		roundMenuBox790Atlas.load();
	}
	/**************************** MAIN MENU ASSETS LOAD UNLOAD ****************************/
	public void unloadMenuAssets(){
		africaMenuAtlas.unload();
		gameLogo=null;
		giraffeFamilyShadow=null;
		africaMenuAtlas=null;
	}
	
	@SuppressWarnings("static-access")
	public void loadMenuAssets(){
		mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("menu/");
		
		africaMenuAtlas= new BitmapTextureAtlas(activity.getTextureManager(),812,500, mNormalTextureOptions);
		
		gameLogo=new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(africaMenuAtlas, activity, "gameLogo.png",0,0);//812x304
		giraffeFamilyShadow=new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(africaMenuAtlas, activity, "giraffeFamilyShadow.png",0,304);//173x130

		africaMenuAtlas.load();
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);
	}
	
	
	/**************************** LOAD COMMON ASSETS ****************************/
	@SuppressWarnings("static-access")
	public void loadCommonItems(){
		mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("common/");
		
		commonItemsAtlas= new BitmapTextureAtlas(activity.getTextureManager(),292,890, mNormalTextureOptions);
		
		playButton=new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(commonItemsAtlas, activity, "playButton.png",0,0);//193x196
		exitButton=new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(commonItemsAtlas, activity, "exitButton.png",0,197);//91x92 82x83  
		coinPileSingle=new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(commonItemsAtlas, activity, "buyCoinPileIcon.png",83,197);//62x77
		shareButton=new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(commonItemsAtlas, activity, "shareButton.png",0,280);//194x196
		
		heart=new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(commonItemsAtlas, activity, "heart.png",0,478);//46x46
		heartEmpty=new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(commonItemsAtlas, activity, "emptyHeart.png",47,478);//46x46
		plusButton=new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(commonItemsAtlas, activity, "plusButton.png",94,478);//35x36
		coin=new BitmapTextureAtlasTextureRegionFactory()
			.createFromAsset(commonItemsAtlas, activity, "coin.png",130,478);//56x55
			
		rectangleButton=new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(commonItemsAtlas, activity, "rectangleButton.png",0,534);//292x103
		rectangleButtonGlare=new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(commonItemsAtlas, activity, "rectangleButtonGlare.png",0,638);//285x46
		storeButton=new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(commonItemsAtlas, activity, "storeButton.png",0,685);//132x143
		star=new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(commonItemsAtlas, activity, "starLarge.png",133,685);//116x111
		
		smallCircle=new BitmapTextureAtlasTextureRegionFactory()
			.createFromAsset(commonItemsAtlas, activity, "circleLevel.png",0,829);//61x61
		
		commonItemsAtlas.load();
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);

	}
	/**************************** AFRICA BACKGROUND LOAD UNLOAD ****************************/

	public void unloadAfricaBackground(){
		africaBackgroundAtlas.unload();
		ABgrass1=null;
		ABgrass2=null;
		ABgrass3=null;
		ABgrass4=null;
		ABTree1=null;
		ABTree2=null;
		ABSun=null;
		africaBackgroundAtlas=null;
	}
	//AFRICA BACKGROUND
	@SuppressWarnings("static-access")
	public void loadAfricaBackground(){
		mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("backgrounds/");
		
		africaBackgroundAtlas = new BitmapTextureAtlas(activity.getTextureManager(),645,1493, mNormalTextureOptions);
		//GRASS
		ABgrass1= new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(africaBackgroundAtlas, activity, "grass1.png",0,0);//535x91
		ABgrass2= new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(africaBackgroundAtlas, activity, "grass2.png",0,91);//325x94
		ABgrass3= new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(africaBackgroundAtlas, activity, "grass3.png",0,185);//454x116
		ABgrass4= new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(africaBackgroundAtlas, activity, "grass4.png",0,301);//571x136
		ABgrass5= new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(africaBackgroundAtlas, activity, "grass5.png",0,437);//645x140
		//TREES
		ABTree1= new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(africaBackgroundAtlas, activity, "treeRed.png",0,577);//482x244
		ABTree2= new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(africaBackgroundAtlas, activity, "treeBendGreen.png",0,821);//297x199
		//SUN AND BACKGROUND
		ABSun= new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(africaBackgroundAtlas, activity, "africaSun.png",0,1020);//472x472
		//ABBackColors= new BitmapTextureAtlasTextureRegionFactory()
		//.createFromAsset(africaBackgroundAtlas, activity, "mapToGiraffe.png",0,805);

		africaBackgroundAtlas.load();
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);
	}

	/**************************** ZOO KEEP WALKING ****************************/
	public void unloadZooKeepWalk(){
		zooKeepWalkAtlas.unload();
		zooKeepWalk=null;
		zooKeepWalkAtlas=null;
	}
	@SuppressWarnings("static-access")
	public void loadZooKeepWalk(){
		mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("enemies/");
		
		zooKeepWalkAtlas = new BitmapTextureAtlas(activity.getTextureManager(),771,910, mNormalTextureOptions);

		zooKeepWalk	=new BitmapTextureAtlasTextureRegionFactory()
		.createTiledFromAsset(zooKeepWalkAtlas, activity, "zooKeepWalk.png",0,0,2,2);//771x910

		zooKeepWalkAtlas.load();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);
	}
	
	
	/**************************** OPENING SCENE LOAD UNLOAD ****************************/
	public void unloadToAfricaCutScene(){
		africaCutSceneAtlas.unload();
		ACSZooKeepLarge=null;
		ACSMap=null;
		africaCutSceneAtlas=null;
	}
	@SuppressWarnings("static-access")
	public void loadToAfricaCutScene(){
		mPreviousAssetBasePath = BitmapTextureAtlasTextureRegionFactory.getAssetBasePath();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("cutscene/");
		
		africaCutSceneAtlas = new BitmapTextureAtlas(activity.getTextureManager(),1025,1560, mNormalTextureOptions);
		
		//ZOO KEEPER AND MAP TO AFRICA
		ACSZooKeepLarge= new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(africaCutSceneAtlas, activity, "evilZooKeep.png",0,0);//1000x805
		ACSMap= new BitmapTextureAtlasTextureRegionFactory()
		.createFromAsset(africaCutSceneAtlas, activity, "mapToGiraffe.png",0,805);//1025x720

		africaCutSceneAtlas.load();
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath(mPreviousAssetBasePath);
	}

}
