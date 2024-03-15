package MJ.fooddelivery.repository;

import MJ.fooddelivery.model.BaseFeeCalculationRules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface BaseFeeCalculationRulesRepository extends JpaRepository<BaseFeeCalculationRules, Long> {
    @Query("SELECT b FROM BaseFeeCalculationRules b WHERE b.city = :city AND b.vehicleType = :vehicleType")
    BaseFeeCalculationRules findBaseFeeByCityAndVehicleType(@Param("city") String city, @Param("vehicleType") String vehicleType);

}
