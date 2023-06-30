package com.rookiesquad.excelparsing.util;

/**
 * 与snowflake算法区别,返回字符串id,
 * 占用更多字节,但直观从id中看出生成时间
 */
public class Snowflake {

    /**
     * 开始时间截 (2015-01-01)
     */
    private static final long EPOCH = 1420041600000L;

    /**
     * 每一部分占有的位数
     */
    private static final long WORKER_ID_BIT = 5L; //机器id所占的位数
    private static final long DATA_CENTER_ID_BIT = 5L; //数据中心id占用的位数
    private static final long SEQUENCE_BIT = 12L; //序列号占用的位数

    /**
     * 每一部分的最大值
     */

    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BIT);
    private static final long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_ID_BIT);
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private static final long WORKER_ID_SHIFT = SEQUENCE_BIT; //机器ID向左移12位
    private static final long DATA_CENTER_ID_SHIFT = SEQUENCE_BIT + WORKER_ID_BIT; //数据标识id向左移17位(12+5)
    private static final long TIMESTAMP_SHIFT = SEQUENCE_BIT + WORKER_ID_BIT + DATA_CENTER_ID_BIT;//时间截向左移22位(5+5+12)

    /**
     * 工作机器ID(0~31)
     */
    private final long workerId;

    /**
     * 数据中心ID(0~31)
     */
    private final long dataCenterId;

    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence = 0L;

    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    public Snowflake() {
        this(0, 0);
    }

    /**
     * 构造函数
     *
     * @param workerId     工作ID (0~31)
     * @param dataCenterId 数据中心ID (0~31)
     */
    public Snowflake(long workerId, long dataCenterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
        }
        if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("dataCenter Id can't be greater than %d or less than 0", MAX_DATA_CENTER_ID));
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long timestamp = generateTimestamp();
        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new IllegalStateException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        //如果是同一时间生成的，则进行毫秒内序列
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            //时间戳改变，毫秒内序列重置
            sequence = 0L;
        }
        //上次生成ID的时间截
        lastTimestamp = timestamp;
        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - EPOCH) << TIMESTAMP_SHIFT)
                | (dataCenterId << DATA_CENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT)
                | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = generateTimestamp();
        while (timestamp <= lastTimestamp) {
            timestamp = generateTimestamp();
        }
        return timestamp;
    }

    /**
     * 生成时间戳
     */
    private long generateTimestamp() {
        return System.currentTimeMillis();
    }

}

