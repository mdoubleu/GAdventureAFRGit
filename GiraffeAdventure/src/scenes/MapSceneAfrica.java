package scenes;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import popups.MapSceneBuyPopup;

import com.wine.core.SceneManager;
import com.wine.core.SoundManager;

import functions.CommonFunctions;
import functions.GameSceneFunctions;
import android.opengl.GLES20;
import android.util.Log;

public class MapSceneAfrica extends BaseScene implements IOnSceneTouchListener{
	public Sprite map;
	public Sprite playerFace;
	public Sprite popupBox;
	public int levelStars,levelNum;
	private static  MapSceneAfrica INSTANCE;
	public static MapSceneAfrica getInstance(){return INSTANCE;}

	@Override
	public void createScene() {
		INSTANCE=this;
		setOnSceneTouchListener(this);
	 	this.setTouchAreaBindingOnActionDownEnabled(true);
	 	CommonFunctions.setMainHud(vbom);
	 	if(rm.SOUND){
	 		if(!SoundManager.getInstance().mainMusicLoop.isPlaying()){
	 			SoundManager.getInstance().mainMusicLoop.play();
	 		}
		}

		map=new Sprite(0,0,rm.AMMap,vbom);
		
		playerFace=new Sprite(-1000,0,rm.AMGiraffeFace,vbom);
		
		Sprite titleBarTop=new Sprite(0,0,rm.titleBar,vbom);
		titleBarTop.setColor(.854f, .721f, .533f);
		
		Sprite titleBarTopShadow=new Sprite(0,0,titleBarTop.getWidth()+5,titleBarTop.getHeight(),
				rm.titleBar,vbom);
		titleBarTopShadow.setColor(.2f, .2f, .2f);
		
		Sprite titleBarTopGlare=new Sprite(0,0,rm.titleBarGlare,vbom);
		titleBarTopGlare.setColor(.917f, .835f, .686f);
		titleBarTopGlare.setPosition(getCenterX(titleBarTop.getWidth(),titleBarTopGlare.getWidth()),4);
		titleBarTop.attachChild(titleBarTopGlare);
		
		titleBarTop.setPosition(getCenterX(titleBarTopShadow.getWidth(),titleBarTop.getWidth()), -4);
		titleBarTopShadow.setPosition(getCenterX(titleBarTopShadow.getWidth()),1);
		titleBarTopShadow.attachChild(titleBarTop);
	
		map.setPosition(getCenterX(map.getWidth()), titleBarTop.getHeight()+10);
		//this.setColor(Color.BLACK);
		this.getBackground().setColor(.647f,.47f,.317f,1f);
		
		//EXIT BUTTON NEEDS ON TOUCH LISTENER:
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
					//SceneManager.getInstance().createMapAfricaScene();
					SceneManager.getInstance().loadMenuSceneFromScene();
				}
				return true;
			}
		};
		
		exitButton.setPosition(titleBarTop.getWidth()-exitButton.getWidth()-10, 
				getCenterY(titleBarTop.getHeight(),exitButton.getHeight()));
		titleBarTop.attachChild(exitButton);
		registerTouchArea(exitButton);
		
		Text levelText=new Text(0,0, 
				rm.fontBold72, 
				"East Africa",new TextOptions(HorizontalAlign.CENTER),vbom);
		levelText.setColor(0,0,0,1f);
		levelText.setPosition(getCenterX(titleBarTop.getWidth(),levelText.getWidth()),
				getCenterY(titleBarTop.getHeight(),levelText.getHeight()));
		
		Text levelTextWhite=new Text(0,-2, 
				rm.fontBold72, 
				"East Africa",new TextOptions(HorizontalAlign.CENTER),vbom);
		levelText.attachChild(levelTextWhite);
		
		titleBarTop.attachChild(levelText);
		
		/**********************PLAY BUTTON****************************/
		Sprite playButton=new Sprite(0,0,142,143,rm.playButton,vbom){
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
					if(rm.SOUND){
						//SoundManager.getInstance().mainMusicLoop.stop();
					 }
					rm.updateCurrentLevel(levelNum);
					SceneManager.getInstance().createGameSceneAfrica(levelStars);
				}
				return true;
			}
		};
		registerTouchArea(playButton);
		playButton.setPosition(map.getX()+map.getWidth()-100, getCenterY(playButton.getHeight())+70);
		
		attachChild(map);
		attachChild(titleBarTopShadow);
		attachChild(playButton);
		loadMapData();
		playerFace.setZIndex(3);
		map.attachChild(playerFace);
	}
	public void refreshMap(){
		for (int i=0; i<map.getChildCount(); i++){
			Sprite tempObj=(Sprite)map.getChildByIndex(i);
			if(unregisterTouchArea(tempObj)){}
		}
		map.detachChildren();
		loadMapData();
		map.attachChild(playerFace);
	}
	public void loadMapData(){
		//levelNum_displayType_Difficulty_Stars_PriceToUnlock_WidthToNext_HeightToNext
		String[] levelParts = rm.levelsData.split(",");
		
		int currentLevel=rm.currentLevel;
		
		float currentY=map.getHeight()-rm.smallCircle.getHeight();
		float currentX=150;
		float lastWidth=0;
		float lastHeight=0;
		int levelPathSizeW=10;
		int levelPathSizeH=7;
		 
		 for (int i = 0; i < levelParts.length; i++) {
			 Log.d("current level", "i:" +i);
			 if(currentLevel>=i){
				String[] levelPiece=levelParts[i].split("_");
				//int level=Integer.parseInt(levelPiece[0]);
				int displayType=Integer.parseInt(levelPiece[1]);
				int stars=Integer.parseInt(levelPiece[2]);
				int priceToUnlock=Integer.parseInt(levelPiece[3]);
				int widthToNext=Integer.parseInt(levelPiece[4]);
				int heightToNext=Integer.parseInt(levelPiece[5]);
				int levelNumber=i+1;
				Sprite levelIcon=setLevelIcon(currentX,currentY,displayType,priceToUnlock,stars,levelNumber);
				levelIcon.setZIndex(2);
				if(priceToUnlock==0){
					 setStars(stars,levelIcon);
				}
				map.attachChild(levelIcon);
				 Log.d("current level", "i:" +i+ " currentx"+currentX+ "curry"+currentY);
				 if(levelNumber==currentLevel){
					 playerFace.setPosition(levelIcon.getX()+
								getCenterX(levelIcon.getWidth(),playerFace.getWidth()), 
								levelIcon.getY()+getCenterY(levelIcon.getHeight(),playerFace.getHeight())-27);
					 levelStars=stars;
					 levelNum=levelNumber;
				 }
				 if(i>0 ){//make white dots line && priceToUnlock==0
					double length=Math.sqrt(((lastWidth*lastWidth)+(lastHeight*lastHeight)));
					//-(levelIcon.getHeight()*2);
					Log.d("LENGTH", "L:" +length+ "last height"+lastHeight+ "last wid"+lastWidth);
					int totalDots=(int)(length/(levelIcon.getHeight()/1.3));
					
					float widthBetween=lastWidth/totalDots;
					float heightBetween=lastHeight/totalDots;
					float tempDotX=currentX-lastWidth+widthBetween+
							getCenterX(levelIcon.getWidth(),levelPathSizeW);
					float tempDotY=currentY-lastHeight+heightBetween+
							getCenterY(levelIcon.getHeight(),levelPathSizeH);
					
					for(int j=0; j<totalDots-1; j++){
						 Sprite dot=new Sprite(tempDotX,tempDotY,levelPathSizeW,levelPathSizeH,
								 rm.smallCircle,vbom); 
						 dot.setZIndex(1);
						 map.attachChild(dot);
						 tempDotX+=widthBetween;
						 tempDotY+=heightBetween;
					}
				 }
				 currentY+=heightToNext;
				 currentX+=widthToNext;
				 lastWidth=widthToNext;
				 lastHeight=heightToNext;
				 map.sortChildren();
	
			 }
			
		 }
	
	}
	public void attachBuyPopup(Sprite levelIcon){
		MapSceneBuyPopup m=new MapSceneBuyPopup();
		Integer price=Integer.parseInt((String) levelIcon.getUserData());
		popupBox=m.addBuyPopup(MapSceneAfrica.getInstance(),"Unlock level for "+price+ " coins?",price);
		popupBox.setPosition(getCenterX(popupBox.getWidth()), 
				getCenterY(popupBox.getHeight()));
		MapSceneAfrica.getInstance().attachChild(popupBox);
	}
	public Sprite setLevelIcon(float currentX,float currentY,
			int displayType,int priceTounlock, int stars, int levelNumber){
		Sprite levelIcon=new Sprite(currentX,currentY,60,28,
					rm.smallCircle,vbom){
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, 
					final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				//PUT GIRAFFE ON ITC
				if(this.getTag()==1){//REGULAR
					playerFace.setPosition(this.getX()+
							getCenterX(this.getWidth(),playerFace.getWidth()), 
							this.getY()+getCenterY(this.getHeight(),playerFace.getHeight())-27);
					int[] tempUserData=(int[])this.getUserData();
					 levelNum=tempUserData[0];
					 levelStars=tempUserData[1];
				}else if(this.getTag()==2 && popupBox==null){//LOCKED MUST BUY
					attachBuyPopup(this);
				}
				return true;
			}
		};
		registerTouchArea(levelIcon);
		
		Sprite levelIconCenter=new Sprite(0,0,levelIcon.getWidth()-11,
				levelIcon.getHeight()-11,rm.smallCircle,vbom);
		levelIconCenter.setPosition(getCenterX(levelIcon.getWidth(),levelIconCenter.getWidth()), 
				getCenterY(levelIcon.getHeight(),levelIconCenter.getHeight()));
		levelIcon.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		levelIconCenter.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		
		Color outerColor=null;
		Color innerColor=null;
		//Greens
		//outerColor=new Color(.44f,.56f,.137f);
		//innerColor=new Color(.66f,.81f,.21f);
		//Purple Outer, white inner
		outerColor=new Color(1f,.937f,.2f);
		innerColor=new Color(.329f,.725f,.282f);
		//innerColor=new Color(1,1f,.6f);
		/*if(difficulty==1){
			//Greens
			outerColor=new Color(.44f,.56f,.137f);
			innerColor=new Color(.66f,.81f,.21f);
		}else if(difficulty==2){
			//yellows
			outerColor=new Color(.85f,.68f,.156f);
			innerColor=new Color(1,.89f,.01f);
		}else if(difficulty==3){
			//reds
			outerColor=new Color(.74f,.129f,.16f);
			innerColor=new Color(.95f,.44f,.36f);
		}*/
		levelIcon.setColor(outerColor);
		levelIconCenter.setColor(innerColor);
		levelIcon.attachChild(levelIconCenter);
		if(priceTounlock==0){//levelIcon is normal, open load it
			levelIcon.setTag(1);
			levelIcon.setUserData(new int[]{levelNumber,stars});
			
		}else if(priceTounlock>0){//LOCKED
			outerColor=new Color(.7f,.7f,.7f);
			levelIcon.setColor(outerColor);
			levelIconCenter.setColor(new Color(.9f,.9f,.9f));
			//levelIcon.setAlpha(.5f);
			//levelIconCenter.setAlpha(.5f);
			levelIcon.setTag(2);
			levelIcon.setUserData(""+priceTounlock);
			Sprite lock=new Sprite(0,0,27,38,rm.lock,vbom);
			lock.setPosition(getCenterX(levelIcon.getWidth(),lock.getWidth()), 
				getCenterY(levelIcon.getHeight(),lock.getHeight()));
			levelIcon.attachChild(lock);
		}
		
		
		
		return levelIcon;
		
	}
	public void removeBuyPopup(int price){
		Sprite[] touchAreas=(Sprite[])popupBox.getUserData();
		for(int i=0; i<touchAreas.length; i++){
			unregisterTouchArea(touchAreas[i]);
		}
		popupBox.detachChildren();
		popupBox.detachSelf();
		popupBox.dispose();
		popupBox=null;
		rm.unloadRoundedMenuBox790();
		//type=0 x button : type=1 buy button
		if(price>0){
			//PURCHASE ITEM AND REFRESH MAP
			new GameSceneFunctions();
			rm.updateCoins(-price);
			GameSceneFunctions.getInstance().updateLevel(rm.furthestLevel+1, 0, 0);
			rm.updateCurrentLevel(rm.currentLevel+1);
			rm.updateFurthestLevel(rm.furthestLevel+1);
			refreshMap();
		}
		
	}
	
	public void setStars(int stars, Sprite levelIcon){
		int starDimension=28;
		Color full=new Color(.925f, .905f, .047f);
		Color emptyGrey=new Color(.6f,.6f,.6f);

		Sprite star1=new Sprite(0,0,starDimension,starDimension,rm.star,vbom);
		star1.setColor(full);
		Sprite star2;
		Sprite star3;
		float yPos=levelIcon.getY();
		
		if(stars==3){
			//3stars
			star2=new Sprite(0,0,starDimension,starDimension,rm.star,vbom);
			star2.setColor(full);
			star3=new Sprite(0,0,starDimension,starDimension,rm.star,vbom);
			star3.setColor(full);
		}else if(stars==2){
			//2stars
			star2=new Sprite(0,0,starDimension,starDimension,rm.star,vbom);
			star2.setColor(full);
			star3=new Sprite(0,0,starDimension,starDimension,rm.star,vbom);
			star3.setColor(emptyGrey);
		}else if(stars==1){
			star2=new Sprite(0,0,starDimension,starDimension,rm.star,vbom);
			star2.setColor(emptyGrey);
			star3=new Sprite(0,0,starDimension,starDimension,rm.star,vbom);
			star3.setColor(emptyGrey);
		}else if (stars==0){
			return;
			/*star1.setColor(emptyGrey);
			star2=new Sprite(0,0,starDimension,starDimension,rm.star,vbom);
			star2.setColor(emptyGrey);
			star3=new Sprite(0,0,starDimension,starDimension,rm.star,vbom);
			star3.setColor(emptyGrey);*/
		}else{
			return;
		}
		star2.setPosition(star1.getWidth(), -10);
		star1.attachChild(star2);
		star3.setPosition(star2.getWidth(), 10);
		star2.attachChild(star3);
		star1.setPosition(levelIcon.getX()+getCenterX(levelIcon.getWidth(),star1.getWidth()*3), 
				yPos-star1.getHeight()+5);//-5
		star1.setZIndex(2);
		map.attachChild(star1);
	}
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		//playerFace.setPosition(-1000,-1000);
		return false;
	}
	@Override
	public void disposeScene() {
		playerFace.dispose();
		playerFace=null;
		map.dispose();
		map=null;
		//UNREGISTER TOUCHES
		unregisterAllTouchAreas();
		detachChildren();
		rm.unloadAfricaMap();
		rm.unloadTitleBarMenu();
	}
	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance().loadMenuSceneFromScene();
	}
}
