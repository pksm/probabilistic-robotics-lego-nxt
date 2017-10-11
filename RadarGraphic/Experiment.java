public class Experiment
{
	public static void main(String[] args)
	{
		CubbyHole c = new CubbyHole();
		Consumer c1 = new Consumer(c);
		Controller p1 = new Controller(c);
		p1.start();
		c1.start();
	}
}
