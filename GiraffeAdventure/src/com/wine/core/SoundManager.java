package com.wine.core;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.FixedStepEngine;
import org.andengine.util.debug.Debug;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

public class SoundManager {

	private final static SoundManager INSTANCE = new SoundManager();
	
	FixedStepEngine engine;
	Activity activity;
	Context context;
	
	public Sound coinFX;
	public Sound jumpFX;
	public Sound woohooFX;
	public Music mainMusicLoop;
	public Music winSound;
	

	public SoundManager(){}
	//====================================================
	// GETTERS & SETTERS
	//====================================================
	// Retrieves a global instance of the ResourceManager
	public static SoundManager getInstance(){return INSTANCE;}
	
	//SETTING UP THE RESOURCE MANAGER - USED IN THE MAINGAMEACTIVITY
	public void setup(FixedStepEngine pEngine, MainGameActivity pActivity, Context pContext){
		getInstance().engine = pEngine;
		getInstance().activity = pActivity;
		getInstance().context = pContext;
		loadMainLoop();
		//LOAD COMMON/INTRO SOUNDS HERE
	}
	
	//====================================================
	// METHODS
	//====================================================
	public void loadMainLoop(){
		MusicFactory.setAssetBasePath("sound/");
		try{
			if(mainMusicLoop==null){
				Log.d("LOAD MUSIC", "LOAD MUSIC");
				mainMusicLoop = MusicFactory
						.createMusicFromAsset(engine.getMusicManager(), activity, "Africa.ogg");
				mainMusicLoop.setVolume(0.7f);
				mainMusicLoop.setLooping(true);
			
			
			}
		}catch(final IOException e){
			Debug.e(e);
		}
	}
	public void unloadGameEffects(){
		if(coinFX!=null){
			coinFX.release();
			coinFX=null;
			jumpFX.release();
			jumpFX=null;
			woohooFX.release();
			woohooFX=null;
		}
	}
	public void loadGameEffects(){
		SoundFactory.setAssetBasePath("sound/");
		try{
			if(coinFX==null){
				coinFX=SoundFactory
						.createSoundFromAsset(engine.getSoundManager(), activity, "coinFX.ogg");
				jumpFX = SoundFactory.createSoundFromAsset(engine.getSoundManager(), activity, "jumpFX.mp3");
				jumpFX.setVolume(0.8f);
			
				woohooFX = SoundFactory.createSoundFromAsset(engine.getSoundManager(), activity, "woohooFX.ogg");
				//winFX = SoundFactory.createSoundFromAsset(engine.getSoundManager(), activity, "winFX.ogg");
				//clickFX=SoundFactory
				//		.createSoundFromAsset(engine.getSoundManager(), activity, "clickFX.mp3");
			}
		}catch(final IOException e){
			Debug.e(e);
		}	
	}
	
}
