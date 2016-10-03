package io.github.alexeychurchill.hillencryption;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.github.alexeychurchill.hillencryption.matrixutils.IntMatrix;

public class EnterDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_data);
        IntMatrix intMatrix = new IntMatrix(2, 2);
        int[][] matrixSource = {
                { 2, 3},
                {1,  4}//,
                //{17, 15, 19}
        };
        intMatrix.fillMe(matrixSource);
        Log.d("zzz", String.valueOf(intMatrix));
//        Log.d("zzz", String.valueOf(intMatrix.minor(0, 0)));
//        Log.d("zzz", String.valueOf(intMatrix.minor(0, 1)));
//        Log.d("zzz", String.valueOf(intMatrix.minor(2, 2)));
//        Log.d("zzz", "det = " + intMatrix.det());
        Log.d("zzz", String.valueOf(intMatrix));
        Log.d("zzz", String.valueOf(intMatrix.matrixOfMinors()));

//        int a = -10, m = 7;
//        if (!InverseByMod.isExists(a, m)) {
//            Log.d("zzz", "NOT EXISTS");
//        } else {
//            int inv = InverseByMod.inverse(a, m);
//            Log.d("zzz", "INVERSE = " + inv);
//        }
    }
}
