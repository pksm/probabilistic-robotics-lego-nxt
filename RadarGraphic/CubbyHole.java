class CubbyHole
{
	private Reading reading;
	private boolean available = false;

	CubbyHole()
	{
		reading = new Reading();
	}
   
	public synchronized Reading get()
	{
		while (available == false)
		{
			try
			{
            	wait();
         	}
			catch (InterruptedException e) {}
      	}
      	available = false;
      	notifyAll();
      	return reading;
	}

	public synchronized void put(int angle, int dist)
	{
		while (available == true)
		{
        	try
			{
            	wait();
			}
			catch (InterruptedException e) {}
      	}
		reading.set(angle, dist);
      	available = true;
      	notifyAll();
   	}
}
