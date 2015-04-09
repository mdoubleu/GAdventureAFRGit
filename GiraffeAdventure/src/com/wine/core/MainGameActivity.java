package com.wine.core;

import org.andengine.engine.FixedStepEngine;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import android.util.Log;

public class MainGameActivity extends BaseGameActivity {
	public FixedStepEngine mEngine;
	public SmoothCamera camera;
	public static MainGameActivity mgA;
	public int pauseType=0;
	public static String message;
	public float scrollMaxSpeed=200;
	
	public static MainGameActivity getInstance(){
		return mgA;
	}
	
	
	/*---------------------------- INITIAL GAME SETUP ----------------------------*/
	//STEP 1: SETUP CAMERA AND ENGINE OPTIONS
	@Override
	public EngineOptions onCreateEngineOptions() {
		camera = new SmoothCamera(0,0,1024,720,1f,scrollMaxSpeed,1f);// BoundCamera top left is origin corner.
		EngineOptions engineOptions= new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR,
				new RatioResolutionPolicy(3,2), camera);
		engineOptions.getAudioOptions().setNeedsSound(true);
		engineOptions.getAudioOptions().setNeedsMusic(true);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		
		return engineOptions;
	}
	//STEP 2: SETUP CAMERA AND ENGINE OPTIONS
	//pre-load all the resources your game will be using regularly here. 
	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		//SETP Resource Manager and Sound Manager: LOAD COMMON ASSETS AND SOUNDS
		ResourceManager.getInstance().setup(mEngine, this, camera, this.getVertexBufferObjectManager(), 
				this.getApplicationContext(), (int)camera.getWidth(), (int)camera.getHeight());
		
		SoundManager.getInstance().setup(mEngine, this, this.getApplicationContext());

		
		pOnCreateResourcesCallback.onCreateResourcesFinished();
		
	}
	
	//STEP 3:  This is where all your behavior is defined (update handlers, scenes etc)
	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);
	}
	
	
	//STEP 4:  This is where you start everything (play music, animate characters etc.)
	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		SceneManager.getInstance().setSplashActive();
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	
	//setup engine
		public FixedStepEngine onCreateEngine(EngineOptions pEngineOptions) {
		mEngine = new FixedStepEngine(pEngineOptions, 60);
		mgA=this;
		mEngine.enableVibrator(this);
		return mEngine;
	}
		
		@Override
		protected void onDestroy(){
		       super.onDestroy();
		       Log.d("ON DESTROY", "DESTROYING");
		}
		@Override
		public void onStop() {
			super.onStop();
			if(ResourceManager.getInstance().SOUND == true
					&& SoundManager.getInstance().mainMusicLoop!=null
					&&SoundManager.getInstance().mainMusicLoop.isPlaying()){
				SoundManager.getInstance().mainMusicLoop.pause();
			}
		}
		@Override
		protected void onPause(){
			super.onPause();
			if(ResourceManager.getInstance().SOUND == true
					&& SoundManager.getInstance().mainMusicLoop!=null
					&&SoundManager.getInstance().mainMusicLoop.isPlaying()){
				SoundManager.getInstance().mainMusicLoop.pause();
			}
		}
		@Override
		protected void onResume(){
			super.onResume();
			if(ResourceManager.getInstance().SOUND == true
					&& SoundManager.getInstance().mainMusicLoop!=null
					&& !SoundManager.getInstance().mainMusicLoop.isPlaying()){
				Log.d("RESUME ", "MUSIC");
				SoundManager.getInstance().mainMusicLoop.play();
			}
			
		}
}
