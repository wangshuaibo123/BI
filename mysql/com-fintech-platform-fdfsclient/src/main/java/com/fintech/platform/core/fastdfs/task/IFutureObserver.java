package com.fintech.platform.core.fastdfs.task;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public abstract interface IFutureObserver extends Observer
{
  public abstract void deal(Observable paramObservable, Future paramFuture)
    throws InterruptedException, ExecutionException;
}