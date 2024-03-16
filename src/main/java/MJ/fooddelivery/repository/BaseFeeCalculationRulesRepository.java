package MJ.fooddelivery.repository;

import MJ.fooddelivery.model.BaseFeeCalculationRules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and managing base fee calculation rules in the database.
 */
@Repository
public interface BaseFeeCalculationRulesRepository extends JpaRepository<BaseFeeCalculationRules, Long> {

    /**
     * Retrieves the base fee calculation rules for a given city and vehicle type.
     *
     * @param city         The city for which base fee calculation rules are to be retrieved.
     * @param vehicleType  The type of vehicle for which base fee calculation rules are to be retrieved.
     * @return             The base fee calculation rules for the specified city and vehicle type,
     *                     or null if no matching rules are found.
     */
    @Query("SELECT b FROM BaseFeeCalculationRules b WHERE b.city = :city AND b.vehicleType = :vehicleType")
    BaseFeeCalculationRules findBaseFeeByCityAndVehicleType(@Param("city") String city, @Param("vehicleType") String vehicleType);
}

