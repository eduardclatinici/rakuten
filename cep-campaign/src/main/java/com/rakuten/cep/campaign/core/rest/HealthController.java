package com.rakuten.cep.campaign.core.rest;

import com.rakuten.cep.campaign.core.dto.StatusDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping
    public ResponseEntity<StatusDTO> getCampaignById() {
        return ResponseEntity.ok(new StatusDTO("Up and running"));
    }
}
