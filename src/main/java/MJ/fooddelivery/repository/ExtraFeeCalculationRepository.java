package MJ.fooddelivery.repository;

import MJ.fooddelivery.model.ExtraFeeCalculationRules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtraFeeCalculationRepository extends JpaRepository<ExtraFeeCalculationRules, Long> {

}
