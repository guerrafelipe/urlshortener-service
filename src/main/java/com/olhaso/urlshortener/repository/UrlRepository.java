package com.olhaso.urlshortener.repository;

import com.olhaso.urlshortener.entities.UrlEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UrlRepository extends MongoRepository<UrlEntity, String> {

    Optional<UrlEntity> findById(String id);

}
