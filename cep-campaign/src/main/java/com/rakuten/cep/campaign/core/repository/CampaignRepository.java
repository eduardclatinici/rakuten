package com.rakuten.cep.campaign.core.repository;

import com.rakuten.cep.campaign.core.domain.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Integer> {

    @Query("select c from Campaign c where c.startDate <= :date and c.endDate >= :date")
    Set<Campaign> findAllByStartDateIsBeforeAndEndDateIsAfter(@Param("date") LocalDate date);

}
