package net.davexunit.rpg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;

public class TextBox extends Actor 
{
	Label mLabel;
	TextField mTextField;
	
	public TextBox () {
		//mBackgroundTexture = new Texture("data/dialogue.png");
		
		NinePatch patch = new NinePatch(new Texture(Gdx.files.internal("data/dialogue_box.png")), 16, 16, 16, 16);
		
		TextFieldStyle style = new TextFieldStyle();
		style.background = patch;
		style.font = new BitmapFont();
		style.fontColor = new Color(Color.WHITE);
		
		mTextField = new TextField("", style);
		mLabel = new Label("lalala\nuuuuuuuuuuuuuuulalal", new Label.LabelStyle(new BitmapFont(), new Color(Color.WHITE)));
		
		mTextField.width = Gdx.graphics.getWidth();
		mTextField.height = Gdx.graphics.getHeight() / 3;
		mLabel.width = Gdx.graphics.getWidth();
		mLabel.height = Gdx.graphics.getHeight() / 3;
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		mTextField.draw(batch, (float) 1.0);
		mLabel.draw(batch, (float) 1.0);
	}

	@Override
	public Actor hit(float x, float y) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setText(String text) {
		mTextField.setText(text);
	}
	
}
