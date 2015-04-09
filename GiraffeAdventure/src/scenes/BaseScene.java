package scenes;

import org.andengine.engine.FixedStepEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.list.SmartList;

import com.wine.core.ResourceManager;

import android.app.Activity;


public abstract class BaseScene extends Scene{
	
	public FixedStepEngine engine;
	public Activity activity;
	public ResourceManager rm;
	public Camera camera;
	public VertexBufferObjectManager vbom;
	
	public float touchX=0;
	public float touchY=0;
	public int touchState=0;

	
	public BaseScene(){
		this.engine=ResourceManager.getInstance().engine;
		this.activity=ResourceManager.getInstance().activity;
		this.vbom=ResourceManager.getInstance().vbom;
		this.camera=ResourceManager.getInstance().camera;
		this.rm=ResourceManager.getInstance();
		createScene();
	}
	
	public float getCenterX(float objectWidth){
		return ResourceManager.getInstance().C_WIDTH/2-objectWidth/2;
	}
	public float getCenterX(float parentObjectWidth, float objectWidth){
		return parentObjectWidth/2-objectWidth/2;
	}
	public float getCenterY(float objectHeight){
		return ResourceManager.getInstance().C_HEIGHT/2-objectHeight/2;
	}
	public float getCenterY(float parentObjectHeight, float objectHeight){
		return parentObjectHeight/2-objectHeight/2;
	}
	public abstract void createScene();
	
	public abstract void disposeScene();
	
	public abstract void onBackKeyPressed();
	
	public void unregisterAllTouchAreas(){
		//UNREGISTER TOUCH AREAS
		int g =getTouchAreas().size();
		SmartList<ITouchArea> touchs=new SmartList<ITouchArea>();
		touchs.addAll(getTouchAreas());
		for (int i = 0; i < g; i++){
	       unregisterTouchArea(touchs.get(i));
		}
	}
	
	public boolean checkClick(IAreaShape object,float touchAreaX, float touchAreaY){
		 if(touchState==0){
			  touchState=1;
			  touchX=touchAreaX;
			  touchY=touchAreaY;
			}else if(touchState==1){
				if(Math.abs(touchX-touchAreaX)>50 
						|| Math.abs(touchY-touchAreaY)>50){
					touchState=0;
					object.setScale(1);
					return false;
				}
		 }
		 return true;
	}

}
