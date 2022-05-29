package hello.advanced.trace.logtrace;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalLogTrace implements LogTrace{

    private final String START_PREFIX = "|-->";
    private final String COMPLETE_PREFIX = "|<--";
    private final String EXCEPTION_PREFIX="<X-";

    private ThreadLocal<TraceId> traceIdHolder = new ThreadLocal<>();
    @Override
    public TraceStatus begin(String message) {
        syncTraceId();
        TraceId traceId = traceIdHolder.get();
        long startTime = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId, message, startTime);
    }

    @Override
    public void end(TraceStatus status) {
        complete(status, null);
    }

    @Override
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    private void complete(TraceStatus status, Exception e) {
        TraceId traceId = status.getTraceId();
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - status.getStartTm();

        if(e == null)
            log.info("[{}] {}{} time={}ms", traceId.getId(), addSpace(COMPLETE_PREFIX, traceId.getLevel()),
                    status.getMessage(), resultTime);
        else
            log.info("[{}] {}{} time={}ms ex={}", traceId.getId(), addSpace(EXCEPTION_PREFIX, traceId.getLevel()),
                    status.getMessage(), resultTime, e.getMessage());

        releaseId();
    }

    private void releaseId() {
        TraceId traceId = traceIdHolder.get();
        if(traceId.isFirstLevel())
            traceIdHolder.remove();
        else
            traceIdHolder.set(traceId.createPrevId());
    }

    private String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i < level; i++)
            sb.append(i == level-1 ? prefix : "|  ");
        return sb.toString();
    }

    private void syncTraceId() {
        TraceId traceId = traceIdHolder.get();

        if(traceId == null)
            traceIdHolder.set(new TraceId());
        else
            traceIdHolder.set(traceId.createNextId());
    }


}
