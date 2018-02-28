package study.thread;


/**
 * 业务生产类
 * 
 * @author LiuFeng
 * @version 1.0
 */
public class Computer {

	private int i;

	public Computer() {
	}

	public synchronized void increasement() {
		while (i > 0) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		i++;
		System.out.println(Thread.currentThread().getName() + ", i = " + i);

		this.notifyAll();
	}

	public synchronized void decreasement() {
		while (i <= 0) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		i--;
		System.out.println(Thread.currentThread().getName() + ", i = " + i);

		this.notifyAll();
	}

}
