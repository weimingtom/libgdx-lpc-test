package net.davexunit.rpg;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ActorEvent;
import com.badlogic.gdx.scenes.scene2d.ActorListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class InventoryScreen implements Screen, InputProcessor {
	RPG game;
	Stage uiStage;
	List list;
	
	public InventoryScreen(RPG game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor((float) 77 / 255, (float) 74 / 255, (float) 93 / 255, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		uiStage.draw();
		Table.drawDebug(uiStage);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		uiStage = new Stage();
		
		ArrayList<String> listItems = new ArrayList<String>();
		
		// Make a list of items in player inventory
		for(Inventory.Element element: game.state.getInventory().elements) {
			listItems.add(element.item.getName());
		}
		
		TextureAtlas atlas = game.manager.get("data/sprites/spritepack.atlas", TextureAtlas.class);
		SpriteDrawable selection = new SpriteDrawable(new Sprite(atlas.findRegion("list-selection")));
		selection.setLeftWidth(32);
		selection.setTopHeight(6);
		selection.setBottomHeight(4);
		NinePatchDrawable backgroundPatch = new NinePatchDrawable(atlas.createPatch("list-box"));
		
		Label title = new Label("Inventory", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		
		List.ListStyle listStyle = new List.ListStyle(new BitmapFont(), Color.LIGHT_GRAY, Color.WHITE, selection);
		list = new List(listItems.toArray(), listStyle);
		list.addListener(new ActorListener() {
			@Override
			public boolean keyDown(ActorEvent event, int keycode) {
				switch(keycode) {
				case Input.Keys.DPAD_UP:
					int index = list.getSelectedIndex() - 1;
					
					if(index < 0)
						index = list.getItems().length - 1;
					
					list.setSelectedIndex(index);
					break;
					
				case Input.Keys.DPAD_DOWN:
					index = list.getSelectedIndex() + 1;
					
					if(index > list.getItems().length - 1)
						index = 0;
					
					list.setSelectedIndex(index);
					break;
				}
				
				return true;
			}
		});
		
		Image itemListImage = new Image(backgroundPatch);
		
		//Stack itemListStack = new Stack();
		//itemListStack.add(itemListImage);
		//itemListStack.add(list);
		
		//ScrollPane.ScrollPaneStyle scrollStyle = new ScrollPane.ScrollPaneStyle(backgroundPatch, hScroll, hScrollKnob, vScroll, vScrollKnob);
		ScrollPane scroll = new ScrollPane(list);
		
		Table table = new Table();
		table.debug();
		table.setPosition(0, 0);
		table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		table.pad(10).defaults().spaceBottom(10);
		table.row();
		table.add(title).left();
		table.row().fill().expand().left().top();
		table.add(scroll);//.width(300);
		
		uiStage.addActor(table);
		uiStage.setKeyboardFocus(list);
		
		InputMultiplexer input = new InputMultiplexer();
		input.addProcessor(this);
		input.addProcessor(uiStage);
		Gdx.input.setInputProcessor(input);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch(keycode) {
		case Input.Keys.ESCAPE:
		case Input.Keys.BACK:
			game.setScreen(game.exploreScreen);
			return true;
		}
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
