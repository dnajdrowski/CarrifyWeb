package com.carrify.web.carrifyweb.model.RegionZone;

import com.carrify.web.carrifyweb.model.RegionZoneCoords.RegionZoneCoordsDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@Setter
public class RegionZoneDTO {

    private Integer id;
    private Integer strokeWidth;
    private String strokeColor;
    private String zoneColor;
    private String createdAt;
    private List<RegionZoneCoordsDTO> regionZoneCoordsDTO;

    public RegionZoneDTO(RegionZone regionZone) {
        this.id = regionZone.getId();
        this.strokeWidth = regionZone.getStrokeWidth();
        this.strokeColor = regionZone.getStrokeColor();
        this.zoneColor = regionZone.getZoneColor();
        this.createdAt = regionZone.getCreatedAt().toString();
        this.regionZoneCoordsDTO = regionZone.getRegionZoneCoords().stream()
                .map(RegionZoneCoordsDTO::new)
                .collect(toList());
    }
}
