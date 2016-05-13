package android.dcsdealerperu.com.dealerperu.Activity;

/**
 * Created by Josue on 13/05/16.
 */
import android.content.Context;

public class CompatUtils {
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}