package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mpelizzari on 2019-10-12
 * @project apptracking
 */
@Slf4j
@Service
public class TestService {

    private List<TestPojo> elements = new ArrayList<>();

    public TestService() {

        for (int i = 0; i< 202; i++) {
            TestPojo tp = new TestPojo("titolo_"+i,"descrizione_"+i);
            elements.add(tp);
        }
    }


    public List<TestPojo> getElements(int offset, int limit) {
        log.info("From {} to {} ",offset,offset+limit);
        List<TestPojo> testPojos =
                elements.subList(offset, Math.min(offset+limit,elements.size()));
        return testPojos;
    }

    public int countTotalElements() {
        log.info("Total elements {}",elements.size());
        return elements.size();
    }
}
