package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.models.CurvePoint;
import com.openclassrooms.poseidon.repositories.CurvePointRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class CurvePointService {

    private static final Logger logger = LogManager.getLogger(CurvePointService.class);

    @Autowired
    private CurvePointRepository curvePointRepository;

    @Autowired
    public CurvePointService(CurvePointRepository curvePointRep) {
        this.curvePointRepository = curvePointRep;
    }

    public List<CurvePoint> getAllCurvePoints() {
        return curvePointRepository.findAll();
    }

    public void addCurvePoint(CurvePoint curvePoint) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        curvePoint.setAsOfDate(timestamp);
        curvePoint.setCreationDate(timestamp);
        curvePointRepository.save(curvePoint);
        logger.info("CurvePoint Id:{} was added to Curve Point List", curvePoint.getCurveId());
    }

    public void updateCurvePoint(CurvePoint curvePoint) {
        curvePointRepository.save(curvePoint);
        logger.info("UPDATE CurvePoint : OK");
    }

    public boolean checkIfIdExists(int id) {
        return curvePointRepository.existsById(id);
    }

    public void deleteCurvePointById(int id) {
        curvePointRepository.deleteById(id);
        logger.info("Delete CurvePoint : OK");
    }

    public CurvePoint getCurvePointById(int curvePointId) {
        return curvePointRepository.findById(curvePointId);
    }

}
