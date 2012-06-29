import java.io.File;
import java.io.IOException;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.jogl.JoglApplication;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.tools.imagepacker.TexturePacker;
import com.badlogic.gdx.tools.imagepacker.TexturePacker.Settings;
import com.badlogic.gdx.tiledmappacker.TiledMapPacker;

public class MyPacker {
    public static void main (String[] args) throws Exception {
        Settings settings = new Settings();
        settings.padding = 0;
        settings.maxWidth = 512;
        settings.maxHeight = 512;
        settings.incremental = true;
        //TexturePacker.process(settings, "data/tiles", "../rpg-android/assets/data");
        
		// Create a new JoglApplication so that Gdx stuff works properly
		new JoglApplication(new ApplicationListener() {
			@Override
			public void create() {
			}

			@Override
			public void dispose() {
			}

			@Override
			public void pause() {
			}

			@Override
			public void render() {
			}

			@Override
			public void resize(int width, int height) {
			}

			@Override
			public void resume() {
			}
		}, "", 0, 0, false);

        TiledMapPacker packer = new TiledMapPacker();
        File inputDir = new File("data");
        File outputDir = new File("../rpg-android/assets/data/maps/");
        
		if (!inputDir.exists()) {
			throw new RuntimeException("Input directory does not exist");
		}
        
        try {
            packer.processMap(inputDir, outputDir, settings);
	    } catch (IOException e) {
	            throw new RuntimeException("Error processing map: " + e.getMessage());
	    }
    }
}