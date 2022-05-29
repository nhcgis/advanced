package hello.advanced.trace.hellotrace;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HelloTrace2 {
    private static final String START_PREFIX="-->";
    private static final String END_PREFIX="<--";
    private static final String EX_PREFIX="<X-";

    public TraceStatus begin(String message){
        TraceId traceId = new TraceId();
        Long startTm = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()),
                message);
        return new TraceStatus(traceId, message, startTm);
    }

    public TraceStatus beginSync(TraceId prevtraceId, String message){
        TraceId traceId = prevtraceId.createNextId();
        Long startTm = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()),
                message);
        return new TraceStatus(traceId, message, startTm);
    }

    public void end(TraceStatus status){
        complete(status, null);
    }

    public void exception(TraceStatus status, Exception e){
        complete(status, e);
    }

    private void complete(TraceStatus status, Exception e){
        long endTm = System.currentTimeMillis();
        long resultTm = endTm - status.getStartTm();
        TraceId trace = status.getTraceId();
        if(e  == null)
            log.info("[{}] {}{} time={}ms", trace.getId(), addSpace(END_PREFIX, trace.getLevel()),
                    status.getMessage(), resultTm);
        else
            log.info("[{}] {}{} time={}ms ex={}", trace.getId(), addSpace(END_PREFIX, trace.getLevel()),
                    status.getMessage(), resultTm, e.getMessage());
    }

    private String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i < level; i++)
            sb.append(i == level-1 ? "|" + prefix : "|  ");
        return sb.toString();
    }
}
