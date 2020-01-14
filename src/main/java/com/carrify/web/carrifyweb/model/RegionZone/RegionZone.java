package com.carrify.web.carrifyweb.model.RegionZone;

import com.carrify.web.carrifyweb.model.RegionZoneCoords.RegionZoneCoords;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "region_zone")
public class RegionZone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Integer id;

    @Column(name = "stroke_width", nullable = false)
    private Integer strokeWidth;

    @Column(name = "stroke_color", nullable = false)
    private String strokeColor;

    @Column(name = "zone_color", nullable = false)
    private String zoneColor;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder.Default
    @OneToMany(mappedBy = "regionZone", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RegionZoneCoords> regionZoneCoords = new ArrayList<>();
}
