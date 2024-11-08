/*
 * Copyright 2012 - 2024 Anton Tananaev (anton@traccar.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.entel.lector_tcp.domain.models;

import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.entel.lector_tcp.app.keys.KEYS.KEY_ALARM;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Position{
    private String type;
    private String protocol;
    private Date serverTime = new Date();
    private Date deviceTime;
    private Date fixTime;
    private boolean outdated;
    private boolean valid;
    private double latitude;
    private double longitude;
    private double altitude; // value in meters
    private double speed; // value in knots
    private double course;
    private String address;
    private double accuracy;
    private Map<String, Object> attributes = new LinkedHashMap<>();
    private List<Long> geofenceIds;


    public Position(String protocol) {
        this.protocol = protocol;
    }
    public void setLatitude(double latitude) {
        if (latitude < -90 || latitude > 90) {
            System.out.println("LAtitud enviada "  + latitude);

            throw new IllegalArgumentException("Latitude out of range");
        }
        this.latitude = latitude;

    }

    public void setTime(Date time) {
        setDeviceTime(time);
        setFixTime(time);
    }
    public void setLongitude(double longitude) {
        if (longitude < -180 || longitude > 180) {
            System.out.println(" LONGITUD ALTO "+ longitude);
            throw new IllegalArgumentException("Longitude out of range");
        }
        this.longitude = longitude;
    }
    public void setGeofenceIds(List<? extends Number> geofenceIds) {
        if (geofenceIds != null) {
            this.geofenceIds = geofenceIds.stream().map(Number::longValue).collect(Collectors.toList());
        } else {
            this.geofenceIds = null;
        }
    }

    public void addAlarm(String alarm) {
        if (alarm != null) {
            if (hasAttribute(KEY_ALARM)) {
                set(KEY_ALARM, getAttributes().get(KEY_ALARM) + "," + alarm);
            } else {
                set(KEY_ALARM, alarm);
            }
        }
    }

    public boolean hasAttribute(String key) {
        return attributes.containsKey(key);
    }


    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = Objects.requireNonNullElseGet(attributes, LinkedHashMap::new);
    }

    public void set(String key, Boolean value) {
        if (value != null) {
            attributes.put(key, value);
        }
    }

    public void set(String key, Byte value) {
        if (value != null) {
            attributes.put(key, value.intValue());
        }
    }

    public void set(String key, Short value) {
        if (value != null) {
            attributes.put(key, value.intValue());
        }
    }

    public void set(String key, Integer value) {
        if (value != null) {
            attributes.put(key, value);
        }
    }

    public void set(String key, Long value) {
        if (value != null) {
            attributes.put(key, value);
        }
    }

    public void set(String key, Float value) {
        if (value != null) {
            attributes.put(key, value.doubleValue());
        }
    }

    public void set(String key, Double value) {
        if (value != null) {
            attributes.put(key, value);
        }
    }

    public void set(String key, String value) {
        if (value != null && !value.isEmpty()) {
            attributes.put(key, value);
        }
    }

    public void add(Map.Entry<String, Object> entry) {
        if (entry != null && entry.getValue() != null) {
            attributes.put(entry.getKey(), entry.getValue());
        }
    }

    public String getString(String key, String defaultValue) {
        if (attributes.containsKey(key)) {
            Object value = attributes.get(key);
            return value != null ? value.toString() : null;
        } else {
            return defaultValue;
        }
    }

    public String getString(String key) {
        return getString(key, null);
    }

    public double getDouble(String key) {
        if (attributes.containsKey(key)) {
            Object value = attributes.get(key);
            if (value instanceof Number numberValue) {
                return numberValue.doubleValue();
            } else {
                return Double.parseDouble(value.toString());
            }
        } else {
            return 0.0;
        }
    }

    public boolean getBoolean(String key) {
        if (attributes.containsKey(key)) {
            Object value = attributes.get(key);
            if (value instanceof Boolean booleanValue) {
                return booleanValue;
            } else {
                return Boolean.parseBoolean(value.toString());
            }
        } else {
            return false;
        }
    }

    public int getInteger(String key) {
        if (attributes.containsKey(key)) {
            Object value = attributes.get(key);
            if (value instanceof Number numberValue) {
                return numberValue.intValue();
            } else {
                return Integer.parseInt(value.toString());
            }
        } else {
            return 0;
        }
    }

    public long getLong(String key) {
        if (attributes.containsKey(key)) {
            Object value = attributes.get(key);
            if (value instanceof Number numberValue) {
                return numberValue.longValue();
            } else {
                return Long.parseLong(value.toString());
            }
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "Position{" +
                "protocol='" + protocol + '\'' +
                ", serverTime=" + serverTime +
                ", deviceTime=" + deviceTime +
                ", fixTime=" + fixTime +
                ", outdated=" + outdated +
                ", valid=" + valid +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", altitude=" + altitude +
                ", speed=" + speed +
                ", course=" + course +
                ", address='" + address + '\'' +
                ", accuracy=" + accuracy +
                ", geofenceIds=" + geofenceIds +
                ", params="+ attributes +
                '}';
    }
}
