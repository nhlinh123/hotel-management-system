package com.ptit.hotelmanagementsystem.repository;

import com.ptit.hotelmanagementsystem.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
}
