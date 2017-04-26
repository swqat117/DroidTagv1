package com.quascenta.petersroad.droidtag.Executor;

import android.os.Process;

import java.util.concurrent.ThreadFactory;

/**
 * Created by AKSHAY on 4/25/2017.
 */

public class PriorityThreadFactory implements ThreadFactory {

    private final int mThreadPriority;


    public PriorityThreadFactory(int mThreadPriority) {
        this.mThreadPriority = mThreadPriority;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        Runnable wrapperRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Process.setThreadPriority(mThreadPriority);
                } catch (Throwable throwable) {

                }

                runnable.run();
            }

        };
        return new Thread(wrapperRunnable);
    }
}
