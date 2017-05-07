package framework;

/**
 * Created by jun.
 */
public class Server {

    public static void main(String[] args) throws Exception {
        new ServerApplication
                .ServerApplicationBuilder()
                .run();
    }
}
