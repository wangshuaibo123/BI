package com.fintech.platform.core.fastdfs.task;

import java.util.Observable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class SimpleFutureObserver extends AbsFutureObserver
{
  public void deal(Observable o, Future future)
    throws InterruptedException, ExecutionException
  {
    System.out.println("Success upload the file:" + future.get());
  }
}