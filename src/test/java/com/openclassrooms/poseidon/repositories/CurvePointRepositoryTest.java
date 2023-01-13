package com.openclassrooms.poseidon.repositories;


import com.openclassrooms.poseidon.models.CurvePoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        // WHEN
        curvePoint = curvePointRepository.save(curvePoint);
        // THEN
        Assertions.assertNotNull(curvePoint.getId());
        Assertions.assertEquals(10, curvePoint.getCurveId());
        Assertions.assertEquals(15d, curvePoint.getTerm());
        Assertions.assertEquals(20d, curvePoint.getValue());

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
        // WHEN
        curvePointRepository.deleteById(id);
        // THEN
        Optional<CurvePoint> curvePointDel = curvePointRepository.findById(id);
        Assertions.assertFalse(curvePointDel.isPresent());
    }
}
