package hello.advanced.trace.logtrace;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FieldLogTrace implements LogTrace{
    private static final String START_PREFIX="-->";
    private static final String COMPLETE_PREFIX="<--";
    private static final String EXCEPTION_PREFIX="<X-";

    private TraceId traceIdHolder;

    @Override
    public TraceStatus begin(String message) {
        syncTraceId();
        TraceId traceId = traceIdHolder;
        Long startTime = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSapce(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId, message, startTime);
    }

    private String addSapce(String prefix, int level){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i < level; i++)
            sb.append((i == level-1 ? "|" + prefix : "|  "));
        return sb.toString();
    }

    private void syncTraceId() {
        if(traceIdHolder == null)
            traceIdHolder = new TraceId();
        else
            traceIdHolder = traceIdHolder.createNextId();
    }

    @Override
    public void end(TraceStatus status) {
        complete(status, null);
    }

    @Override
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    private void complete(TraceStatus status, Exception e){
        TraceId traceId = status.getTraceId();
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - status.getStartTm();
        if(e == null)
            log.info("[{}] {}{} time={}ms", traceId.getId(), addSapce(COMPLETE_PREFIX, traceId.getLevel()),
                    status.getMessage(), resultTime);
        else
            log.info("[{}] {}{} time={}ms ex={}", traceId.getId(), addSapce(COMPLETE_PREFIX, traceId.getLevel()),
                    status.getMessage(), resultTime, e.getMessage());

        releaseTraceId();
    }

    private void releaseTraceId() {
        if(traceIdHolder.isFirstLevel())
            traceIdHolder = null;
        else
            traceIdHolder = traceIdHolder.createPrevId();
    }
}
