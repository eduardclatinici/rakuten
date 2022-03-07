package com.rakuten.cep.points.service;


import com.rakuten.cep.points.dto.CampaignProductDTO;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class CampaignService {

  private RestTemplate restTemplate;

  private static CampaignService campaignService = null;

  private String baseUrl;

  private static String HIGHEST_RATE = "/products/highest-rate";


  private CampaignService (String baseUrl) {

    this.restTemplate = new RestTemplate();
    this.baseUrl = baseUrl;
  }


  public static CampaignService getInstance (String baseUrl) {

    if (campaignService == null) {
      campaignService = new CampaignService(baseUrl);
      return campaignService;
    } else {
      return campaignService;
    }
  }


  public Set<CampaignProductDTO> getExistingProductsMatching (Set<Integer> orderProductIds) {

    CampaignProductDTO[] response = restTemplate.postForEntity(
        baseUrl + HIGHEST_RATE,
        orderProductIds,
        CampaignProductDTO[].class
    ).getBody();
    return new HashSet<>(Arrays.asList(Objects.requireNonNull(response)));
  }
}
