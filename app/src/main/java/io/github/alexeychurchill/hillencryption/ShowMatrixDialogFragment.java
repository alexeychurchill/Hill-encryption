package io.github.alexeychurchill.hillencryption;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class ShowMatrixDialogFragment extends DialogFragment {
    private int[][] mMatrix;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(getContext(), R.layout.grid_matrix_item, R.id.tvText, toLinear(mMatrix));
        GridView gvMatrix = new GridView(getContext());
        gvMatrix.setAdapter(adapter);
        gvMatrix.setNumColumns(mMatrix[0].length);
        gvMatrix.setHorizontalSpacing(1);
        gvMatrix.setVerticalSpacing(1);
        return builder.setTitle("Matrix: ")
                .setView(gvMatrix)
                .setNeutralButton(R.string.text_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create();
    }

    public void setData(int[][] matrix) {
        mMatrix = matrix;
    }

    private static Integer[] toLinear(int[][] mMatrix) {
        if (mMatrix == null) {
            return null;
        }
        List<Integer> rowMatrix = new ArrayList<>();
        for (int[] row : mMatrix) {
            for (Integer number : row) {
                rowMatrix.add(number);
            }
        }
        Integer[] integers = new Integer[rowMatrix.size()];
        integers = rowMatrix.toArray(integers);
        return integers;
    }
}
