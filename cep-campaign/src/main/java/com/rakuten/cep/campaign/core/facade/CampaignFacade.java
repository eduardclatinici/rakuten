package com.rakuten.cep.campaign.core.facade;

import com.rakuten.cep.campaign.core.domain.Product;
import com.rakuten.cep.campaign.core.dto.CampaignDTO;
import com.rakuten.cep.campaign.core.dto.ProductDTO;
import com.rakuten.cep.campaign.core.domain.Campaign;
import com.rakuten.cep.campaign.core.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
public class CampaignFacade {

    @Autowired
    private CampaignService campaignService;

    public CampaignDTO createCampaign(CampaignDTO campaignDTO) {
        checkCampaignDates(campaignDTO.getStartDate(), campaignDTO.getEndDate());
        return transformCampaignDTOFrom(campaignService.saveCampaign(transformCampaignFrom(campaignDTO)));
    }

    private void checkCampaignDates(LocalDate startDate, LocalDate endDate) {
        if (!startDate.isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The earliest start date for this campaign is tomorrow");
        }
        if (!endDate.isAfter(startDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campaign end date must be after campaign start date");
        }
    }

    private Campaign transformCampaignFrom(CampaignDTO campaignDTO) {
        return Campaign.builder()
                .name(campaignDTO.getName())
                .startDate(campaignDTO.getStartDate())
                .endDate(campaignDTO.getEndDate())
                .points(campaignDTO.getPoints())
                .products(transformProductsFrom(campaignDTO.getProducts()))
                .build();
    }

    private CampaignDTO transformCampaignDTOFrom(Campaign campaign) {
        return new CampaignDTO(campaign.getId(), campaign.getName(), campaign.getStartDate(),
                campaign.getEndDate(), campaign.getPoints(), campaign.getProducts().stream()
                .map(this::transformProductDTOFrom).collect(toSet()));
    }

    private Set<Product> transformProductsFrom(Set<ProductDTO> products) {
        return products.stream()
                .map(product -> Product.builder().id(product.getId()).name(product.getName()).price(product.getPrice()).build())
                .collect(toSet());
    }

    public Set<CampaignDTO> getActiveCampaignsOn(LocalDate date) {
        Set<Campaign> activeCampaigns = campaignService.getActiveCampaignsOn(date);
        return activeCampaigns.stream()
                .map(this::transformCampaignDTOFrom)
                .collect(toSet());
    }

    private ProductDTO transformProductDTOFrom(Product product) {
        return new ProductDTO(product.getId(), product.getName(), product.getPrice());
    }

    public CampaignDTO getCampaignById(int id) {
        return transformCampaignDTOFrom(campaignService.getCampaignById(id));
    }

    public CampaignDTO updateCampaign(int id, CampaignDTO campaignDTO) {
        if (campaignDTO.getId() == 0 || campaignDTO.getId() != id) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ids don't match or body id is null");
        }
        checkCampaignDates(campaignDTO.getStartDate(), campaignDTO.getEndDate());
        Campaign campaign = campaignService.getCampaignById(id).toBuilder()
                .name(campaignDTO.getName())
                .startDate(campaignDTO.getStartDate())
                .endDate(campaignDTO.getEndDate())
                .points(campaignDTO.getPoints())
                .products(transformProductsFrom(campaignDTO.getProducts()))
                .build();

        return transformCampaignDTOFrom(campaignService.saveCampaign(campaign));
    }

    public void deleteCampaignBy(int id) {
        campaignService.deleteCampaignBy(id);
    }
}
