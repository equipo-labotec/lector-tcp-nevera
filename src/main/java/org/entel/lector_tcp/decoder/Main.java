package org.entel.lector_tcp.decoder;

import org.entel.lector_tcp.protocol.WialonProtocolDecoder;

public class Main {

    public static void main(String[] args) throws Exception {
        WialonProtocolDecoder wialonProtocolDecoder = new WialonProtocolDecoder();

        String code = "#D#071124;154431;1901.21200;S;00718.12600;W;0;10;300;13;1;1;0;3.24;;csq:2:1:20,iccid:3:89560100001137155887,pwr:1:1,mov:1:1,geo:1:1,fw:3:3.1.0.0";
        Position position = (Position) wialonProtocolDecoder.decode(code);
        System.out.println(position);
    }

}
