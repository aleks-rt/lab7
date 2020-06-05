package arguments.handlers;

import application.Context;
import communication.Response;

public interface HandlerRequst {
    public abstract void processing(Context context, Response response);
}
