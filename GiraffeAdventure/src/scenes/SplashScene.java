package scenes;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.sprite.Sprite;

import com.wine.core.SceneManager;

public class SplashScene extends BaseScene{
	Sprite evilZooKeep;

	@Override
	public void createScene() {
		evilZooKeep=new Sprite(0,0,rm.ACSZooKeepLarge,vbom);
		Sprite mapToGiraffe=new Sprite(0,0,rm.ACSMap,vbom);
		
		evilZooKeep.setScale(2f);
		evilZooKeep.setPosition(getCenterX(evilZooKeep.getWidth()), 
				getCenterY(evilZooKeep.getHeight()));
		evilZooKeep.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		mapToGiraffe.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		mapToGiraffe.setPosition(getCenterX(mapToGiraffe.getWidth()), 
				getCenterY(mapToGiraffe.getHeight()));
		
		attachChild(mapToGiraffe);
		attachChild(evilZooKeep);		
	}
	
	public void activateZoom(){
		engine.registerUpdateHandler(new TimerHandler(0.05f, true, new ITimerCallback(){
					
					public void onTimePassed(TimerHandler pTimerHandler){
						
						if(evilZooKeep.getScaleX() > 0.1f){
							evilZooKeep.setScale(evilZooKeep.getScaleX()-0.02f);
						}else{
							evilZooKeep.setVisible(false);
							engine.unregisterUpdateHandler(pTimerHandler);
							//LOAD NEXT SCENE
							engine.registerUpdateHandler(new TimerHandler(.5f, true, new ITimerCallback(){
								public void onTimePassed(TimerHandler pTimerHandler){
									engine.unregisterUpdateHandler(pTimerHandler);
									SceneManager.getInstance().createMenuScene();
								}
							}));
						}
					}
				}));
	}

	@Override
	public void disposeScene() {
		rm.unloadToAfricaCutScene();
		evilZooKeep.detachSelf();
		evilZooKeep.dispose();
		detachChildren();
	}

	@Override
	public void onBackKeyPressed() {
		return;
		
	}

}
