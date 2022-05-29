package hello.advanced.app.v1;

import hello.advanced.trace.hellotrace.HelloTrace;
import hello.advanced.trace.TraceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV1 {
    private final HelloTrace trace;

    void save(String itemId){
        TraceStatus status = null;
        try {
            status = trace.begin("OrderRepository.save()");
            if (itemId.equals("ex"))
                throw new IllegalStateException("예외발생!!!");
            sleep(1000);
            trace.end(status);
        } catch (IllegalStateException e){
            trace.exception(status, e);
            throw e;
        }
    }

    private void sleep(int mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
