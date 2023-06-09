package ru.mirea.bandurin.mireaproject.ui.filechanger;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import ru.mirea.bandurin.mireaproject.databinding.FragmentFileChangerBinding;
import ru.mirea.bandurin.mireaproject.databinding.FragmentSlideshowBinding;
import ru.mirea.bandurin.mireaproject.ui.slideshow.SlideshowViewModel;

public class FileChangerFragment extends Fragment {
    private FloatingActionButton but;
    private FloatingActionButton obut;
    private FragmentFileChangerBinding binding;
    private String str;
    private EditText edt;
    private String strr;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FileChangerViewModel slideshowViewModel =
                new ViewModelProvider(this).get(FileChangerViewModel.class);

        binding = FragmentFileChangerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        but = binding.fbut;
        obut = binding.obut;
        edt = binding.edtt;
        /*String username = "bob@google.org";
        String password = "Password1";
        String secretID = "BlahBlahBlah";
        String SALT2 = "deliciously salty";

        // Get the Key
        byte[] key = (SALT2 + username + password).getBytes();*/
        sharedPref = getActivity().getSharedPreferences("mirea_key", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isExternalStorageReadable()&&isExternalStorageWritable()){
                    String cur = edt.getText().toString();
                    SecretKey Key = generateKey();
                    String cur1 = encryptMsg(cur,Key).toString();
                    editor.putString("NAME", keyToString(Key));
                    strr=keyToString(Key);
                    Log.d(TAG,strr);
                    editor.apply();
                    writeFileToExternalStorage("top.txt",cur1);
                }
            }
        });
        obut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isExternalStorageReadable()&&isExternalStorageWritable()){
                    String cur="";
                    String cur1="";
                    //Key k = "ddhdjjdde";
                    SecretKey k1 = decodeKeyFromString(strr);
                    Log.d(TAG, keyToString(k1));
                    List<String> lcur=readFileFromExternalStorage("top.txt");
                    for (int i = 0; i < lcur.size(); i++){
                        //cur1=decryptMsg(lcur.get(i).getBytes(),k1);
                        cur+=lcur.get(i)+'\n';
                        //cur1="";
                    }
                    edt.setText(cur) ;
                }
            }
        });
        final TextView textView = binding.textFilechanger;
        //slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    /* Проверяем внешнее хранилище на доступность чтения */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
    public void writeFileToExternalStorage(String file_name,String text) {
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(path, file_name+".txt");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file.getAbsoluteFile());
            OutputStreamWriter output = new OutputStreamWriter(fileOutputStream);
// Запись строки в файл
            output.write(text);
// Закрытие потока записи
            output.close();
        } catch (IOException e) {
            Log.w("ExternalStorage", "Error writing " + file, e);
        }
    }
    public List<String> readFileFromExternalStorage(String file_name) {
        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS);
        File file = new File(path, file_name+".txt");
        try {
            FileInputStream fileInputStream = new FileInputStream(file.getAbsoluteFile());

            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);

            List<String> lines = new ArrayList<String>();
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            Log.w("ExternalStorage", String.format("Read from file %s successful", lines.toString()));
            return lines;
        } catch (Exception e) {
            Log.w("ExternalStorage", String.format("Read from file %s failed", e.getMessage()));
        }
        return null;
    }
    public static SecretKey generateKey(){
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed("any data used as random seed".getBytes());
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(256, sr);
            return new SecretKeySpec((kg.generateKey()).getEncoded(), "AES");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] encryptMsg(String message, SecretKey secret) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            return cipher.doFinal(message.getBytes());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 BadPaddingException | IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }
    }
    public static String decryptMsg(byte[] cipherText, SecretKey secret){
        /* Decrypt the message */
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secret);
            return new String(cipher.doFinal(cipherText));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
                 | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
    public static SecretKey decodeKeyFromString(String keyStr) {
        /* Decodes a Base64 encoded String into a byte array */
        byte[] decodedKey = Base64.getDecoder().decode(keyStr);

        /* Constructs a secret key from the given byte array */
        SecretKey secretKey = new SecretKeySpec(decodedKey, 0,
                decodedKey.length, "AES");

        return secretKey;
    }
    public static String keyToString(SecretKey secretKey) {
        /* Get key in encoding format */
        byte encoded[] = secretKey.getEncoded();

        /*
         * Encodes the specified byte array into a String using Base64 encoding
         * scheme
         */
        String encodedKey = Base64.getEncoder().encodeToString(encoded);

        return encodedKey;
    }


}