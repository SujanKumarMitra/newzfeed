package com.herokuapp.newsfeedapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * This class is used as a thread pool to create {@link Thread} instances and
 * keep track of them while they are alive.
 * 
 * @since 2020-05-23
 * @author Sujan Kumar Mitra
 * @see NewsService
 *
 */
@Service
public class ThreadPoolService {

	/**
	 * Stores active threads.
	 */
	private List<Thread> threadPool = new ArrayList<>();

	/**
	 * This method returns instance of a {@link Thread}
	 * Also saves the instance reference in {@link ThreadPoolService#threadPool}
	 * 
	 * @return instance of {@link Thread}
	 * @since 2020-05-23
	 * @author Sujan Kumar Mitra
	 */
	public Thread getThread() {
		Thread thread = new Thread();
		threadPool.add(thread);
		return thread;
	}

	/**
	 * This method returns instance of {@link Thread}
	 * Also saves the instance reference in {@link ThreadPoolService#threadPool}
	 * 
	 * @param target instance of {@link Runnable}
	 * @param name   name of {@link Thread}
	 * @return instance of {@link Thread}
	 * @since 2020-05-23
	 * @author Sujan Kumar Mitra
	 */
	public Thread getThread(Runnable target, String name) {
		Thread thread = new Thread(target, name);
		threadPool.add(thread);
		return thread;
	}

	/**
	 * This method checks whether all the threads created by
	 * {@link ThreadPoolService} are dead or not.
	 * 
	 * @return if any {@link Thread} is alive then false otherwise
	 *         <strong>true</strong>
	 * @since 2020-05-23
	 * @author Sujan Kumar Mitra
	 */
	public boolean areAllThreadsDead() {
		return !threadPool
				.parallelStream()
				.filter((thread) -> thread.isAlive())
				.findFirst()
				.isPresent();
	}

	/**
	 * Removes all thread references from {@link ThreadPoolService#threadPool}
	 * 
	 * @since 2020-05-23
	 * @author Sujan Kumar Mitra
	 */
	public void removeAllThreads() {
		threadPool.removeIf((thread) -> true);
	}

}
