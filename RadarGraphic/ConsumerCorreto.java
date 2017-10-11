class Consumer extends Thread
{
	private CubbyHole cubbyhole;
   
	public Consumer(CubbyHole c)
	{
		cubbyhole = c;
	}

	public void run()
	{
		Reading r;

		while(true)
		{
			r = cubbyhole.get();
         	System.out.println(r);
		}
	}
}
