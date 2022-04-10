package com.consumer.constants;

public class InfoMessages {

    public static final String TASK_START_PROCESS = "[CONSUMER-I1] Processing task id {}";
    public static final String TASK_END_PROCESS_SUCCESS = "[CONSUMER-I2] Processing task success with task id {}";
    public static final String TASK_END_PROCESS_NO_SUCCESS = "[CONSUMER-I3] Processing task no success with task id {}";
    public static final String TASK_DLQ_START_PROCESS = "[CONSUMER-I4] Processing dlq task id {}";
    public static final String TASK_DLQ_END_PROCESS_SUCCESS = "[CONSUMER-I5] Processing dlq task success with task id {}";
    public static final String TRY_WORK_WITH_TEXT = "[CONSUMER-I6] Try work with text: {}";
}
