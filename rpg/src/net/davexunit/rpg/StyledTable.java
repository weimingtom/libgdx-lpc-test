package net.davexunit.rpg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.tablelayout.Table;

public class StyledTable extends Table {
	
	protected TableStyle style;
	
	public StyledTable () {
		this.style = getDefaultTexture();
	}
	
	public StyledTable (TableStyle style) {
		this.style = style;
	}
	
	protected static TableStyle getDefaultTexture() {
		FileHandle handle = Gdx.files.internal("data/dialogue_box.png");
		if (! handle.exists())
			return new TableStyle();
		
		NinePatch patch = new NinePatch(new Texture(handle), 16, 16, 16, 16);
		TableStyle style = new TableStyle();
		style.background = patch;
		return style;
	}
	
	public static class TableStyle {
		public NinePatch background;
		public BitmapFont font;
		public Color fontColor;
		public int padX;
		public int padY;
		
		public TableStyle() {
			this.background = null;
			this.font = new BitmapFont(); //default font
			this.fontColor = new Color(Color.WHITE);
			this.padX = 0;
			this.padY = 0;
		}
	}

}
