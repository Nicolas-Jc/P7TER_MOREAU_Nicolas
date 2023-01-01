package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.models.RuleModel;
import com.openclassrooms.poseidon.repositories.RuleNameRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleNameService {

    private static final Logger logger = LogManager.getLogger(RuleNameService.class);

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @Autowired
    public RuleNameService(RuleNameRepository ruleNameRep) {
        this.ruleNameRepository = ruleNameRep;
    }

    public List<RuleModel> getAllRuleNames() {
        return ruleNameRepository.findAll();
    }

    public boolean checkIfIdExists(int id) {
        return ruleNameRepository.existsById(id);
    }

    public void saveRuleName(RuleModel ruleName) {
        ruleNameRepository.save(ruleName);
        logger.info("Rule Name Id:{} was saved to Rule List", ruleName.getId());
    }

    public void deleteRuleNameById(int id) {
        ruleNameRepository.deleteById(id);
        logger.info("Delete Rule : OK");
    }

    public RuleModel getRuleNameById(int id) {
        return ruleNameRepository.findById(id);
    }

}
