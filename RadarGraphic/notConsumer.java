class Consumer extends Thread
{
	private CubbyHole cubbyhole;
   
	public Consumer(CubbyHole c)
	{
		cubbyhole = c;
	}

	public void run()
	{
		RadarProcessing radar = new RadarProcessing();
	}

	public CubbyHole getCubby()
	{
		return this.cubbyhole; 
	}

}
