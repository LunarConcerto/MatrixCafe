package cafe.lunarconcerto.matrixcafe.api.application;

import cafe.lunarconcerto.matrixcafe.api.data.event.MessageEvent;
import cafe.lunarconcerto.matrixcafe.api.data.event.NoticeEvent;
import cafe.lunarconcerto.matrixcafe.api.data.event.SystemEvent;
import cafe.lunarconcerto.matrixcafe.api.data.message.Message;
import cafe.lunarconcerto.matrixcafe.api.data.notice.Notice;
import lombok.extern.slf4j.Slf4j;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public final class Bus {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Static
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private static final Bus BUS = new Bus();

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Field
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private final EventBus MESSAGE_BUS ;

    private final EventBus NOTICE_BUS ;

    private final EventBus SYSTEM_EVENT_BUS ;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Container
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private Bus() {
        MESSAGE_BUS = EventBus.builder()
                .logger(NoLogger.NO_LOGGER)
                .installDefaultEventBus();
        NOTICE_BUS = EventBus.builder()
                .logger(NoLogger.NO_LOGGER)
                .build();
        SYSTEM_EVENT_BUS = EventBus.builder()
                .logger(NoLogger.NO_LOGGER)
                .build();
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Message Event Method
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public static void postMessage(Message message){
        //TODO: message factory
    }

    public static void registerMessageListener(Object subscriber) {
        BUS.MESSAGE_BUS.register(subscriber);
    }

    public static boolean isRegisteredMessageListener(Object subscriber) {
        return BUS.MESSAGE_BUS.isRegistered(subscriber);
    }

    public static void MessageListener(Object subscriber) {
        BUS.MESSAGE_BUS.unregister(subscriber);
    }

    public static void cancelMessageDelivery(MessageEvent event) {
        BUS.MESSAGE_BUS.cancelEventDelivery(event);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Notice Event Method
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public static void postNotice(Notice<?> notice){

    }

    public static void registerNoticeListener(Object subscriber) {
        BUS.NOTICE_BUS.register(subscriber);
    }

    public static boolean isRegisteredNoticeListener(Object subscriber) {
        return BUS.NOTICE_BUS.isRegistered(subscriber);
    }

    public static void unregisterNoticeListener(Object subscriber) {
        BUS.NOTICE_BUS.unregister(subscriber);
    }

    public static void cancelNoticeDelivery(NoticeEvent event) {
        BUS.NOTICE_BUS.cancelEventDelivery(event);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * System Event Method
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public static void postSystemEvent(SystemEvent event){
        BUS.SYSTEM_EVENT_BUS.post(event);
    }

    public static void registerSystemEventListener(Object subscriber) {
        BUS.SYSTEM_EVENT_BUS.register(subscriber);
    }

    public static boolean isRegisteredSystemEventListener(Object subscriber) {
        return BUS.SYSTEM_EVENT_BUS.isRegistered(subscriber);
    }

    public static void unregisterSystemEventListener(Object subscriber) {
        BUS.SYSTEM_EVENT_BUS.unregister(subscriber);
    }

    public static void cancelSystemEventDelivery(Object event) {
        BUS.SYSTEM_EVENT_BUS.cancelEventDelivery(event);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Logger
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    static class NoLogger implements Logger {

        static final NoLogger NO_LOGGER = new NoLogger();

        @Override
        public void log(Level level, String msg) { /* Nothing to do */ }

        @Override
        public void log(Level level, String msg, Throwable th) { /* Nothing to do */ }
    }

    @Slf4j
    static class Slf4jLogger implements Logger {

        static final Slf4jLogger SLF_4_J_LOGGER = new Slf4jLogger();

        @Override
        public void log(@NotNull Level level, String msg) {
            switch (level.intValue()){
                case 1000, 900 -> log.warn(msg);
                case 800, 700 -> log.info(msg);
                case 500, 400, 300 -> log.debug(msg);
            }
        }

        @Override
        public void log(Level level, String msg, Throwable th) {
            log.error(msg, th);
        }
    }

}
