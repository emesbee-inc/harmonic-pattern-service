package com.emesbee.capm.harmonicpatternservice.controller;

import com.emesbee.capm.harmonicpatternservice.entity.RequestModel;
import com.emesbee.capm.harmonicpatternservice.service.HarmonicPatternService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/harmonic-pattern")
@SuppressFBWarnings("ENTITY_MASS_ASSIGNMENT")
public class HarmonicPattternController {

    private final HarmonicPatternService harmonicPatternService;

    public HarmonicPattternController(final HarmonicPatternService harmonicPatternService) {
        this.harmonicPatternService = harmonicPatternService;
    }


    @PostMapping("/post-harmonic-data")
    public ResponseEntity<?> postHarmonicData(final @RequestBody RequestModel requestModel) {
        return harmonicPatternService.postMessage(requestModel);
    }


}
