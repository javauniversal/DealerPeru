package android.dcsdealerperu.com.dealerperu.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

/**
 * Created by germangarcia on 9/05/16.
 */
public class CustomListener implements DialogInterface.OnClickListener {

    private Activity activity;

    public CustomListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Toast.makeText(activity, "Invalid data", Toast.LENGTH_SHORT).show();
    }
}
