package eu.autorank.carsales.repository;

import eu.autorank.carsales.model.CarOffer;
import eu.autorank.carsales.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarOfferRepository extends CrudRepository<CarOffer, Long> {
    List<CarOffer> findAll(Sort publishedAt);

    List<CarOffer> findAllByUserIsOrderByPublishedAtDesc(User user);

    @Query("SELECT c FROM CarOffer c")
    List<CarOffer> findWithPageable(Pageable pageable);

    @Query("SELECT c FROM CarOffer c WHERE c.make LIKE %:make% AND c.model LIKE %:model% ORDER BY c.publishedAt DESC")
    List<CarOffer> searchOffers(@Param("make") String make, @Param("model") String model);

    @Query("SELECT c FROM CarOffer c WHERE c.body=:bodyType ORDER BY c.publishedAt DESC")
    List<CarOffer> searchOffersByBodyType(@Param("bodyType") CarOffer.BodyType type);
}
