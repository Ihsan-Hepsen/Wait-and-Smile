package com.server.repository;

import com.server.domain.WaitListEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaitListEntryRepository extends JpaRepository<WaitListEntry, Long> {
}
