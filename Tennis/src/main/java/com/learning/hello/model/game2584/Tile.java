package com.learning.hello.model.game2584;


public class Tile {
	private int value;

	public Tile() {
		value = 0;
	}

	public Tile(int number) {
		value = number;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}
}

