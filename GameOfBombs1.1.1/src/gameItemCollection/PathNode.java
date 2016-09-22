package gameItemCollection;

import java.util.ArrayList;

import game.Game;

public class PathNode {
	
	PathNode parent;
	public double f,g,h;
	boolean walkable;
	public int row,col;
	public int destrow,destcol;
	public int x,y;
	public PathNode(int row, int col, int destrow, int destcol, boolean walkable, PathNode parent) {
		this.destrow = destrow;
		this.destcol = destcol;
		int diffx = row - destrow;
		int diffy = col - destcol;
		this.h = Math.sqrt((diffx * diffx)+(diffy*diffy));
		this.f = g + h;
		this.parent = parent;
		this.row = row;
		this.col = col;
		this.walkable = walkable;
		this.x = col * Game.gridWidth;
		this.y = row * Game.gridHeight;
	}
	public PathNode getParent() {
		return parent;
	}
	public void setParent(PathNode parent) {
		this.parent = parent;
	}
	public boolean isWalkable() {
		return walkable;
	}
	public void setWalkable(boolean walkable) {
		this.walkable = walkable;
	}
	public boolean isGoal(){
		return row == destrow && col == destcol;
	}
//	public ArrayList<PathNode> getNeighbors() {
//		ArrayList<PathNode> temp = new ArrayList();
//		if(row)
//		return temp;
//	}
	public void setF() {
		if(parent == null) {
			g = 0;
		} else {
			if(parent.row != row && parent.col != col) {
				g = parent.g + 1;
			} else {
				g = parent.g + 2;
			}
		}
		this.f = g + h;
	}
	
}
