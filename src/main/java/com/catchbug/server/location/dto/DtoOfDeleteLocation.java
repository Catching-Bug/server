package com.catchbug.server.location.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * <h1>DtoOfDeleteLocation</h1>
 * <p>
 *     Dto of delete location
 * </p>
 * <p>
 *     위치 정보를 삭제할 때 사용되는 DTO
 * </p>
 *
 * @see com.catchbug.server.location.LocationService
 * @see com.catchbug.server.location.LocationController
 * @author younghoCha
 */
@Getter
@Builder
public class DtoOfDeleteLocation {

    /**
     * 삭제하려는 위치정보 id
     */
    private Long id;

}
