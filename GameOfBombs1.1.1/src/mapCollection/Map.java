package mapCollection;

import gameItemCollection.Tree;
import gameItemCollection.Wall;

import java.util.ArrayList;

public abstract class Map {
	ArrayList<Wall> walls;
	ArrayList<Tree> trees;
	int width;
	int height;
	public Map(int width, int height) {
		
	}
}
