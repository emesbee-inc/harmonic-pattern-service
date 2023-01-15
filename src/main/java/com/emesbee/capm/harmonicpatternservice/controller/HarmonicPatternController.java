package com.emesbee.capm.harmonicpatternservice.controller;

import com.emesbee.capm.harmonicpatternservice.entity.RequestModel;
import com.emesbee.capm.harmonicpatternservice.service.HarmonicPatternService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1")
@SuppressFBWarnings("ENTITY_MASS_ASSIGNMENT")
public class HarmonicPatternController {

    private final HarmonicPatternService harmonicPatternService;

    public HarmonicPatternController(final HarmonicPatternService harmonicPatternService) {
        this.harmonicPatternService = harmonicPatternService;
    }

    @PostMapping(value = "/post-harmonic-data", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> postHarmonicData(final @RequestBody RequestModel requestModel) {
        return harmonicPatternService.postMessage(requestModel);
    }

}
