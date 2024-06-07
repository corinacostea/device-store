package com.device.store.repository;

import com.device.store.model.DeviceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceOrderRepository extends JpaRepository<DeviceOrder, Long> {
}
