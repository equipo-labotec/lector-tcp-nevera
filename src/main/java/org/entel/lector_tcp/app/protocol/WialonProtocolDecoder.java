package org.entel.lector_tcp.app.protocol;

import lombok.AllArgsConstructor;
import org.entel.lector_tcp.app.helper.Parser;
import org.entel.lector_tcp.app.helper.PatternBuilder;
import org.entel.lector_tcp.app.keys.KEYS;
import org.entel.lector_tcp.domain.models.Position;
import org.entel.lector_tcp.app.util.UnitsConverter;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
public class WialonProtocolDecoder {
    private static final Pattern PATTERN_ANY = new PatternBuilder()
            .number("d.d;").optional()
            .expression("([^#]+)?")              // imei
            .text("#")                           // start byte
            .expression("([^#]+)")               // type
            .text("#")                           // separator
            .expression("(.*)")                  // message
            .compile();

    private static final Pattern PATTERN = new PatternBuilder()
            .number("(?:NA|(dd)(dd)(dd));")      // date (ddmmyy)
            .number("(?:NA|(dd)(dd)(dd));")      // time (hhmmss)
            .number("(?:NA|(d+)(dd.d+));")       // latitude
            .expression("(?:NA|([NS]));")
            .number("(?:NA|(d+)(dd.d+));")       // longitude
            .expression("(?:NA|([EW]));")
            .number("(?:NA|(d+.?d*))?;")         // speed
            .number("(?:NA|(d+.?d*))?;")         // course
            .number("(?:NA|(-?d+.?d*));")        // altitude
            .number("(?:NA|(d+))")               // satellites
            .groupBegin().text(";")
            .number("(?:NA|(d+.?d*));")          // hdop
            .number("(?:NA|(d+));")              // inputs
            .number("(?:NA|(d+));")              // outputs
            .expression("(?:NA|([^;]*));")       // adc
            .expression("(?:NA|([^;]*));")       // ibutton
            .expression("(?:NA|([^;]*))")        // params
            .groupEnd("?")
            .any()
            .compile();



    private Position decodePosition(String substring) {
        //System.out.println("CODIGO ENTRADA ES " + substring);


        Parser parser = new Parser(PATTERN, substring);
        if (!parser.matches()) {
            return null;
        }

        Position position = new Position("wialon");

        if (parser.hasNext(6)) {
            position.setTime(parser.nextDateTime(Parser.DateTimeFormat.DMY_HMS));
        } else {
            position.setTime(new Date());
        }

        if (parser.hasNextAny(9)) {
            position.setLatitude(parser.nextCoordinate());
            position.setLongitude(parser.nextCoordinate());
            position.setSpeed(UnitsConverter.knotsFromKph(parser.nextDouble(0)));
            position.setCourse(parser.nextDouble(0));
            position.setAltitude(parser.nextDouble(0));
        } else {
            //Implementar
            //getLastLocation(position, position.getDeviceTime());
        }

        if (parser.hasNext()) {
            int satellites = parser.nextInt(0);
            position.setValid(satellites >= 3);
            position.set(KEYS.KEY_SATELLITES, satellites);
        }

        position.set(KEYS.KEY_HDOP, parser.nextDouble());
        position.set(KEYS.KEY_INPUT, parser.next());
        position.set(KEYS.KEY_OUTPUT, parser.next());

        if (parser.hasNext()) {
            String[] values = parser.next().split(",");
            for (int i = 0; i < values.length; i++) {
                position.set(KEYS.PREFIX_ADC + (i + 1), values[i]);
            }
        }

        position.set(KEYS.KEY_DRIVER_UNIQUE_ID, parser.next());

        if (parser.hasNext()) {
            String[] values = parser.next().split(",");
            for (String param : values) {
                Matcher paramParser = Pattern.compile("(.*):[1-3]:(.*)").matcher(param);
                if (paramParser.matches()) {
                    String key = paramParser.group(1).toLowerCase();
                    String value = paramParser.group(2);
                    try {
                        if (key.equals("accuracy")) {
                            position.setAccuracy(Double.parseDouble(value));
                        } else {
                            position.set(key, Double.parseDouble(value));
                        }
                    } catch (NumberFormatException e) {
                        if (value.equalsIgnoreCase("true")) {
                            position.set(key, true);
                        } else if (value.equalsIgnoreCase("false")) {
                            position.set(key, false);
                        } else {
                            position.set(key, value);
                        }
                    }
                }
            }
        }

        return position;
    }

    public String getTypeMessage(Object msg){
        String sentence = (String) msg;
        Parser parser = new Parser(PATTERN_ANY, sentence);
        if (!parser.matches()) {
            return null;
        }

        String id = parser.next();
        String type = parser.next();
        String data = parser.next();

        return type;
    }
    public String getDataMessage(Object msg){
        String sentence = (String) msg;
        //System.out.println("LGOIN ?? = " + msg);
        Parser parser = new Parser(PATTERN_ANY, sentence);
        if (!parser.matches()) {
            return null;
        }

        String id = parser.next();
        String type = parser.next();

        return parser.next();
    }
    public Object decode(String type , String data) {
        //DeviceSession deviceSession;
        Position position;

        switch (type) {
            case "L" -> {
                String[] values = data.split(";");
                String imei = values[0].indexOf('.') >= 0 ? values[1] : values[0];
            }
            case "P" -> {
            }
            case "D", "SD" -> {
                position = decodePosition(data);
                if (position != null) {
                    return position;
                }
            }
            case "B" -> {
                String[] messages = data.split("\\|");
                List<Position> positions = new LinkedList<>();

                for (String message : messages) {
                    position = decodePosition(message);
                    if (position != null) {
                        position.set(KEYS.KEY_ARCHIVE, true);
                        positions.add(position);
                    }
                }

                //sendResponse(chanel, remoteAddress, type, messages.length);
                if (!positions.isEmpty()) {
                    return positions;
                }
            }
            case "M" -> {
                position = new Position("wialon");
                position.setValid(false);
                position.set(KEYS.KEY_RESULT, data);
                return position;
            }
        }

        return null;
    }
}
