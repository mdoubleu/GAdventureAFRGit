package com.wine.core;

import org.andengine.engine.FixedStepEngine;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import scenes.BaseScene;
import scenes.GameSceneAfrica;
import scenes.MainMenuScene;
import scenes.MapSceneAfrica;
import scenes.SplashScene;
import scenes.StoreScene;

public class SceneManager {
	private static final SceneManager INSTANCE = new SceneManager();
	private FixedStepEngine engine = ResourceManager.getInstance().engine;
	
	public BaseScene currentScene;
	public BaseScene lastScene;
	
	public static SceneManager getInstance(){return INSTANCE;}
	
	public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback){
		currentScene=new SplashScene();
		pOnCreateSceneCallback.onCreateSceneFinished(currentScene);
	}
	public void setSplashActive(){
		((SplashScene) currentScene).activateZoom();
	}
	
	public void disposeLastScene(){
		lastScene=currentScene;
		lastScene.disposeScene();
		lastScene=null;
	}
	
	public void createMenuScene(){
		disposeLastScene();
		currentScene=new MainMenuScene();
		engine.setScene(currentScene);
	}
	
	public void createMapAfricaScene(){
		ResourceManager.getInstance().loadAfricaMap();
		ResourceManager.getInstance().loadTitleBarMenu();
		disposeLastScene();
		currentScene=new MapSceneAfrica();
		engine.setScene(currentScene);
	}
	public void loadMenuSceneFromScene(){
		ResourceManager.getInstance().loadMenuAssets();
		createMenuScene();
	}
	public void createGameSceneAfrica(int stars){
		disposeLastScene();
		ResourceManager.getInstance().loadAfricaGameScene();
		SoundManager.getInstance().loadGameEffects();
		currentScene=new GameSceneAfrica();
		((GameSceneAfrica)currentScene).starsSaved=stars;
		engine.setScene(currentScene);
	}
	public void restartGameScene(){
		((GameSceneAfrica)currentScene).unloadGameScene();
		currentScene=null;
		currentScene=new GameSceneAfrica();
		engine.setScene(currentScene);
	}
	public void createStoreScene(){
		disposeLastScene();
		ResourceManager.getInstance().loadStore();
		ResourceManager.getInstance().loadTitleBarMenu();
		currentScene=new StoreScene();
		engine.setScene(currentScene);
	}
}
