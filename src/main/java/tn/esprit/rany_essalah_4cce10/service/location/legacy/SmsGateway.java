package tn.esprit.rany_essalah_4cce10.service.location.legacy;

public class SmsGateway {
    private final String url;

    public SmsGateway(String url) {
        this.url = url;
    }

    public void send(String telephone, String message) {
        System.out.println("[SMS " + url + "] -> " + telephone + " : " + message);
    }
}