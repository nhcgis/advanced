package hello.advanced.trace.logtrace;

import hello.advanced.trace.TraceStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldLogTraceTest {

    @Test
    void start_end_test(){
        LogTrace logTrace = new FieldLogTrace();
        TraceStatus status1 = logTrace.begin("hello1");
        TraceStatus status2 = logTrace.begin("hello2");
        logTrace.end(status2);
        logTrace.end(status1);
    }

}