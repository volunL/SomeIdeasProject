import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

class FileT extends Thread {
    private File[] filepath;
    private int tnum;

    public File[] getFilepath() {
        return filepath;
    }

    public FileT(File[] filepath, int i) {
        this.filepath = filepath;
        this.tnum = i;
    }


    @Override
    public void run() {

        System.out.println("线程[" + this.tnum + "]开始");
        FindP findP = new FindP();
        findP.findPDFs(this.filepath);
        Iterator<Map.Entry<String, String>> iterator = findP.getFileMap().entrySet().iterator();
        while (iterator.hasNext()) {
            findP.ReadPDFAndCopyFind(iterator.next().getValue(), "./tf");
        }
        System.out.println("线程[" + this.tnum + "]结束");

    }
}

public class ThreadFile {


    public static void main(String args[]) {

        int aa = 0;
        File file = new File("./sfile");
        File[] fpt = file.listFiles();
        double threadcount=2.0;
        int count = (int) Math.ceil(fpt.length / threadcount);
        System.out.println("数:"+count);
        for (int i = 0; i <= count; i = i + count) {

            File[]  splitfile=null;
            if (i > fpt.length) {
                splitfile = Arrays.copyOfRange(fpt, i, fpt.length );
            } else {
                splitfile = Arrays.copyOfRange(fpt, i, i + count);

            }

             FileT fthread = new FileT(splitfile, aa);
             fthread.start();
             aa++;
        }
    }
}
