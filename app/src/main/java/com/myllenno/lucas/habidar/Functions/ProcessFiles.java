package com.myllenno.lucas.habidar.Functions;
import android.content.Context;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;


public class ProcessFiles {

    public static void createFileSettings(Context context) throws Exception {
        File file = preparedFile(context, "settings.txt");
        if(!file.exists()){
            file.createNewFile();
            // Configuração padrão.
            ArrayList<String> write = new ArrayList<String>();
            write.add(Definitions.music+""); // Música de Fundo.
            write.add(Definitions.effectsSound+""); // Efeitos Sonoros.
            write.add(Definitions.timeGame+""); // Tempo de Resposta.
            write.add(Definitions.timeInterval+""); // Tempo de Descanso.
            writeFile(context, "settings.txt", write);
        }
    }

    public static void createFile(Context context, String fileName) throws Exception {
        File file = preparedFile(context, fileName);
        if(!file.exists()){
            file.createNewFile();
        }
    }

    public static void removeFile(Context context, String fileName) throws Exception {
        File file = preparedFile(context, fileName);
        if (file.exists())
            file.delete();
    }

    public static File preparedFile(Context context, String fileName) throws Exception {
        String filePath = context.getFilesDir().getPath().toString() + fileName;
        File file = new File(filePath);
        return file;
    }

    public static BufferedReader preparedFileRead(Context context, String fileName) throws Exception {
        File file = preparedFile(context, fileName);
        InputStream inputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        return bufferedReader;
    }

    public static Writer preparedFileWrite(Context context, String fileName) throws Exception {
        File file = preparedFile(context, fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        Writer writer = new BufferedWriter(outputStreamWriter);
        return writer;
    }

    public static ArrayList<String> readFile(Context context, String fileName) throws Exception {
        BufferedReader bufferedReader = preparedFileRead(context, fileName);
        ArrayList<String> read = new ArrayList<String>();
        String line = bufferedReader.readLine();
        while (line != null){
            read.add(line);
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        return read;
    }

    public static void writeFile(Context context, String fileName, ArrayList<String> write) throws Exception {
        Writer writer = preparedFileWrite(context, fileName);
        String stringAux = "";
        for (int i=0; i < write.size(); i++){
            stringAux += write.get(i);
            stringAux += "\n";
        }
        writer.write(stringAux);
        writer.close();
    }
}