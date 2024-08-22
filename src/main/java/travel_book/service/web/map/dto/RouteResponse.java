package travel_book.service.web.map.dto;

import lombok.Data;

import java.util.List;

//@Data
public class RouteResponse {
    /**
     * // 카카오맵 도보 경로 받아오기 위해 선언했던 내용
     */

    private List<Route> routes;

    public static class Route {
        private List<Section> sections;

        public static class Section {
            private List<Road> roads;

            public static class Road {
                private List<Coord> path;

                public static class Coord {
                    private double x;
                    private double y;

                    // getters and setters

                    public double getX() {
                        return x;
                    }

                    public void setX(double x) {
                        this.x = x;
                    }

                    public double getY() {
                        return y;
                    }

                    public void setY(double y) {
                        this.y = y;
                    }
                }

                // getters and setters

                public List<Coord> getPath() {
                    return path;
                }

                public void setPath(List<Coord> path) {
                    this.path = path;
                }
            }

            // getters and setters

            public List<Road> getRoads() {
                return roads;
            }

            public void setRoads(List<Road> roads) {
                this.roads = roads;
            }
        }

        // getters and setters

        public List<Section> getSections() {
            return sections;
        }

        public void setSections(List<Section> sections) {
            this.sections = sections;
        }
    }

    // getters and setters

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }
}