package hello.advanced.trace;


public class TraceStatus {
    TraceId traceId;
    String message;
    Long startTm;

    public TraceStatus(TraceId traceId, String message, Long startTm) {
        this.traceId = traceId;
        this.message = message;
        this.startTm = startTm;
    }

    public TraceId getTraceId() {
        return traceId;
    }

    public String getMessage() {
        return message;
    }

    public Long getStartTm() {
        return startTm;
    }
}
