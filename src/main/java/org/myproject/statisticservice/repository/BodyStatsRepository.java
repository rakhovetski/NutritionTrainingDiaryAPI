package org.myproject.statisticservice.repository;

import org.myproject.statisticservice.entity.BodyStatRecord;
import org.myproject.statisticservice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BodyStatsRepository extends JpaRepository<BodyStatRecord, Long> {

    Optional<BodyStatRecord> findByIdAndUser(Long id, User user);

    Page<BodyStatRecord> findAllByUser(User user, Pageable pageable);
}
