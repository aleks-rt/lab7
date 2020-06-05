package communication;

import java.io.Serializable;

public class Response implements Serializable {

    private String nameCommand;
    private String resultComand;

    public Response(String nameCommand, String resultComand) {
        this.nameCommand = nameCommand;
        this.resultComand = resultComand;
    }

    public String getNameCommand() {
        return nameCommand;
    }

    public String getResultComand() {
        return resultComand;
    }
}
