package org.entel.lector_tcp.domain.dto;

public class TransferenciaDTO {
    private int status;
    private String msg;

    public TransferenciaDTO(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public TransferenciaDTO() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

