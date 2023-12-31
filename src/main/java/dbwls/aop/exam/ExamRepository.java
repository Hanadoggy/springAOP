package dbwls.aop.exam;

import dbwls.aop.exam.annotation.Retry;
import dbwls.aop.exam.annotation.Trace;
import org.springframework.stereotype.Repository;

@Repository
public class ExamRepository {

    private static int seq = 0;

    @Trace
    @Retry(4)
    public String save(String itemId) {
        seq++;
        if (seq % 5 == 0) {
            throw new IllegalStateException("exception!");
        }
        return "ok";
    }
}
