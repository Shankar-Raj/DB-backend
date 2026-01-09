package com.gpstracker.backend.repository;

import com.gpstracker.backend.entity.deviceLiveDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface deviceLiveDetailsRepository
        extends JpaRepository<deviceLiveDetails, Long> {

}
