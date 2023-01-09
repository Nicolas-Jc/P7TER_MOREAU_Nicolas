package com.openclassrooms.poseidon.repositories;


import com.openclassrooms.poseidon.models.CurvePoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class CurvePointRepositoryTest {
    @Autowired
    private CurvePointRepository curvePointRepository;

    @Test
    @DisplayName("CurvePointRepository ==>  CurvePoint SAVE, UPDATE, FIND, DELETE - UNIT TESTS")
    void curvePointTest() {
        // *************** SAVE ***********************************
        // GIVEN
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(10);
        curvePoint.setTerm(15d);
        curvePoint.setValue(20d);

        String stringDate = "2007-11-11 12:13:14";
        Timestamp timeStamp = Timestamp.valueOf(stringDate);
        curvePoint.setAsOfDate(timeStamp);
        curvePoint.setCreationDate(timeStamp);

        // WHEN
        curvePoint = curvePointRepository.save(curvePoint);
        // THEN
        Assertions.assertNotNull(curvePoint.getId());
        Assertions.assertEquals(10, curvePoint.getCurveId());
        Assertions.assertEquals(15d, curvePoint.getTerm());
        Assertions.assertEquals(20d, curvePoint.getValue());
        Assertions.assertEquals(timeStamp, curvePoint.getAsOfDate());
        Assertions.assertEquals(timeStamp, curvePoint.getCreationDate());


        // *************** UPDATE ***********************************
        // GIVEN
        curvePoint.setCurveId(30);
        curvePoint.setTerm(40d);
        curvePoint.setValue(50d);
        // WHEN
        curvePoint = curvePointRepository.save(curvePoint);
        // THEN
        Assertions.assertEquals(30, curvePoint.getCurveId());
        Assertions.assertEquals(40d, curvePoint.getTerm());
        Assertions.assertEquals(50d, curvePoint.getValue());

        // *************** CHECK IF EXISTS BY ID ***********************************
        // GIVEN
        Integer id = curvePoint.getId();
        // WHEN
        boolean checkIfIdExists = curvePointRepository.existsById(id);
        // THEN
        Assertions.assertTrue(checkIfIdExists);

        // *************** FIND ALL ***********************************
        // WHEN
        List<CurvePoint> listResult = curvePointRepository.findAll();
        // THEN
        Assertions.assertTrue(listResult.size() > 0);

        // *************** DELETE ***********************************
        // GIVEN
        //Integer id3 = curvePoint.getBidListId();
        // WHEN
        curvePointRepository.deleteById(id);
        // THEN
        Optional<CurvePoint> curvePointDel = curvePointRepository.findById(id);
        Assertions.assertFalse(curvePointDel.isPresent());
    }
}
