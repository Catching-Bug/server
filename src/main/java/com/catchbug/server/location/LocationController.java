package com.catchbug.server.location;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
/**
 * <h1>LocationController</h1>
 * <p>
 *     MVC pattern of Controller
 * </p>
 * <p>
 *     MVC 패턴의 Controller
 * </p>
 *
 * @see com.catchbug.server.location.LocationService
 * @author younghoCha
 */
@RequiredArgsConstructor
@RestController
public class LocationController {
    private final LocationService locationService;
}
