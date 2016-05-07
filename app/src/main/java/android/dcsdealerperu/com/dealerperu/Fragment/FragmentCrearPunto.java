package android.dcsdealerperu.com.dealerperu.Fragment;

import android.dcsdealerperu.com.dealerperu.R;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentCrearPunto extends BaseVolleyFragment {

    private FragmentDatosPersonales fragmentDatosPersonales;


    public FragmentCrearPunto() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_crear_punto, container, false);

        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FragmentManager fManager = getFragmentManager();
        fragmentDatosPersonales = new FragmentDatosPersonales();
        fManager.beginTransaction().replace(R.id.contentPanel, fragmentDatosPersonales).commit();

    }

}
