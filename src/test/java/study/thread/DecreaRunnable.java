package study.thread;

/**
 * 具体业务类
 * 
 * @author LiuFeng
 * @version 1.0
 */
public class DecreaRunnable implements Runnable{

	private Computer computer;
	
	public DecreaRunnable(Computer computer) {
		this.computer = computer;
	}
	
	@Override
	public void run() {
		while (true) {
			computer.decreasement();
		}
	}

}
