package com.fintech.platform.core.fastdfs.task;

public abstract class BaseMutilFastDealMaster
{
  protected static int CORE_POOL_SIZE = 10;
  protected static int MAX_NUM_POOL_SIZE = 100;
  protected static long KEEP_ALIVE_TIME = 10L;
  protected static int TASK_QUEUE = 100;
  private int corePoolSize;
  private int maxNumPoolSize;
  private long keepAliveTime;
  private int taskQueue;

  public int getCorePoolSize()
  {
    if (this.corePoolSize == 0)
      this.corePoolSize = CORE_POOL_SIZE;

    return this.corePoolSize;
  }

  public void setCorePoolSize(int corePoolSize)
  {
    this.corePoolSize = corePoolSize;
  }

  public int getMaxNumPoolSize()
  {
    if (this.maxNumPoolSize == 0)
      this.maxNumPoolSize = MAX_NUM_POOL_SIZE;

    return this.maxNumPoolSize;
  }

  public void setMaxNumPoolSize(int maxNumPoolSize)
  {
    this.maxNumPoolSize = maxNumPoolSize;
  }

  public long getKeepAliveTime()
  {
    if (this.keepAliveTime == 0L)
      this.keepAliveTime = KEEP_ALIVE_TIME;

    return this.keepAliveTime;
  }

  public void setKeepAliveTime(long keepAliveTime)
  {
    this.keepAliveTime = keepAliveTime;
  }

  public int getTaskQueue()
  {
    if (this.taskQueue == 0)
      this.taskQueue = TASK_QUEUE;

    return this.taskQueue;
  }

  public void setTaskQueue(int taskQueue)
  {
    this.taskQueue = taskQueue;
  }
}