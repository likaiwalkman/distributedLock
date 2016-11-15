package com.victor.zk.distrilock.core.exception;
/**
 * @author 作者 E-mail:ruanjianlxm@sina.com
 * @version 创建时间：2015年9月11日 下午10:55:51
 * 类说明
 */
public class ZkDistributedLockException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ZkDistributedLockException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ZkDistributedLockException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ZkDistributedLockException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ZkDistributedLockException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ZkDistributedLockException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
