package com.catchbug.server.location;


import org.springframework.data.jpa.repository.JpaRepository;
/**
 * <h1>LocationRepository</h1>
 * <p>
 *     DAO of Location Entity
 * </p>
 * <p>
 *     Location 엔티티에 대한 DAO 클래스
 * </p>
 *
 * @see com.catchbug.server.location.LocationService
 * @author younghoCha
 */
public interface LocationRepository extends JpaRepository<Location, Long> {
}
