package com.rakuten.cep.campaign.core.rest;

import com.rakuten.cep.campaign.core.dto.CampaignDTO;
import com.rakuten.cep.campaign.core.facade.CampaignFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDate;
import java.util.Set;
import javax.validation.Valid;

@RestController
@RequestMapping("/campaign")
public class CampaignController {

    @Autowired
    private CampaignFacade campaignFacade;

    @PostMapping
    public ResponseEntity<CampaignDTO> createCampaign(@RequestBody @Valid CampaignDTO campaignDTO) {
        return ResponseEntity.created(URI.create("/campaign/")).body(campaignFacade.createCampaign(campaignDTO));
    }

    @GetMapping(path = "/active/{date}")
    public Set<CampaignDTO> getActiveCampaigns(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return campaignFacade.getActiveCampaignsOn(date);
    }

    @GetMapping(path = "/{id}")
    public CampaignDTO getCampaignById(@PathVariable("id") int id) {
        return campaignFacade.getCampaignById(id);
    }

    @PutMapping(path = "/{id}")
    public CampaignDTO updateCampaign(@PathVariable("id") int id, @RequestBody @Valid CampaignDTO campaignDTO) {
        return campaignFacade.updateCampaign(id, campaignDTO);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteCampaign(@PathVariable("id") int id) {
        campaignFacade.deleteCampaignBy(id);
    }
}
