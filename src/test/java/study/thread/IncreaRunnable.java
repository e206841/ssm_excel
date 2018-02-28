package study.thread;

/**
 * 具体业务类
 * 
 * @author LiuFeng
 * @version 1.0
 */
public class IncreaRunnable implements Runnable{
	
	private Computer computer;
	
	public IncreaRunnable(Computer computer) {
		this.computer = computer;
	}

	@Override
	public void run() {
		while (true) {
			computer.increasement();
		}
	}

}
