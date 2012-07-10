package net.davexunit.rpg;

import java.awt.FontMetrics;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Menu extends StyledTable implements InputProcessor {
	private ArrayList<TextBox> textBoxes;
	private Image arrow;
	private int selectedOption;
	
	public Menu(String [] options, TableStyle style) {
		super(style);
		
		selectedOption = 0;
		textBoxes = new ArrayList<TextBox>(options.length);
		
		for(int i=0; i < options.length; i++) {
			TextBox textBox = new TextBox(options[i], new TableStyle());
			textBox.style.padX = this.style.padX;
			textBox.style.padY = this.style.padY;
			textBox.getLabel().setAlignment(Align.center, Align.center);
			textBoxes.add(textBox);
		}
		
		
		Texture texture = new Texture(Gdx.files.internal("data/arrows-right.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		Tileset tileset = new Tileset(new TextureRegion(texture, texture.getWidth(), texture.getHeight()), 24, 21, 0, 0);
		
		//mArrow = new Image(tileset.getTile(0), Scaling.fill, Align.left);
		arrow = new Image(tileset.getTile(0));
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		this.setBackground(this.style.background);
		super.draw(batch, parentAlpha);
		
		for(TextBox textBox : textBoxes) {
			textBox.draw(batch, parentAlpha);
		}
		
		arrow.draw(batch, parentAlpha);
	}
	
	@Override
	public void setPosition(float x, float y) {
		setX(x);
		setY(y);
	}
	
	public void setX(float x) {
		super.setX(x);
		
		arrow.setX(x);
		
		for(TextBox textBox : textBoxes) {
			textBox.setX(x+this.style.padX);
		}
		
	}
	
	@Override
	public void setY(float y) {
		super.setY(y);
		arrow.setY(y);
		
		float height = 0;
		y -= this.style.padY;
		for(TextBox textBox : textBoxes) {
			textBox.setY(y + height);
			height += textBox.getHeight();
		}
	}
	
	@Override
	public void setWidth(float width) {
		super.setWidth(width);
		for(TextBox textBox : textBoxes) {
			textBox.setWidth(width - this.style.padX);
		}
	}
	
	public void setHeight(float height) {
		super.setHeight(height);
		height = height / textBoxes.size();
		for(TextBox textBox : textBoxes) {
			textBox.setHeight(height - this.style.padY);
		}
	}
	
	public Menu(String [] options) {
		this(options, new TableStyle());
	}
	
	@Override
	public boolean keyDown (int keycode) {
		return false;
	}

	@Override
	public boolean keyUp (int keycode) {
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
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
