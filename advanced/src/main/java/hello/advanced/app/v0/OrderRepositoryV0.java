package hello.advanced.app.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV0 {

    public void sava(String itemId){

        if(itemId.equals("ex")){
            throw new IllegalStateException("예외 발생");
        }
        sleep(1000); //상품을 저장하는데 약 1초가 걸린다고 가정
    }

    private void sleep(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
