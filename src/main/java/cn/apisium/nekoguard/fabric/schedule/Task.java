package cn.apisium.nekoguard.fabric.schedule;

public abstract class Task implements Runnable{
    private int state = 0;
    private final String name;

    public Task(String name){
        this.name = name;
    }

    public abstract void run();

    public void cancel(){
        state = -1;
    }

    public String getName() {
        return name;
    }

    public boolean isCancel(){
        return state == -1;
    }
}

