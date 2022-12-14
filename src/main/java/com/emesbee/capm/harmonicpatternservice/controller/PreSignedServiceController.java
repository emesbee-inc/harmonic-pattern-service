package com.emesbee.capm.harmonicpatternservice.controller;

import com.emesbee.capm.harmonicpatternservice.entity.RequestModel;
import com.emesbee.capm.harmonicpatternservice.service.HarmonicPatternService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/harmonic-pattern")
@SuppressFBWarnings("ENTITY_MASS_ASSIGNMENT")
public class PreSignedServiceController {

    private final HarmonicPatternService harmonicPatternService;

    public PreSignedServiceController(HarmonicPatternService harmonicPatternService) {
        this.harmonicPatternService = harmonicPatternService;
    }


    @PostMapping("/post-harmonic-data")
    public ResponseEntity<?> PostHarmonicData(@RequestBody RequestModel requestModel) {
        return harmonicPatternService.postMessage(requestModel);
    }


}
