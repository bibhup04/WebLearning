package com.learning.hello.contoller;

public class FibbonacciController {
	int currentNum  ;
	public FibbonacciController(int num) {
		this.currentNum = num;
	}

//	public int prevFibbonacciValue() {
//	    return (int)Math.round(currentNum/ ((1 + Math.sqrt(5)) / 2.0));
//	}
//	public int nextFibbonacciValue() {
//	    return (int)Math.round(currentNum * ((1 + Math.sqrt(5)) / 2.0));	
//	}
	
	  public int prevFibbonacciValue(int currentNum) {
	        return (int)Math.round(currentNum/ ((1 + Math.sqrt(5)) / 2.0));
	    }
	    public int nextFibbonacciValue(int currentNum) {
	        return (int)Math.round(currentNum * ((1 + Math.sqrt(5)) / 2.0));    
	    }
	
	    public boolean compareTiles(int i, int j) {
	        FibbonacciController f1 = new FibbonacciController(i);
	        FibbonacciController f2 = new FibbonacciController(j);
	        if(f1.nextFibbonacciValue(i) == j || f1.prevFibbonacciValue(i) == j) return true;
	        else if(f2.nextFibbonacciValue(j) == i || f2.prevFibbonacciValue(j) == i) return true;
	        return false;

	    }
//	public static void main(String args[]) {
//		FibbonacciController f = new FibbonacciController(8);
//		System.out.println(f.prevFibbonacciValue());
//		System.out.println(f.nextFibbonacciValue());
//		
//	}
}
