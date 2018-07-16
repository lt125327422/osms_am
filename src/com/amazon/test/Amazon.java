package com.amazon.test;

/**
 * @author wanghw
 * @date 2015-4-17
 * @description TODO
 * @version
 */
public class Amazon implements Runnable {
	private String sessionId;
	private Test test;
	
	public void setTest(Test test) {
		this.test = test;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Amazon() {
		super();
	}

	public Amazon(String sessionId) {
		super();
		this.sessionId = sessionId;
	}

	@Override
	public void run() {
		try {
			System.out.println(Thread.currentThread().getName() + "------开始等待！");
			String tmp=new String(sessionId);
			Thread.currentThread().sleep(30000);
			test.work(tmp);
			System.out.println(Thread.currentThread().getName() + "-----结束等待！");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
