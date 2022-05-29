package hello.advanced.app.v0;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryV0 {
    void save(String itemId){
        if(itemId.equals("ex"))
            throw new IllegalStateException("예외발생!!!");
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
