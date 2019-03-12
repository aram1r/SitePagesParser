package MainFrame;

import java.io.*;

public class FileHandler {
    public FileHandler() {

    }
    public static void saveFile (File file, String string) throws IOException {
        try (
                //Создаём байтовый поток для считывния строк
             InputStream in = new ByteArrayInputStream((string).getBytes());
                //Создаём выводящий поток для записи в файл
                OutputStream out = new FileOutputStream(file)
        )
        {
            //Создаём буфер на 1 мб, для помб считывания файла
            byte[] buffer = new byte[1000000];
            //Переменная счётчик количества считанных мб
            int read;
            //Считывам строки и пишем их в файл
            while ((read=in.read(buffer)) >= 0) {
                out.write(buffer, 0, read);
            }
        }
    }
}
