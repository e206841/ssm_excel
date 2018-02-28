package study.thread;

/**
 * 主体测试类
 * 
 * @author LiuFeng
 * @version 1.0
 */
public class MainTest {

	public static void main(String[] args) {

		Computer computer = new Computer();

		IncreaRunnable increaRunnable = new IncreaRunnable(computer);
		DecreaRunnable decreaRunnable = new DecreaRunnable(computer);

		new Thread(increaRunnable).start();
		new Thread(decreaRunnable).start();
		new Thread(increaRunnable).start();
		new Thread(decreaRunnable).start();

	}

}
