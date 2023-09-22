package cafe.lunarconcerto.matrixcafe.api.data.event.time;

import cafe.lunarconcerto.matrixcafe.api.data.event.SystemEvent;

/**
 * 与时间有关事件的基类.
 */
public class TimeEvent extends SystemEvent {
    protected int hour = 0;
    protected int minute = 0;
    protected int second = 0;
    protected long timestamp = System.currentTimeMillis();

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Container
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public TimeEvent() { }

    public TimeEvent(int hour) {
        this.hour = hour;
    }

    public TimeEvent(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public TimeEvent(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Method
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public boolean compareTime(int time){
        if (time <= 24){
            return time == hour;
        }else if (time <= 24_9){
            return (time / 10 == hour) && (time % 10 == minute);
        } else if (time <= 24_60) {
            return (time / 100 == hour) && (time % 100 == minute);
        }else if (time <= 24_60_9){
            return (time / 1000 == hour) && (time % 1000 / 10 == minute) && (time % 10 == second);
        }else if (time <= 24_60_60){
            return (time / 10000 == hour) && (time % 10000 / 100 == minute) && (time % 100 == second);
        }else {
            return false;
        }
    }

    public boolean compareHour(int hour){
        return this.hour == hour;
    }

    public boolean compareMinute(int minute){
        return this.minute == minute;
    }

    public boolean compareSeconds(int second){
        return this.second == second;
    }

    public boolean compareTimestamp(long timestamp){
        return this.timestamp == timestamp;
    }

}
