package custom;


import android.annotation.SuppressLint;

import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.opengl.util.GLState;

import com.wine.core.ResourceManager;


/**
 * (c) 2010 Nicolas Gramlich 
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 15:36:26 - 19.07.2010
 */
public class ParallaxBackground2 extends ParallaxBackground {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final ArrayList<ParallaxEntity> mParallaxEntities = new ArrayList<ParallaxEntity>();
	private int mParallaxEntityCount;

	protected float mParallaxValue;

	// ===========================================================
	// Constructors
	// ===========================================================

	public ParallaxBackground2(final float pRed, final float pGreen, final float pBlue) {
		super(pRed, pGreen, pBlue);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public void setParallaxValue(final float pParallaxValue) {
		this.mParallaxValue = pParallaxValue;
	}
	public static boolean ifPauseRotation=false;
	 @SuppressLint("WrongCall")
	@Override
     public void onDraw(final GLState pGL, final Camera pCamera) {
             super.onDraw(pGL, pCamera);
        
             final float parallaxValue = this.mParallaxValue;
             final ArrayList<ParallaxEntity> parallaxEntities = this.mParallaxEntities;

             for(int i = 0; i < this.mParallaxEntityCount; i++) {
                     parallaxEntities.get(i).onDraw(pGL, pCamera, parallaxValue);
             }
         
     }

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================
	

	// ===========================================================
	// Methods
	// ===========================================================

	public void attachParallaxEntity(final ParallaxEntity pParallaxEntity) {
		this.mParallaxEntities.add(pParallaxEntity);
		this.mParallaxEntityCount++;
	}
	/*public void setScale(){
		for (int i=0; i<mParallaxEntities.size(); i++){
			IAreaShape m=mParallaxEntities.get(i).mAreaShape;
			m.setScale(ResourceManager.getInstance().camera.getZoomFactor());
		}
	}*/

	public boolean detachParallaxEntity(final ParallaxEntity pParallaxEntity) {
		this.mParallaxEntityCount--;
		final boolean success = this.mParallaxEntities.remove(pParallaxEntity);
		if(!success) {
			this.mParallaxEntityCount++;
		}
		return success;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

	public static class ParallaxEntity {
		// ===========================================================
		// Constants
		// ===========================================================

		// ===========================================================
		// Fields
		// ===========================================================

		final float mParallaxFactor;
		final IAreaShape mAreaShape;

		final float mParallaxYFactor;
		Boolean mParallaxXRepeat;
		Boolean mParallaxYRepeat;
		public int tag=0;

		// ===========================================================
		// Constructors
		// ===========================================================
		public void setTag(int newTag){
			this.tag=newTag;
		}
		public ParallaxEntity(final float pParallaxFactor, final IAreaShape pAreaShape) {
			this.mParallaxFactor = pParallaxFactor;
			this.mAreaShape = pAreaShape;
			this.mParallaxYFactor = 0;
			this.mParallaxXRepeat = true;
			this.mParallaxYRepeat = false;
		}
		public ParallaxEntity(final float pParallaxXFactor, final float pParallaxYFactor, 
	              Boolean pParallaxXRepeat, Boolean pParallaxYRepeat, final IAreaShape pShape) 
		{
			this.mParallaxFactor = pParallaxXFactor;
			this.mAreaShape = pShape;
			this.mParallaxYFactor = pParallaxYFactor;
			this.mParallaxXRepeat = pParallaxXRepeat;
			this.mParallaxYRepeat = pParallaxYRepeat;
			
		}
		public ParallaxEntity(final float pParallaxXFactor, final float pParallaxYFactor, 
	              Boolean pParallaxXRepeat, Boolean pParallaxYRepeat, final IAreaShape pShape,int Tag) 
		{
			this.mParallaxFactor = pParallaxXFactor;
			this.mAreaShape = pShape;
			this.mParallaxYFactor = pParallaxYFactor;
			this.mParallaxXRepeat = pParallaxXRepeat;
			this.mParallaxYRepeat = pParallaxYRepeat;
			this.tag=Tag;
		}

		// ===========================================================
		// Getter & Setter
		// ===========================================================

		// ===========================================================
		// Methods for/from SuperClass/Interfaces
		// ===========================================================

		// ===========================================================
		// Methods
		// ===========================================================

		@SuppressLint("WrongCall")
		public void onDraw(final GLState pGLState, final Camera pCamera, final float pParallaxValue) {
			
			pGLState.pushModelViewGLMatrix();
			{
				final float cameraWidth = pCamera.getWidth()*ResourceManager.getInstance().camera.getZoomFactor();
				final float cameraCenter=pCamera.getCenterY();
			//	this.mAreaShape.setScale(ResourceManager.getInstance().camera.getZoomFactor());
				//mAreaShape.setScale(ResourceManager.getInstance().camera.getZoomFactor());
				float shapeWidthScaled = this.mAreaShape.getWidthScaled()-2;
				if(this.tag==3){
					shapeWidthScaled=1024;
                }
				
			
														//this controls where the next layer gets autoset more negative, the more closer it comes to the prior one
				final float shapeHeightScaled = this.mAreaShape.getHeightScaled();
				float baseOffset = (pParallaxValue * this.mParallaxFactor) % shapeWidthScaled;
				float baseYOffset = (pParallaxValue * this.mParallaxYFactor) % shapeHeightScaled;
				//if(baseOffset<=-shapeWidthScaled){
					//Log.d("BASE OFFSETS", "baseoff"+baseOffset+ " parrallaxFactor"+pParallaxValue);
				//}
				//Log.d("BASE OFFSETS : SHAPE WIDTH"+shapeWidthScaled, 
					//	"baseoff"+baseOffset+ " parrallaxFactor"+pParallaxValue);
				
				while(baseOffset > 0) {
				//	Log.d("BASE OFFSETS", "WHAT HAPPENS HERE?");
					baseOffset -= (shapeWidthScaled);
				}

				
				baseYOffset=cameraCenter*mParallaxYFactor;
				
				
				//Log.d("Camer Center", ": "+cameraCenter+ 
						//" base y offset="+baseYOffset + " parralxyfactor="+mParallaxYFactor);
				
				//while(baseYOffset > 0) {
				//	baseYOffset -= shapeHeightScaled;
					//baseYOffset=0;
				//}
			//	baseYOffset = -shapeHeightScaled;
			
				pGLState.translateModelViewGLMatrixf(baseOffset, baseYOffset, 0);
				if(!mParallaxXRepeat){
					this.mAreaShape.onDraw(pGLState, pCamera);
	                pGLState.translateModelViewGLMatrixf(shapeWidthScaled, 0, 0);
	                if(this.tag==2 ){
						mAreaShape.setRotation(mAreaShape.getRotation()+0.1f);
	                }
				}
				else{
					
					float currentMaxX = baseOffset;
					
					 do {
	                     this.mAreaShape.onDraw(pGLState, pCamera);
	                     pGLState.translateModelViewGLMatrixf(shapeWidthScaled, 0, 0);
	                     currentMaxX += shapeWidthScaled;
	                   
					 } while(currentMaxX < cameraWidth); 
					}
					
				}

			pGLState.popModelViewGLMatrix();
			
		}

		// ===========================================================
		// Inner and Anonymous Classes
		// ===========================================================
	}
}
