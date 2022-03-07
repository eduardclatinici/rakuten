package com.rakuten.cep.campaign.core.repository;

import com.rakuten.cep.campaign.core.dto.ProductRateProjection;
import com.rakuten.cep.campaign.core.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "select p.id, p.name, p.price, coalesce(c.points, 1) as rate from cep_campaign.product p "
            + "left join cep_campaign.campaign_product cp on p.id = cp.product_id "
            + "left join cep_campaign.campaign c on c.id = cp.campaign_id "
            + "where p.id in :productIds "
            + "group by p.id, cp.product_id, c.id, cp.campaign_id "
            + "having c.points = (select max(ic.points) from cep_campaign.campaign ic "
            + "join cep_campaign.campaign_product icp on ic.id = icp.campaign_id "
            + "where icp.product_id = p.id) or c.points is null ", nativeQuery = true)
    Set<ProductRateProjection> getHighestRateProductsForIdsIn(@Param("productIds") Set<Integer> productIds);
}
