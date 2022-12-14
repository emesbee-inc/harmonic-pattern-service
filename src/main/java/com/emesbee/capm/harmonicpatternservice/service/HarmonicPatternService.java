package com.emesbee.capm.harmonicpatternservice.service;


import com.emesbee.capm.harmonicpatternservice.entity.RequestModel;

import com.emesbee.capm.harmonicpatternservice.repository.HarmonicRepository;
import com.emesbee.common.web.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings({"PMD.GuardLogStatement","PMD.AvoidCatchingGenericException"})
public class HarmonicPatternService {

    private final HarmonicRepository harmonicRepository;

    public HarmonicPatternService(final HarmonicRepository harmonicRepository) {
        this.harmonicRepository = harmonicRepository;
    }

    public ResponseEntity<?> postMessage(RequestModel requestModel) {
        try{
            var data = harmonicRepository.save(requestModel);
            return WebResponse.generateResponse("Message uploaded successfully ", HttpStatus.OK,"", false);
        }catch (Exception e){
            return WebResponse.generateResponse("error while uploading message", HttpStatus.INTERNAL_SERVER_ERROR, "", true);
        }
    }
}
