package android.dcsdealerperu.com.dealerperu.Entry;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Josue on 6/05/16.
 */
public class PasaDataSeriaRutero implements Serializable {


    private List<ResponseHome> responseHomeList;

    public List<ResponseHome> getResponseHomeList() {
        return responseHomeList;
    }

    public void setResponseHomeList(List<ResponseHome> responseHomeList) {
        this.responseHomeList = responseHomeList;
    }
}
