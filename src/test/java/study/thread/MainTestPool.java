package study.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池版本
 * 
 * @author LiuFeng
 * @version 1.0
 */
public class MainTestPool {

	public static void main(String[] args) {

		Computer computer = new Computer();

		IncreaRunnable increaRunnable = new IncreaRunnable(computer);
		DecreaRunnable decreaRunnable = new DecreaRunnable(computer);

//		ExecutorService executorService = Executors.newFixedThreadPool(4);
//		executorService.execute(increaRunnable);
//		executorService.execute(decreaRunnable);
//		executorService.execute(increaRunnable);
//		executorService.execute(decreaRunnable);
		
		ExecutorService executorService2 = Executors.newCachedThreadPool();
		executorService2.execute(increaRunnable);
		executorService2.execute(increaRunnable);
		executorService2.execute(decreaRunnable);
		executorService2.execute(decreaRunnable);
		

	}

}
