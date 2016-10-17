package io.github.alexeychurchill.hillencryption;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import io.github.alexeychurchill.hillencryption.encryptionutils.HillCoder;
import io.github.alexeychurchill.hillencryption.filebrowser.OpenFileActivity;
import io.github.alexeychurchill.hillencryption.matrixutils.IntMatrixUtils;

public class EnterDataActivity extends AppCompatActivity {
    private static final int REQ_CODE_OPEN_FILE = 1;
    private HillCoder mHillCoder = new HillCoder();
    private File mOpenedFile;
    private String mInput;
    private String mOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_data);
    }

    public void btnOpenFileOnClick(View view) {
        callOpenFile();
    }

    private void callOpenFile() {
        Intent intent = new Intent(this, OpenFileActivity.class);
        startActivityForResult(intent, REQ_CODE_OPEN_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_OPEN_FILE && resultCode == RESULT_OK && data != null) {
            loadFile(data.getStringExtra(OpenFileActivity.EXTRA_FILENAME));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void loadFile(String filename) {
        if (filename == null) {
            return;
        }
        mOpenedFile = new File(filename);
        try {
            InputStream inputStream = new FileInputStream(filename);
            Scanner scanner = new Scanner(inputStream);
            StringBuilder builder = new StringBuilder();
            while (scanner.hasNextLine()) {
                builder.append(scanner.nextLine());
            }
            mInput = builder.toString();
            inputStream.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "File not found", Toast.LENGTH_SHORT)
                    .show();
        } catch (IOException e) {
            Toast.makeText(this, "IO Exception", Toast.LENGTH_SHORT)
                    .show();
        }
        Toast.makeText(this, "Loaded", Toast.LENGTH_SHORT)
                .show();
    }

    public void btnProceedOnClick(View view) {
        callProceed();
    }

    private void callProceed() {
        RadioGroup rgAction = ((RadioGroup) findViewById(R.id.rgDirection));
        if (rgAction != null && rgAction.getCheckedRadioButtonId() == R.id.rbEncode) {
            callEncode();
        }
        if (rgAction != null && rgAction.getCheckedRadioButtonId() == R.id.rbDecode) {
            callDecode();
        }
    }

    private void callDecode() {
        if (!setCoderKey()) {
            return;
        }
        mOutput = mHillCoder.decode(mInput);
        if (mOutput == null) {
            Toast.makeText(this, "Decode error!", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        Toast.makeText(this, "Decoded", Toast.LENGTH_SHORT)
                .show();
        saveFile("decoded");
    }

    private void callEncode() {
        if (!setCoderKey()) {
            return;
        }
        mOutput = mHillCoder.encode(mInput);
        if (mOutput == null) {
            Toast.makeText(this, "Encode error!", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        Toast.makeText(this, "Encoded", Toast.LENGTH_SHORT)
                .show();
        saveFile("encoded");
    }

    private void saveFile(String tip) {
        mOpenedFile = new File(mOpenedFile.getAbsolutePath().replace(".txt", "." + tip + ".txt"));
        try {
            OutputStream outputStream = new FileOutputStream(mOpenedFile);
            outputStream.write(mOutput.getBytes());
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "File not found", Toast.LENGTH_SHORT)
                    .show();
        } catch (IOException e) {
            Toast.makeText(this, "IO Exception", Toast.LENGTH_SHORT)
                    .show();
        }
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT)
                .show();
    }

    private boolean setCoderKey() {
        EditText etKey = ((EditText) findViewById(R.id.etKey));
        if (etKey == null) {
            return false;
        }
        String key = etKey.getText().toString();
        if (key.isEmpty()) {
            Toast.makeText(this, "Empty key!", Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        mHillCoder.setKey(key);
        return true;
    }

    public void btnOnShowEncryptionMatrix(View view) {
        if (!setCoderKey()) {
            return;
        }
        int[][] keyMatrix = mHillCoder.getEncodeKeyMatrix();
        showMatrix(keyMatrix);
    }

    public void btnOnShowDecryptionMatrix(View view) {
        if (!setCoderKey()) {
            return;
        }
        int[][] decodeKeyMatrix = mHillCoder.getDecodeKeyMatrix();
        if (decodeKeyMatrix == null) {
            Toast.makeText(this, "Not exists!", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        showMatrix(decodeKeyMatrix);
    }

    private void showMatrix(int[][] matrix) {
        if (matrix == null) {
            return;
        }
        ShowMatrixDialogFragment dialogFragment = new ShowMatrixDialogFragment();
        dialogFragment.setData(matrix);
        dialogFragment.show(getSupportFragmentManager(), "ShowMatrixDialogFragment");
    }
}
