public interface Cons implements Runnable{
	private CubbyHole cubbyhole;
   
	public Consumer(CubbyHole c);
	public void run();
}


class Consumer implements Cons, RP {
	private CubbyHole cubbyhole;
   
	public Consumer(CubbyHole c)
	{
		cubbyhole = c;
	}

	public void run()
	{
		Reading R;
		while(true){
			r = cubbyhole.get();
			iAngle = r.getAngle();
			iDistance = r.getDistance();
		}
	}


}
