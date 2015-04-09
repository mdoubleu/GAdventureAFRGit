package scenes;

import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import popups.StoreSceneBuyItem;

import com.wine.core.SceneManager;

import android.graphics.Typeface;
import android.util.Log;

public class StoreScene extends BaseScene{
	public static StoreScene INSTANCE;
	public Sprite popupBox;
	public Text heartsText;
	public Text coinsText;
	public static StoreScene getInstance(){
		return INSTANCE;
	}
	@Override
	public void createScene() {
	 	setTouchAreaBindingOnActionDownEnabled(true);
		INSTANCE=this;
		this.getBackground().setColor(.411f,.411f,.411f);
		
		Sprite titleBarTop=new Sprite(0,0,rm.titleBar,vbom);
		titleBarTop.setColor(.109f, .47f, .294f);
		
		Sprite titleBarTopShadow=new Sprite(0,0,titleBarTop.getWidth()+10,titleBarTop.getHeight(),
				rm.titleBar,vbom);
		titleBarTopShadow.setColor(.2f, .2f, .2f);
		
		Sprite titleBarTopGlare=new Sprite(0,0,rm.titleBarGlare,vbom);
		titleBarTopGlare.setColor(.5f, .7f, .533f);
		titleBarTopGlare.setPosition(getCenterX(titleBarTop.getWidth(),titleBarTopGlare.getWidth()),4);
		titleBarTop.attachChild(titleBarTopGlare);
		
		titleBarTop.setPosition(getCenterX(titleBarTop.getWidth()), -4);
		titleBarTopShadow.setPosition(getCenterX(titleBarTopShadow.getWidth()),1);
		
		Sprite exitButton=new Sprite(0,0,rm.exitButton,vbom){
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
					SceneManager.getInstance().loadMenuSceneFromScene();
				}
				return true;
			}
		};
		exitButton.setPosition(titleBarTop.getWidth()-exitButton.getWidth()-10, 
				getCenterY(titleBarTop.getHeight(),exitButton.getHeight()));
		titleBarTop.attachChild(exitButton);
		
		Font fontDefault72Bold= FontFactory.create(engine.getFontManager(), 
				engine.getTextureManager(), 512, 512, 
				Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD), 72f, true, android.graphics.Color.WHITE);
		fontDefault72Bold.load();
		Font fontDefault45Bold= FontFactory.create(engine.getFontManager(), 
				engine.getTextureManager(), 512, 512, 
				Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD), 45f, true, android.graphics.Color.WHITE);
		fontDefault45Bold.load();
		
		Text levelText=new Text(0,0, 
				fontDefault72Bold, 
				"Store",new TextOptions(HorizontalAlign.CENTER),vbom);
		levelText.setColor(0,0,0,1f);
		levelText.setPosition(getCenterX(titleBarTop.getWidth(),levelText.getWidth()),
				getCenterY(titleBarTop.getHeight(),levelText.getHeight()));
		Text levelTextWhite=new Text(0,-2, 
				fontDefault72Bold, 
				"Store",new TextOptions(HorizontalAlign.CENTER),vbom);
		levelText.attachChild(levelTextWhite);
		titleBarTop.attachChild(levelText);
		
		attachChild(titleBarTopShadow);
		attachChild(titleBarTop);
		
		Sprite titleBarBottom=new Sprite(0,0,rm.titleBar,vbom);
		titleBarBottom.setColor(titleBarTop.getColor());
		Sprite titleBarBottomShadow=new Sprite(0,0,rm.titleBar,vbom);
		titleBarBottomShadow.setColor(.2f,.2f,.2f);
		
		Sprite titleBarBottomGlare=new Sprite(0,0,rm.titleBarGlare,vbom);
		titleBarBottomGlare.setColor(titleBarTopGlare.getColor());
		titleBarBottomGlare.setPosition(getCenterX(titleBarBottom.getWidth(),titleBarBottomGlare.getWidth()),2);
		titleBarBottom.attachChild(titleBarBottomGlare);
		//Attach lots of things
		
		Sprite heartCount=new Sprite(0,0,rm.heart,vbom);
		Sprite coinCount=new Sprite(0,0,rm.coin,vbom);
		
		Sprite resourceBarCoin=new Sprite(0,0,rm.resourceBar,vbom);
		resourceBarCoin.setColor(.5f,.5f,.5f);
		Sprite resourceBarCoinShadow=new Sprite(0,0,resourceBarCoin.getWidth()+3,
				resourceBarCoin.getHeight()+5,rm.resourceBar,vbom);
		resourceBarCoinShadow.setColor(.3f,.3f,.3f);
		
		resourceBarCoin.setPosition(getCenterX(resourceBarCoinShadow.getWidth(),resourceBarCoin.getWidth()),
				getCenterY(resourceBarCoinShadow.getHeight(), resourceBarCoin.getHeight()));
		resourceBarCoinShadow.attachChild(resourceBarCoin);
		
		coinCount.setPosition(-coinCount.getWidth()/2, 
				getCenterY(resourceBarCoinShadow.getHeight(),coinCount.getHeight()));
		resourceBarCoinShadow.attachChild(coinCount);
		
		coinsText=new Text(0,0, 
				fontDefault45Bold, 
				""+rm.totalCoins,new TextOptions(HorizontalAlign.CENTER),vbom);
		coinsText.setPosition(coinCount.getX()+coinCount.getWidth()+15,
				getCenterY(resourceBarCoinShadow.getHeight(),coinsText.getHeight()));
		resourceBarCoinShadow.attachChild(coinsText);
		
		Sprite resourceBarHeart=new Sprite(0,0,rm.resourceBar,vbom);
		resourceBarHeart.setColor(.5f,.5f,.5f);
		Sprite resourceBarHeartShadow=new Sprite(0,0,resourceBarHeart.getWidth()+3,
				resourceBarHeart.getHeight()+5,rm.resourceBar,vbom);
		resourceBarHeartShadow.setColor(.3f,.3f,.3f);
		
		resourceBarHeart.setPosition(getCenterX(resourceBarHeartShadow.getWidth(),resourceBarHeart.getWidth()),
				getCenterY(resourceBarHeartShadow.getHeight(), resourceBarHeart.getHeight()));
		resourceBarHeartShadow.attachChild(resourceBarHeart);
		
		heartCount.setPosition(-heartCount.getWidth()/2f, 
				getCenterY(resourceBarHeartShadow.getHeight(),heartCount.getHeight()));
		resourceBarHeartShadow.attachChild(heartCount);
		
		heartsText=new Text(0,0, 
				fontDefault45Bold, 
				""+rm.totalHearts,new TextOptions(HorizontalAlign.CENTER),vbom);
		heartsText.setPosition(coinCount.getX()+coinCount.getWidth()+15,
				getCenterY(resourceBarHeartShadow.getHeight(),heartsText.getHeight()));
		resourceBarHeartShadow.attachChild(heartsText);
		
		resourceBarHeartShadow.setPosition(resourceBarCoinShadow.getWidth()+60, 0);
		
		resourceBarCoinShadow.attachChild(resourceBarHeartShadow);
		
		resourceBarCoinShadow.setPosition(getCenterX(titleBarBottom.getWidth(),
				resourceBarCoinShadow.getWidth()+resourceBarHeartShadow.getWidth()+60), 
				getCenterY(titleBarBottom.getHeight(),resourceBarCoinShadow.getHeight()));
		
		titleBarBottom.attachChild(resourceBarCoinShadow);
		
		titleBarBottomShadow.setPosition(getCenterX(titleBarBottomShadow.getWidth()), 720-titleBarBottomShadow.getHeight());
		titleBarBottom.setPosition(getCenterX(titleBarBottom.getWidth()), 720-titleBarBottom.getHeight());//+2
		attachChild(titleBarBottomShadow);
		attachChild(titleBarBottom);
		
		Sprite storeItemBox=new Sprite(0,0,rm.storeItemBox,vbom);
		storeItemBox.setColor(.498f,.764f,.317f);
		Sprite storeItemBoxShadow=new Sprite(0,0,rm.storeItemBox,vbom){
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
					//BUY POPUP
					if(this.getColor().equals(Color.RED)){
						//LOAD WATCH VIDEO BOX
					}else{
						if(rm.heartsCapacity>rm.totalHearts){
							Log.d("BUY", "buy store Item");
							StoreSceneBuyItem scbi=new StoreSceneBuyItem();
							Integer price=(Integer)this.getUserData();
							Sprite image=new Sprite(0,0,68,66,rm.heart,vbom);
							popupBox=scbi.addBuyPopup(StoreScene.getInstance(),
									"Buy 1 Heart for "+price+ " coins?",price,image);
						}else{
							Log.d("BUY", "buy store Item NO");
						}
					}
					
					this.setScale(1f);
				}
				return true;
			}
		};
		storeItemBoxShadow.setUserData(50);
		storeItemBoxShadow.setColor(.317f,.521f,.207f);

		Sprite storeItemShade=new Sprite(0,0,rm.storeItemBottomShade,vbom);
		storeItemShade.setColor(.439f,.682f,.282f);
		
		storeItemShade.setPosition(getCenterX(storeItemBox.getWidth(), storeItemShade.getWidth()),
				storeItemBox.getHeight()-storeItemShade.getHeight()-4);
		
		Text storeItemText=new Text(0,0, 
				fontDefault45Bold, 
				"1 Heart",new TextOptions(HorizontalAlign.CENTER),vbom);
		storeItemText.setPosition(getCenterX(storeItemBox.getWidth(),storeItemText.getWidth()), 10);
		storeItemBox.attachChild(storeItemText);
		
		Sprite storeItem=new Sprite(0,0,rm.heart,vbom);
		storeItem.setPosition(getCenterX(storeItemBox.getWidth(),storeItem.getWidth()),
				storeItemText.getY()+storeItemText.getHeight()+15);
		
		storeItemBox.attachChild(storeItem);
		
		Sprite coin=new Sprite(0,0,40,40,rm.coin,vbom);
		
		int price=50;
		Text storeItemPrice=new Text(0,0, 
				fontDefault45Bold, 
				""+price,new TextOptions(HorizontalAlign.CENTER),vbom);
		if(price>rm.totalCoins ){
			storeItemPrice.setColor(Color.RED);
		}
		
		coin.setPosition(-coin.getWidth()-5, getCenterY(storeItemPrice.getHeight(),coin.getHeight()));
		
		storeItemPrice.attachChild(coin);
		
		storeItemPrice.setPosition(getCenterX(storeItemShade.getWidth(),
				storeItemPrice.getWidth()-coin.getWidth()-5), 
				getCenterY(storeItemShade.getHeight(),storeItemPrice.getHeight()));
		
		storeItemShade.attachChild(storeItemPrice);
		
		storeItemBox.attachChild(storeItemShade);
		storeItemBoxShadow.setPosition(25, titleBarTop.getHeight()+28);
		storeItemBox.setPosition(0, -8);
		storeItemBoxShadow.attachChild(storeItemBox);
		
		registerTouchArea(exitButton);
		registerTouchArea(storeItemBoxShadow);
		attachChild(storeItemBoxShadow);
		
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
			updateCoinsHeartsDisplay();
		}
	}
	public void updateCoinsHeartsDisplay(){
		heartsText.setText(""+rm.totalHearts);
		coinsText.setText(""+rm.totalCoins);
	}
	public void updateHearts(int increment){
		rm.updateHearts(increment);
		Log.d("TOTAL HEARTS", "2TOT HEARTS"+rm.totalHearts);
		//UPDATE HEARTS TEXT AND COINS TEXT
		//GameSceneFunctions.getInstance().removeHearts();
		//GameSceneFunctions.getInstance().setHearts(heartsUsed);
	}
	@Override
	public void disposeScene() {
		rm.unloadStore();
		rm.unloadTitleBarMenu();
		unregisterAllTouchAreas();
		this.detachChildren();
		this.dispose();
		
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		
	}

}
