package com.fintech.platform.core.fastdfs.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FutureWatcher extends Observable
{
  private List<Future> futures = Collections.synchronizedList(new ArrayList());

  public void addFuture(Future<?> future)
  {
    synchronized (this.futures) {
      this.futures.add(future);
    }
    setChanged();
    notifyObservers(future);
  }

  public void delFuture(Future<?> future)
  {
    synchronized (this.futures) {
      this.futures.remove(future);
    }
  }

  public void clearWatcher() throws InterruptedException, ExecutionException {
    this.futures.clear();
  }

  public void register(IFutureObserver observer)
  {
    addObserver(observer);
  }

  public void setObs(IFutureObserver observer) {
    register(observer);
  }

  public static void main(String[] args)
  {
  }
}