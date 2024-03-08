package MJ.fooddelivery.repository;

import MJ.fooddelivery.model.WeatherData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
    @Query("SELECT wd FROM WeatherData wd WHERE wd.stationName = :stationName ORDER BY wd.timestamp DESC")
    List<WeatherData> findLatestWeatherDataByStationName(@Param("stationName") String stationName, Pageable pageable);
}

