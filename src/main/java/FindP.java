import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FindP {


    private HashMap<String, String> FileMap = new HashMap<String, String>();

    public HashMap<String, String> getFileMap() {
        return FileMap;
    }

    //比较PDF内容
    public boolean isMatchStr(String cstr) {
        if (cstr.indexOf("跟我") > -1) {
            return true;
        } else {
            return false;
        }

    }

    //读取PDF内容
    public void ReadPDFAndCopyFind(String ifilepath, String targetpath) {
        PDDocument doc = null;
        String content = "";
        try {
            //加载一个pdf对象
            File ifile = new File(ifilepath);
            doc = PDDocument.load(ifile);
            //获取一个PDFTextStripper文本剥离对象
            PDFTextStripper textStripper = new PDFTextStripper();
            content = textStripper.getText(doc);
            if (isMatchStr(content)) {
                System.out.println("文件[" + ifile.getName() + "]匹配" + "，文件夹[" + ifile.getParent() + "] 开始转移文件");
                FileUtil.RemoveFile(ifile.getParent(), targetpath);
                System.out.println("文件[" + ifile.getName() + "]匹配" + "，文件夹[" + ifile.getParent() + "] 结束转移文件");
            }
            //关闭文档
            doc.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }


    public void findPDFs(File[] spaths) {
        if (spaths.length != 0 && spaths != null)
            for (int i = 0; i < spaths.length; i++) {
                if(spaths[i]!=null){findPDF(spaths[i].getPath());}
            }
    }

    public void findPDF(String spath) {
        File file = new File(spath);
        if (!file.exists()) {
            System.out.println("地址错误");
            return;
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile() && files[i].getName().indexOf(".pdf") > -1) {
                    this.FileMap.put(files[i].getName(), files[i].getPath());
                } else if (files[i].isDirectory()) {
                    findPDF(files[i].getPath());
                }
            }

        } else {
            System.out.println("文件[" + file.getName() + "]跳过");
            return;
        }
    }

    public static void main(String[] args) {


        FindP findP = new FindP();
        findP.findPDF("./sfile");
        Iterator<Map.Entry<String, String>> iterator = findP.getFileMap().entrySet().iterator();
        while (iterator.hasNext()) {
            findP.ReadPDFAndCopyFind(iterator.next().getValue(), "./tf");
        }


    }
}
