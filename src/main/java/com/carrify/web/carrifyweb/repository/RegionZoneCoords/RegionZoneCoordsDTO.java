package com.carrify.web.carrifyweb.repository.RegionZoneCoords;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionZoneCoordsDTO {

    private Integer id;
    private Double latitude;
    private Double longitude;
    private String createdAt;

    public RegionZoneCoordsDTO(RegionZoneCoords regionZoneCoords) {
        this.id = regionZoneCoords.getId();
        this.latitude = regionZoneCoords.getLatitude();
        this.longitude = regionZoneCoords.getLongitude();
        this.createdAt = regionZoneCoords.getCreatedAt().toString();
    }
}
