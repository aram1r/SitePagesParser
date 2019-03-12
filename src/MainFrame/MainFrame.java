package MainFrame;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;



public class MainFrame extends JFrame {
    private JTextField inputField;
    private JPanel rootPane;
    private JButton startButton;
    private JButton stopButton;
    private JProgressBar progressBar1;
    private JFileChooser jFileChooser;

    {
        setContentPane(rootPane);
        Dimension dimension = new Dimension(400, 300);
        setMinimumSize(dimension);
        setPreferredSize(dimension);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        jFileChooser = new JFileChooser() {
            //Переопределяем метод в классе, для вызова окна в случае существования файла с таким именем при сохранении
            @Override
            public void approveSelection() {
                File checkFile = new File(jFileChooser.getSelectedFile().getAbsolutePath());
                if (checkFile.exists() && getDialogType() == SAVE_DIALOG) {
                    int result = JOptionPane.showConfirmDialog(this,"Файл с таким именем уже существует" +
                            " перезаписать?","Презапись файла",JOptionPane.YES_NO_CANCEL_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        super.approveSelection();
                        return;
                    } else if (result == JOptionPane.NO_OPTION) {
                        return;
                    } else if (result == JOptionPane.CANCEL_OPTION) {
                        return;
                    } else if (result == JOptionPane.CLOSED_OPTION) {
                        return;
                    }
                }
                super.approveSelection();
            }
        };
        //Устанавливаем фильтр, чтобы файлы записывались в txt формате
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        jFileChooser.setDialogTitle("Выбор файла для записи");
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jFileChooser.setCurrentDirectory(new File("."));
        jFileChooser.setFileFilter(filter);
    }

    public MainFrame() {
        startButton.addActionListener(e -> {
            if (startButton.getText().equals("Старт")) {
                File file = null;
                try {
                    //Получаем выбранный файл, точнее его путь
                    if (jFileChooser.showSaveDialog(jFileChooser) == JFileChooser.APPROVE_OPTION ) {
                        file = new File(jFileChooser.getSelectedFile().getAbsolutePath());
                    }
                    //Создаём поток обработки ссылки для получения ссылок на файлы
                    String pages = null;
                    try {
                        ParseThread parseThread = new ParseThread(getInputField());
                        parseThread.start();
                        while (!parseThread.isReady()) {
                            Thread.currentThread().sleep(1);
                        }
                        pages = parseThread.getLinks();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    } finally {
                        //Меняем текст кнопки на паузу
                        startButton.setText("Пауза");
                        System.out.println(pages);
                        FileHandler.saveFile(file, pages);
                    }

                }
                //Выводим ошибку при невозможности получить абсолютный юрл путь в конструкторе
                catch (MalformedURLException d) {
                    d.printStackTrace();
                    showErrorMessage("Не удалось получить страницу");
                } catch (IOException exc) {
                    JOptionPane.showMessageDialog(null,"Что-то пошло не так", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    exc.printStackTrace();
                }
//                catch (InterruptedException exc2) {
//                    JOptionPane.showMessageDialog(null,"Поток прерван", "Ошибка", JOptionPane.ERROR_MESSAGE);
//                    Thread.currentThread().interrupt();
//                }

            } else if (startButton.getText().equals("Пауза")) {
                startButton.setText("Старт");
            }
        });
    }

    public String getInputField() {
        return inputField.getText();
    }

    private void showErrorMessage(String string) {
        JOptionPane.showMessageDialog(null, string);
    }


}


