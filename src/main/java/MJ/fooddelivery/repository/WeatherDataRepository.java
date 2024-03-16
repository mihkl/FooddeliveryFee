package MJ.fooddelivery.repository;

import MJ.fooddelivery.model.WeatherData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing and managing weather data in the database.
 */
@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {

    /**
     * Retrieves the latest weather data for a given station name.
     *
     * @param stationName  The name of the weather station for which the latest data is to be retrieved.
     * @param pageable     Pagination information to limit the number of results.
     * @return             A list of latest weather data entries for the specified station name,
     *                     ordered by timestamp in descending order.
     */
    @Query("SELECT wd FROM WeatherData wd WHERE wd.stationName = :stationName ORDER BY wd.timestamp DESC")
    List<WeatherData> findLatestWeatherDataByStationName(@Param("stationName") String stationName, Pageable pageable);
}


