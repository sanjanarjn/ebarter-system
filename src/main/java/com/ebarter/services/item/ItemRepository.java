package com.ebarter.services.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findByAvailabilityStatus(ItemAvailabilityStatus status, PageRequest pageRequest);
}
