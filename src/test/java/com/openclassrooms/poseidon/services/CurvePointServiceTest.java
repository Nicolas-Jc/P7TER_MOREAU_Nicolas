package com.openclassrooms.poseidon.services;


import com.openclassrooms.poseidon.models.CurvePoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class CurvePointServiceTest {

    @Autowired
    private CurvePointService CurvePointService;

    @Test
    @DisplayName("CurvePointService ==> CurvePoint SAVE, UPDATE, FIND, DELETE - UNIT TESTS")
    void curvePointTest() {
        // *************** SAVE ***********************************
        // GIVEN
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(10);
        curvePoint.setTerm(15d);
        curvePoint.setValue(20d);
        // WHEN
        CurvePointService.addCurvePoint(curvePoint);
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
        CurvePointService.updateCurvePoint(curvePoint);
        // THEN
        Assertions.assertEquals(30, curvePoint.getCurveId());
        Assertions.assertEquals(40d, curvePoint.getTerm());
        Assertions.assertEquals(50d, curvePoint.getValue());

        // *************** CHECK IF EXISTS BY ID ***********************************
        // GIVEN
        Integer id = curvePoint.getId();
        // WHEN
        boolean checkIfIdExists = CurvePointService.checkIfIdExists(id);
        // THEN
        Assertions.assertTrue(checkIfIdExists);

        // *************** FIND ALL ***********************************
        // WHEN
        List<CurvePoint> listResult = CurvePointService.getAllCurvePoints();
        // THEN
        Assertions.assertTrue(listResult.size() > 0);

        // *************** DELETE ***********************************
        // GIVEN
        // WHEN
        CurvePointService.deleteCurvePointById(id);
        // THEN
        Optional<CurvePoint> curvePointDel = Optional.ofNullable(CurvePointService.getCurvePointById(id));
        Assertions.assertFalse(curvePointDel.isPresent());
    }
}
