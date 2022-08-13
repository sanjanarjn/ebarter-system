package com.ebarter.services.item;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long> {

    public boolean existsByName(String name);
}
