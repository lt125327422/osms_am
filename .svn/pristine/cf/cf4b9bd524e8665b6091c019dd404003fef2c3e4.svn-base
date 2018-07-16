package com.amazon.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** 
 * @author wanghw
 * @date 2015-3-19 
 * @description TODO
 * @version
 */
public class TestImpl implements Test{
	private ExecutorService pool=Executors.newSingleThreadExecutor();
	private Test test;
	private Amazon amazon; 
	
	public void setAmazon(Amazon amazon) {
		this.amazon = amazon;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	@Override
	public void say(final String sessionId) {
		System.out.println("Hello!");
		amazon.setSessionId(sessionId);
		pool.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.currentThread().setName("pool-1-thread-"+sessionId);
					System.out.println(Thread.currentThread().getName() + "------开始等待！");
					Thread.currentThread().sleep(30000);
					test.work(sessionId);
					System.out.println(Thread.currentThread().getName() + "-----结束等待！");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		System.out.println("bye!");
	}
	
	public void work(String sessionId){
		System.out.println(Thread.currentThread().getName()+"doing work!");
		System.out.println("sessionId------------"+sessionId);
	}
}
