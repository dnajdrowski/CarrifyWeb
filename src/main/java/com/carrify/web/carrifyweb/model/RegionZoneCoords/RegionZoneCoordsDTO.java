package com.carrify.web.carrifyweb.model.RegionZoneCoords;

import com.carrify.web.carrifyweb.formatter.CarrifyDateTimeFormatter;
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
        this.createdAt = CarrifyDateTimeFormatter.formatDate(regionZoneCoords.getCreatedAt());
    }
}
