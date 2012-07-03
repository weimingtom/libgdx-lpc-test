package net.davexunit.rpg;

import java.util.ArrayList;

public class Path {
	public static class Point {
		int x;
		int y;
		
		public Point() {
			this.x = 0;
			this.y = 0;
		}
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	public ArrayList<Point> points;
	
	public Path() {
		this.points = new ArrayList<Point>();
	}
	
	public void addPoint(int x, int y) {
		points.add(new Point(x, y));
	}
	
	public void addPoint(Point p) {
		points.add(p);
	}
}
