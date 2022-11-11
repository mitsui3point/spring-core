package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient {
    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출; NetworkClient.NetworkClient");
    }

    public void call(String message) {
        System.out.println("NetworkClient.call");
        System.out.println("url = " + url + ", message = " + message);
    }

    public void connect() {
        System.out.println("NetworkClient.connect");
        System.out.println("url = " + url);
    }

    public void disconnect() {
        System.out.println("NetworkClient.disconnect");
        System.out.println("url = " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void init() {
        connect();
        call("초기화 연결 메시지");
    }

    public void close() {
        disconnect();
    }
}
