package cn.apisium.nekoguard.fabric.schedule;

import net.fabricmc.fabric.api.event.server.ServerTickCallback;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Schedule {
    public static final Schedule instance = new Schedule();
    private final ConcurrentLinkedQueue<Task> nextTaskList = new ConcurrentLinkedQueue<Task>();
    private final ConcurrentHashMap<Task,Long> delayTaskMap = new ConcurrentHashMap<Task,Long>();
    private final ConcurrentHashMap<Task,Long> timerTaskMap = new ConcurrentHashMap<Task,Long>();

    private Schedule(){
        if (instance != null){
            throw new RuntimeException();
        }
        ServerTickCallback.EVENT.register(cb->tick());
    }

    public void runTask(Task task){
        nextTaskList.offer(task);
    }

    public void runTaskLater(Task task, long delay){
        delayTaskMap.put(task, delay);
    }

    public void runTaskTimer(Task task, long delay, long period){
        delayTaskMap.put(task, delay);
        timerTaskMap.put(task, period);
    }

    private void tick(){
        Task t;
        while ((t = nextTaskList.poll()) != null){
            if(!t.isCancel()){
                carryTask(t);
            }
        }

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
                carryTask(entry.getKey());
                delayItr.remove();
                continue;
            }
            entry.setValue(newDelay);
        }

        Iterator<Map.Entry<Task,Long>> timerItr = timerTaskMap.entrySet().iterator();
        while (timerItr.hasNext()){
            Map.Entry<Task,Long> entry = timerItr.next();
            Task task = entry.getKey();
            if(task.isCancel()){
                timerItr.remove();
                continue;
            }
            if(!delayTaskMap.containsKey(entry.getKey())){
                delayTaskMap.put(entry.getKey(), entry.getValue());
            }
        }
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
