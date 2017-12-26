package com.fintech.platform.core.fastdfs.task;

import java.util.Observable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public abstract class AbsFutureObserver
  implements IFutureObserver
{
  private Future future = null;

  public void update(Observable o, Object arg)
  {
    if (arg instanceof Future) {
      this.future = ((Future)arg);
      try {
        System.out.println("update : " + this.future.get() + " status:" + this.future.isDone());
      }
      catch (InterruptedException e1)
      {
        e1.printStackTrace();
      }
      catch (ExecutionException e1)
      {
        e1.printStackTrace();
      }
      if (this.future.isDone())
        try {
          deal(o, this.future);
        }
        catch (InterruptedException e) {
          e.printStackTrace();
        }
        catch (ExecutionException e) {
          e.printStackTrace();
        }
    }
  }

  public static void main(String[] args)
  {
  }
}