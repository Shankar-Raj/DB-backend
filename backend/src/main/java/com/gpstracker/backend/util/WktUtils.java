package com.gpstracker.backend.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Leaflet ↔ MySQL WKT coordinate conversion.
 *
 * Coordinate order:
 *   Leaflet → [lat, lng]       e.g. [20.59, 78.96]
 *   WKT     → POINT(lng lat)   e.g. POINT(78.96 20.59)
 *   WKT is always LONGITUDE first.
 */
public final class WktUtils {

    private WktUtils() {}

    // ── Leaflet → WKT ──────────────────────────────────

    /** [[lat,lng],...] → "POLYGON((lng lat, ...))" */
    public static String toPolygonWkt(List<List<Double>> coords) {
        List<String> pairs = coords.stream()
                .map(p -> p.get(1) + " " + p.get(0))
                .collect(Collectors.toCollection(ArrayList::new));
        // WKT ring must close
        if (!pairs.get(0).equals(pairs.get(pairs.size() - 1))) {
            pairs.add(pairs.get(0));
        }
        return "POLYGON((" + String.join(", ", pairs) + "))";
    }

    /** [[lat,lng],...] → "LINESTRING(lng lat, ...)" */
    public static String toLineStringWkt(List<List<Double>> coords) {
        String pairs = coords.stream()
                .map(p -> p.get(1) + " " + p.get(0))
                .collect(Collectors.joining(", "));
        return "LINESTRING(" + pairs + ")";
    }

    /** [lat, lng] → "POINT(lng lat)" */
    public static String toPointWkt(List<Double> latLng) {
        return "POINT(" + latLng.get(1) + " " + latLng.get(0) + ")";
    }

    // ── WKT → Leaflet ──────────────────────────────────

    /** "POLYGON((lng lat,...))" → [[lat,lng],...] */
    public static List<List<Double>> fromPolygonWkt(String wkt) {
        String inner = wkt.replace("POLYGON((", "").replace("))", "");
        List<List<Double>> coords = parsePairs(inner);
        // Strip WKT closing duplicate point
        if (coords.size() > 1 &&
                coords.get(0).equals(coords.get(coords.size() - 1))) {
            coords.remove(coords.size() - 1);
        }
        return coords;
    }

    /** "LINESTRING(lng lat,...)" → [[lat,lng],...] */
    public static List<List<Double>> fromLineStringWkt(String wkt) {
        String inner = wkt.replace("LINESTRING(", "").replace(")", "");
        return parsePairs(inner);
    }

    /** "POINT(lng lat)" → [lat, lng] */
    public static List<Double> fromPointWkt(String wkt) {
        String[] p = wkt.replace("POINT(", "").replace(")", "").trim().split("\\s+");
        return List.of(Double.parseDouble(p[1]), Double.parseDouble(p[0]));
    }

    private static List<List<Double>> parsePairs(String str) {
        return List.of(str.split(",")).stream()
                .map(s -> {
                    String[] p = s.trim().split("\\s+");
                    return List.of(Double.parseDouble(p[1]), Double.parseDouble(p[0]));
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }
}