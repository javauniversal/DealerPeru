package android.dcsdealerperu.com.dealerperu.utils;


import android.dcsdealerperu.com.dealerperu.Entry.ConfigSplash;
import android.dcsdealerperu.com.dealerperu.cnst.Flags;

public class ValidationUtil {

    public static int hasPath(ConfigSplash cs) {
        if (cs.getPathSplash().isEmpty())
            return Flags.WITH_LOGO;
        else
            return Flags.WITH_PATH;
    }
}
