package MJ.fooddelivery.repository;

import MJ.fooddelivery.model.ExtraFeeCalculationRules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and managing extra fee calculation rules in the database.
 */
@Repository
public interface ExtraFeeCalculationRepository extends JpaRepository<ExtraFeeCalculationRules, Long> {
}

