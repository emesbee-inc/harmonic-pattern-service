package com.emesbee.capm.harmonicpatternservice.service;

import com.emesbee.capm.harmonicpatternservice.entity.RequestModel;
import com.emesbee.capm.harmonicpatternservice.repository.HarmonicRepository;
import com.emesbee.common.web.WebResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings({"PMD.GuardLogStatement", "PMD.AvoidCatchingGenericException"})
public class HarmonicPatternService {

    private final HarmonicRepository harmonicRepository;

    public HarmonicPatternService(final HarmonicRepository harmonicRepository) {
        this.harmonicRepository = harmonicRepository;
    }

    public ResponseEntity<?> postMessage(final RequestModel requestModel) {
        try {
            if ("pattern.notification".equals(requestModel.getMsgType())) {
                var data = requestModel.getData();
                harmonicRepository.saveAll(data);
            }
            return WebResponse.generateResponse("", HttpStatus.OK, "", false);
        } catch (Exception e) {
            return WebResponse.generateResponse("", HttpStatus.OK, "", false);
        }
    }
}
