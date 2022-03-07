package com.rakuten.cep.campaign.core.service;

import com.rakuten.cep.campaign.core.domain.Campaign;
import com.rakuten.cep.campaign.core.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Set;

@Service
public class CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;

    public Campaign saveCampaign(Campaign campaign) {
        return this.campaignRepository.save(campaign);
    }

    public Set<Campaign> getActiveCampaignsOn(LocalDate date) {
        return this.campaignRepository.findAllByStartDateIsBeforeAndEndDateIsAfter(date);
    }

    public Campaign getCampaignById(int id) {
        return this.campaignRepository.findById(id)
                .orElseThrow(() -> getCampaignNotFoundExceptionFor(id));
    }


    private ResponseStatusException getCampaignNotFoundExceptionFor(int id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Campaign not found for id: " + id);
    }

    public void deleteCampaignBy(int id) {
        if (!campaignRepository.existsById(id)) {
            throw getCampaignNotFoundExceptionFor(id);
        }
        campaignRepository.deleteById(id);
    }
}
