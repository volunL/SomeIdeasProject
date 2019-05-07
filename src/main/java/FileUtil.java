import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;


/*
* 文件复制Util类
*
* 用于将源文件中的全部文件包括里面文件夹中的文件复制到目标路径
*
*
*
* */
public class FileUtil {

    public static void copyFile(File srcFile, File targetFile) {
        try {
            if (targetFile.exists()) {
                targetFile.delete();
            }
            //使用Files方法复制文件
            Files.copy(srcFile.toPath(), targetFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
     * srcPath 源文件地址
     * targetPath 目标文件地址
     * */
    public static void RemoveFile(String srcPath, String targetPath) {

        File srcFile = new File(srcPath);

        if (!srcFile.exists()) {
            System.out.println("请输入正确路径");
            return;
        }

        if (srcFile.isDirectory() && srcFile.listFiles().length != 0) {
            ArrayList<File> filepaths = new ArrayList<File>();
            File[] files = srcFile.listFiles();

            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    //如果是目录先收录进列表
                    filepaths.add(files[i]);
                } else {
                    //目标文件路径
                    File targetFilepath = new File(targetPath + File.separator + srcFile.getName());
                    //目标文件
                    File targetFile = new File(targetPath + File.separator + srcFile.getName() + File.separator + files[i].getName());

                    //创建目标路径
                    if (!targetFilepath.exists()) {
                        targetFilepath.mkdirs();
                    }

                    //复制文件
                    copyFile(files[i], targetFile);
                }
            }//for end


            // 处理文件路径列表filepaths 递归处理
            if (!filepaths.isEmpty()) {
                for (int i = 0; i < filepaths.size(); i++) {
                    RemoveFile(filepaths.get(i).getPath(), targetPath + File.separator + srcFile.getName());
                }
            }

        } else if (srcFile.listFiles().length == 0) {
            //目录下面没有文件
            File targetFilepath = new File(targetPath + File.separator + srcFile.getName());
            //创建目标路径
            if (!targetFilepath.exists()) {
                targetFilepath.mkdirs();
            }
        } else {
            System.out.println("请不要输入文件名");
        }


    }


}
