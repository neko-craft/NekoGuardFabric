package cn.apisium.nekoguard.fabric.schedule;

import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncSchedule {
    public static final AsyncSchedule instance = new AsyncSchedule();
    private final ConcurrentHashMap<Task,Long> delayTaskMap = new ConcurrentHashMap<Task,Long>();
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final Timer timer = new Timer();

    private AsyncSchedule(){
        if (instance != null){
            throw new RuntimeException();
        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                task();
            }
        }, 0, 50);
    }

    private void task(){
        Iterator<Map.Entry<Task,Long>> delayItr = delayTaskMap.entrySet().iterator();
        while (delayItr.hasNext()){
            Map.Entry<Task,Long> entry = delayItr.next();
            Task delayTask = entry.getKey();
            long newDelay = entry.getValue()-1;
            if(delayTask.isCancel()){
                delayItr.remove();
                continue;
            }
            if(newDelay < 0){
                executorService.submit(()->carryTask(entry.getKey()));
                delayItr.remove();
                continue;
            }
            entry.setValue(newDelay);
        }
    }

    public void runTask(Task task){
        if(!task.isCancel()){
            executorService.submit(()->carryTask(task));
        }
    }

    public void runTaskLater(Task task, long delay){
        delayTaskMap.put(task, delay);
    }

    public void runTaskTimer(Task task, long delay, long period){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(!task.isCancel()){
                    carryTask(task);
                }
            }
        }, delay*50, period*50);
    }

    private void carryTask(Task task){
        try {
            task.run();
        } catch (Exception e){
            System.err.println("调度任务时出现异常.");
            System.err.println("任务： " + task.getName());
            e.printStackTrace();
        }
    }
}
