package org.entel.lector_tcp.domain.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseListData {
    private boolean status;
    private String msg;
    private int total;
    private List<?> data;
}
