package hello.advanced.app.v0;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryV0 {
    void save(String itemId){
        if(itemId.equals("ex"))
            throw new IllegalStateException("μμΈλ°μ!!!");
        sleep(1000);
    }

    private void sleep(int mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
