package com.example.android.tropeca.map.direction;

import java.util.List;

public class DirectionRoot {
    private List<Route> routes;

    public List<Route> getRoutes() {
        return routes;
    }

    public DirectionRoot setRoutes(List<Route> routes) {
        this.routes = routes;
        return this;
    }
}
