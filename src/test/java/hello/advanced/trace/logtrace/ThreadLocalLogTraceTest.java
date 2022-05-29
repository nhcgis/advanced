package hello.advanced.trace.logtrace;

import hello.advanced.trace.TraceStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThreadLocalLogTraceTest {
    private ThreadLocalLogTrace logTrace = new ThreadLocalLogTrace();

    @Test
    void begin_end(){
        TraceStatus status1 = logTrace.begin("Hello1");
        TraceStatus status2 = logTrace.begin("hello2");
        logTrace.end(status2);
        logTrace.end(status1);
    }

    @Test
    void exceptionTest(){
        TraceStatus status = logTrace.begin("hello1");
        TraceStatus status2 = logTrace.begin("hello2");
        logTrace.exception(status2, new IllegalStateException("예외발생"));
        logTrace.end(status);
    }

}