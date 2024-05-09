package com.AnikateLearning.Inventory.Service.Repository;

import com.AnikateLearning.Inventory.Service.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;
@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {


    List<Inventory> findBySkuCodeIn(List<String> skuCode);
}
