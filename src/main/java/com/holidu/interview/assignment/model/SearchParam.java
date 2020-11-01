package com.holidu.interview.assignment.model;

public class SearchParam {
	private double x_coord, y_coord, radius;
	private String common_name;

	public double getX_coord() {
		return x_coord;
	}

	public void setX_coord(double x_coord) {
		this.x_coord = x_coord;
	}

	public double getY_coord() {
		return y_coord;
	}

	public void setY_coord(double y_coord) {
		this.y_coord = y_coord;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public String getCommon_name() {
		return common_name;
	}

	public void setCommon_name(String common_name) {
		this.common_name = common_name;
	}

	@Override
	public String toString() {
		return "SearchParam [x_coord=" + x_coord + ", y_coord=" + y_coord + ", radius=" + radius + ", common_name="
				+ common_name + "]";
	}

}
