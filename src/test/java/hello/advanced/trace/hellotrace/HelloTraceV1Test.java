package hello.advanced.trace.hellotrace;


import hello.advanced.trace.TraceStatus;
import org.junit.jupiter.api.Test;

public class HelloTraceV1Test {
    @Test
    void start_end_test(){
        HelloTrace trace = new HelloTrace();
        TraceStatus status= trace.begin("hello");
        trace.end(status);
    }
}
