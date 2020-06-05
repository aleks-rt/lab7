package application;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;
import java.util.StringTokenizer;


public class HandlerInput {
    private BufferedReader bufferedReader;
    private Stack<NamedBufferedReader> bufferedReaderStack;
    private HashSet<String> paths;

    public HandlerInput() {
        bufferedReaderStack = new Stack<>();
        paths = new HashSet<>();
        updateSystemInReader();
    }

    public boolean isSIN() {
        return bufferedReaderStack.isEmpty();
    }

    private void pushBufferedReader(NamedBufferedReader namedBufferedReader) {
        bufferedReaderStack.push(namedBufferedReader);
        bufferedReader = namedBufferedReader.getBufferedReader();
    }

    private void popBufferedReader() {
        paths.remove(bufferedReaderStack.peek().getName());
        bufferedReader = bufferedReaderStack.pop().getBufferedReader();
    }

    private void updateSystemInReader() {
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void pushFileBufferedStream(String path) throws Exception {
        path = new File(path).getCanonicalPath();
        if (paths.contains(path)) {
            throw new Exception("Вы вызываете файл, из-за которого произойдет рекурсия! Пропуск команды.");
        }
        pushBufferedReader(new NamedBufferedReader(path, new BufferedReader(new InputStreamReader(new FileInputStream(path)))));
        paths.add(path);
    }

    public ArrayList<String> getData() throws IOException {
        String srcData;
        ArrayList<String> data = new ArrayList<>();
        if ((srcData = bufferedReader.readLine()) != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(srcData);
            while (stringTokenizer.hasMoreTokens()) {
                data.add(stringTokenizer.nextToken());
            }
        }
        else {
            if(bufferedReaderStack.size() == 0) {
                updateSystemInReader();
            }
            if(bufferedReaderStack.size() != 0) {
                popBufferedReader();
            }
        }
        return data;
    }
}
