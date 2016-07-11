package com.mustafaali.sensorssandbox.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import com.mustafaali.sensorssandbox.R;
import com.mustafaali.sensorssandbox.util.OnDialogDismissedListener;
import it.gmariotti.changelibs.library.view.ChangeLogRecyclerView;

/**
 * @author Mustafa Ali, 11/07/16.
 */
public class ChangeLogDialogFragment extends DialogFragment {

  public static final String TAG = "ChangeLogDialogFragment";

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(
        Context.LAYOUT_INFLATER_SERVICE);
    ChangeLogRecyclerView chgList= (ChangeLogRecyclerView) layoutInflater.inflate(R.layout.dialog_changelog, null);

    setRetainInstance(false);

    return new AlertDialog.Builder(getActivity(),R.style.AppCompatAlertDialogStyle)
        .setTitle(R.string.title_changelog_dialog)
        .setView(chgList)
        .setPositiveButton(android.R.string.ok,
            new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
              }
            }
        )
        .create();
  }

  @Override public void onDismiss(DialogInterface dialog) {
    super.onDismiss(dialog);
    Activity activity = getActivity();
    if(activity instanceof OnDialogDismissedListener) {
      ((OnDialogDismissedListener) activity).onDismissed();
    }
  }
}
