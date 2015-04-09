package popups;

import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import scenes.BaseScene;
import scenes.StoreScene;

public class StoreSceneBuyItem extends BaseScene{

	public Sprite addBuyPopup(StoreScene storeScene, String question, int price, Sprite image){
		Sprite rectangleBig=new Sprite(0,0,639,480,rm.roundedMenuBox, vbom);
		rectangleBig.setColor(.4f,.4f,.4f);
		
		Sprite rectangleOuter=new Sprite(0,0,646,490,rm.roundedMenuBox, vbom);
		rectangleOuter.setColor(.1f,.1f,.1f);
		
		
		Text levelText=new Text(0,0, 
				rm.fontBold72, 
				"Buy?",new TextOptions(HorizontalAlign.CENTER),vbom);
		levelText.setColor(0,0,0,1f);
		levelText.setPosition(getCenterX(rectangleBig.getWidth(),levelText.getWidth()),
				5);
		
		Text levelTextWhite=new Text(0,-2, 
				rm.fontBold72, 
				"Buy?",new TextOptions(HorizontalAlign.CENTER),vbom);
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
					StoreScene.getInstance().removePopup(0);
				}
				return true;
			}
		};
		exitButton.setPosition(rectangleBig.getWidth()-exitButton.getWidth()-10, 
				6);
	
		rectangleBig.attachChild(exitButton);

		Sprite rectangleSmall=new Sprite(0,0,rectangleBig.getWidth()-18,
				rectangleBig.getHeight()-exitButton.getHeight()-20,rm.roundedMenuBox, vbom);
		rectangleSmall.setColor(.9f, .9f, .9f,1f);
		rectangleSmall.setPosition(getCenterX(rectangleBig.getWidth(),rectangleSmall.getWidth()), 
				exitButton.getHeight()+10);
		
		
		Sprite buyButton=new Sprite(0,0,rm.rectangleButton,vbom);
		buyButton.setColor(.439f, .682f, .282f);
		
		Sprite buyButtonGlare=new Sprite(0,0,rm.rectangleButtonGlare,vbom);
		buyButtonGlare.setColor(.631f,.776f,.274f);
		buyButtonGlare.setPosition(getCenterX(buyButton.getWidth(),buyButtonGlare.getWidth()), 3);
		buyButton.attachChild(buyButtonGlare);
		
		Sprite coin=new Sprite(0,0,rm.coin,vbom);
		Text priceText=new Text(0,-2, 
				rm.fontBold60, 
				""+price,new TextOptions(HorizontalAlign.CENTER),vbom);
		priceText.setPosition(coin.getWidth()+10, getCenterY(coin.getHeight(),priceText.getHeight()));
		
		if(price>rm.totalCoins){
			//priceText.setColor(Color.RED);
		}
		
		coin.attachChild(priceText);
		coin.setPosition(getCenterX(buyButton.getWidth(),coin.getWidth()+10+priceText.getWidth()), 
				getCenterY(buyButton.getHeight(),coin.getHeight()));
		buyButton.attachChild(coin);
		
		Text questionText=new Text(0,-2, 
				rm.fontBold36, 
				question,new TextOptions(HorizontalAlign.CENTER),vbom);
		questionText.setPosition(getCenterX(rectangleSmall.getWidth(),questionText.getWidth()),50);
		questionText.setColor(.4f,.4f,.4f);
		rectangleSmall.attachChild(questionText);
		
		
		image.setPosition(getCenterX(rectangleSmall.getWidth(),image.getWidth()),
				questionText.getY()+questionText.getHeight()+35);
		rectangleSmall.attachChild(image);
		
		buyButton.setPosition(0, -5);
		Sprite buyButtonShadow=new Sprite(0,0,rm.rectangleButton,vbom){
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionMove()){
					 if(!checkClick((IAreaShape)this,pTouchAreaLocalX,pTouchAreaLocalY)){
						 this.setTag(0);}
				}else if(pSceneTouchEvent.isActionDown() && this.getScaleX()!=.8f){
					this.setTag(3);
					this.setScale(.8f);
				}
				if(pSceneTouchEvent.isActionUp() && this.getTag()==3){
					if((Integer)this.getUserData()>rm.totalCoins || 4>2){
						StoreScene.getInstance().updateHearts(1);
						StoreScene.getInstance().removePopup((Integer)this.getUserData());
						
						
					}else{
						this.setScale(1);
						//NOT ENOUGH MONEY
					}
					//PURCHASE ITEM
				}
				return true;
			}
		};
		buyButtonShadow.setColor(.25f,.435f,.164f);
		buyButtonShadow.attachChild(buyButton);
		buyButtonShadow.setUserData(price);
		buyButtonShadow.setPosition(getCenterX(rectangleSmall.getWidth(),buyButtonShadow.getWidth()),
				rectangleSmall.getHeight()-buyButtonShadow.getHeight()-15);
		
		rectangleSmall.attachChild(buyButtonShadow);
		rectangleBig.attachChild(rectangleSmall);
		
		rectangleOuter.setPosition(getCenterX(rectangleBig.getWidth()), getCenterY(rectangleBig.getHeight()));
	
		storeScene.registerTouchArea(buyButtonShadow);
		storeScene.registerTouchArea(exitButton);
		
		rectangleOuter.setUserData(new Sprite[]{buyButtonShadow,exitButton});
		
		rectangleBig.setPosition(getCenterX(rectangleOuter.getWidth(),rectangleBig.getWidth()), 4);

		rectangleOuter.attachChild(rectangleBig);
		storeScene.attachChild(rectangleOuter);
		return rectangleOuter;
	}
	@Override
	public void createScene() {
		rm.loadRoundedMenuBox790();
	}

	@Override
	public void disposeScene() {
		rm.unloadRoundedMenuBox790();
		detachChildren();
		dispose();
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		
	}

}
