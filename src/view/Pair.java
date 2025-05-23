package view;

public class Pair implements Comparable{
	int x;
	int y;
	public Pair(int x,int y){
		this.x = x;this.y = y;
	}
	
	public int compareTo(Object o){
		Pair e = (Pair)o;
		if(e.x==x){
			return e.y-y;
		}else{
			return e.x-x;
		}
	}
	public String toString(){
		return x + " " + y;
	}
}
