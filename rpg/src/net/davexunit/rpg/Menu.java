package net.davexunit.rpg;

import java.awt.FontMetrics;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;
import com.badlogic.gdx.utils.Scaling;

public class Menu extends StyledTable implements InputProcessor {
	
	private ArrayList<TextBox> mTextBoxes;
	private Image mArrow;
	private int mSelectedOption;
	
	public Menu(String [] options, TableStyle style) {
		super(style);
		
		mSelectedOption = 0;
		mTextBoxes = new ArrayList<TextBox>(options.length);
		
		for(int i=0; i < options.length; i++) {
			TextBox textBox = new TextBox(options[i], new TableStyle());
			textBox.style.padX = this.style.padX;
			textBox.style.padY = this.style.padY;
			textBox.getLabel().setAlignment(Align.CENTER, Align.CENTER);
			mTextBoxes.add(textBox);
		}
		
		
		Texture texture = new Texture(Gdx.files.internal("data/arrows-right.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		Tileset tileset = new Tileset(texture, 24, 21, 0, 0);
		
		mArrow = new Image(tileset.getTile(0), Scaling.fill, Align.LEFT);
	
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		this.setBackground(this.style.background);
		super.draw(batch, parentAlpha);
		
		for(TextBox textBox : mTextBoxes) {
			textBox.draw(batch, parentAlpha);
		}
		
		mArrow.draw(batch, parentAlpha);
	}
	
	public void setXY(float x, float y) {
		setX(x);
		setY(y);
	}
	
	public void setX(float x) {
		this.x = x;
		
		mArrow.x = x;
		
		for(TextBox textBox : mTextBoxes) {
			textBox.setX(x+this.style.padX);
		}
		
	}
	
	public void setY(float y) {
		this.y = y;
		mArrow.y = y;
		
		float height = 0;
		y -= this.style.padY;
		for(TextBox textBox : mTextBoxes) {
			textBox.setY(y + height);
			height += textBox.height;
		}
	}
	
	public void setWidth(float width) {
		this.width = width;
		for(TextBox textBox : mTextBoxes) {
			textBox.width = width - this.style.padX;
		}
	}
	
	public void setHeight(float height) {
		this.height = height;
		height = height / mTextBoxes.size();
		for(TextBox textBox : mTextBoxes) {
			textBox.height = height - this.style.padY;
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
	public boolean touchMoved(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

}
