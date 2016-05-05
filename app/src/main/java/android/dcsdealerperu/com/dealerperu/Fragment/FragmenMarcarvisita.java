package android.dcsdealerperu.com.dealerperu.Fragment;


import android.dcsdealerperu.com.dealerperu.Entry.ResponseMarcarPedido;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.dcsdealerperu.com.dealerperu.R;


public class FragmenMarcarvisita extends BaseVolleyFragment {

    private static final String DESCRIBABLE_KEY = "describable_key";
    private ResponseMarcarPedido mDescribable;

    public static FragmenMarcarvisita newInstance(ResponseMarcarPedido describable) {
        FragmenMarcarvisita fragment = new FragmenMarcarvisita();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DESCRIBABLE_KEY, describable);
        fragment.setArguments(bundle);

        return fragment;
    }

    public FragmenMarcarvisita() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_marcarvisita, container, false);

        mDescribable = (ResponseMarcarPedido) getArguments().getSerializable(DESCRIBABLE_KEY);

        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
